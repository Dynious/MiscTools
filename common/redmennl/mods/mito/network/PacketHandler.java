package redmennl.mods.mito.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import redmennl.mods.mito.entity.companion.EntityCompanion;
import redmennl.mods.mito.entity.companion.addon.AddonBase;
import redmennl.mods.mito.entity.companion.addon.AddonCrafting;
import redmennl.mods.mito.lib.Library;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
        EntityPlayer ep = (EntityPlayer) player;
        byte packetId = reader.readByte();

        // companionCraft packet
        if (packetId == 0)
        {
            int entityId = reader.readInt();
            Entity e = ep.worldObj.getEntityByID(entityId);

            if (e != null && e instanceof EntityCompanion)
            {
                for (AddonBase addon : ((EntityCompanion) e).getAddons())
                {
                    if (addon instanceof AddonCrafting)
                    {
                        ((AddonCrafting) addon).craft(((AddonCrafting) addon)
                                .craftResult(reader.readByte()));
                    }
                }
            }
        }

        // companionKill packet
        if (packetId == 1)
        {
            int entityId = reader.readInt();
            Entity e = ep.worldObj.getEntityByID(entityId);

            if (e != null && e instanceof EntityCompanion)
            {
                ((EntityCompanion) e).killCompanion();
            }
        }

        // companionFollow packet
        if (packetId == 2)
        {
            int entityId = reader.readInt();
            Entity e = ep.worldObj.getEntityByID(entityId);

            if (e != null && e instanceof EntityCompanion)
            {
                ((EntityCompanion) e).toggleStandCloseToPlayer();
            }
            System.out.println(((EntityCompanion) e).isSitting());
        }

        // companionCutWood packet
        if (packetId == 3)
        {
            int entityId = reader.readInt();
            Entity e = ep.worldObj.getEntityByID(entityId);

            if (e != null && e instanceof EntityCompanion)
            {
                ((EntityCompanion) e).toggleCollectLogs();
            }
        }
        
        // companionAutoCraft packet
        if (packetId == 4)
        {
            int entityId = reader.readInt();
            Entity e = ep.worldObj.getEntityByID(entityId);
            
            if (e != null && e instanceof EntityCompanion)
            {
                for (AddonBase addon : ((EntityCompanion) e).getAddons())
                {
                    if (addon instanceof AddonCrafting)
                    {
                        ((AddonCrafting) addon).autoCraft = !((AddonCrafting) addon).autoCraft;
                    }
                }
                ((EntityCompanion) e).onInventoryChanged();
            }
        }
    }

    public static void companionCraft(EntityCompanion e, int enabledTab)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.write(0);
            dataStream.writeInt(e.entityId);
            dataStream.write((byte)enabledTab);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
                    Library.CHANNEL_NAME, byteStream.toByteArray()));
        } catch (IOException ex)
        {
            System.out
                    .print("Misc Tools encountered an error sending a craft packet!");
        }
    }

    public static void companionKill(EntityCompanion e)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.write(1);
            dataStream.writeInt(e.entityId);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
                    Library.CHANNEL_NAME, byteStream.toByteArray()));
        } catch (IOException ex)
        {
            System.out
                    .print("Misc Tools encountered an error sending a kill packet!");
        }
    }

    public static void companionFollow(EntityCompanion e)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.write(2);
            dataStream.writeInt(e.entityId);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
                    Library.CHANNEL_NAME, byteStream.toByteArray()));
        } catch (IOException ex)
        {
            System.out
                    .print("Misc Tools encountered an error sending a follow packet!");
        }
    }

    public static void companionCutWood(EntityCompanion e)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.write(3);
            dataStream.writeInt(e.entityId);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
                    Library.CHANNEL_NAME, byteStream.toByteArray()));
        } catch (IOException ex)
        {
            System.out
                    .print("Misc Tools encountered an error sending a woodcutting packet!");
        }
    }
    
    public static void companionAutoCraft(EntityCompanion e)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.write(4);
            dataStream.writeInt(e.entityId);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(
                    Library.CHANNEL_NAME, byteStream.toByteArray()));
        } catch (IOException ex)
        {
            System.out
                    .print("Misc Tools encountered an error sending a craft packet!");
        }
    }

}
