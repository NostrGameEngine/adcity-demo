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

    private static final  float EYE_HEIGHT = 0.5f;
    private static final  float WALK_BOB_FREQ = 1.3f; 
    private static final  float WALK_BOB_AMPL = 0.12f; 

 
    private static final float MOVE_SPEED = 0.14f;
    private static final float ACCEL = 1.6f;
    private static final float DECEL = 2.4f;
    private static final float AIR_CONTROL = 0.55f;

    private float bobPhase = 0f;
    private final Vector3f camOffset = new Vector3f();
    private final Vector3f moveVel = new Vector3f();
    
    @Override
    public void updateAppLogic(ComponentManager mng, float tpf) {
   
        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();

        if (!debugCam) {
            Camera cam = mainViewPort.getCamera();
            Vector3f fwd = cam.getDirection().clone();
            Vector3f leftV = cam.getLeft().clone();

            fwd.y = 0;
            leftV.y = 0;
            if (fwd.lengthSquared() > 0)
                fwd.normalizeLocal();
            if (leftV.lengthSquared() > 0)
                leftV.normalizeLocal();

            int f = (up ? 1 : 0) + (down ? -1 : 0);
            int s = (right ? 1 : 0) + (left ? -1 : 0);

            Vector3f desiredDir = new Vector3f();
            if (f != 0)
                desiredDir.addLocal(fwd.multLocal((float) f));
            if (s != 0)
                desiredDir.addLocal(leftV.multLocal((float) -s));
            if (desiredDir.lengthSquared() > 0f)
                desiredDir.normalizeLocal();

            float desiredSpeed = (desiredDir.lengthSquared() > 0f) ? MOVE_SPEED : 0f;
            boolean onGround = isOnGround();

            Vector3f desiredVel = desiredDir.mult(desiredSpeed);
            Vector3f delta = desiredVel.subtract(moveVel);

            float maxAccel = (onGround ? ACCEL : ACCEL * AIR_CONTROL) * tpf;
            float deltaLen = delta.length();
            if (deltaLen > 0f) {
                float scale = (deltaLen > maxAccel) ? (maxAccel / deltaLen) : 1f;
                moveVel.addLocal(delta.multLocal(scale));
            }

            if (desiredSpeed == 0f) {
                float len = moveVel.length();
                if (len > 0f) {
                    float drop = DECEL * tpf;
                    float newLen = Math.max(0f, len - drop);
                    if (newLen == 0f)
                        moveVel.set(0, 0, 0);
                    else
                        moveVel.multLocal(newLen / len);
                }
            }

            if (jump)
                characterControl.jump();

            walkDirection.set(moveVel);
            characterControl.setWalkDirection(moveVel);

            applyFpsCamera(mainViewPort, tpf);
        }
    }


    private void applyFpsCamera(ViewPort mainViewPort, float tpf) {
        boolean onGround = isOnGround();

        float speed = Math.min(walkDirection.length() / MOVE_SPEED, 1f);
        float x = 0f, y = 0f;

        if (onGround) {
            if (speed > 0.02f) {
                float freq = WALK_BOB_FREQ * (0.8f + 0.4f * speed);
                bobPhase += tpf * freq * FastMath.TWO_PI;

                float amplY = WALK_BOB_AMPL * speed;

                y = FastMath.sin(bobPhase) * amplY;
            }
        }

        camOffset.set(x, y, 0f);

        Vector3f base = characterControl.getPhysicsLocation().clone();
        base.y -= EYE_HEIGHT;
        mainViewPort.getCamera().setLocation(base.add(camOffset));

        Vector3f viewDir = characterControl.getViewDirection().clone().normalizeLocal();
        mainViewPort.getCamera().lookAtDirection(viewDir, Vector3f.UNIT_Y);
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
