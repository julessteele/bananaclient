package net.julessteele.bananaclient.command

import net.minecraft.client.MinecraftClient

abstract class Command(val name: String, val description: String, val usage: String) {

    protected val client = MinecraftClient.getInstance()!!

    abstract fun execute(args: List<String>)
}