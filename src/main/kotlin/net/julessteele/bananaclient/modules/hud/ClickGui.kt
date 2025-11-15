package net.julessteele.bananaclient.modules.hud

import net.julessteele.bananaclient.modules.module.Category
import net.julessteele.bananaclient.modules.module.Module
import net.julessteele.bananaclient.screens.openClickGuiScreen
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT

class ClickGui: Module("ClickGUI", "A click GUI to enable modules with.", Category.HUD, GLFW_KEY_RIGHT_SHIFT) {

    override fun onEnable() {
        enabled = false
        openClickGuiScreen()
    }
}