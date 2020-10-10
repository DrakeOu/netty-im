package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import utils.LoginUtil;
import utils.SessionUtil;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!SessionUtil.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            ctx.pipeline().remove(this);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(SessionUtil.hasLogin(ctx.channel())){
            System.out.println("已登陆，移除验证");
        }else{
            System.out.println("未登录，关闭连接");
        }
    }
}
