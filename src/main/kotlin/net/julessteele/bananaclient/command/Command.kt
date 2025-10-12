package net.julessteele.bananaclient.command

import net.julessteele.bananaclient.Banana
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

abstract class Command(val name: String, val description: String, val usage: String) {

    abstract fun execute(args: List<String>)
}
