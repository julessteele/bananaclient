package net.julessteele.bananaclient.module.modules.movement

import net.julessteele.bananaclient.module.Category
import net.julessteele.bananaclient.module.Module
import net.minecraft.network.packet.Packet

class Blink: Module("Blink", "Lets the player disconnect from the server to fake lag to a different position.", Category.MOVEMENT) {

    var packets = mutableListOf<Packet<*>>()

    override fun onEnable() {
        packets.clear()
    }

    override fun onDisable() {
        packets.forEach {
            client.networkHandler?.connection?.send(it)
        }
        packets.clear()
    }
}