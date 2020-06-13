package com.googosoft.model;

import java.io.IOException;
import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googosoft.handler.NettyClientHandler;
import com.googosoft.info.LOG;
import com.googosoft.info.NETTY_CLIENT_CONF;
import com.googosoft.initializer.NettyClientChannelInitializer;
import com.googosoft.util.DateUtils;
import com.googosoft.util.SpringUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author songyan
 * @date 2020年4月24日 上午10:42:28
 * @desc Comet模型
 */
public class Comet implements IComet {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private String masterIp;
	private int masterPort;
	private ChannelFuture future;
	private EventLoopGroup group;
	private Bootstrap b;
	int count = 0;

	public Comet() {
		init();
	}

	@Override
	public void init() {
		// 在配置文件中的读取配置信息
		masterIp = SpringUtil.getProperty(NETTY_CLIENT_CONF.MASTER_IP, NETTY_CLIENT_CONF.MASTER_IP_DEFAULT);
		masterPort = Integer
				.parseInt(SpringUtil.getProperty(NETTY_CLIENT_CONF.MASTER_PORT, NETTY_CLIENT_CONF.MASTER_PORT_DEFAULT));
	}

	@Override
	public void sendMsg(Object msg) {
		logger.info(LOG.SEND_MSG, msg);
		try {
			future.channel().writeAndFlush(msg);
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connect() {
		try {
			createConnect();
		} catch (ConnectException e) {
			try {
				// 服务端无法连接时等待1秒发起重连
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			connect();
		}
	}

	@Override
	public void createConnect() throws ConnectException {
		logger.info(LOG.CONNECTION, masterIp, masterPort, DateUtils.getDateTime());
		if (group == null || group.isTerminated()) {
			group = new NioEventLoopGroup();
			b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new NettyClientChannelInitializer() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast("decoder", new StringDecoder());
							p.addLast("encoder", new StringEncoder());
							p.addLast(new NettyClientHandler());
						}
					});
		}
		try {
			if (future == null) {
				future = b.connect(masterIp, masterPort).sync();
				// 发送需要需要发送的反馈指令
				/*
				 * List<MessageBeans> messageBeansList =
				 * CommandUtil.getAllSendCommand(); if (messageBeansList !=
				 * null) { sendMsg(messageBeansList); }
				 */
			}
			sendMsg("agent message" + count++);
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error(LOG.MASTER_UNCONNECTION);
		} finally {
			group.shutdownGracefully();
		}
	}

	@Override
	public void reconnect(ChannelHandlerContext ctx) {
		disconnect(ctx);
		//connect();
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx) {
		// 关闭消息通道
		ctx.channel().close();
		try {
			if (future != null)
				future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (group != null)
				group.shutdownGracefully();
		}
	}
}
