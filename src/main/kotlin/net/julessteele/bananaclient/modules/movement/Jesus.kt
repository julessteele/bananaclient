package net.julessteele.bananaclient.modules.movement

import net.julessteele.bananaclient.module.Module
import net.minecraft.block.Blocks
import net.minecraft.client.option.KeyBinding
import net.minecraft.util.math.BlockPos

class Jesus : Module(name = "Jesus", description = "Allows the player to walk on water.", category = Category.MOVEMENT) {

    private var lastY: Double? = null
    private var clientFallDistance = 0.0

    // TODO add option to toggle jump ability while walking on water
    private var canJump = true

    private val fallThreshold = 3.0

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

        // If we're falling a lot, don't force Jesus behavior (let player fall naturally)
        if (clientFallDistance > fallThreshold) return

        // Basic checks
        if (player.isSneaking) return

        val pos = BlockPos.ofFloored(player.x, player.y - 0.1, player.z)
        val blockBelowState = world.getBlockState(pos)
        val blockAboveState = world.getBlockState(pos.up())

        fun isWaterLike(block: net.minecraft.block.Block) = block == Blocks.WATER || block == Blocks.SEAGRASS || block == Blocks.TALL_SEAGRASS

        // not above water-like block, nothing to do
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

        if (client.options.jumpKey.isPressed && this.canJump) {
            player.jump()
        }
    }

    override fun onDisable() {

        val player = client.player ?: return
        val world = client.world ?: return

        val pos = BlockPos.ofFloored(player.x, player.y - 0.1, player.z)
        if (world.getBlockState(pos).block == Blocks.WATER) {
            player.setPos(player.x, pos.y + 1.5, player.z)
        }

        // Reset
        lastY = null
        clientFallDistance = 0.0
    }
}
