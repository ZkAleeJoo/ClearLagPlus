package clp.zkaleejoo;

import clp.zkaleejoo.commands.MainCommand;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.listeners.EntityListener;
import clp.zkaleejoo.managers.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearLagPlus extends JavaPlugin {

    public static String prefix = "&8[&aClearLag&e+&8] ";
    private String version = getDescription().getVersion();
    private MainConfigManager mainConfigManager;
    private TaskManager taskManager;
    

    @Override
    public void onEnable() {
        regsterCommands();
        registerEvents();
        mainConfigManager = new MainConfigManager(this);
        taskManager = new TaskManager(this);
        taskManager.startTasks();

        Bukkit.getConsoleSender().sendMessage("_________ .__                      .____                     __________.__                \n"+
                "\\_   ___ \\|  |   ____ _____ _______|    |   _____     ____   \\______   \\  |  __ __  ______\n"+
                "/    \\  \\/|  | _/ __ \\\\__  \\\\_  __ \\    |   \\__  \\   / ___\\   |     ___/  | |  |  \\/  ___/\n"+
                "\\     \\___|  |_\\  ___/ / __ \\|  | \\/    |___ / __ \\_/ /_/  >  |    |   |  |_|  |  /\\___ \\ \n"+
                " \\______  /____/\\___  >____  /__|  |_______ (____  /\\___  /   |____|   |____/____//____  >\n"+
                "        \\/          \\/     \\/              \\/    \\//_____/                             \\/ \n");
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&fIt was activated correctly in the version &a" + version));
    }

    @Override
    public void onDisable() {
        taskManager.stopTasks();
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&fIt was successfully deactivated"));
    }

    public void regsterCommands() {
        MainCommand mainCommand = new MainCommand(this);
        this.getCommand("clearlagplus").setExecutor(new MainCommand(this));
        this.getCommand("clearlagplus").setTabCompleter(mainCommand);
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }
}