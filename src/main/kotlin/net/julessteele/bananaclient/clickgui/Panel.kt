package net.julessteele.bananaclient.clickgui

import net.julessteele.bananaclient.clickgui.components.ModuleToggleButton
import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.screens.ClickGuiScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import org.joml.Vector2d

class Panel(var pos: Vector2d, val width: Double, val height: Double, var category: Category) {

    val components = mutableListOf<Component>()

    var dragging = false

    private val client = MinecraftClient.getInstance()

    private var dragOffset = Vector2d(0.0, 0.0)

    fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        // Left bound
        if (pos.x < 0.0) {
            pos.x = 0.0
            components.forEach {
                it.x = pos.x
                if (it is ModuleToggleButton && it.module.children.isNotEmpty())
                    it.module.children.forEach { c -> c.x = it.x + it.width }
            }
        }

        // TODO FIX THIS BASED ON WHETHER OR NOT THE SETTINGS EXPANDER IS SHOWING ON MODULE
        // Right bound
        if (pos.x + width > context.scaledWindowWidth) {
            pos.x = context.scaledWindowWidth.toDouble() - width
            components.forEach {
                it.x = pos.x
                if (it is ModuleToggleButton && it.module.children.isNotEmpty())
                    it.module.children.forEach { c -> c.x = it.x + it.width }
            }
        }

        // Top bound
        if (pos.y < 0.0) {
            pos.y = 0.0
            components.forEachIndexed { i, comp ->
                comp.y = pos.y + ClickGuiScreen().buttonHeight * (i + 1)
                if (comp is ModuleToggleButton && comp.module.children.isNotEmpty())
                    comp.module.children.forEachIndexed { i1, child -> child.y = comp.y + ClickGuiScreen().buttonHeight * i1 }
            }
        }
        // Bottom bound
        if (pos.y + height > context.scaledWindowHeight) {
            pos.y = context.scaledWindowHeight.toDouble() - height
            components.forEachIndexed { i, comp ->
                comp.y = pos.y + ClickGuiScreen().buttonHeight * (i + 1)
                if (comp is ModuleToggleButton && comp.module.children.isNotEmpty())
                    comp.module.children.forEachIndexed { i1, child -> child.y = comp.y + ClickGuiScreen().buttonHeight * i1 }
            }
        }

        // Panel background
        context.fill(pos.x.toInt(), pos.y.toInt(), (pos.x + width).toInt(), (pos.y + height).toInt(), 0x99000000.toInt())

        // Panel title
        context.drawCenteredTextWithShadow(client.textRenderer, category.friendlyName, (pos.x + width / 2).toInt(), /* 4 is half of font height (9 font height) */ (pos.y + 4).toInt(), 0xFFFFFFFF.toInt())

        // Render components
        components.forEach {

            // Render each component
            it.render(context, mouseX, mouseY)

            // And then check if it has setting buttons we need to render next to it
            if (it is ModuleToggleButton) {
                // Don't render anything if there are no children
                if (it.module.children.isEmpty())
                    return
                else {
                    // We already know now that it's a ModuleToggleButton and thus can check if visible
                    if (it.module.expanded) {
                        // And finally render the child if it can be seen
                        it.module.children.forEach { c -> c.render(context, mouseX, mouseY) }
                    }
                }
            }
        }
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {

        // Start dragging panel if clicked on top bar and using left click
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

        // Defined in ClickGuiScreen and Panel
        val buttonHeight = 14.0

        // Move panel and components if dragging
        if (dragging) {
            // Update position of panel
            pos.x = mouseX - dragOffset.x
            pos.y = mouseY - dragOffset.y

            // Update position of components registered under panel
            var yOffset = buttonHeight

            components.forEach {

                it.x = mouseX - dragOffset.x
                it.y = mouseY - dragOffset.y + yOffset

                yOffset += buttonHeight

                // Move each child within each component if the component is a ModuleToggleButton
                var yOffsetChild = 0.0

                if (it is ModuleToggleButton) {
                    it.module.children.forEach { c ->

                        c.x = mouseX - dragOffset.x + width
                        // Must add yOffset and yOffsetChild since we have to get to the point down on the screen where the module is rendered, and then each time a setting is rendered
                        c.y = mouseY - dragOffset.y + yOffset + yOffsetChild - buttonHeight

                        yOffsetChild += buttonHeight
                    }
                }
            }
        }

        components.forEach { it.mouseDragged(mouseX, mouseY, button) }
    }

    fun mouseMoved(mouseX: Double, mouseY: Double) {
        components.forEach { it.mouseMoved(mouseX, mouseY) }
    }
}
