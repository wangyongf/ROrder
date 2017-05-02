package com.yongf.rorder;

import org.junit.runners.model.InitializationError;
import org.robolectric.RoboSettings;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;

import java.io.File;

public class MyRobolectricTestRunner extends RobolectricTestRunner {

    private static final String APP_MODULE_NAME = "app";

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     *
     * @throws InitializationError if junit says so
     */
    public MyRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        RoboSettings.setMavenRepositoryId("alimaven");
        RoboSettings.setMavenRepositoryUrl("http://maven.aliyun.com/nexus/content/groups/public/");

        System.out.println("testClass=" + testClass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String userDir = System.getProperty("user.dir", "./");
        File current = new File(userDir);
        String prefix;
        if (new File(current, APP_MODULE_NAME).exists()) {
            System.out.println("Probably running on AndroidStudio");
            prefix = "./" + APP_MODULE_NAME;
        }
        else if (new File(current.getParentFile(), APP_MODULE_NAME).exists()) {
            System.out.println("Probably running on Console");
            prefix = "../" + APP_MODULE_NAME;
        }
        else {
            throw new IllegalStateException("Could not find app module, app module should be \"app\" directory in the project.");
        }
        System.setProperty("android.manifest", prefix + "/src/main/AndroidManifest.xml");
        System.setProperty("android.resources", prefix + "/src/main/res");
        System.setProperty("android.assets", prefix + "/src/test/assets");

        return super.getAppManifest(config);
    }
}
