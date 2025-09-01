package clp.zkaleejoo.commands;

import clp.zkaleejoo.ClearLagPlus;
import clp.zkaleejoo.utils.MessageUtils;
import clp.zkaleejoo.utils.EntityClearer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

    private ClearLagPlus plugin;

    public MainCommand(ClearLagPlus plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!(sender instanceof Player)){
            if(args.length >= 1 && args[0].equalsIgnoreCase("reload")){
                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + plugin.getMainConfigManager().getPluginReload()));
                plugin.getMainConfigManager().reloadConfig();
                plugin.getTaskManager().reloadTasks();
                return true;

            } else if(args.length >= 1 && args[0].equalsIgnoreCase("clear")){
                EntityClearer clearer = new EntityClearer(plugin);
                clearer.clearEntities(true, sender);
                return true;

            }

            sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + plugin.getMainConfigManager().getMessageConsole()));
            return true;
        }

        // JUGADOR
        Player player = (Player) sender;

        if (!player.hasPermission("clearlagplus.admin")) {
            player.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + plugin.getMainConfigManager().getNoPermission()));
            return true;
        }

        if(args.length >= 1){
            if(args[0].equalsIgnoreCase("reload")){
                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + plugin.getMainConfigManager().getPluginReload()));
                plugin.getMainConfigManager().reloadConfig();
                plugin.getTaskManager().reloadTasks();

            } else if(args[0].equalsIgnoreCase("get")){
                subcommandGet(sender, args);

            } else if(args[0].equalsIgnoreCase("clear")){
                EntityClearer clearer = new EntityClearer(plugin);
                clearer.clearEntities(true, sender);

            } else {
                help(sender);
            }
        } else {
            help(sender);
        }

        return true;
    }

    public void help(CommandSender sender){
        sender.sendMessage(MessageUtils.getColoredMessage("&8&m----------------------------------------"));
        sender.sendMessage(MessageUtils.getColoredMessage("&a&l✦ &e&lCommands of &aClearLag&e+ &a&l✦"));
        sender.sendMessage(MessageUtils.getColoredMessage("&8"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6➤ &f/clearlagplus &e&lreload"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6➤ &f/clearlagplus &e&lclear"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6➤ &f/clearlagplus &e&lget authorr"));
        sender.sendMessage(MessageUtils.getColoredMessage("&6➤ &f/clearlagplus &e&lget version"));
        sender.sendMessage(MessageUtils.getColoredMessage("&8"));
        sender.sendMessage(MessageUtils.getColoredMessage("&8&m----------------------------------------"));
    }

    public void subcommandGet(CommandSender sender, String[] args){
        if(args.length >= 2){
            if(args[1].equalsIgnoreCase("author")){
                sender.sendMessage(MessageUtils.getColoredMessage("&fPlugin made by &a"+plugin.getDescription().getAuthors()));

            } else if(args[1].equalsIgnoreCase("version")){
                sender.sendMessage(MessageUtils.getColoredMessage("&fCurrent version: &a "+plugin.getDescription().getVersion()));

            } else {
                sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + plugin.getMainConfigManager().getSubcommandInvalid()));

            }
        } else {
            sender.sendMessage(MessageUtils.getColoredMessage(plugin.getMainConfigManager().getPrefix() + plugin.getMainConfigManager().getSubcommandSpecified()));

        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();


        if (!sender.hasPermission("clearlagplus.admin")) {
            return completions;
        }

        if (args.length == 1) {

            completions.add("reload");
            completions.add("clear");
            completions.add("get");

            return filterCompletions(completions, args[0]);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            completions.add("author");
            completions.add("version");

            return filterCompletions(completions, args[1]);
        }

        return completions;
    }

    private List<String> filterCompletions(List<String> completions, String input) {
        List<String> filtered = new ArrayList<>();
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(input.toLowerCase())) {
                filtered.add(completion);
            }
        }
        return filtered;
    }
}