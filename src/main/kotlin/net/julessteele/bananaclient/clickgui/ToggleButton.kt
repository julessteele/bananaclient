package net.julessteele.bananaclient.clickgui

import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

class ToggleButton(x: Int, y: Int, width: Int, height: Int, val label: Text, var enabled: Boolean): Component(x, y, width, height) {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        super.render(context, mouseX, mouseY)

        val color = if (enabled) 0xFF00FF00.toInt() else if (hovered) 0xFFAAAAAA.toInt() else 0xFF777777.toInt()
        context.fill(x, y, x + width, y + height, color)
        context.drawCenteredTextWithShadow(client.textRenderer, label, x + width, y + height / 2 - 4, 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (hovered) enabled = !enabled
    }
}