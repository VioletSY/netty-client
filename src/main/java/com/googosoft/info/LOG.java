package com.googosoft.info;

/**
 * @author songyan
 * @date 2020年4月24日 上午10:02:54
 * @desc 日志
 */
public class LOG {
	
	//通信相关
	public static final String CHANNEL_ACTIVE = "连接创建成功";
	public static final String CHANNEL_INACTIVE = "服务端终止了服务";
	public static final String CHANNEL_READ = "接收到服务端指令:{}";
	public static final String EXCEPTION = "服务端发生异常【{}】";
	
	//comet相关
	public static final String SEND_MSG = "发送消息:{};";
	public static final String CONNECTION = "客户端请求建立连接:{};{};{}";
	public static final String MASTER_UNCONNECTION = "无法连接到master节点";
	
	//其他
	public static final String COMMAND_HANDLE = "处理指令";
	
	
}
