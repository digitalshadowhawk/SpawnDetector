package com.shadowhawk.lightspawn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.mumfrey.liteloader.core.LiteLoader;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mumfrey.liteloader.Tickable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class LiteModLightSpawn implements Tickable {

	public static final String MOD_NAME = "Light Spawn";
	public static final String MOD_VERSION = "1.0.0";
    public static KeyBinding keyBinding;
    public static LiteModLightSpawn instance;
    
    @Override
	public String getName()
    {
        return MOD_NAME;
    }
    
    public WorldOverlayRenderer renderer = new WorldOverlayRenderer();

    @Override
	public String getVersion()
    {
        return MOD_VERSION;
    }
    
    public String confpath;

    @Override
	public void init(File configPath)
    {
        this.confpath = configPath.getPath();
        this.initfields(this.confpath);
        instance = this;
        keyBinding = new KeyBinding("Toggle Light Regions", 48, "LiteMods");
        LiteLoader.getInput().registerKeyBinding(keyBinding);
    }
    
    
    
    private void initfields(String configPath)
    {
        HashMap<String, Object> json = this.loadjson(configPath);

        if (json != null)
        {
            Object obj;

            if (json.containsKey("radius"))
            {}

            if (json.containsKey("lineheight"))
            {}

            if (json.containsKey("unlimited"))
            {}
        }
    }
    
    public static HashMap<String, Object> parse(String json)
    {
        JsonParser parser = new JsonParser();
        JsonElement elm = parser.parse(json);

        if (elm != null && !(elm instanceof JsonNull))
        {
            JsonObject object = (JsonObject)elm;
            Set<?> set = object.entrySet();
            Iterator<?> iterator = set.iterator();
            HashMap<String, Object> map = new HashMap<String, Object>();

            while (iterator.hasNext())
            {
                Entry<?, ?> entry = (Entry<?, ?>)iterator.next();
                String key = (String)entry.getKey();
                JsonElement value = (JsonElement)entry.getValue();

                if (!value.isJsonPrimitive())
                {
                    map.put(key, parse(value.toString()));
                }
                else
                {
                    map.put(key, value.getAsString());
                }
            }

            return map;
        }
        else
        {
            return null;
        }
    }
    
    private HashMap<String, Object> loadjson(String configPath)
    {
        String jsonname = configPath + "\\lightspawn.json";
        File file = new File(jsonname);

        if (file.exists() && file.canRead())
        {
            try
            {
                byte[] e = Files.readAllBytes(Paths.get(jsonname, new String[0]));
                String buff = new String(e, Charset.defaultCharset());
                return parse(buff);
            }
            catch (FileNotFoundException var6)
            {
                var6.printStackTrace();
            }
            catch (IOException var7)
            {
                var7.printStackTrace();
            }
        }

        return null;
    }

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

	@Override
	public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
    {
        if (keyBinding.isPressed())
        {
            this.renderer.keyPress();
        }
    }
	
}
