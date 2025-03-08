package zk.ClearLagPlus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import zk.ClearLagPlus.ClearLagPlus;
import zk.ClearLagPlus.utils.MessageUtils;

public class MainCommand implements CommandExecutor {

    private ClearLagPlus plugin;

    public MainCommand(ClearLagPlus plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if (!(sender instanceof Player)) {
            //Consola
            sender.sendMessage(MessageUtils.getColoredMessage(ClearLagPlus.prefix+plugin.getMainConfigManager().getConsolemessage()));
            return true;

        }

        //Jugador
        Player player = (Player) sender;

        //Comando ClearLag
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                    //-clearlagplus reload
                subCommandReload(sender);
            }else if(args[0].equalsIgnoreCase("get")){
                    //-clearlagplus get versión
                subCommandGet(sender, args);
            }else {
                help(sender);
            }
        } else {
            //-clearlagplus
            help(sender);
        }

        return false;

    }

    public void help (CommandSender sender){
        sender.sendMessage(MessageUtils.getColoredMessage("&7► &eClearLagPlus &7Help"));
        sender.sendMessage(MessageUtils.getColoredMessage("&f&l┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7- /clearlagplus reload"));
        sender.sendMessage(MessageUtils.getColoredMessage("&7- /clearlagplus get version"));
        sender.sendMessage(MessageUtils.getColoredMessage("&f&l┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"));
    }

    public void subCommandGet(CommandSender sender, String[] args){
        if(args.length == 1){
            //-clearlagplus get
            sender.sendMessage(MessageUtils.getColoredMessage(ClearLagPlus.prefix+plugin.getMainConfigManager().getMessageversion()));
            return;
        }
        if(args[1].equalsIgnoreCase("version")){
            //-clearlagplus get version
            sender.sendMessage(MessageUtils.getColoredMessage(ClearLagPlus.prefix+"&aLa versión del plugin es "+plugin.getDescription().getVersion()));
        }else{
            sender.sendMessage(MessageUtils.getColoredMessage(ClearLagPlus.prefix+plugin.getMainConfigManager().getMessageversion()));
        }
    }

    public void subCommandReload(CommandSender sender){
        if(!sender.hasPermission("clearlag.reload")){
            sender.sendMessage(MessageUtils.getColoredMessage(ClearLagPlus.prefix+plugin.getMainConfigManager().getNopermission()));
            return;
        }
        plugin.getMainConfigManager().reloadConfig();
        sender.sendMessage(MessageUtils.getColoredMessage(ClearLagPlus.prefix+plugin.getMainConfigManager().getReloadmessage()));
        plugin.startClearLagTask();
    }



}
