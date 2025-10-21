package net.julessteele.bananaclient.modules.movement

import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.module.Module
import net.minecraft.client.option.KeyBinding

class AirJump: Module("AirJump", "Allows the player to jump on the air.", Category.MOVEMENT) {

    private var wasJumping = false

    override fun onTick() {
        if (!enabled) return
        val player = client.player ?: return

        val jumpKey: KeyBinding = client.options.jumpKey

        if (jumpKey.isPressed && !wasJumping) {
            // Force a jump even if not on ground
            if (player.isOnGround) return
            player.jump()
            wasJumping = true
        }

        // Reset once key is released
        if (!jumpKey.isPressed) {
            wasJumping = false
        }
    }
}