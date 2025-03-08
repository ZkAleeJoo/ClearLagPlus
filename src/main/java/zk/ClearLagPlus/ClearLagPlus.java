package zk.ClearLagPlus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import zk.ClearLagPlus.commands.MainCommand;
import zk.ClearLagPlus.config.MainConfigManager;
import zk.ClearLagPlus.listeners.PlayerListener;

public class ClearLagPlus extends JavaPlugin {

    public static String prefix = "&8[&eClearLagPlus&8] ";


    private BukkitRunnable clearLagTask;

    private String version = getDescription().getVersion();
    private MainConfigManager mainConfigManager;

    public ClearLagPlus() {}

    @Override
    public void onEnable() {

        registerCommands();
        registerEvents();
        mainConfigManager = new MainConfigManager(this);

        startClearLagTask();

        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &8=============================="));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &fClearLag Plus"));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &fHecho por ZkAleeJoo"));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &8=============================="));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &fEl plugin cargo correctamente, Versión: "+version));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &fGracias por usar mi plugin"));

    }


    //SERVER SE APAGA
    @Override
    public void onDisable() {

        if (clearLagTask != null) {
            clearLagTask.cancel();
        }

        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &fEl plugin se desactivo correctamente, Versión: "+version));
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8[&eClearLagPlus&8] &fGracias por usar mi plugin!"));
    }


    // ----------------------------------------------------------------------
    //   REGISTROS DE COMANDOS Y EVENTOS
    // ----------------------------------------------------------------------
    public void registerCommands(){
        this.getCommand("clearlagplus").setExecutor(new MainCommand(this));
    }

    public void registerEvents(){
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }


    // ----------------------------------------------------------------------
    //   TAREA DE CLEAR LAG
    // ----------------------------------------------------------------------
    public void startClearLagTask() {
        if (clearLagTask != null) {
            clearLagTask.cancel();
        }

        clearLagTask = new BukkitRunnable() {
            int ticksRemaining = getMainConfigManager().isTime() * 20;

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    return;
                }

                ticksRemaining -= 20;

                if (ticksRemaining == 30 * 20 && getMainConfigManager().isEneablealerts()) {
                    broadcastToPlayers(getMainConfigManager().getMessageprefix() + " " + getMainConfigManager().getMessage30seconds());
                }

                if (ticksRemaining <= 0) {
                    int removed = 0;
                    for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
                        if (entity instanceof Item) {
                            entity.remove();
                            removed++;
                        }
                    }

                    broadcastToPlayers(getMainConfigManager().getMessageprefix() + " " + getMainConfigManager().getMessage1() + " &c" + removed + " " + getMainConfigManager().getMessage2());
                    ticksRemaining = getMainConfigManager().isTime() * 20;
                }
            }
        };

        clearLagTask.runTaskTimer(this, 0L, 20L);
    }

    private void broadcastToPlayers(String message) {
        String colored = ChatColor.translateAlternateColorCodes('&', message);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(colored);
        }
    }

    public MainConfigManager getMainConfigManager() {
        return mainConfigManager;
    }
}
