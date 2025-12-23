package net.julessteele.bananaclient.module.modules.movement

import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.module.Module
import net.julessteele.bananaclient.settings.setting.NumberSetting

class Fly: Module("Fly", "Allows the user to fly.", Category.MOVEMENT) {

    init {
        registerSetting(NumberSetting("Speed", 0.5, 0.1, 1.0))
    }

    override fun onEnable() {
        val player = client.player ?: return
        player.abilities.allowFlying = true
        player.abilities.flying = true
    }

    override fun onDisable() {
        val player = client.player ?: return
        player.abilities.flying = false
        player.abilities.allowFlying = false
    }

    override fun onTick() {
        val player = client.player ?: return

        player.velocity = player.velocity.multiply(1.0, 0.0, 1.0)

        if (client.options.jumpKey.isPressed) {
            player.addVelocity(0.0, (findSetting("Speed")?.value ?: 0.5) as Double, 0.0)
        }

        if (client.options.sneakKey.isPressed) {
            player.addVelocity(0.0, -((findSetting("Speed")?.value ?: 0.5) as Double), 0.0)
        }

        player.velocityDirty = true
    }
}