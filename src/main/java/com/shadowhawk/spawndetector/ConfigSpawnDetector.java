package com.shadowhawk.spawndetector;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import com.shadowhawk.spawndetector.LiteModSpawnDetector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class ConfigSpawnDetector implements ConfigPanel{
	
	/** Line spacing, in points. */
	private final static int SPACING = 24;
	  
	private GuiButton activeButton;
	private GuiButton overlayMode;
	private GuiButton transparency;
	private GuiButton radius;
	private Minecraft minecraft;
	private LiteModSpawnDetector shell = LiteModSpawnDetector.instance;
	private float [] transparencies = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f};
	private int [] radii = {8, 16, 24, 32};
	

	@Override
	public String getPanelTitle() {
		
		return I18n.format("spawndetector.configpanel.title", new Object[] {LiteModSpawnDetector.MOD_NAME});
		//return "Spawn Detector Settings";
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
	    overlayMode = new GuiButton(id++, 10, SPACING * line++, I18n.format("spawndetector.configpanel.button.mode", new Object [] {}) + I18n.format("spawndetector.configpanel.mode." + shell.getOverlayMode(), new Object [] {}));
	    transparency = new GuiButton(id++, 10, SPACING * line++, I18n.format("spawndetector.configpanel.button.intensity", new Object [] {}) + shell.getOverlayStrength());
	    radius = new GuiButton(id++, 10, SPACING * line++, I18n.format("spawndetector.configpanel.button.radius", new Object [] {}) + shell.getRadius());
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
		transparency.drawButton(minecraft, mouseX, mouseY);
		radius.drawButton(minecraft, mouseX, mouseY);
	}

	@Override
	public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
		if (overlayMode.mousePressed(minecraft, mouseX, mouseY))
		{
			activeButton = overlayMode;
			shell.setOverlayMode(Math.abs(shell.getOverlayMode()-1));
			overlayMode.displayString = I18n.format("spawndetector.configpanel.button.mode", new Object [] {}) + I18n.format("spawndetector.configpanel.mode." + shell.getOverlayMode(), new Object [] {});
			overlayMode.playPressSound(minecraft.getSoundHandler());
		}
		if(transparency.mousePressed(minecraft, mouseX, mouseY))
		{
			activeButton = transparency;
			int index = 0;
			for(int i = 0; i < transparencies.length; i++)
			{
				if(shell.getOverlayStrength() == transparencies[i])
				{
					index = (i+1) % transparencies.length;
					
				}
			}
			shell.setOverlayStrength(transparencies[index]);
			
			transparency.displayString = I18n.format("spawndetector.configpanel.button.intensity", new Object [] {}) + shell.getOverlayStrength();
			transparency.playPressSound(minecraft.getSoundHandler());
		}
		if(radius.mousePressed(minecraft, mouseX, mouseY))
		{
			activeButton = radius;
			int index = 0;
			for(int i = 0; i < radii.length; i++)
			{
				if(shell.getRadius() == radii[i])
				{
					index = (i+1) % radii.length;
					
				}
			}
			shell.setRadius(radii[index]);
			
			radius.displayString = I18n.format("spawndetector.configpanel.button.radius", new Object [] {}) + shell.getRadius();
			radius.playPressSound(minecraft.getSoundHandler());
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
