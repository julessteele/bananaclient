package net.julessteele.bananaclient.mixin.client;

import net.julessteele.bananaclient.module.ModuleManager;
import net.julessteele.bananaclient.module.modules.movement.Blink;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSend(Packet<?> packet, CallbackInfo ci) {
        if (packet instanceof PlayerMoveC2SPacket) {
            Blink blink = (Blink) ModuleManager.INSTANCE.getModule("blink", false);
            if (blink != null && blink.getEnabled()) {
                blink.getPackets().add(packet);
                // Cancel the packet so it's not sent to the server
                ci.cancel();
            }
        }
    }
}
