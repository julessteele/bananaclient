package net.julessteele.bananaclient.command

import net.julessteele.bananaclient.util.ChatUtil

object CommandManager {

    private val commands = mutableListOf<Command>()

    var prefix = "."

    fun register(command: Command) = commands.add(command)

    fun handleChatMsg(message: String): Boolean {

        if (ChatUtil.suppressNext) return false

        if (!message.startsWith(prefix)) return false

        val args = message.removePrefix(prefix).split(" ")
        val commandName = args.first().lowercase()
        val command = commands.find { it.name.equals(commandName, ignoreCase = true) }

        if (command != null) {
            command.execute(args.drop(1))
        } else {
            ChatUtil.sendClientMsg("Unknown command: $commandName")
        }

        return true
    }

    fun getCommands(name: String? = null): List<Command> {
        return if (name == null) {
            commands
        } else {
            commands.filter { it.name.equals(name, ignoreCase = true) }
        }
    }
}
