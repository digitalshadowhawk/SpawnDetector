package com.shadowhawk.spawndetector;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import com.shadowhawk.spawndetector.LiteModSpawnDetector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSlider;

public class ConfigSpawnDetector implements ConfigPanel{
	
	/** Line spacing, in points. */
	private final static int SPACING = 24;
	  
	private GuiButton activeButton;
	private GuiButton overlayMode;
	private GuiButton disclaimer;
	private GuiButton disclaimer2;
	private GuiSlider overlayIntensity;
	private GuiSlider radius;
	private Minecraft minecraft;
	private LiteModSpawnDetector shell = LiteModSpawnDetector.instance;
	

	@Override
	public String getPanelTitle() {
		
		return "Pop Enchant Tags Settings";
	}

	@Override
	public int getContentHeight() {
		return SPACING * 3;
	}

	@Override
	public void onPanelShown(ConfigPanelHost host) {
		minecraft = Minecraft.getMinecraft();
	    int id = 0;
	    int line = 0;
	    overlayMode = new GuiButton(id++, 10, SPACING * line++, "Overlay Mode: " + shell.getOverlayModeString());
	    disclaimer = new GuiButton(id++, 10, SPACING * line++, "Sliders coming soon!");
	    disclaimer2 = new GuiButton(id++, 10, SPACING * line++, "Change values in SpawnDetector.json");
	    overlayIntensity = new GuiSlider(null, id++, 10, SPACING * line++, "Overlay Intensity", 0.01f, 0.3f, 0.25f, null);
	    radius = new GuiSlider(null, id++, 10, SPACING * line++, "Radius", 8, 64, 8, null);
	}

	@Override
	public void onPanelResize(ConfigPanelHost host) {}

	@Override
	public void onPanelHidden()
	{
		LiteLoader.getInstance().writeConfig(shell);
	}

	@Override
	public void onTick(ConfigPanelHost host) {}

	@Override
	public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
		overlayMode.drawButton(minecraft, mouseX, mouseY);
		disclaimer.drawButton(minecraft, mouseX, mouseY);
		disclaimer2.drawButton(minecraft, mouseX, mouseY);
		overlayIntensity.drawButton(minecraft, mouseX, mouseY);
		radius.drawButton(minecraft, mouseX, mouseY);
	}

	@Override
	public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
		if (overlayMode.mousePressed(minecraft, mouseX, mouseY))
		{
			activeButton = overlayMode;
			shell.setOverlayMode(Math.abs(shell.getOverlayMode()-1));
			overlayMode.displayString = ("Overlay Mode: " + shell.getOverlayModeString());
			overlayMode.playPressSound(minecraft.getSoundHandler());
		}
	}

	@Override
	public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
	{
		if (activeButton != null) {
		      activeButton.mouseReleased(mouseX, mouseY);
		      activeButton = null;
		    }
	}

	@Override
	public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {}

	@Override
	public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
	{
		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RETURN) {
		      host.close();
		}
	}

}
