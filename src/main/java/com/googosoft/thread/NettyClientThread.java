package com.googosoft.thread;

import com.googosoft.model.Comet;

/**
 * @author songyan
 * @date 2020年4月23日 下午3:30:57
 * @desc 发送需要发送的指令&&接受需要执行的指令
 */
public class NettyClientThread implements Runnable{

	@Override
	public void run() {
		new Comet().connect();
	}

}
