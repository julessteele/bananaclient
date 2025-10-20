package net.julessteele.bananaclient.clickgui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext

abstract class Component(var x: Int, var y: Int, var width: Int, var height: Int) {

    protected val client: MinecraftClient = MinecraftClient.getInstance()

    var hovered = false

    open fun render(context: DrawContext, mouseX: Int, mouseY: Int) {
        hovered = mouseX in x..(x + width) && mouseY in y..(y + height)
    }

    open fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) { }
    open fun mouseReleased(mouseX: Int, mouseY: Int, button: Int) { }
    open fun mouseDragged(mouseX: Int, mouseY: Int, button: Int) { }
}