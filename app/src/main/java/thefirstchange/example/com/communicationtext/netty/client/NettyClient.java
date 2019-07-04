package thefirstchange.example.com.communicationtext.netty.client;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import thefirstchange.example.com.communicationtext.config.Config;
import thefirstchange.example.com.communicationtext.netty.NettyListener;
import thefirstchange.example.com.communicationtext.netty.client.handler.HeartBeatResponseHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.HeartBeatTimerHandler;
import thefirstchange.example.com.communicationtext.netty.client.handler.IMHandler;
import thefirstchange.example.com.communicationtext.netty.codec.PacketCodecHandler;
import thefirstchange.example.com.communicationtext.netty.codec.Spliter;
import thefirstchange.example.com.communicationtext.netty.handler.IMIdleStateHandler;
import thefirstchange.example.com.communicationtext.service.NettyService;

public class NettyClient {
    private static  int MAX_RETRY = Integer.MAX_VALUE;

    private NettyListener listener = null;
    private NettyService nettyService = null;


    private boolean isConnected = false;   //是否已经连接
    private boolean isConnecting = false;   //是否正在尝试与服务器连接中

    private static  NettyClient nettyClient = null;
    private static  NioEventLoopGroup workerGroup;
    private static Bootstrap bootstrap;
    public static Channel channel;

    public boolean isInitOK(){
        if(listener!=null&&nettyService!=null){
            return true;
        }
        return false;
    }

    public static NettyClient getInstance(){
        if(nettyClient==null){
            nettyClient = new NettyClient();
        }
        return nettyClient;
    }

    private NettyClient(){

        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {

                        // 空闲检测
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketCodecHandler());

                        ch.pipeline().addLast(new HeartBeatResponseHandler());
                        ch.pipeline().addLast(new IMHandler());
                        // 心跳定时器
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });

    }

    public static void main(String[] args){
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect();
    }

    public void connect() {

        if(isConnected||isConnecting){
            return;
        }

        connect(bootstrap, Config.ServerIP, Config.ServerPort, MAX_RETRY);
    }

    private void connect(Bootstrap bootstrap, String host, int port, int retry) {

        if(isConnected||isConnecting){
            return;
        }
        isConnecting = true;
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                isConnecting = false;
                //System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                channel = ((ChannelFuture) future).channel();
               // startConsoleThread(channel);
                setConnectStatus(true);
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_SUCCESS);


            } else if (retry == 0) {
                isConnecting = false;
              //  System.err.println("重试次数已用完，放弃连接！");
                setConnectStatus(false);
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
            } else {
                isConnecting = false;
                setConnectStatus(false);
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                if(order>=5){
                    delay=15;
                }
              //  System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    public void disconnect() {
        workerGroup.shutdownGracefully();
        setConnectStatus(false);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
    }

    public boolean getConnectStatus(){
        return isConnected;
    }
    public void setConnectStatus(boolean status){
        this.isConnected = status;
    }

    public void setReconnectNum(int reconnectNum) {
        this.MAX_RETRY = reconnectNum;
    }

    public void setListener(NettyListener listener) {
        this.listener = listener;
    }

    public void setService(NettyService nettyService){
        this.nettyService = nettyService;
    }

    public NettyListener getListener() {
        return listener;
    }

    public NettyService getNettyService() {
        return nettyService;
    }

    public Channel getChannel() {
        return channel;
    }

    public boolean isConnecting() {
        return isConnecting;
    }

    public void setConnecting(boolean connecting) {
        isConnecting = connecting;
    }
}
