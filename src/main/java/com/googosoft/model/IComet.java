package com.googosoft.model;

import java.net.ConnectException;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author songyan
 * @date 2020年4月24日 上午10:43:01
 * @desc Comet模型接口类
 */
public interface IComet {
	
	/**
	 * 初始化
	 */
	void init();
	
	/**
	 * 发起连接请求
	 */
	void connect() ;
	
	/**
	 * 连接master
	 * @throws ConnectException master无法连接
	 */
	void createConnect() throws ConnectException;

	/**
	 * 发送消息
	 */
	void sendMsg(Object msg);
	
	/**
	 * 重连
	 */
	void reconnect(ChannelHandlerContext ctx);
	
	/**
	 * 断开连接
	 */
	void disconnect(ChannelHandlerContext ctx);
}
