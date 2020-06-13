package com.googosoft.model;

import com.googosoft.thread.NettyClientThread;

/**
 * @author songyan
 * @date 2020年4月23日 下午8:11:04
 * @desc 线程调度器
 */
public class Dispacher {

	/**
	 * 开启“指令处理”线程
	 */
	//private static RunCommandThread runCommandThread = new RunCommandThread();
	
	/**
	 * 开启“产生反馈指令”线程
	 */
	//private static SendCommandThread sendCommandThread = new SendCommandThread();
	
	/**
	 * 开启“netty连接”线程
	 */
	private static NettyClientThread nettyClientThread = new NettyClientThread();
	
	/**
	 * 调度执行
	 */
	public static void run() {
		new Thread(nettyClientThread).start();
		//new Thread(runCommandThread).start();
    	//new Thread(sendCommandThread).start();
	}
}
