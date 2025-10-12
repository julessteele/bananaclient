package net.julessteele.bananaclient.util

import net.julessteele.bananaclient.Banana
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

object CommandChatUtil {

    var suppressNext = false

    /**
     * Sends a message to the player in chat
     */
    fun sendClientMsg(text: String) {
        MinecraftClient.getInstance().player?.sendMessage(Text.literal("§6[§e${Banana.NAME}§6] §f$text"), false)
    }

    // Used for command use feedback
    fun sendUseCaseClientMsg(text: String) {
        MinecraftClient.getInstance().player?.sendMessage(Text.literal("§6[§e${Banana.NAME}§6] §fUsage: §7$text"), false)
    }

    // Sends a message to the server safely
    fun sendServerMsg(message: String) {

        val client = MinecraftClient.getInstance()
        val player = client.player ?: return

        suppressNext = true
        player.networkHandler.sendChatMessage(message)
        suppressNext = false
    }
}