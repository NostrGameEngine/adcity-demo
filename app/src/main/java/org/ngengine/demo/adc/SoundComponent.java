package org.ngengine.demo.adc;

import java.util.function.Consumer;

import org.ngengine.AsyncAssetManager;
import org.ngengine.ViewPortManager;
import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.AsyncAssetLoadingFragment;
import org.ngengine.components.fragments.InputHandlerFragment;
import org.ngengine.components.fragments.LogicFragment;
import org.ngengine.components.fragments.MainViewPortFragment;
import org.ngengine.components.fragments.RenderFragment;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStore;
import org.ngengine.store.DataStoreProvider;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioKey;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.math.FastMath;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class SoundComponent implements Component<Object>, LogicFragment, AsyncAssetLoadingFragment, InputHandlerFragment {

    private AudioNode backgroundMusic;
    private AudioNode footstepSound;
    private AudioNode jumpSound;
    private float lastFootstepTime = 0f;

    @Override
    public void loadAssetsAsync(ComponentManager mng, AsyncAssetManager assetManager, DataStore cache, Consumer<Object> preload) {
    

        AudioKey audioKey = new AudioKey("adc/e.q._city.ogg", false, false);
        AudioData audioData = assetManager.loadAudio(audioKey);
        backgroundMusic = new AudioNode(audioData, audioKey);
        backgroundMusic.setLooping(true);
        backgroundMusic.setPositional(false);
        backgroundMusic.setVolume(0.4f);

        AudioKey footstepKey = new AudioKey("adc/footstep.ogg", false, false);
        AudioData footstepData = assetManager.loadAudio(footstepKey);
        footstepSound = new AudioNode(footstepData, footstepKey);
        footstepSound.setLooping(false);
        footstepSound.setPositional(false);
        footstepSound.setVolume(0.5f);

          
        AudioKey jumpKey = new AudioKey("adc/jump.ogg", false, false);
        AudioData jumpData = assetManager.loadAudio(jumpKey);
        jumpSound = new AudioNode(jumpData, jumpKey);
        jumpSound.setLooping(false);
        jumpSound.setPositional(false);
        jumpSound.setVolume(0.5f);
     

    }

 

    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {

        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();
        Node rootNode = vpm.getRootNode(mainViewPort);
        rootNode.attachChild(backgroundMusic);
        backgroundMusic.play();
       
        rootNode.attachChild(footstepSound);
        rootNode.attachChild(jumpSound);
    }

    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        backgroundMusic.removeFromParent();
        backgroundMusic.stop();
        footstepSound.removeFromParent();
        jumpSound.removeFromParent();
        
    }

    @Override
    public void onKeyEvent(ComponentManager mng, KeyInputEvent evt) {
        CharacterComponent character = mng.getComponent(CharacterComponent.class);

        if(character !=null && evt.getKeyCode() == KeyInput.KEY_SPACE && evt.isPressed() && character.isOnGround()){
            jumpSound.playInstance();
        }
    }

    @Override

    public void updateAppLogic(ComponentManager mng, float tpf){
        CharacterComponent character = mng.getComponent(CharacterComponent.class);

        if (character != null && character.isWalking() && character.isOnGround()) {
            lastFootstepTime += tpf;
            if (lastFootstepTime > 0.3f) {
                footstepSound.setPitch(1f - FastMath.nextRandomFloat() * 0.1f);
                footstepSound.setVolume(0.3f - FastMath.nextRandomFloat() * 0.1f);
                footstepSound.playInstance();
                lastFootstepTime = 0f;
            }
        }
    }
       
    
    
}
