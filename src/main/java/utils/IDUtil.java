package utils;

import java.util.UUID;

public class IDUtil {
    public static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
