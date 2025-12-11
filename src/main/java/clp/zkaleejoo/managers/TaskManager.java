package clp.zkaleejoo.managers;

import clp.zkaleejoo.ClearLagPlus;
import clp.zkaleejoo.utils.EntityClearer;
import clp.zkaleejoo.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import clp.zkaleejoo.utils.Lag;

public class TaskManager {

    private final ClearLagPlus plugin;
    private final EntityClearer entityClearer;
    private int taskId = -1;
    private int warningTaskId = -1;

    public TaskManager(ClearLagPlus plugin) {
        this.plugin = plugin;
        this.entityClearer = new EntityClearer(plugin);
    }

    public void startTasks() {
        // Tarea principal de limpieza
        if (plugin.getMainConfigManager().getAutoClearEnabled()) {
            taskId = new BukkitRunnable() {
                @Override
                public void run() {
                    double currentTps = Lag.getTPS();
                    double minTps = plugin.getMainConfigManager().getAutoClearMinTps();

                    if (currentTps >= minTps) {
                        return; 
                    }

                    // Si el TPS es bajo, procedemos a limpiar
                    entityClearer.clearEntities(false, null);
                }
            }.runTaskTimer(plugin,
                    plugin.getMainConfigManager().getAutoClearInterval() * 20L,
                    plugin.getMainConfigManager().getAutoClearInterval() * 20L
            ).getTaskId();
        }

        // Tarea de advertencia
        if (plugin.getMainConfigManager().getWarningEnabled()) {
            int warningTime = plugin.getMainConfigManager().getWarningSecondsBefore();
            long warningDelay = (plugin.getMainConfigManager().getAutoClearInterval() - warningTime) * 20L;

            warningTaskId = new BukkitRunnable() {
                @Override
                public void run() {
                    String message = plugin.getMainConfigManager().getWarningMessage()
                            .replace("{time}", String.valueOf(warningTime));
                    Bukkit.broadcastMessage(MessageUtils.getColoredMessage(
                            plugin.getMainConfigManager().getPrefix() + message));
                }
            }.runTaskTimer(plugin, warningDelay,
                    plugin.getMainConfigManager().getAutoClearInterval() * 20L
            ).getTaskId();
        }
    }

    public void stopTasks() {
        if (taskId != -1) Bukkit.getScheduler().cancelTask(taskId);
        if (warningTaskId != -1) Bukkit.getScheduler().cancelTask(warningTaskId);
    }

    public void reloadTasks() {
        stopTasks();
        startTasks();
    }
}