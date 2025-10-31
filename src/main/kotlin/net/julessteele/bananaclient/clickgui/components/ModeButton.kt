package net.julessteele.bananaclient.clickgui.components

import net.julessteele.bananaclient.clickgui.Component
import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.settings.setting.ModeSetting
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Colors

class ModeButton(x: Double, y: Double, width: Double, height: Double, parent: Panel, val label: String, var setting: ModeSetting): Component(x, y, width, height, parent) {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, parent: Panel) {

        super.render(context, mouseX, mouseY, parent)

        val color = if (hovered) Colors.DARK_GRAY else Colors.GRAY

        // Fill in background rect
        context.fill(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt(), color)

        // Draw text
        context.drawCenteredTextWithShadow(client.textRenderer, Text.of(label + ": ${setting.value}"), (x + width / 2).toInt(), (y + height / 2).toInt(), 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovered) {
            nextMode()
        }
    }

    private fun nextMode() {
        var i = 0
        // loop through modes and check if each one is current mode, get "i" index of current mode
        for (mode in setting.modeList) {
            if (i > setting.modeList.size) i = 0
            if (mode.equals(setting.value as String?, ignoreCase = true)) break
            i++
        }
        // Set the mode to the NEXT "i" location
        setting.value = setting.modeList[++i]
    }
}