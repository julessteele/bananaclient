package net.julessteele.bananaclient.settings

import net.julessteele.bananaclient.clickgui.Panel
import net.minecraft.client.gui.DrawContext

open class Setting(val name: String, var value: Any) {

    open var settingType: SettingType? = null

    open fun render(context: DrawContext, mouseX: Int, mouseY: Int, parent: Panel) { }
}