package thefirstchange.example.com.communicationtext.netty.commandToserver.sonCommandSender;

import io.netty.channel.Channel;
import thefirstchange.example.com.communicationtext.netty.commandToserver.CommandSender;
import thefirstchange.example.com.communicationtext.netty.protocol.request.otherRequest.CreateCorprationRequestPacket;

/*
创建社团
 */
public class CreateCorprationCommandSender implements CommandSender {

    static CreateCorprationCommandSender sender ;
    public static CreateCorprationCommandSender getInsatnce(){
        if(sender==null){
            sender = new CreateCorprationCommandSender();
        }
        return sender;
    }

    private CreateCorprationCommandSender(){
    }

    CreateCorprationRequestPacket packet;
    public CreateCorprationCommandSender getPacket(String name,String type,String info ,String corppart,int year,int xueqi){
        packet = new CreateCorprationRequestPacket( name, type, info , corppart, year, xueqi);

        return sender;
    }

    @Override
    public void execute(Channel channel) {
        if(channel==null||!channel.isActive()){
            return;
        }
        channel.writeAndFlush(packet);
    }

}
