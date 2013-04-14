package com.doward.persistantcookietest;

import android.util.Log;
import com.xtremelabs.robolectric.RobolectricConfig;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.runners.model.InitializationError;
import java.io.File;

public class TestRunner extends RobolectricTestRunner {

    public static final String MAIN_PROJECT_PATH = "";

    public TestRunner(Class<?> testClass) throws InitializationError {
        super(testClass, new RobolectricConfig(new File("")));
    }
}