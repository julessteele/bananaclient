package net.julessteele.bananaclient.clickgui

import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.screens.ClickGuiScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import org.joml.Vector2d

class Panel(var x: Double, var y: Double, val width: Double, val height: Double, var category: Category) {

    val components = mutableListOf<Component>()
    var dragging = false
    private var dragOffset = Vector2d(0.0, 0.0)

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        // Panel background
        context.fill(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt(), 0x99000000.toInt())

        // Panel title
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, category.friendlyName, (x + width / 2).toInt(), (y + 4).toInt(), 0xFFFFFFFF.toInt())

        // Render components
        components.forEach { it.render(context, mouseX, mouseY, it.parent) }
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {

        // Start dragging panel if clicked on top bar
        if (mouseX in x..(x + width) && mouseY in y..(y + 12)) {
            dragging = true
            dragOffset.x = mouseX - x
            dragOffset.y = mouseY - y
        }

        components.forEach { it.mouseClicked(mouseX, mouseY, button) }
    }

    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        dragging = false
        components.forEach { it.mouseReleased(mouseX, mouseY, button) }
    }

    fun mouseDragged(mouseX: Double, mouseY: Double, button: Int) {

        if (dragging) {
            x = mouseX - dragOffset.x
            y = mouseY - dragOffset.y

            // Update position of components registered under panel
            var yOffset = ClickGuiScreen().moduleHeightInPanel

            components.forEach {

                it.x = mouseX - dragOffset.x
                it.y = mouseY - dragOffset.y + yOffset

                yOffset += ClickGuiScreen().moduleHeightInPanel
            }
        }

        components.forEach { it.mouseDragged(mouseX, mouseY, button) }
    }
}
