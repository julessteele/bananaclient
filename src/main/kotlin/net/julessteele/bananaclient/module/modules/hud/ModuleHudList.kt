package net.julessteele.bananaclient.module.modules.hud

import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.module.Module
import net.julessteele.bananaclient.module.ModuleManager
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Colors

class ModuleHudList: Module("ModuleHudList", "Displays a list of all active modules on the screen.", Category.HUD) {

    override fun onRenderHUD(context: DrawContext) {

        val textRenderer = client.textRenderer ?: return
        val modules = ModuleManager.getEnabledModules().sortedByDescending { textRenderer.getWidth(it.name) }

        var yOffset = 5

        for (module in modules) {
            if (module is ClickGui) return

            context.drawTextWithShadow(textRenderer, Text.literal(module.name), 5, yOffset, Colors.WHITE)

            yOffset += textRenderer.fontHeight + 3
        }
    }
}
