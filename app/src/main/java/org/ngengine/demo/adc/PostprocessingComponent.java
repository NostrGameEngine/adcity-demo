package org.ngengine.demo.adc;

import java.util.function.Consumer;

import javax.swing.text.View;

import org.ngengine.ViewPortManager;
import org.ngengine.components.Component;
import org.ngengine.components.ComponentManager;
import org.ngengine.components.fragments.AssetLoadingFragment;
import org.ngengine.components.fragments.LogicFragment;
import org.ngengine.components.fragments.MainViewPortFragment;
import org.ngengine.runner.Runner;
import org.ngengine.store.DataStore;
import org.ngengine.store.DataStoreProvider;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.FXAAFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.filters.KHRToneMapFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.texture.Image.Format;

public class PostprocessingComponent implements Component<Object>, LogicFragment {
    private FilterPostProcessor fpp;

 

 
    FogFilter fog;
    SSAOFilter ssaoFilter;
    BloomFilter bloom;
    KHRToneMapFilter tonemap;

    @Override
    public void onEnable(ComponentManager mng, Runner runner, DataStoreProvider dataStore, boolean firstTime,
            Object arg) {

        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();
        AssetManager assetManager = mng.getGlobalInstance(AssetManager.class);

        fpp = new FilterPostProcessor(assetManager);
        fpp.setFrameBufferDepthFormat(Format.Depth24Stencil8);
        fpp.setNumSamples(2);
        mainViewPort.addProcessor(fpp);

        ssaoFilter = new SSAOFilter(2.9299974f, 25f, 5.8100376f, 0.091000035f);
         ssaoFilter.setScale(0.6f);
        ssaoFilter.setBias(0.79f);
        ssaoFilter.setApproximateNormals(false);
        ssaoFilter.setSampleRadius(0.8f);
        ssaoFilter.setIntensity(63f);

        fpp.addFilter(ssaoFilter);

        fog = new FogFilter();
        fog.setFogDistance(211f);
        fog.setFogDensity(0.4f);
        fog.setFogColor(new ColorRGBA(15.0f / 255.0f, 0.0f, 110f / 255.0f, 1f));
        fpp.addFilter(fog);

      

        tonemap = new KHRToneMapFilter();
        tonemap.setGamma(new Vector3f(0.9f,0.9f,0.9f));
        tonemap.setExposure(new Vector3f(0.7f,0.6f,0.8f));
        fpp.addFilter(tonemap);

        bloom=new BloomFilter();
        bloom.setDownSamplingFactor(2);
        bloom.setBlurScale(1.17f);
        bloom.setExposurePower(3.30f);
        bloom.setExposureCutOff(0.2f);
        bloom.setBloomIntensity(2.45f);
            
        fpp.addFilter(bloom);

        bloom.setBlurScale(1.f);
        bloom.setDownSamplingFactor(2);
        fog.setFogDensity(0.8f);
        bloom.setBloomIntensity(0.12f);
        tonemap.setExposure(new Vector3f(1.4f, 1f, 1f));
        bloom.setBloomIntensity(0.52f);

        FXAAFilter fxaa = new FXAAFilter();
        fpp.addFilter(fxaa);

        fog.setFogDensity(0.8f);
        tonemap.setExposure(new Vector3f(1.1f, 1.1f, 1f));
    }
 

    @Override
    public void onDisable(ComponentManager mng, Runner runner, DataStoreProvider dataStore) {
        ViewPortManager vpm = mng.getGlobalInstance(ViewPortManager.class);
        ViewPort mainViewPort = vpm.getMainSceneViewPort();
        mainViewPort.removeProcessor(fpp);
    }

    @Override
    public void updateAppLogic(ComponentManager mng, float tpf) {


    }

 
    
}
