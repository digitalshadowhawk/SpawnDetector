package com.shadowhawk.spawndetector;

import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class SpawnDetectorConfigPanel extends AbstractConfigPanel{

	public static final int SPACING = 24;
	private float [] transparencies = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f};
	private int [] radii = {8, 16, 24, 32};
	
	@Override
	public String getPanelTitle() {
		return I18n.format("spawndetector.configpanel.title");
	}

	@Override
	public void onPanelHidden() {}

	@Override
	protected void addOptions(ConfigPanelHost host) {
		LiteModSpawnDetector mod = host.<LiteModSpawnDetector>getMod();
		
		int id = 0;
        
		this.addControl(new GuiButton(id++, 20, (id - 1) * SPACING, 200, 20, I18n.format("spawndetector.configpanel.button.mode") + I18n.format("spawndetector.configpanel.mode." + mod.getOverlayMode())), new ConfigOptionListener<GuiButton>()
        {
        	@Override
        	public void actionPerformed(GuiButton control)
        	{
        		mod.setOverlayMode(Math.abs(mod.getOverlayMode()-1));
    			control.displayString = I18n.format("spawndetector.configpanel.button.mode") + I18n.format("spawndetector.configpanel.mode." + mod.getOverlayMode());
        	}
        });
		this.addControl(new GuiButton(id++, 20, (id - 1) * SPACING, 200, 20, I18n.format("spawndetector.configpanel.button.intensity") + mod.getOverlayStrength()), new ConfigOptionListener<GuiButton>()
        {
        	@Override
        	public void actionPerformed(GuiButton control)
        	{
        		int index = 0;
    			for(int i = 0; i < transparencies.length; i++)
    			{
    				if(mod.getOverlayStrength() == transparencies[i])
    				{
    					index = (i+1) % transparencies.length;
    					
    				}
    			}
    			mod.setOverlayStrength(transparencies[index]);
    			control.displayString = I18n.format("spawndetector.configpanel.button.intensity") + mod.getOverlayStrength();
        	}
        });
		this.addControl(new GuiButton(id++, 20, (id - 1) * SPACING, 200, 20, I18n.format("spawndetector.configpanel.button.radius") + mod.getRadius()), new ConfigOptionListener<GuiButton>()
        {
        	@Override
        	public void actionPerformed(GuiButton control)
        	{
        		int index = 0;
    			for(int i = 0; i < radii.length; i++)
    			{
    				if(mod.getRadius() == radii[i])
    				{
    					index = (i+1) % radii.length;
    					
    				}
    			}
    			mod.setRadius(radii[index]);
    			control.displayString = I18n.format("spawndetector.configpanel.button.radius") + mod.getRadius();
        	}
        });
	}

}
