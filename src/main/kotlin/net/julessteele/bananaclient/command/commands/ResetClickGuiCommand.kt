package net.julessteele.bananaclient.command.commands

import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.clickgui.ClickGuiConfig
import net.julessteele.bananaclient.command.Command
import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.util.ChatUtil

class ResetClickGuiCommand: Command("resetclickgui", "Resets the positions of all panels in the ClickGUI.", ".resetclickgui") {

    override fun execute(args: List<String>) {
        var i = 10.0
        Category.entries.forEach {
            i += 110.0
            ClickGuiConfig.setPosition(it, i, 10.0)
            Banana.logger.info("Reset position of panel ${it.friendlyName}.")
        }
        ChatUtil.sendClientMsg("Reset ClickGUI panel positions.")
    }
}