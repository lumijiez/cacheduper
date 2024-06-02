package com.lumijiez.cacheduper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import java.util.UUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

public class CacheDupeCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "cachedp";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cachedp <block_id> <number>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.addChatMessage(new ChatComponentText("Invalid arguments. Usage: " + getCommandUsage(sender)));
            return;
        }

        String itemId = args[0];
        int number;

        try {
            number = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.addChatMessage(new ChatComponentText("The number parameter must be an integer."));
            return;
        }

        Utils utils = new Utils();

        ItemStack stack = Utils.getItemStackFromId(itemId, number);

        TileEntity checkTile = utils.tile();

        try {
            if (Class.forName("cofh.thermalexpansion.block.cache.TileCache").isInstance(checkTile)) {
                Object packet = Class.forName("cofh.core.network.PacketTile").getConstructor(TileEntity.class).newInstance(checkTile);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addString", String.class).invoke(packet, "");
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", byte.class).invoke(packet, (byte) 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addUUID", UUID.class).invoke(packet, UUID.randomUUID());
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addString", String.class).invoke(packet, "");
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", boolean.class).invoke(packet, true);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", byte.class).invoke(packet, (byte) 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", boolean.class).invoke(packet, true);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addInt", int.class).invoke(packet, 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByteArray", byte[].class).invoke(packet, new byte[6]);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", byte.class).invoke(packet, (byte) 0);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", boolean.class).invoke(packet, false);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addItemStack", ItemStack.class).invoke(packet, stack);
                Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addInt", int.class).invoke(packet, number);
                Class.forName("cofh.core.network.PacketHandler").getMethod("sendToServer", Class.forName("cofh.core.network.PacketBase")).invoke(null, packet);
            }
        } catch(Exception ignored) {}
    }
}
