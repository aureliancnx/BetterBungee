package fr.aureliancnx.betterbungee.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ProxyUtils {

    /**
     * Disable security manager of BungeeCord
     */
    public static void disableSecurityManager() {
        // Since vanilla Bungeecord has a security manager now,
        // we can't create unsafe threads
        // @see https://github.com/SpigotMC/BungeeCord/commit/5d1b660e328c334acc8e7920117d08ed992e2d2b
        try {
            final Method method = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
            method.setAccessible(true);
            final Field[] allFields = (Field[]) method.invoke(System.class, false);
            for (final Field field : allFields) {
                if (field.getName().equals("security")) {
                    field.setAccessible(true);
                    field.set(null, null);
                }
            }
        }catch(Exception error) {
            error.printStackTrace();
        }
    }

}
