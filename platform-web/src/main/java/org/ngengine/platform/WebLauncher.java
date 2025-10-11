package org.ngengine.platform;

import org.ngengine.NGEApplication.NGEAppRunner;
import org.ngengine.app.Main;
import org.ngengine.platform.teavm.TeaVMPlatform;
import org.ngengine.web.context.WebSystem;
import com.jme3.system.JmeSystem;


public class WebLauncher {

    public static void main(String[] args)   {
        new Thread(()->{ // must be a thread to run in a suspendable context in teavm
            NGEPlatform.set(new TeaVMPlatform());
            JmeSystem.setSystemDelegate(new WebSystem());
    
            NGEAppRunner runner = Main.main(args);
            runner.start();
        }).start();
    }
}