package com.googosoft.encoder;

import com.googosoft.info.CHARSET;
import com.googosoft.model.Header;
import com.googosoft.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author songyan
 * @date 2020年4月24日 下午2:39:30
 * @desc 消息编码器
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
	        Header header = msg.getHeader();
	        out.writeByte(MessageDecoder.PACKAGE_TAG);
	        out.writeByte(header.getEncode());
	        out.writeByte(header.getEncrypt());
	        out.writeByte(header.getExtend1());
	        out.writeByte(header.getExtend2());
	        out.writeBytes(header.getSessionid().getBytes());
	        out.writeInt(header.getLength());
	        out.writeInt(header.getCammand());
	        out.writeBytes(msg.getData().getBytes(CHARSET.UTF_8));
	}

}
