package com.hexagram2021.chromosomelib.common.compat.jade.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;
import snownee.jade.api.ui.Element;
import snownee.jade.overlay.OverlayRenderer;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

/**
 * A Jade UI element that renders an 8x8 icon representing the biological sex of an entity. <br/>
 * Uses a raw quad draw to blit a custom texture onto the Jade overlay.
 *
 * @author liudongyu
 */
public class BiologicalSexIconElement extends Element {
	/** Pre-built element for displaying the male sex icon. */
	public static final BiologicalSexIconElement MALE = new BiologicalSexIconElement(new ResourceLocation(MODID, "textures/gui/sex/male.png"));

	/** Pre-built element for displaying the female sex icon. */
	public static final BiologicalSexIconElement FEMALE = new BiologicalSexIconElement(new ResourceLocation(MODID, "textures/gui/sex/female.png"));

	private final ResourceLocation icon;

	/**
	 * Creates a new icon element backed by the given texture location.
	 *
	 * @param icon The {@link ResourceLocation} of the icon texture to render.
	 */
	public BiologicalSexIconElement(ResourceLocation icon) {
		this.icon = icon;
	}

	/**
	 * Returns the fixed size of this icon element: 8x8 pixels.
	 *
	 * @return A {@link Vec2} with both components equal to {@code 8}.
	 */
	@Override
	public Vec2 getSize() {
		return new Vec2(8, 8);
	}

	/**
	 * Renders the icon texture as a textured quad at the specified position within the Jade overlay.
	 *
	 * @param transform The current {@link GuiGraphics} context carrying the pose stack.
	 * @param x         Left edge of the render region.
	 * @param y         Top edge of the render region.
	 * @param maxX      Right edge of the render region (unused; icon is fixed 8 px wide).
	 * @param maxY      Bottom edge of the render region (unused; icon is fixed 8 px tall).
	 */
	@Override
	public void render(GuiGraphics transform, float x, float y, float maxX, float maxY) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, OverlayRenderer.alpha);
		RenderSystem.setShaderTexture(0, this.icon);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Matrix4f matrix4f = transform.pose().last().pose();
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(matrix4f, x, y, 0.0F).uv(0.0F, 0.0F).endVertex();
		bufferbuilder.vertex(matrix4f, x, y + 8.0F, 0.0F).uv(0.0F, 1.0F).endVertex();
		bufferbuilder.vertex(matrix4f, x + 8.0F, y + 8.0F, 0.0F).uv(1.0F, 1.0F).endVertex();
		bufferbuilder.vertex(matrix4f, x + 8.0F, y, 0.0F).uv(1.0F, 0.0F).endVertex();
		BufferUploader.drawWithShader(bufferbuilder.end());
	}
}
