package net.julessteele.bananaclient.clickgui.components.setting

import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.clickgui.SettingComponent
import net.julessteele.bananaclient.modules.module.Module
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text

class Slider(x: Double, y: Double, width: Double, height: Double, val label: Text, var min: Double, var max: Double, var value: Double, val module: Module, parent: Panel): SettingComponent(x, y, width, height, parent) {

    var dragging = false

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        super.render(context, mouseX, mouseY)

        // Draw background
        context.fill(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt(), 0xFF444444.toInt())

        // Draw slider fill
        val fillWidth = ((value - min) / (max - min) * width).toInt()
        context.fill(x.toInt(), y.toInt(), (x + fillWidth).toInt(), (y + height).toInt(), 0xFF00AAFF.toInt())

        // Draw label + value
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.of("${label.string}: ${"%.2f".format(value)}"), (x + 2).toInt(), (y + 3).toInt(), 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovered) dragging = true
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        dragging = false
        module.findSetting(label.string)?.value = value
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int) {

        if (dragging) {
            val percent = ((mouseX - x).coerceIn(0.0, width)) / width
            value = min + (max - min) * percent
        }
    }
}