package com.ychstudio.gamesys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GM {
    private static GM instance = new GM();
    
    public static final float PPM = 64f;
    
    public static final float SKY_LINE = 20f;

    public static final short NOTHING_CATEGORY_BITS = 0;
    public static final short GROUND_CATEGORY_BITS = 1;
    public static final short PLAYER_CATEGORY_BITS = 2;
    public static final short ASTEROID_CATEGORY_BITS = 3;
    
    
    public static final short GROUND_MASK_BITS = PLAYER_CATEGORY_BITS;
    public static final short PLAYER_MASK_BITS = GROUND_CATEGORY_BITS | ASTEROID_CATEGORY_BITS;
    public static final short ASTEROID_MASK_BITS = PLAYER_CATEGORY_BITS;

    public static float soundVolume = 1.0f;
    public static float musicVolume = 0.5f;
    
    private AssetManager assetManager;

    public static GM getInstance() {
        return instance;
    }

    public static AssetManager getAssetManager() {
        return instance.assetManager;
    }

    private GM() {
        assetManager = new AssetManager();
        assetManager.load("images/actors.pack", TextureAtlas.class);
        assetManager.load("images/EarthBackground.png", Texture.class);
        assetManager.load("images/Stars.png", Texture.class);
        assetManager.load("images/BlueDust.png", Texture.class);
        assetManager.load("images/ColdNebula.png", Texture.class);
        assetManager.load("images/HotNebula.png", Texture.class);
        assetManager.load("images/VioletDust.png", Texture.class);
        assetManager.load("images/YellowDust.png", Texture.class);
        assetManager.load("images/Moon.png", Texture.class);
        assetManager.load("images/Earth.png", Texture.class);
        assetManager.load("particles/explode.particle", ParticleEffect.class);
        assetManager.load("particles/asteroid_explode.particle", ParticleEffect.class);
        assetManager.load("particles/asteroid_explode_small.particle", ParticleEffect.class);
        assetManager.load("particles/asteroid_explode_medium.particle", ParticleEffect.class);
        
        assetManager.load("audio/Menu.mp3", Sound.class);
        assetManager.load("audio/Throttle.mp3", Sound.class);
        assetManager.load("audio/Explosion1.ogg", Sound.class);
        assetManager.load("audio/Explosion2.ogg", Sound.class);
        assetManager.load("audio/S31-Night Prowler.ogg", Music.class);
        assetManager.finishLoading();
    }
}
