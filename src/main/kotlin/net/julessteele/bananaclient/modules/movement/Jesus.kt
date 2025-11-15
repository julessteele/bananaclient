package net.julessteele.bananaclient.modules.movement

import net.julessteele.bananaclient.modules.module.Category
import net.julessteele.bananaclient.modules.module.Module
import net.julessteele.bananaclient.settings.setting.BooleanSetting
import net.julessteele.bananaclient.settings.setting.NumberSetting
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos

class Jesus: Module("Jesus", "Allows the player to walk on water.", Category.MOVEMENT) {

    private var lastY: Double? = null
    private var clientFallDistance = 0.0

    private val fallThreshold = 3.0

    init {
        registerSetting(NumberSetting("Y-Boost", 1.5, 1.0, 2.0))
        registerSetting(BooleanSetting("Can Jump", true))
    }

    override fun onTick() {

        val player = client.player ?: return
        val world = client.world ?: return

        // Update client side tracker
        val currentY = player.y
        val prevY = lastY
        if (prevY == null) {
            lastY = currentY
            clientFallDistance = 0.0
        } else {
            val delta = prevY - currentY // POSITIVE when moving down
            if (player.isOnGround || delta <= 0.0) {
                // reset when on ground, sneaking
                clientFallDistance = 0.0
            } else {
                clientFallDistance += delta
            }
            lastY = currentY
        }

        // Let player fall into water if above threshold
        if (clientFallDistance > fallThreshold) return

        // Basic checks
        if (player.isSneaking) return

        val pos = BlockPos.ofFloored(player.x, player.y - 0.1, player.z)
        val blockBelowState = world.getBlockState(pos)
        val blockAboveState = world.getBlockState(pos.up())

        fun isWaterLike(block: net.minecraft.block.Block) = block == Blocks.WATER || block == Blocks.SEAGRASS || block == Blocks.TALL_SEAGRASS

        // Not above waterlike block -> skip
        if (!isWaterLike(blockBelowState.block) && !isWaterLike(blockAboveState.block)) return

        // If submerged or water directly above, float up slightly
        if (blockAboveState.block == Blocks.WATER || player.isSubmergedInWater) {
            player.velocity = player.velocity.add(0.0, 0.1, 0.0)
            player.setPos(player.x, pos.y + 0.99, player.z)
            return
        }

        // If moving downward into the water, cancel downward velocity and snap to surface
        if (player.velocity.y < 0) {
            player.velocity = player.velocity.multiply(1.0, 0.0, 1.0)
            if (blockAboveState.block != Blocks.AIR) {
                player.velocity = player.velocity.add(0.0, 0.1, 0.0)
            }
            player.setPos(player.x, pos.y + 0.99, player.z)
        }

        // Check if the player is allowed to jump on water based on settings
        if (client.options.jumpKey.isPressed && ((findSetting("Can Jump")?.value ?: true) as Boolean)) {
            player.jump()
        }
    }

    override fun onDisable() {

        val player = client.player ?: return
        val world = client.world ?: return

        val pos = BlockPos.ofFloored(player.x, player.y - 0.1, player.z)
        if (world.getBlockState(pos).block == Blocks.WATER) {
            // Give player boost to get out of water on disable
            val yBoost: Double = (this.findSetting("Y-Boost") as NumberSetting).value as Double
            player.setPos(player.x, pos.y + yBoost, player.z)
        }

        // Reset
        lastY = null
        clientFallDistance = 0.0
    }
}
