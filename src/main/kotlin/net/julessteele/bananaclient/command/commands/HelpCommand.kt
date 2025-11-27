package net.julessteele.bananaclient.command.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.command.CommandManager
import net.julessteele.bananaclient.util.ChatUtil
import net.julessteele.bananaclient.util.ChatUtil.sendClientMsg
import net.julessteele.bananaclient.util.ChatUtil.sendUseCaseClientMsg

class HelpCommand: Command("help", "Shows all commands or command usage.", ".help | .help <command>") {

    override fun execute(args: List<String>) {

        if (args.isEmpty()) {
            CommandManager.getCommands().forEach {
                sendClientMsg("§e.${it.name}§7 - ${it.description}")
            }
        } else {
            val cmdName = args[0]
            val cmd: Command? = if (CommandManager.getCommands(cmdName).isEmpty()) { null } else { CommandManager.getCommands(cmdName).first() }

            if (cmd != null) {
                sendUseCaseClientMsg(cmd.usage)
            } else {
                sendClientMsg("There is no module titled \"$cmdName\". Please see .help for a list of all commands and their functions.")
            }
        }
    }
}