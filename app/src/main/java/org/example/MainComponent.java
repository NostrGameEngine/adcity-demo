package org.example;

import org.ngengine.AsyncAssetManager;
import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.AsyncAssetLoadingFragment;
import org.ngengine.components.fragments.InputHandlerFragment;
import org.ngengine.components.fragments.MainViewPortFragment;
import org.ngengine.gui.components.NLabel;
import org.ngengine.gui.components.containers.NRow;
import org.ngengine.gui.win.NWindowManagerComponent;
import org.ngengine.gui.win.std.NHud;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStoreProvider;

import com.jme3.asset.AssetManager;
import com.jme3.environment.EnvironmentProbeControl;
import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ToneMapFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;
import com.jme3.util.SkyFactory.EnvMapType;

public class MainComponent implements Component<Object>, AsyncAssetLoadingFragment, MainViewPortFragment, InputHandlerFragment{
    // In a real-world application, you should probably split this into multiple components
    
    private Spatial sky;
    private EnvironmentProbeControl evp;
    private Node rootNode;
    private Node characterNode;

    @Override
    public void loadAssetsAsync(AsyncAssetManager assetManager) {
        // load resources
        sky = SkyFactory.createSky(assetManager, "Sky/citrus_orchard_puresky_4k.hdr", EnvMapType.EquirectMap);
        evp = new EnvironmentProbeControl(assetManager, 256);

        // Tag sky for environment baking
        EnvironmentProbeControl.tagGlobal(sky);

        // load character model
        characterNode = new Node("CharacterNode");
        Geometry characterGeom = new Geometry("MyCharacter", new Box(1f,1f,1f));
        characterNode.attachChild(characterGeom);

        // set up material for character
        Material characterMat = new Material(assetManager, Materials.PBR);
        characterMat.setColor("BaseColor", ColorRGBA.White);
        characterMat.setFloat("Metallic", 1.0f);
        characterMat.setFloat("Roughness", 0.0f);
        characterGeom.setMaterial(characterMat);       
        
    }


    @Override
    public void receiveMainViewPort(ViewPort viewPort) {
        rootNode = getRootNode(viewPort);
        viewPort.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
    }

    @Override
    public void loadMainViewPortFilterPostprocessor(AssetManager assetManager, FilterPostProcessor fpp) {
        ToneMapFilter toneMapFilter = new ToneMapFilter(Vector3f.UNIT_XYZ.mult(1.6f));
        fpp.addFilter(toneMapFilter);
    }

    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime, Object arg) {
        // Compose the scene
        rootNode.attachChild(sky);
        rootNode.addControl(evp);
        rootNode.attachChild(characterNode);     

        NWindowManagerComponent windowManager = mng.getComponent(NWindowManagerComponent.class);
        windowManager.showWindow(NHud.class, (win,err)->{
            if(err != null) {
                System.err.println("Error showing HUD window: " + err.getMessage());
                return;
            }
            NRow topRow = win.getTop();
            NLabel label = new NLabel("Use WASD to move the cube");
            topRow.addChild(label);            
        });
    }

    @Override
    public void onKeyEvent(KeyInputEvent evt) {
        if(evt.getKeyCode() == KeyInput.KEY_W){
            characterNode.move(0, 0, -0.1f); // Move character forward
        } else if(evt.getKeyCode() == KeyInput.KEY_S) {
            characterNode.move(0, 0, 0.1f); // Move character backward
        } else if(evt.getKeyCode() == KeyInput.KEY_A) {
            characterNode.move(-0.1f, 0, 0); // Move character left
        } else if(evt.getKeyCode() == KeyInput.KEY_D) {
            characterNode.move(0.1f, 0, 0); // Move character right
        }
    }

    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
      
    }



    
}
