package net.julessteele.bananaclient.module

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext
import net.julessteele.bananaclient.Banana
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.client.util.math.MatrixStack
import org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN

abstract class Module(val name: String, val description: String, val category: Category, keyCode: Int = GLFW_KEY_UNKNOWN) {

    var enabled: Boolean = false
        private set

    val keybind: KeyBinding = KeyBindingHelper.registerKeyBinding(KeyBinding("key.${Banana.modid}.${name.lowercase()}", InputUtil.Type.KEYSYM, keyCode, "category.${Banana.modid}"))

    open fun onEnable() { }
    open fun onDisable() { }
    open fun onTick() { }
    open fun onRenderEntity(matrices: MatrixStack, context: WorldRenderContext) { }
    open fun onRenderHUD(mc: MinecraftClient, matrices: MatrixStack) { }

    fun toggle() {
        enabled = !enabled
        if (enabled)
            onEnable()
        else
            onDisable()
    }

    enum class Category {
        RENDER, HUD, MOVEMENT, MISC
    }
}