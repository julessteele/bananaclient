package net.julessteele.bananaclient.modules.module

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.julessteele.bananaclient.Banana
import net.julessteele.bananaclient.clickgui.SettingComponent
import net.julessteele.bananaclient.settings.Setting
import net.julessteele.bananaclient.util.KeybindUtil
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.client.util.math.MatrixStack
import org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN

abstract class Module(val name: String, val description: String, val category: Category, keyCode: Int = GLFW_KEY_UNKNOWN) {

    protected val client: MinecraftClient = MinecraftClient.getInstance()

    var enabled: Boolean = false

    // Settings -> List of Settings used for manipulating changeable values of modules, registered in module init { }
    private val settings = mutableListOf<Setting>()
    // Children -> List of SettingComponents used for rendering buttons of settings on the ClickGUI, added automatically when ClickGUI is initialized
    val children = mutableListOf<SettingComponent>()

    val keybind: KeyBinding = KeyBindingHelper.registerKeyBinding(KeyBinding(
        "key.${Banana.MOD_ID}.${name.lowercase()}",
        InputUtil.Type.KEYSYM,
        keyCode,
        KeybindUtil.bananaClientKeybindCategory))

    open fun onEnable() { }
    open fun onDisable() { }
    open fun onTick() { }
    open fun onRenderEntity(matrices: MatrixStack) { }
    open fun onRenderHUD(context: DrawContext) { }

    fun toggle() {
        enabled = !enabled
        if (enabled) onEnable() else onDisable()
        // Save modules after toggling
        ModuleManager.saveModules()
    }

    // Register settings in module init { }
    fun registerSetting(setting: Setting) = settings.add(setting)
    fun findSetting(name: String): Setting? = settings.find { it.name.equals(name, ignoreCase = true) }
    fun getSettings() = settings

    fun findChild(settingComponent: SettingComponent): SettingComponent? = children.find { it.javaClass.simpleName.equals(settingComponent.javaClass.simpleName, ignoreCase = true) }
}