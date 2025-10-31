package net.julessteele.bananaclient.commands

import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.modules.module.ModuleManager
import net.julessteele.bananaclient.util.CommandChatUtil.sendClientMsg
import net.julessteele.bananaclient.util.CommandChatUtil.sendUseCaseClientMsg

class ListModulesCommand: Command("listmodules", "Lists all modules available in the client and their descriptions.", ".listmodules") {

    override fun execute(args: List<String>) {

        if (args.isNotEmpty()) {
            sendUseCaseClientMsg(usage)
        } else {
            ModuleManager.getModules().forEach { sendClientMsg("${it.name} - §7${it.description}") }
        }
    }
}