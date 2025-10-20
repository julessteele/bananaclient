package net.julessteele.bananaclient.clickgui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

class Slider(x: Int, y: Int, width: Int, height: Int, val label: Text, var min: Float, var max: Float, var value: Float): Component(x, y, width, height) {

    var dragging = false

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        super.render(context, mouseX, mouseY)
        // Draw background
        context.fill(x, y, x + width, y + height, 0xFF444444.toInt())
        // Draw slider fill
        val fillWidth = ((value - min) / (max - min) * width).toInt()
        context.fill(x, y, x + fillWidth, y + height, 0xFF00AAFF.toInt())
        // Draw label + value
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of("${label.string}: ${"%.2f".format(value)}"), x + 2, y + 2, 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        if (hovered) dragging = true
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, button: Int) {
        dragging = false
    }

    override fun mouseDragged(mouseX: Int, mouseY: Int, button: Int) {
        if (dragging) {
            val percent = ((mouseX - x).coerceIn(0, width)).toFloat() / width
            value = min + (max - min) * percent
        }
    }
}