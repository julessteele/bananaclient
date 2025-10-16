package net.julessteele.bananaclient.modules.hud

import net.julessteele.bananaclient.module.Module
import net.julessteele.bananaclient.module.ModuleManager
import net.minecraft.client.gui.DrawContext

class ModuleHudList: Module("ModuleHudList", "Displays a list of all active modules on the screen.", Category.HUD) {

    override fun onRenderHUD(context: DrawContext) {

        val textRenderer = client.textRenderer
        var modules = ModuleManager.getEnabledModules()

        modules = modules.sortedByDescending { textRenderer.getWidth(it.name) }

        var yOffset = 5

        for (module in modules) {
            context.drawTextWithShadow(textRenderer, module.name, 5, yOffset, 0xFFFFFF)
            yOffset += textRenderer.fontHeight + 3
        }
    }
}