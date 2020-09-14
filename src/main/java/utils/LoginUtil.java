package utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import protocol.Attributes;

public class LoginUtil {
    public static void maskAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> login = channel.attr(Attributes.LOGIN);

        return login.get() != null;
    }

}
