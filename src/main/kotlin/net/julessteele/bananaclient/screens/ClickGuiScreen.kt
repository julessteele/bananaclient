package net.julessteele.bananaclient.screens

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.julessteele.bananaclient.clickgui.ClickGuiConfig
import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.clickgui.components.setting.ModeButton
import net.julessteele.bananaclient.clickgui.components.ModuleToggleButton
import net.julessteele.bananaclient.clickgui.components.setting.SettingToggleButton
import net.julessteele.bananaclient.clickgui.components.setting.Slider
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

    // Defined in ClickGuiScreen & Panel
    val buttonHeight = 14.0

    private val panels = mutableListOf<Panel>()

    init {

        // Load saved ClickGUI config
        ClickGuiConfig.load()

        /************************ Handle All ClickGuiDrawing *****************************/
        val width = 100.0
        val height = 160.0

        var xOffset = 10.0

        // Iterate through categories and draw them on screen
        Category.entries.forEach { category ->

            val savedPos = ClickGuiConfig.getPosition(category.name)
            val x = savedPos?.first ?: xOffset
            val y = savedPos?.second ?: 10.0

            val panel = Panel(Vector2d(x, y), width, height, category)

            var yOffset = buttonHeight

            // Iterate through the modules in the current category and draw them
            ModuleManager.getModules(category).forEach { module ->

                // Add all buttons for module in current category
                panel.components.add(ModuleToggleButton(x, y + yOffset, width, buttonHeight, panel, module))
                // Increment yOffset for next draw by the height of each drawn button
                yOffset += buttonHeight

                var yOffsetChild = 0.0

                // Add children buttons for all settings in each module when added and initialized
                if (!module.getSettings().isEmpty() && module.children.isEmpty()) {
                    module.getSettings().forEach { setting ->
                        when (setting.settingType) {
                            SettingType.BOOLEAN -> {
                                module.children.add(SettingToggleButton(
                                    x + width,
                                    y + yOffset + yOffsetChild - buttonHeight,
                                    width,
                                    buttonHeight,
                                    panel,
                                    module,
                                    Text.of(setting.name),
                                    setting.value as Boolean))

                                yOffsetChild += buttonHeight
                            }
                            SettingType.NUMBER -> {
                                setting as NumberSetting
                                module.children.add(Slider(
                                    x + width,
                                    y + yOffset + yOffsetChild - buttonHeight,
                                    width,
                                    buttonHeight,
                                    Text.of(setting.name),
                                    setting.min,
                                    setting.max,
                                    setting.value as Double,
                                    module,
                                    panel))

                                yOffsetChild += buttonHeight
                            }
                            SettingType.MODE -> {
                                module.children.add(ModeButton(
                                    x + width,
                                    y + yOffset + yOffsetChild - buttonHeight,
                                    width,
                                    buttonHeight,
                                    panel,
                                    setting.name,
                                    module.findSetting(setting.name) as ModeSetting
                                ))

                                yOffsetChild += buttonHeight
                            }
                        }
                    }
                }
            }

            panels.add(panel)

            xOffset += width + 10.0
        }
    }

    override fun close() {
        super.close()
        ModuleManager.getModules().forEach { it.children.clear() }
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

    override fun mouseMoved(mouseX: Double, mouseY: Double) {

        panels.forEach { it.mouseMoved(mouseX, mouseY) }

        return super.mouseMoved(mouseX, mouseY)
    }

    override fun shouldPause(): Boolean = false
}

fun openClickGuiScreen() = MinecraftClient.getInstance().setScreen(ClickGuiScreen())