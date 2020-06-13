package com.googosoft.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import com.googosoft.info.CHARSET;
import com.googosoft.model.Header;
import com.googosoft.model.Message;

/**
 * 
 * @author songyan
 * @date 2020年4月24日 下午2:37:38
 * @desc 消息解码器
 */
public class MessageDecoder extends ByteToMessageDecoder {
	public static final int HEAD_LENGHT = 45;
	public static final byte PACKAGE_TAG = 0x01;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		buffer.markReaderIndex();
		if (buffer.readableBytes() < HEAD_LENGHT) {
			throw new CorruptedFrameException();
		}
		byte tag = buffer.readByte();
		if (tag != PACKAGE_TAG) {
			throw new CorruptedFrameException();
		}
		byte encode = buffer.readByte();
		byte encrypt = buffer.readByte();
		byte extend1 = buffer.readByte();
		byte extend2 = buffer.readByte();
		byte sessionByte[] = new byte[32];
		buffer.readBytes(sessionByte);
		String sessionid = new String(sessionByte,CHARSET.UTF_8);
		int length = buffer.readInt();
	    int cammand=buffer.readInt();
		Header header = new Header(tag,encode, encrypt, extend1, extend2, sessionid, length, cammand);
		byte[] data=new byte[length];
		buffer.readBytes(data);
		Message message = new Message(header,new String(data,CHARSET.UTF_8));
		out.add(message);
	}
}
