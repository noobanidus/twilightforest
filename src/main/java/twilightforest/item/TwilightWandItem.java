package twilightforest.item;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFSounds;
import twilightforest.entity.projectile.TwilightWandBoltEntity;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class TwilightWandItem extends Item {

	protected TwilightWandItem(Properties props) {
		super(props);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (stack.getDamageValue() == stack.getMaxDamage()) {
			return InteractionResultHolder.fail(player.getItemInHand(hand));
		} else {
			player.playSound(TFSounds.SCEPTER_PEARL, 1.0F, (world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F);

			if (!world.isClientSide) {
				world.addFreshEntity(new TwilightWandBoltEntity(world, player));
				stack.hurt(1, random, (ServerPlayer) null);
			}

			return InteractionResultHolder.success(player.getItemInHand(hand));
		}
	}

	@Override
	public float getXpRepairRatio(ItemStack stack) {
		return 1f;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, world, tooltip, flags);
		tooltip.add(new TranslatableComponent("twilightforest.scepter_charges", stack.getMaxDamage() - stack.getDamageValue()));
	}
}