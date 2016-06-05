package com.shadowhawk.lightspawn;

import com.mumfrey.liteloader.modconfig.Exposable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

public class LightSpawnRenderer implements Exposable {

	private boolean enabled = false;
	
	
	public void toggleLightSpawn() {
		this.enabled = !this.enabled;
	}

	public void render(Minecraft minecraft, float partialTicks)
		{
			if(enabled)
			{
				EntityPlayerSP player = minecraft.thePlayer;
				World world = minecraft.theWorld;

		
	}

}
}
