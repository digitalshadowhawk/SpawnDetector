package com.shadowhawk.lightspawn;

import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonNull;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.PostRenderListener;
import com.mumfrey.liteloader.Tickable;

import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.world.World;

public class LiteModLightSpawn implements PostRenderListener,Tickable,Configurable {

	public static final String MOD_NAME = "Light Spawn";
	public static final String MOD_VERSION = "1.0.0";
    public static KeyBinding keyBinding;
    public static LiteModLightSpawn instance;

	public static KeyBinding toggleLightSpawn;
	public LightSpawnRenderer renderer = new LightSpawnRenderer();
    
	private int lastWorld;
	private int currentWorld;
	
	
	public LiteModLightSpawn()
	{
		if(instance == null) instance = this;
	}
	
	@Override
	public String getName()
    {
        return MOD_NAME;
    }

    @Override
	public String getVersion()
    {
        return MOD_VERSION;
    }
    
    public String confpath;

    @Override
	public void init(File configPath) 
	{
		LiteLoader.getInstance().registerExposable(renderer, null);
		toggleLightSpawn = new KeyBinding("Toggle Chunk Borders", Keyboard.KEY_L, "ChunkBorders");
		LiteLoader.getInput().registerKeyBinding(toggleLightSpawn);
	}
    
    @Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{

		if(toggleLightSpawn.isPressed())
		{
			renderer.toggleLightSpawn();
		}
		
		if(minecraft.theWorld != null)
		{
			this.currentWorld = minecraft.theWorld.hashCode();
			this.lastWorld = minecraft.theWorld.hashCode();
		}
		else
		{
			this.lastWorld = 0;
			this.currentWorld = 0;
		}
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass() {
		return ConfigLightSpawn.class;
	}

	@Override
	public void onPostRenderEntities(float partialTicks) 
	{
		renderer.render(Minecraft.getMinecraft(), partialTicks);
	}

	@Override
	public void onPostRender(float partialTicks) {}
    
    
}
