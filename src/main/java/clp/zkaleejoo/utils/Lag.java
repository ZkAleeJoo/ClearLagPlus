package clp.zkaleejoo.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Method;

public class Lag {

    public static double getTPS() {
        try {
            Object server = Bukkit.getServer();
            
            Method getTPSMethod = server.getClass().getMethod("getTPS");
            
            double[] tps = (double[]) getTPSMethod.invoke(server);
            
            return Math.min(20.0, tps[0]);
            
        } catch (Exception e) {
            return 20.0;
        }
    }
}