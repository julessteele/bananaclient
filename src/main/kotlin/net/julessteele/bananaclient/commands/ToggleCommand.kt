package net.julessteele.bananaclient.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.modules.module.ModuleManager
import net.julessteele.bananaclient.util.ChatUtil

class ToggleCommand: Command("toggle", "Toggles a module.", ".toggle <module>") {

    override fun execute(args: List<String>) {

        if (args.isEmpty()) {
            ChatUtil.sendUseCaseClientMsg(usage)
            return
        }

        val module = ModuleManager.getModule(args.first())

        if (module != null) {
            module.toggle()
            ChatUtil.sendClientMsg("Toggled ${module.name} ${if (module.enabled) "§aON" else "§cOFF"}")
        } else {
            ChatUtil.sendClientMsg("Module provided is invalid, please try again.")
        }
    }
}