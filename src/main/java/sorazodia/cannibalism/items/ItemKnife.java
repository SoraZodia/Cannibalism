package sorazodia.cannibalism.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import sorazodia.cannibalism.api.EntityData;
import sorazodia.cannibalism.api.ICutable;
import sorazodia.cannibalism.config.ConfigHandler;
import sorazodia.cannibalism.config.JSONConfig;
import sorazodia.cannibalism.main.Cannibalism;
import sorazodia.cannibalism.main.ItemRegistry;
import sorazodia.cannibalism.mechanic.nbt.FleshNBTHelper;

public class ItemKnife extends Item
{

	private boolean interact = false;
	private JSONConfig json = Cannibalism.getJson();
	private final SoundEvent hurtSound = new SoundEvent(new ResourceLocation(Cannibalism.MODID, "item.knife.hurt"));
	private final SoundEvent screamSound = new SoundEvent(new ResourceLocation(Cannibalism.MODID, "item.knife.scream"));

	public ItemKnife(String name, ToolMaterial material)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(Cannibalism.cannibalismTab);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BLOCK;
	}
	
	@Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
	
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState blockState, BlockPos position, EntityLivingBase entityLiving)
    {
		if (this.canHarvestBlock(blockState)) 
		{
			stack.damageItem(1, entityLiving);
		}
		else if (blockState.getBlockHardness(world, position) != 0)
        {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockState)
    {
        return blockState.getBlock() == Blocks.WEB || blockState.getBlock() == Blocks.LEAVES || blockState.getBlock() == Blocks.LEAVES2;
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();
        Material material = state.getMaterial();
        
        if (block == Blocks.WEB || material == Material.PLANTS)
        {
            return 15.0F;
        }
        else
        {
            return 1.5F;
        }
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		
		if (!interact && player.isSneaking())
		{
			spookyEffect(world, player);
			player.swingArm(hand);
			if (!world.isRemote)
			{
				EntityData playerData = json.getPlayerData();
				cutPlayer(player, getDamage(playerData.getMinDamage(), playerData.getMaxDamage()), player.getName(), ItemRegistry.playerFlesh);
				stack.damageItem(1, player);
			}
		}
		interact = false;

		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}

	private void setMeatName(ItemStack meat, String owner)
	{
		FleshNBTHelper.addName(meat, owner);
		FleshNBTHelper.setItemNickname(meat);
	}

	private void spookyEffect(World world, EntityPlayer player)
	{
		if (!ConfigHandler.muteScream()) 
		{
			if (ConfigHandler.doScream())
				world.playSound(null, player.getPosition(), ConfigHandler.useCustomScream() ? this.screamSound : SoundEvents.ENTITY_GHAST_HURT, SoundCategory.PLAYERS, 1.0F, ConfigHandler.getPinch());
			if (!ConfigHandler.doScream())
				world.playSound(null, player.getPosition(),  ConfigHandler.useCustomScream() ?  this.hurtSound : SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0F, ConfigHandler.getPinch());
		}
		spawnBlood(player, world, 1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand)
	{

		if (target.hurtTime < 1 && target.getHealth() > 0)
		{

			if (!player.isSneaking() && target.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null && target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor)
			{
				ItemArmor armor = (ItemArmor) target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
				player.swingArm(hand);
				player.world.playSound(null, player.getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
				stack.damageItem(15 * armor.damageReduceAmount, player);
				return true;
			}

			if (json.checkEntity(target))
			{
				EntityData data = json.getData(target);
				cutEntity(player, target, getDamage(data.getMinDamage(), data.getMaxDamage()), data.getDrops());
			}

			else if (json.getWildCardIndex(target, player.world) >= 0)
			{
				EntityData data = json.getData(target, player.world);
				cutEntity(player, target, getDamage(data.getMinDamage(), data.getMaxDamage()), data.getDrops());
			}

			else if (target instanceof ICutable)
			{
				interact = true;
				ICutable cutable = (ICutable) target;
				cutable.cut(player);
			}

			if (target instanceof EntityPlayerMP)
			{
				EntityData playerData = json.getPlayerData();
				cutPlayer((EntityPlayer) target, getDamage(playerData.getMinDamage(), playerData.getMaxDamage()), target.getName(), ItemRegistry.playerFlesh);
			}

		}

		if (interact == true)
		{
			player.swingArm(hand);
			stack.damageItem(1, player);
			spawnBlood(target, target.world, 0);
		}

		return interact;
	}

	private void cutPlayer(EntityPlayer player, float damage, String owner, ItemFlesh flesh)
	{
		interact = true;
		String name = I18n.translateToLocalFormatted("item.info.playerFleshOwner", owner);

		if (!player.world.isRemote)
		{
			ItemStack meat = new ItemStack(flesh);
			setMeatName(meat, name);
			cutDamage(player, player, damage);
			player.entityDropItem(meat, 0.0F);
		}
	}

	private void cutEntity(EntityPlayer player, EntityLivingBase entity, float damage, ItemStack[] drops)
	{
		interact = true;
		if (!entity.world.isRemote)
		{
			cutDamage(player, entity, damage);
			for (ItemStack item : drops)
			{
				entity.entityDropItem(item.copy(), 0.0F);
			}
		}
	}

	private void cutDamage(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		if (!(entity instanceof EntityPlayer))
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), damage);

		if (!player.capabilities.isCreativeMode)
		{
			if (entity instanceof EntityPlayerMP)
			{
				entity.setHealth(entity.getHealth() - damage);
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.01F);
				player.limbSwingAmount = 1.5F;
				if (entity.getHealth() < 0.01F)
				{
					entity.onDeath(DamageSource.causePlayerDamage(player));
				}
			}
			else if (entity instanceof EntityPlayer)
			{
				player.setHealth(entity.getHealth() - damage);
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 0.01F);
				player.limbSwingAmount = 1.5F;
				if (player.getHealth() < 0.01F)
				{
					player.onDeath(DamageSource.causePlayerDamage(player));
				}
			}
		}

	}

	private void spawnBlood(EntityLivingBase entityLiving, World world, float ySubtract)
	{
		for (int repeat = 0; repeat < ConfigHandler.getBloodAmount(); repeat++)
		{
			world.spawnParticle(EnumParticleTypes.REDSTONE, entityLiving.posX + Math.random() - Math.random(), entityLiving.posY - ySubtract, entityLiving.posZ + Math.random() - Math.random(), 0.0F, 0.0F, 0.0F);
		}
	}

	private float getDamage(float min, float max)
	{
		float damage = min + (float) (Math.random() * ((max - min) + 1));
		return damage;
	}
}