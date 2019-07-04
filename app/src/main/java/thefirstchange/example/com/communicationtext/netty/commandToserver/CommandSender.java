package thefirstchange.example.com.communicationtext.netty.commandToserver;

import io.netty.channel.Channel;

public interface CommandSender {

    public void execute(Channel channel);

}
