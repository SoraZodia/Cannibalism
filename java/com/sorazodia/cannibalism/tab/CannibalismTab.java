package com.sorazodia.cannibalism.tab;

import com.sorazodia.cannibalism.items.manager.ItemList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CannibalismTab extends CreativeTabs{

	public CannibalismTab() {
		super("cannibalism");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
        return ItemList.ironKnife;
    }
	
}
