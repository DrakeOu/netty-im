package client;

import client.console.ConsoleCommandManager;
import client.console.LoginConsoleCommand;
import client.handler.*;
import codec.PacketCodecHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import codec.PacketDecoder;
import codec.PacketEncoder;
import codec.Spliter;
import protocol.request.LoginRequestPacket;
import protocol.request.MessageRequestPacket;
import utils.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;


    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                //添加拆包粘包处理器
                                .addLast(new Spliter())
                                .addLast(PacketCodecHandler.INSTANCE)
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new LogoutResponseHandler())
                                .addLast(new CreateGroupResponseHandler())
                                .addLast(new JoinGroupResponseHandler())
                                .addLast(new QuitGroupResponseHandler())
                                .addLast(new GroupMsgResponseHandler());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess()){
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
                System.out.println("连接成功!");
            }else if(retry == 0){
                System.err.println("重试次数已用完，放弃连接!");
            }else{
                int order = MAX_RETRY - retry + 1;
                //指数退避重连时间
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败, 第" + order + "次重连...");
                bootstrap.config().group().schedule(()->connect(bootstrap, host, port, retry-1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel){
        Scanner scanner = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        new Thread(()->{
            while(!Thread.interrupted()){
                if(!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(scanner, channel);
                }else{
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }

}
