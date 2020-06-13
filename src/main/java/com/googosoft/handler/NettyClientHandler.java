package com.googosoft.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googosoft.info.LOG;
import com.googosoft.model.Comet;
import com.googosoft.model.IComet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author songyan
 * @date 2020年4月22日 下午9:50:31
 * @desc netty事件处理
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	IComet comet = new Comet();

	/**
	 * 连接成功
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		logger.info(LOG.CHANNEL_ACTIVE);
	}

	/**
	 * 服务端终止连接
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		logger.info(LOG.CHANNEL_INACTIVE);
		comet.reconnect(ctx);
	}

	/**
	 * 接收到master回写的消息
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		logger.info(LOG.CHANNEL_READ, msg);
		if (msg != null) {
			/*try {
				CommandUtil.putRunCommand(msg.toString());
			} catch (Exception e) {
				System.out.println(msg);
			}*/
			
		}
		comet.reconnect(ctx);
	}

	/**
	 * 发生异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error(LOG.EXCEPTION, cause.getMessage());
		cause.printStackTrace();
		comet.reconnect(ctx);
	}

}
