package net.julessteele.bananaclient.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.command.CommandManager
import net.julessteele.bananaclient.util.CommandChatUtil

class HelpCommand: Command("help", "Shows all commands or command usage.", ".help | .help <command>") {

    override fun execute(args: List<String>) {

        if (args.isEmpty()) {
            CommandManager.getCommands().forEach {
                CommandChatUtil.sendClientMsg("§e.${it.name}§7 - ${it.description}")
            }
        } else {
            CommandChatUtil.sendUseCaseClientMsg(CommandManager.getCommands(args[0]).first().usage.replaceFirstChar { it.uppercase() })
        }
    }
}