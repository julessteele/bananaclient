package net.julessteele.bananaclient.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.module.ModuleManager
import net.julessteele.bananaclient.util.CommandChatUtil

class ToggleCommand: Command("toggle", "Toggles a module.", ".toggle <module>") {

    override fun execute(args: List<String>) {

        if (args.isEmpty()) {
            CommandChatUtil.sendUseCaseClientMsg(usage)
            return
        }

        val module = ModuleManager.getModule(args.first())

        if (module != null) {
            module.toggle()
            CommandChatUtil.sendClientMsg("Toggled ${module.name} ${if (module.enabled) "§aON" else "§cOFF"}")
        } else {
            CommandChatUtil.sendClientMsg("Module provided is invalid, please try again.")
        }
    }
}