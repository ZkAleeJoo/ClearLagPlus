package clp.zkaleejoo;

import clp.zkaleejoo.commands.MainCommand;
import clp.zkaleejoo.config.MainConfigManager;
import clp.zkaleejoo.listeners.EntityListener;
import clp.zkaleejoo.managers.TaskManager;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearLagPlus extends JavaPlugin {

    public static String prefix = "&8[&aClearLag&e+&8] ";
    private MainConfigManager mainConfigManager;
    private TaskManager taskManager;
    private String latestVersion;

    @Override
    public void onEnable() {
        int pluginId = 28645;
        Metrics metrics = new Metrics(this, pluginId); 
        
        regsterCommands();
        registerEvents();
        mainConfigManager = new MainConfigManager(this);
        checkUpdates();
        taskManager = new TaskManager(this);
        taskManager.startTasks();

        Bukkit.getConsoleSender().sendMessage("_________ .__                      .____                     __________.__                \n"+
                "\\_   ___ \\|  |   ____ _____ _______|    |   _____     ____   \\______   \\  |  __ __  ______\n"+
                "/    \\  \\/|  | _/ __ \\\\__  \\\\_  __ \\    |   \\__  \\   / ___\\   |     ___/  | |  |  \\/  ___/\n"+
                "\\     \\___|  |_\\  ___/ / __ \\|  | \\/    |___ / __ \\_/ /_/  >  |    |   |  |_|  |  /\\___ \\ \n"+
                " \\______  /____/\\___  >____  /__|  |_______ (____  /\\___  /   |____|   |____/____//____  >\n"+
                "        \\/          \\/     \\/              \\/    \\//_____/                             \\/ \n");
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', prefix + "&fIt was activated correctly in the version &a"));
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

    private void checkUpdates() {
    if (!mainConfigManager.isUpdateCheckEnabled()) return;
    new clp.zkaleejoo.utils.UpdateChecker(this, 122239).getVersion(version -> {
        if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
            getLogger().info("You are using the latest version!");
        } else {
            this.latestVersion = version;
            getLogger().warning("A new version is available: " + version);
        }
    });
}

    public String getLatestVersion() { return latestVersion; }
}