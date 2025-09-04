package org.ngengine.app;

import org.ngengine.NGEApplication;
import org.ngengine.NGEApplication.NGEAppRunner;
import org.ngengine.ads.ImmersiveAdComponent;
import org.ngengine.components.ComponentManager;
import org.ngengine.demo.adc.CharacterComponent;
import org.ngengine.demo.adc.HudComponent;
import org.ngengine.demo.adc.LoadingScreenComponent;
import org.ngengine.demo.adc.MapComponent;
import org.ngengine.demo.adc.PhysicsComponent;
import org.ngengine.demo.adc.PostprocessingComponent;
import org.ngengine.demo.adc.SoundComponent;
import org.ngengine.gui.win.NWindowManagerComponent;
import org.ngengine.nostr4j.keypair.NostrPublicKey;

import com.jme3.system.AppSettings;

public class Main {
    
    
    
    public static NGEAppRunner main(String arg[]){
        AppSettings settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL32);
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setGammaCorrection(true);
        settings.setSamples(2);
        settings.setStencilBits(8);
        settings.setDepthBits(24);
        settings.setVSync(true);
        settings.setGraphicsDebug(false);
        settings.setX11PlatformPreferred(true);
        settings.setTitle("Nostr Game Engine Demo");
        settings.setEmulateMouse(true);
        settings.setEmulateKeyboard(true);


        NostrPublicKey appId = NostrPublicKey.fromBech32("npub1tc32kq8hr992tzvt09rwuc5wk6aa08pquqawasv20acjp4umny5q6axr4t");
        
        NGEAppRunner appRunner = NGEApplication.createApp(appId, settings, app -> {
            ImmersiveAdComponent dep = app.enableAds();

            ComponentManager mng = app.getComponentManager();
            mng.addAndEnableComponent(new LoadingScreenComponent(), null, new Object[] { NWindowManagerComponent.class });

            mng.addAndEnableComponent(new NWindowManagerComponent());
            mng.addAndEnableComponent(new PhysicsComponent());
            mng.addAndEnableComponent(new SoundComponent());
            mng.addAndEnableComponent(new PostprocessingComponent());
            mng.addAndEnableComponent(new MapComponent(), null,  new Object[]{ PhysicsComponent.class, ImmersiveAdComponent.class});            
            mng.addAndEnableComponent(new CharacterComponent(), null, new Object[] { 
                    MapComponent.class, PhysicsComponent.class });
            mng.addAndEnableComponent(new HudComponent(), null, new Object[] { CharacterComponent.class });

            app.getJme3App().setFlyCamEnabled(true);
            app.getJme3App().getInputManager().setCursorVisible(false);
            
            
        });
    
        return appRunner;
    }
}
