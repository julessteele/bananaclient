package net.julessteele.bananaclient.screens

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.clickgui.ClickGuiConfig
import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.clickgui.components.ModeButton
import net.julessteele.bananaclient.clickgui.components.ModuleToggleButton
import net.julessteele.bananaclient.clickgui.components.SettingToggleButton
import net.julessteele.bananaclient.clickgui.components.Slider
import net.julessteele.bananaclient.modules.module.Category
import net.julessteele.bananaclient.modules.module.ModuleManager
import net.julessteele.bananaclient.settings.SettingType
import net.julessteele.bananaclient.settings.setting.ModeSetting
import net.julessteele.bananaclient.settings.setting.NumberSetting
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Click
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import org.joml.Vector2d
import kotlin.collections.forEach

@Environment(EnvType.CLIENT)
class ClickGuiScreen: Screen(Text.of("ClickGUI")) {

    val moduleHeightInPanel = 14.0

    private val panels = mutableListOf<Panel>()

    init {

        // Load saved ClickGUI config
        ClickGuiConfig.load()

        /********************** Handle All ClickGuiDrawing *****************************/
        val width = 100.0
        val height = 160.0

        var xOffset = 10.0

        // Iterate through categories and draw them on screen
        Category.entries.forEach { category ->

            val savedPos = ClickGuiConfig.getPosition(category.name)
            val x = savedPos?.first ?: xOffset
            val y = savedPos?.second ?: 10.0

            val panel = Panel(Vector2d(x, y), width, height, category)

            var yOffset = moduleHeightInPanel

            // Iterate through the modules in the current category and draw them
            ModuleManager.getModules(category).forEach { module ->

                val moduleToggleButton = ModuleToggleButton(x, y + yOffset, width, moduleHeightInPanel, panel, module)

                panel.components.add(moduleToggleButton)
                yOffset += moduleHeightInPanel

                // Add settings
                if (!module.getSettings().isEmpty()) {
                    module.getSettings().forEach { setting ->
                        when (setting.settingType) {
                            SettingType.BOOLEAN -> {
                                panel.components.add(SettingToggleButton(
                                    x,
                                    y + yOffset,
                                    width,
                                    moduleHeightInPanel,
                                    panel,
                                    module,
                                    Text.of(setting.name),
                                    false))

                                yOffset += moduleHeightInPanel
                            }
                            SettingType.NUMBER -> {
                                setting as NumberSetting
                                panel.components.add(Slider(
                                    x,
                                    y + yOffset,
                                    width,
                                    moduleHeightInPanel,
                                    Text.of(setting.name),
                                    setting.min,
                                    setting.max,
                                    setting.value as Double,
                                    module,
                                    panel))

                                yOffset += moduleHeightInPanel
                            }
                            SettingType.MODE -> {
                                panel.components.add(ModeButton(
                                    x,
                                    y + yOffset,
                                    width,
                                    moduleHeightInPanel,
                                    panel,
                                    setting.name,
                                    module.getSetting(setting.name) as ModeSetting
                                ))

                                yOffset += moduleHeightInPanel
                            }
                            else -> Banana.logger.warn("Invalid setting type ${setting.settingType}")
                        }
                    }
                }
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

        panels.forEach {
            it.mouseReleased(click.x, click.y, click.button())
            // Save positions of each panel after each time the mouse is released
            ClickGuiConfig.setPosition(it.category.name, it.pos.x, it.pos.y)
        }

        return super.mouseReleased(click)
    }

    override fun mouseDragged(click: Click, deltaY: Double, offsetY: Double): Boolean {

        panels.forEach { it.mouseDragged(click.x, click.y, click.button()) }

        return super.mouseDragged(click, deltaY, offsetY)
    }

    override fun shouldPause(): Boolean = false
}

fun openClickGuiScreen() = MinecraftClient.getInstance().setScreen(ClickGuiScreen())