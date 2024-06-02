package com.lumijiez.cacheduper;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;

public class Utils {

    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public int[] mop() {
        MovingObjectPosition mop = mc().renderViewEntity.rayTrace(200, 1.0F);
        Entity ent = pointedEntity();
        return new int[] { mop.blockX, mop.blockY, mop.blockZ, mop.sideHit, (ent != null) ? ent.getEntityId() : -1 };
    }

    public static ItemStack getItemStackFromId(String itemId, int quantity) {
        String[] parts = itemId.split(":");
        if (parts.length != 2) {
            System.out.println("Invalid item ID format. Expected modid:itemname");
            return null;
        }

        String modId = parts[0];
        String itemName = parts[1];

        Item item = GameRegistry.findItem(modId, itemName);
        if (item == null) {
            System.out.println("Item not found: " + itemId);
            return null;
        }

        return new ItemStack(item, quantity);
    }

    public WorldClient world() {
        return mc().theWorld;
    }

    public TileEntity tile() {
        return tile(mop());
    }

    public TileEntity tile(int[] mop) {
        return tile(mop[0], mop[1], mop[2]);
    }

    public TileEntity tile(int x, int y, int z) {
        return world().getTileEntity(x, y, z);
    }

    public Entity pointedEntity() {
        return mc().objectMouseOver.entityHit;
    }
}