package thefirstchange.example.com.communicationtext.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import thefirstchange.example.com.communicationtext.netty.protocol.Packet;
import thefirstchange.example.com.communicationtext.netty.protocol.PacketCodec;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodec.INSTANCE.encode(out, packet);
    }
}
