package net.julessteele.bananaclient.clickgui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

abstract class Component(var x: Double, var y: Double, var width: Double, var height: Double, var parent: Panel) {

    protected val client: MinecraftClient = MinecraftClient.getInstance()

    var hovered = false

    open fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        hovered = mouseX.toDouble() in x..(x + width) && mouseY.toDouble() in y..(y + height)
    }

    open fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) { }
    open fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) { }
    open fun mouseDragged(mouseX: Double, mouseY: Double, button: Int) { }
    open fun mouseMoved(mouseX: Double, mouseY: Double) { }
}