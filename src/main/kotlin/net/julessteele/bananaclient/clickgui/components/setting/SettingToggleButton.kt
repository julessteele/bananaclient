package net.julessteele.bananaclient.clickgui.components.setting

import net.julessteele.bananaclient.clickgui.Panel
import net.julessteele.bananaclient.clickgui.SettingComponent
import net.julessteele.bananaclient.module.Module
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Colors

class SettingToggleButton(x: Double, y: Double, width: Double, height: Double, parent: Panel, val module: Module, val settingName: Text, var enabled: Boolean): SettingComponent(x, y, width, height, parent) {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int) {

        super.render(context, mouseX, mouseY)

        val color = if (enabled) Colors.GREEN + 40 else if (hovered) 0xFF777777.toInt() else 0xFFAAAAAA.toInt()

        // Draw background
        context.fill(x.toInt(), y.toInt(), (x + width).toInt(), (y + height).toInt(), color)

        // Draw text
        context.drawTextWithShadow(client.textRenderer, settingName, (x + 3).toInt(), (y + height / 2 - 4).toInt(), 0xFFFFFFFF.toInt())
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovered)
            toggle(settingName)
    }

    fun toggle(setting: Text) {
        enabled = !enabled
        module.findSetting(setting.string)?.value = enabled
    }
}