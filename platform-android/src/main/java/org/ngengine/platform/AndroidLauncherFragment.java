
package org.ngengine.platform;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jme3.audio.AudioRenderer;
import com.jme3.input.JoyInput;
import com.jme3.input.android.AndroidSensorJoyInput;

import com.jme3.system.AppSettings;
import com.jme3.system.SystemListener;
import com.jme3.system.android.JmeAndroidSystem;
import com.jme3.system.android.OGLESContext;
import com.jme3.util.AndroidLogHandler;
import com.jme3.util.AndroidNativeBufferAllocator;
import com.jme3.util.BufferAllocatorFactory;

import android.app.AlertDialog;
import org.ngengine.NGEApplication.NGEAppRunner;

import org.ngengine.NGEApplication;
import org.ngengine.app.Main;
import org.ngengine.platform.android.AndroidThreadedPlatform;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class AndroidLauncherFragment extends Fragment implements
      SystemListener {
    private static final Logger logger = Logger.getLogger(AndroidLauncherFragment.class.getName());

    protected GLSurfaceView view = null;

    private NGEApplication app = null;
    private boolean finishOnAppStop = true;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setCloseActivityOnAppStop(boolean close){
        this.finishOnAppStop = close;
    }

    /**
     * onCreate is called once for this Fragment instance.
     * Note: AppSettings configuration has been removed. Configure settings in your app.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        initializeLogHandler();
        logger.fine("onCreate");
        super.onCreate(savedInstanceState);

        System.setProperty( BufferAllocatorFactory.PROPERTY_BUFFER_ALLOCATOR_IMPLEMENTATION, AndroidNativeBufferAllocator.class.getName());
        NGEPlatform.set(new AndroidThreadedPlatform(this.getContext()));
        NGEAppRunner runner = Main.main(new String[0]);
        app = runner.app();

        AppSettings settings = app.getSettings();
        settings.setAudioRenderer(AppSettings.ANDROID_OPENAL_SOFT);

        app.start();


        OGLESContext ctx = (OGLESContext) app.getJme3App().getContext();
        ctx.setSystemListener(this);
    }

    /**
     * Create the GLSurfaceView and return it as the Fragment's view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.fine("onCreateView");
        view = ((OGLESContext) app.getJme3App().getContext()).createView(requireActivity());
        JmeAndroidSystem.setView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.fine("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        logger.fine("onStart");
        super.onStart();
    }

    /**
     * When the Fragment resumes, call gainFocus() in the jME application.
     */
    @Override
    public void onResume() {
        logger.fine("onResume");
        super.onResume();
        gainFocus();
    }

    /**
     * When the Fragment pauses, call loseFocus() in the jME application.
     */
    @Override
    public void onPause() {
        logger.fine("onPause");
        loseFocus();
        super.onPause();
    }

    @Override
    public void onStop() {
        logger.fine("onStop");
        super.onStop();
    }

    /**
     * Clear references to the GLSurfaceView.
     */
    @Override
    public void onDestroyView() {
        logger.fine("onDestroyView");
        if (view != null) {
            if (view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        view = null;
        JmeAndroidSystem.setView(null);
        super.onDestroyView();
    }

    /**
     * Called by the system when the application is being destroyed.
     */
    @Override
    public void onDestroy() {
        logger.fine("onDestroy");
        if (app != null) {
            app.stop();
        }
        app = null;
        super.onDestroy();
    }

    /**
     * Called when an error has occurred. Shows an error message and logs the exception.
     */
    @Override
    public void handleError(final String errorMsg, final Throwable t) {
        String stackTrace = "";
        String title = "Error";

        if (t != null) {
            // Convert exception to string
            StringWriter sw = new StringWriter(100);
            t.printStackTrace(new PrintWriter(sw));
            stackTrace = sw.toString();
            title = t.toString();
        }

        final String finalTitle = title;
        final String finalMsg = (errorMsg != null ? errorMsg : "Uncaught Exception")
                + "\n" + stackTrace;

        logger.log(Level.SEVERE, finalMsg);

        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(finalTitle);
                builder.setMessage(finalMsg);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }




    /**
     * Removes the standard Android log handler due to an issue with not logging
     * entries lower than INFO level and adds a handler that produces
     * JME formatted log messages.
     */
    protected void initializeLogHandler() {
        Logger log = LogManager.getLogManager().getLogger("");
        for (Handler handler : log.getHandlers()) {
            if (log.getLevel() != null && log.getLevel().intValue() <= Level.FINE.intValue()) {
                Log.v("AndroidHarness", "Removing Handler class: " + handler.getClass().getName());
            }
            log.removeHandler(handler);
        }
        Handler handler = new AndroidLogHandler();
        log.addHandler(handler);
        handler.setLevel(Level.ALL);
    }

    @Override
    public void initialize() {
        app.getJme3App().initialize();

    }

    @Override
    public void reshape(int width, int height) {
        app.getJme3App().reshape(width, height);
    }

    @Override
    public void rescale(float x, float y) {
        app.getJme3App().rescale(x, y);
    }

    @Override
    public void update() {
        app.getJme3App().update();
    }

    @Override
    public void requestClose(boolean esc) {
        app.getJme3App().requestClose(esc);
    }

    @Override
    public void destroy() {
        if (app != null) {
            app.getJme3App().destroy();
        }
        if (finishOnAppStop) {
            requireActivity().finish();
        }
    }

    @Override
    public void gainFocus() {
        logger.fine("gainFocus");
        if (view != null) {
            view.onResume();
        }

        if (app != null) {
            // resume the audio
            AudioRenderer audioRenderer = app.getJme3App().getAudioRenderer();
            if (audioRenderer != null) {
                audioRenderer.resumeAll();
            }
            // resume the sensors (aka joysticks)
            if (app.getJme3App().getContext() != null) {
                JoyInput joyInput = app.getJme3App().getContext().getJoyInput();
                if (joyInput instanceof AndroidSensorJoyInput) {
                    ((AndroidSensorJoyInput) joyInput).resumeSensors();
                }
            }
            app.getJme3App().gainFocus();
        }
    }

    @Override
    public void loseFocus() {
        logger.fine("loseFocus");
        if (app != null) {
            app.getJme3App().loseFocus();
        }

        if (view != null) {
            view.onPause();
        }

        if (app != null) {
            // pause the audio
            AudioRenderer audioRenderer = app.getJme3App().getAudioRenderer();
            if (audioRenderer != null) {
                audioRenderer.pauseAll();
            }
            // pause the sensors (aka joysticks)
            if (app.getJme3App().getContext() != null) {
                JoyInput joyInput = app.getJme3App().getContext().getJoyInput();
                if (joyInput instanceof AndroidSensorJoyInput) {
                    ((AndroidSensorJoyInput) joyInput).pauseSensors();
                }
            }
        }
    }


}