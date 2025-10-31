package net.julessteele.bananaclient.clickgui

import net.julessteele.bananaclient.modules.module.Category
import net.julessteele.bananaclient.screens.ClickGuiScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import org.joml.Vector2d

class Panel(var pos: Vector2d, val width: Double, val height: Double, var category: Category) {

    val components = mutableListOf<Component>()
    var dragging = false
    private var dragOffset = Vector2d(0.0, 0.0)

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        // Panel background
        context.fill(pos.x.toInt(), pos.y.toInt(), (pos.x + width).toInt(), (pos.y + height).toInt(), 0x99000000.toInt())

        // Panel title
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, category.friendlyName, (pos.x + width / 2).toInt(), /* 4 is half of font height (9 font height) */ (pos.y + 4).toInt(), 0xFFFFFFFF.toInt())

        // Render components
        components.forEach { it.render(context, mouseX, mouseY, it.parent) }
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {

        // Start dragging panel if clicked on top bar
        if (mouseX in pos.x..(pos.x + width) && mouseY in pos.y..(pos.y + 12) /* 12 for height of top draggable bar */ && button == 0) {
            dragging = true
            dragOffset.x = mouseX - pos.x
            dragOffset.y = mouseY - pos.y
        }

        components.forEach { it.mouseClicked(mouseX, mouseY, button) }
    }

    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        dragging = false
        components.forEach { it.mouseReleased(mouseX, mouseY, button) }
    }

    fun mouseDragged(mouseX: Double, mouseY: Double, button: Int) {

        if (dragging) {
            pos.x = mouseX - dragOffset.x
            pos.y = mouseY - dragOffset.y

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
