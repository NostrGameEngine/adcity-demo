package org.ngengine.demo.adc;

import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.InputHandlerFragment;
import org.ngengine.components.fragments.LogicFragment;
import org.ngengine.gui.components.NLabel;
import org.ngengine.gui.win.NWindowManagerComponent;
import org.ngengine.gui.win.std.NHud;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStoreProvider;

import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.VAlignment;

public class LoadingScreenComponent  implements Component<Object>, LogicFragment {
    private Runnable closeHud;
    private NLabel txt;
    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {
        
        mng.disableComponent(LoadingScreenComponent.class);
        NWindowManagerComponent windowManager = mng.getComponent(NWindowManagerComponent.class);
        
        windowManager.showCursor(false);
        
        txt = new NLabel("Loading");
  
        this.closeHud= windowManager.showWindow(
            NHud.class,
            (win, err) -> {     
                win.setFitContent(false);           
                win.setFullscreen(true);
                txt.setFontSize(32);
                txt.setTextVAlignment(VAlignment.Center);
                txt.setTextHAlignment(HAlignment.Center);
                win.getCenter().addChild(txt);                
            }
        );
    }

    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        this.closeHud.run();
    }

    float time = 0;

    @Override
    public void updateAppLogic(ComponentManager mng, float tpf) {
        time += tpf;
        if(txt!=null&&time>1f){
            time=0;
            txt.setText(txt.getText()+".");
            System.out.println(txt.getText());
            if(txt.getText().length()>15){
                txt.setText("Loading");
            }
        }
    }

    
    
}
