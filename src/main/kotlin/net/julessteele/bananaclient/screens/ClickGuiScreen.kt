package net.julessteele.bananaclient.screens

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.clickgui.components.ToggleButton
import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.module.ModuleManager
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Click
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.joml.Vector2i
import kotlin.collections.forEach

@Environment(EnvType.CLIENT)
class ClickGuiScreen: Screen(Text.literal("ClickGUI")) {

    private val panels = mutableListOf<Panel>()

    val moduleHeightInPanel = 14.0

    // TODO Add variables to save position of panels

    init {

        var xOffset = 10.0

        val width = 100.0
        val height = 160.0

        Category.entries.forEach { category ->

            var yOffset = 0.0 + moduleHeightInPanel

            val panel = Panel(xOffset, 100.0, width, height, category)

            ModuleManager.getModules(null, category).forEach { module ->

                panel.components.add(ToggleButton(xOffset, 100.0 + yOffset, 100.0, moduleHeightInPanel, panel, Text.of(module.name), module.enabled, module))

                yOffset += moduleHeightInPanel
            }

            panels.add(panel)

            xOffset += width + 10.0
        }
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {

        panels.forEach { it.render(context, mouseX, mouseY) }

        super.render(context, mouseX, mouseY, delta)
    }

    override fun mouseClicked(click: Click, doubled: Boolean): Boolean {

        panels.forEach { it.mouseClicked(click.x, click.y, click.button()) }

        return super.mouseClicked(click, doubled)
    }

    override fun mouseReleased(click: Click): Boolean {

        panels.forEach { it.mouseReleased(click.x, click.y, click.button()) }

        return super.mouseReleased(click)
    }

    override fun mouseDragged(click: Click, deltaY: Double, offsetY: Double): Boolean {

        panels.forEach { it.mouseDragged(click.x, click.y, click.button()) }

        return super.mouseDragged(click, deltaY, offsetY)
    }

    override fun shouldPause(): Boolean = false
}

fun openClickGuiScreen() = MinecraftClient.getInstance().setScreen(ClickGuiScreen())