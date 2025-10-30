package org.ngengine.demo.adc;

import java.util.function.Consumer;

import org.ngengine.AsyncAssetManager;
import org.ngengine.ViewPortManager;
import org.ngengine.ads.ImmersiveAdComponent;
import org.ngengine.ads.ImmersiveAdControl;
import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.AsyncAssetLoadingFragment;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStore;
import org.ngengine.store.DataStoreProvider;

import com.jme3.asset.TextureKey;
import com.jme3.environment.EnvironmentProbeControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class MapComponent implements Component<Object>, AsyncAssetLoadingFragment {
    private Spatial map;
    private Spatial sky;

 
 

    @Override
    public void loadAssetsAsync(ComponentManager mng, AsyncAssetManager assetManager, DataStore cache, Consumer<Object> preload) {
        try{

            System.out.println("AdCity: Loading map from assets...");
            
            map = assetManager.loadModel("adc/city-opt/city.glb");
            
        
            PhysicsComponent physics = mng.getComponent(PhysicsComponent.class);
            map.depthFirstTraversal(sx->{
                Object worldGuard = sx.getUserData("nge.worldguard");
                if(worldGuard!=null){
                    sx.setCullHint(CullHint.Always);
                    physics.createStaticRigidBody(sx);
                    System.out.println("AdCity: Added worldguard collision to "+sx.getName());
                }
            });

               
            

            TextureKey key = new TextureKey("adc/night-sky.png", true);
            key.setGenerateMips(false);
            Texture skyTextyre = assetManager.loadTexture(key);
            
            sky = SkyFactory.createSky(assetManager, skyTextyre, SkyFactory.EnvMapType.EquirectMap);
            sky.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI, Vector3f.UNIT_Y));
            EnvironmentProbeControl.tagGlobal(sky);
            
            preload.accept(map);
            preload.accept(sky);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {
        PhysicsComponent physics = mng.getComponent(PhysicsComponent.class);
        physics.addAll(map);

        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();
        AsyncAssetManager assetManager = mng.getGlobalInstance(AsyncAssetManager.class);
        

        Node rootNode = vpm.getRootNode(mainViewPort);
        rootNode.attachChild(map);
        rootNode.attachChild(sky);
            
        int resolution = 128;
        rootNode.addControl(new EnvironmentProbeControl(assetManager, resolution));

        ImmersiveAdControl adControl = map.getControl(ImmersiveAdControl.class);
        if(adControl==null){
            adControl = new ImmersiveAdControl(assetManager);
            map.addControl(adControl);
            mng.getComponent(ImmersiveAdComponent.class).register(adControl);
        }

        System.out.println("Map ready");

    }

    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        PhysicsComponent physics = mng.getComponent(PhysicsComponent.class);
        physics.removeAll(map);

        ImmersiveAdControl adControl = map.getControl(ImmersiveAdControl.class);
        if(adControl!=null){
            mng.getComponent(ImmersiveAdComponent.class).unregister(adControl);
        }

        map.removeFromParent();

        sky.removeFromParent();

    }
    
}
