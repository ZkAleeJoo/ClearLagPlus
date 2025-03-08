package zk.ClearLagPlus.config;

import org.bukkit.configuration.file.FileConfiguration;
import zk.ClearLagPlus.ClearLagPlus;

public class MainConfigManager {

    private CustomConfig configFile;
    private ClearLagPlus plugin;

    private String messageprefix;
    private String message1;
    private String message2;

    private int time;

    private boolean eneablealerts;

    private String reloadmessage;
    private String nopermission;
    private String message30seconds;

    private String consolemessage;
    private String messageversion;


    public MainConfigManager(ClearLagPlus plugin){
        this.plugin = plugin;
        configFile = new CustomConfig("config.yml", null,plugin);
        configFile.registerConfig();
        loadConfig();
    }

    public void loadConfig(){
        FileConfiguration config = configFile.getConfig();
        messageprefix = config.getString("prefix");
        message1 = config.getString("message1");
        message2 = config.getString("message2");
        time = config.getInt("time");
        eneablealerts = config.getBoolean("enable-alerts");
        reloadmessage = config.getString("reload-message");
        nopermission = config.getString("no-permission");
        message30seconds = config.getString("message-30seconds");
        consolemessage = config.getString("no-message-console");
        messageversion = config.getString("message-version");
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }

    public String getMessageprefix() {
        return messageprefix;
    }

    public String getMessage1() {
        return message1;
    }

    public String getMessage2() {
        return message2;
    }

    public int isTime() {
        return time;
    }

    public boolean isEneablealerts() {
        return eneablealerts;
    }

    public String getReloadmessage() {
        return reloadmessage;
    }

    public String getNopermission() {
        return nopermission;
    }

    public String getMessage30seconds() {
        return message30seconds;
    }

    public String getConsolemessage() {
        return consolemessage;
    }

    public String getMessageversion() {
        return messageversion;
    }
}
