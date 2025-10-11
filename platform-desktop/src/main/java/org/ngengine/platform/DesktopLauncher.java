package org.ngengine.platform;

import org.ngengine.app.Main;
import org.ngengine.platform.jvm.JVMAsyncPlatform;

import java.io.IOException;
import org.ngengine.NGEApplication.NGEAppRunner;

public class DesktopLauncher {
    public static void main(String[] args) throws IOException {
        NGEPlatform.set(new JVMAsyncPlatform());
        NGEAppRunner runner = Main.main(args);
        runner.start();
    }
    
}
