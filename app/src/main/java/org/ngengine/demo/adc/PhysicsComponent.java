package org.ngengine.demo.adc;

import org.ngengine.ViewPortManager;
import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.AppFragment;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStoreProvider;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.BulletAppState.ThreadingType;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PhysicsComponent implements Component<Object>{
    private BulletAppState physics;

    


    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {
        physics = new BulletAppState();
        physics.setThreadingType(ThreadingType.PARALLEL);
        AppStateManager stateManager=mng.getGlobalInstance(AppStateManager.class);
        stateManager.attach(physics);
        System.out.println("Physics ready");

    }

    public RigidBodyControl createStaticRigidBody(Spatial sx){
        CollisionShape shp = CollisionShapeFactory.createDynamicMeshShape(sx);
        RigidBodyControl rb = new RigidBodyControl(shp, 0);
        rb.setFriction(1f);
        sx.addControl(rb);
        return rb;
    }

    public CharacterControl createCharacterControl(Node player){
        CharacterControl characterControl = new CharacterControl(new CapsuleCollisionShape(1.5f, 0.8f), .1f);
        player.addControl(characterControl);
        return characterControl;
    }

    public void addAll(Spatial v){
        physics.getPhysicsSpace().addAll(v);
    }

    public void removeAll(Spatial v){
        physics.getPhysicsSpace().removeAll(v);        
    }



    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        AppStateManager stateManager = mng.getGlobalInstance(AppStateManager.class);
        stateManager.detach(physics);
    }
    
}
