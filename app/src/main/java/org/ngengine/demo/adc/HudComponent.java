package org.ngengine.demo.adc;

import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.InputHandlerFragment;
import org.ngengine.gui.components.NLabel;
import org.ngengine.gui.win.NWindowManagerComponent;
import org.ngengine.gui.win.std.NHud;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStoreProvider;

import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;

public class HudComponent implements Component<Object>, InputHandlerFragment {
    private NHud hud;

    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {
        
        mng.disableComponent(LoadingScreenComponent.class);
        NWindowManagerComponent windowManager = mng.getComponent(NWindowManagerComponent.class);
        
        windowManager.showCursor(false);
                
        this.hud = windowManager.showWindow( NHud.class  );
        hud.setFitContent(false);
        hud.setFullscreen(true);
        NLabel crossair = new NLabel("+");
        crossair.setTextVAlignment(VAlignment.Center);
        crossair.setTextHAlignment(HAlignment.Center);
        hud.getCenter().addChild(crossair);
    }

    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        hud.close();
    }

    @Override
    public void onKeyEvent(ComponentManager mng, KeyInputEvent evt) {
        if(evt.getKeyCode() == KeyInput.KEY_E){
            if(evt.isPressed()){
                NWindowManagerComponent windowManager = mng.getComponent(NWindowManagerComponent.class);
                windowManager.toastAction(0);
            }
        }
    }
}
