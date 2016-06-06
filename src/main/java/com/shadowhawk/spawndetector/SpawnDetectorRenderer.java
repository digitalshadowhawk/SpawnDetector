package com.shadowhawk.spawndetector;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3d;

import com.mumfrey.liteloader.modconfig.Exposable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class SpawnDetectorRenderer implements Exposable
{
    public static boolean mobOverlay = false;

    private static Entity dummyEntity = new EntityPig(null);

    public static AxisAlignedBB aabb(double minx, double miny, double minz, double maxx, double maxy, double maxz) {
        return new AxisAlignedBB(minx, miny, minz, maxx, maxy, maxz);
    }

    private static int getSpawnMode(Chunk chunk, int x, int y, int z) {
        World world = chunk.getWorld();
        BlockPos pos = new BlockPos(x, y, z);
        if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(SpawnPlacementType.ON_GROUND, world, pos) ||
                chunk.getLightFor(EnumSkyBlock.BLOCK, pos) >= 8)
            return 0;

        AxisAlignedBB aabb = aabb(x+0.2, y+0.01, z+0.2, x+0.8, y+1.8, z+0.8);
        if (!world.checkNoEntityCollision(aabb) ||
                !world.getCollisionBoxes(dummyEntity, aabb).isEmpty() ||
                world.containsAnyLiquid(aabb))
            return 0;

        if (chunk.getLightFor(EnumSkyBlock.SKY, pos) >= 8)
            return 1;
        return 2;
    }

    private static void renderMobSpawnOverlay(Entity entity) {
        if (!mobOverlay)
            return;

        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        glLineWidth(1.5F);
        glBegin(GL_LINES);

        GlStateManager.color(1, 0, 0);

        World world = entity.worldObj;
        int x1 = (int) entity.posX;
        int z1 = (int) entity.posZ;
        int y1 = (int) MathHelper.clamp_double(entity.posY, 16, world.getHeight() - 16);

        for (int x = x1 - 16; x <= x1 + 16; x++)
            for (int z = z1 - 16; z <= z1 + 16; z++) {
                BlockPos pos = new BlockPos(x, y1, z);
                Chunk chunk = world.getChunkFromBlockCoords(pos);
                BiomeGenBase biome = world.getBiomeGenForCoords(pos);
                if (biome.getSpawnableList(EnumCreatureType.MONSTER).isEmpty() || biome.getSpawningChance() <= 0)
                    continue;

                for (int y = y1 - 16; y < y1 + 16; y++) {
                    int spawnMode = getSpawnMode(chunk, x, y, z);
                    if (spawnMode == 0)
                        continue;

                    if (spawnMode == 1)
                        GlStateManager.color(1, 1, 0);
                    else
                        GlStateManager.color(1, 0, 0);

                    glVertex3d(x, y + 0.004, z);
                    glVertex3d(x + 1, y + 0.004, z + 1);
                    glVertex3d(x + 1, y + 0.004, z);
                    glVertex3d(x, y + 0.004, z + 1);
                }
            }

        glEnd();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }
    public static void translateToWorldCoords(Entity entity, float frame)
    {
    	double interpPosX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * frame;
    	double interpPosY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * frame;
    	double interpPosZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * frame;

    	GlStateManager.translate(-interpPosX, -interpPosY, -interpPosZ);
    }
    
    public void render(Minecraft minecraft, float partialTicks) {

		GlStateManager.pushMatrix();
        EntityPlayerSP player = minecraft.thePlayer;
        translateToWorldCoords(player, partialTicks);

        renderMobSpawnOverlay(player);
        GlStateManager.popMatrix();
    }
    
    public void toggleSpawnDetector() {
        mobOverlay = !mobOverlay;
    }
}
