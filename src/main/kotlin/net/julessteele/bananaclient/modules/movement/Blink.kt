package net.julessteele.bananaclient.modules.movement

import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.module.Module
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket

class Blink: Module("Blink", "Lets the player disconnect from the server to fake lag to a different position.", Category.MOVEMENT) {

    var isBlinking = false
    var packets = mutableListOf<Packet<*>>()

    override fun onEnable() {
        isBlinking = true
        packets.clear()
    }

    override fun onDisable() {
        isBlinking = false
        packets.forEach { client.player?.networkHandler?.sendPacket(it) }
        packets.clear()
    }

    override fun onPacket(packet: Packet<*>) {
        if (isBlinking && packet is PlayerMoveC2SPacket) {
            packets.add(packet)
        } else {
            client.networkHandler?.sendPacket(packet) ?: return
        }
    }
}