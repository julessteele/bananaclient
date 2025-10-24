package net.julessteele.bananaclient.mixin.client;

import net.julessteele.bananaclient.module.ModuleManager;
import net.julessteele.bananaclient.modules.movement.Blink;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.*;

import java.util.List;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    /**
     * @author MysteriousLychee
     * @reason Provide hooks for bananaclient packet functionality
     */
    @Overwrite
    public void send(Packet<?> packet) {
        if (packet instanceof PlayerMoveC2SPacket) {
            Blink blink = ((Blink)(ModuleManager.INSTANCE.getModule("blink")));
            assert blink != null;
            if (blink.getEnabled()) {
                List<Packet<?>> packetList = blink.getPackets();
                packetList.add(packet);
                blink.setPackets(packetList);
            }
        } else {
            MinecraftClient.getInstance().getNetworkHandler().getConnection().send(packet, null);
        }
    }
}
