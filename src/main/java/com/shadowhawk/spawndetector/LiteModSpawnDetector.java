package com.shadowhawk.spawndetector;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.PostRenderListener;
import com.mumfrey.liteloader.Tickable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class LiteModSpawnDetector implements PostRenderListener,Tickable,Configurable {

	public static final String MOD_NAME = "Spawn Detector";
	public static final String MOD_VERSION = "1.0.0";
    public static LiteModSpawnDetector instance;

	public static KeyBinding toggleSpawnDetector;
	public SpawnDetectorRenderer renderer = new SpawnDetectorRenderer();
	
	
	public LiteModSpawnDetector()
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
		toggleSpawnDetector = new KeyBinding("Toggle Spawn Detector", Keyboard.KEY_L, "Spawn Detector");
		LiteLoader.getInput().registerKeyBinding(toggleSpawnDetector);
	}
    
    @Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
	{

		if(toggleSpawnDetector.isPressed())
		{
			renderer.toggleSpawnDetector();
		}
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass() {
		return ConfigSpawnDetector.class;
	}

	@Override
	public void onPostRenderEntities(float partialTicks) 
	{
		renderer.render(Minecraft.getMinecraft(), partialTicks);
	}

	@Override
	public void onPostRender(float partialTicks) {}
    
    
}
