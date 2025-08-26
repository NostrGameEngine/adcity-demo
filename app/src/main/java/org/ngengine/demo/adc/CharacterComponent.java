package org.ngengine.demo.adc;

import org.ngengine.ViewPortManager;
import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.InputHandlerFragment;
import org.ngengine.components.fragments.LogicFragment;
import org.ngengine.components.fragments.MainViewPortFragment;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStoreProvider;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class CharacterComponent implements Component<Object>, InputHandlerFragment, LogicFragment{
    private static final float MAX_PITCH = FastMath.HALF_PI - 0.1f;

    private Node player;
    private CharacterControl characterControl;
    private boolean debugCam = false;
    private float currentYaw = 0;
    private float currentPitch = 0;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean jump = false;

    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {
        
        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();    
        Node rootNode = vpm.getRootNode(mainViewPort);

        player = new Node("Player");
        player.setLocalTranslation(new Vector3f(-33.665176f, 41.3f, -23.83905f));
        player.setLocalRotation(new Quaternion(-0.10938066f, -0.004970028f, -5.469133f, 0.9939874f));
        rootNode.attachChild(player);

        PhysicsComponent physics = mng.getComponent(PhysicsComponent.class);
        characterControl = physics.createCharacterControl(player);
        physics.addAll(player);

        System.out.println("Character ready");
    }

 
    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        PhysicsComponent physics = mng.getComponent(PhysicsComponent.class);
        physics.removeAll(player);
        player.removeFromParent();
    }



    @Override
    public void onKeyEvent(ComponentManager mng, KeyInputEvent evt) {
        if(evt.getKeyCode() == KeyInput.KEY_W) {
            up = evt.isPressed();
        } else if(evt.getKeyCode() == KeyInput.KEY_S) {
            down = evt.isPressed();
        } else if(evt.getKeyCode() == KeyInput.KEY_A) {
            left = evt.isPressed();
        } else if(evt.getKeyCode() == KeyInput.KEY_D) {
            right = evt.isPressed();
        }  else if(evt.getKeyCode() == KeyInput.KEY_SPACE) {
            jump = evt.isPressed();
        } else if(evt.getKeyCode() == KeyInput.KEY_F){
            if(evt.isPressed()){
                debugCam = !debugCam;
            }
        }
    }

 

    @Override
    public void updateAppLogic(ComponentManager mng, float tpf){
        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();
        if(!debugCam){
            Camera cam = mainViewPort.getCamera();
            Vector3f camDir = cam.getDirection().clone().multLocal(0.1f);
            Vector3f camLeft = cam.getLeft().clone().multLocal(0.1f);
            camDir.y = 0;
            camLeft.y = 0;
            walkDirection.set(0, 0, 0);
            if (left) {
                walkDirection.addLocal(camLeft);
            }
            if (right) {
                walkDirection.addLocal(camLeft.negate());
            }
            if (up) {
                walkDirection.addLocal(camDir);
            }
            if (down) {
                walkDirection.addLocal(camDir.negate());
            }
            if (jump){         
                characterControl.jump();
            }    
            characterControl.setWalkDirection(walkDirection);            
        }

        if (!debugCam) {
            mainViewPort.getCamera().setLocation(characterControl.getPhysicsLocation());
            mainViewPort.getCamera().getLocation().y -= 0.5f;
            mainViewPort.getCamera().lookAtDirection(characterControl.getViewDirection(), Vector3f.UNIT_Y);
        }
    }

  
    public boolean isOnGround(){
        return characterControl.onGround();
    }

    public boolean isWalking(){
        return walkDirection.lengthSquared() > 0;
    }

    @Override
    public void onMouseMotionEvent(ComponentManager mng, MouseMotionEvent evt) {
        float sensitivity = 0.0005f;
        
        
        // Update rotation angles
        currentYaw += -(float)evt.getDX() * sensitivity;
        currentPitch += -(float)evt.getDY() * sensitivity;
        
        // Limit pitch to prevent flipping
        currentPitch = FastMath.clamp(currentPitch, -MAX_PITCH, MAX_PITCH);
        
        // Create rotation quaternion from yaw angle (horizontal only)
        Quaternion rotation = new Quaternion();
        rotation.fromAngles(currentPitch, currentYaw, 0);
        
        // Set character direction based on yaw only (horizontal movement)
        Vector3f viewDir = new Vector3f(0, 0, 1); // Forward vector
        viewDir = rotation.mult(viewDir);
        characterControl.setViewDirection(viewDir);
        
    }
}
