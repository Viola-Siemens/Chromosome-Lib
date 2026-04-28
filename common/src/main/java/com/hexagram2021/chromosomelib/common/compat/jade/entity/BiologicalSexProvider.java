package com.hexagram2021.chromosomelib.common.compat.jade.entity;

import com.hexagram2021.chromosomelib.common.compat.jade.element.BiologicalSexIconElement;
import com.hexagram2021.chromosomelib.common.sex.BiologicalSex;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

/**
 * Jade entity component provider that reads and displays the biological sex of a living entity. <br/>
 * Implements both {@link IEntityComponentProvider} (client-side tooltip rendering) and
 * {@link IServerDataProvider} (server-side NBT data collection).
 *
 * @author liudongyu
 */
@SuppressWarnings("java:S6548")
public enum BiologicalSexProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
	INSTANCE;

	/** NBT key used to transmit the biological sex byte from server to client. */
	public static final String KEY = "biological_sex";

	/** Unique identifier for this Jade provider, used for registration and config. */
	public static final ResourceLocation UID = new ResourceLocation(MODID, "jade/" + KEY);

	BiologicalSexProvider() {
	}

	/**
	 * Appends the biological sex icon and label to the Jade tooltip on the client side. <br/>
	 * Only adds content when the server data contains a valid {@code biological_sex} byte.
	 *
	 * @param iTooltip      The tooltip to append content to.
	 * @param entityAccessor Accessor providing entity and server data.
	 * @param iPluginConfig  Current plugin configuration.
	 */
	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
		if (entityAccessor.getServerData().contains(KEY, Tag.TAG_BYTE)) {
			byte sex = entityAccessor.getServerData().getByte(KEY);
			if(sex == BiologicalSex.MALE.getId()) {
				iTooltip.add(BiologicalSexIconElement.MALE);
				iTooltip.append(Component.translatable("jade.chromosomelib.biological_sex.male"));
			} else if(sex == BiologicalSex.FEMALE.getId()) {
				iTooltip.add(BiologicalSexIconElement.FEMALE);
				iTooltip.append(Component.translatable("jade.chromosomelib.biological_sex.female"));
			}
		}
	}

	/**
	 * Collects the entity's biological sex on the server side and writes it into the NBT tag
	 * so it can be sent to the client for tooltip rendering.
	 *
	 * @param compoundTag    The NBT tag to write data into.
	 * @param entityAccessor Accessor providing the target entity.
	 */
	@Override
	public void appendServerData(CompoundTag compoundTag, EntityAccessor entityAccessor) {
		// FIXME 1. icon doesn't show 2. change the following line to show its actual sex.
		compoundTag.putByte(KEY, (byte)((entityAccessor.getEntity().getUUID().hashCode() & 1) + 1));
	}

	/**
	 * Returns the unique identifier for this provider.
	 *
	 * @return The {@link ResourceLocation} UID used for registration and config lookup.
	 */
	@Override
	public ResourceLocation getUid() {
		return UID;
	}
}
