package net.julessteele.bananaclient.screens

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text
import net.minecraft.util.Colors

@Environment(EnvType.CLIENT)
class ClickGuiScreen: Screen(Text.literal("ClickGuiScreen")) {

    override fun init() {

        super.init()

        addDrawableChild(ButtonWidget.builder(Text.literal("Click me!")) { button ->
            println("Button CLICKED!")
        }.dimensions(width / 2 - 50, height / 2 - 10, 100, 20)
            .build())
    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {

        context?.drawCenteredTextWithShadow(textRenderer, Text.literal("Hello Kotlin GUI!"), width/2, height/2-50, Colors.WHITE)

        super.render(context, mouseX, mouseY, deltaTicks)
    }

    override fun shouldPause(): Boolean {
        return false
    }
}

fun openClickGuiScreen() {
    MinecraftClient.getInstance().setScreen(ClickGuiScreen())
}