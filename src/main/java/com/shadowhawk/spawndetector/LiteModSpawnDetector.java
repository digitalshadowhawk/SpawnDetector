package com.shadowhawk.spawndetector;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.PostRenderListener;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@ExposableOptions(
		strategy = ConfigStrategy.Unversioned,
		filename = "spawndetector.json",
		aggressive = true
)

public class LiteModSpawnDetector implements PostRenderListener,Tickable,Exposable,Configurable {

	@Expose
	@SerializedName("overlay_mode")
	private int overlayMode = 0;
	@Expose
	@SerializedName("overlay_strength")
	private float overlayStrength = 0.25f;
	@Expose
	@SerializedName("radius")
	private int radius = 8;
	
	public static final String MOD_NAME = "Spawn Detector";
	public static final String MOD_VERSION = "1.0.0";
    public static LiteModSpawnDetector instance;
	public static KeyBinding toggleSpawnDetector;
	public SpawnDetectorRenderer renderer = new SpawnDetectorRenderer();
	
	
	public String confpath;
	
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

    @Override
	public void init(File configPath) 
	{
		//LiteLoader.getInstance().registerExposable(renderer, null);
		toggleSpawnDetector = new KeyBinding("Toggle Spawn Detector", Keyboard.KEY_L, "Spawn Detector");
		LiteLoader.getInput().registerKeyBinding(toggleSpawnDetector);
	}
    
    @Override
	public void onPostRender(float partialTicks) {}

	@Override
	public void onPostRenderEntities(float partialTicks) 
	{
		renderer.render(Minecraft.getMinecraft(), partialTicks);
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

	public int getOverlayMode() {
		return overlayMode;
	}

	public void setOverlayMode(int overlayMode) {
		this.overlayMode = overlayMode;
	}

	public float getOverlayStrength() {
		return overlayStrength;
	}

	public void setOverlayStrength(float overlayStrength) {
		this.overlayStrength = overlayStrength;
	}
	
	@Override
	public Class<? extends ConfigPanel> getConfigPanelClass() {
		return ConfigSpawnDetector.class;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getOverlayModeString() {
		if(getOverlayMode() == 0)
		{
			return "Cross";
		} else
		{
			return "Top Face";
		}
	}
}
