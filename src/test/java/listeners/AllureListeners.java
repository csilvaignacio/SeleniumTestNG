package listeners;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import utilities.FileManager;
import utilities.Logs;
import utilities.WebDriverProvider;

public class AllureListeners implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result){
        Logs.debug("Before test stop de allure");
        final var resultType = result.getStatus();

        switch (resultType){
            case BROKEN, FAILED -> {
                if (new WebDriverProvider().get() != null){
                    FileManager.getScreenshot();
                    FileManager.getPageSource();
                }
            }
        }
    }
}
