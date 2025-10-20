package net.julessteele.bananaclient.clickgui.components

import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.clickgui.Component
import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.module.Module
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Colors

class ToggleButton(x: Double, y: Double, width: Double, height: Double, parent: Panel, val label: Text, var enabled: Boolean, val module: Module): Component(x, y, width, height, parent) {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, parent: Panel) {

        super.render(context, mouseX, mouseY, parent)

        val color = if (enabled) Colors.GREEN + 40 else if (hovered) 0xFF777777.toInt() else 0xFFAAAAAA.toInt()

        // Draw background
        context.fill(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt(), color)

        // Draw text
        context.drawCenteredTextWithShadow(client.textRenderer, label, (x + width/2).toInt(), (y + height / 2 - 4).toInt(), 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovered) {
            toggle(module)
        }
    }

    fun toggle(module: Module) {

        module.toggle()
        enabled = module.enabled
        Banana.logger.info("CLICKGUI: Toggled ${module.name} to $enabled")
    }
}