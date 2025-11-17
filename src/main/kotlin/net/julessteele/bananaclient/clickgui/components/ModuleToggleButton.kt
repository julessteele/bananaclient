package net.julessteele.bananaclient.clickgui.components

import net.julessteele.bananaclient.clickgui.Component
import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.modules.module.Module
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Colors

class ModuleToggleButton(x: Double, y: Double, width: Double, height: Double, parent: Panel, val module: Module): Component(x, y, width, height, parent) {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        super.render(context, mouseX, mouseY)

        val color = if (module.enabled) Colors.GREEN + 40 else if (hovered) 0xFF777777.toInt() else 0xFFAAAAAA.toInt()

        // Draw background
        context.fill(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt(), color)

        // Draw text
        context.drawCenteredTextWithShadow(client.textRenderer, module.name, (x + width/2).toInt(), (y + height / 2 - 4).toInt(), 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovered) {
            if (button == 0)
                module.toggle()
            if (button == 1)
                module.expanded = !module.expanded
        }

        module.children.forEach { it.mouseClicked(mouseX, mouseY, button) }
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        module.children.forEach { it.mouseReleased(mouseX, mouseY, button) }
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int) {
        module.children.forEach { it.mouseDragged(mouseX, mouseY, button) }
    }

    override fun mouseMoved(mouseX: Double, mouseY: Double) {
        module.children.forEach { it.mouseMoved(mouseX, mouseY) }
    }
}