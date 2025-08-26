package org.ngengine.demo.adc;

import org.ngengine.NGEApplication;
import org.ngengine.ads.ImmersiveAdComponent;
import org.ngengine.components.ComponentManager;
import org.ngengine.gui.win.NWindowManagerComponent;

import com.jme3.system.AppSettings;

public class AdcDemo {
    
    
    
    public static void main(String arg[]){
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
        
        

        
        Runnable appBuilder = NGEApplication.createApp(settings, app -> {
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
    
        appBuilder.run();
    }
}
