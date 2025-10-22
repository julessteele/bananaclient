package net.julessteele.bananaclient.mixin.client;

import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {

    /**
     * @author
     * MysteriousLychee
     * @reason
     * Provide logic for checking if send needs to be cancellable
     */
    @Overwrite
    public void sendPacket(Packet<?> packet) {

    }
}
