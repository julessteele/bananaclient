package net.julessteele.bananaclient.util

import net.julessteele.bananaclient.Banana
import net.minecraft.client.option.KeyBinding
import net.minecraft.util.Identifier

object KeybindUtil {

    val bananaClientKeybindCategory: KeyBinding.Category = KeyBinding.Category.create(Identifier.of(Banana.MOD_ID, "modules"))
}