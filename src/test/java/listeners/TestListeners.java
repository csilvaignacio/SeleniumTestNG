package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.FileManager;
import utilities.Logs;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        Logs.info("Comenzando el test: %s", result.getName());
        FileManager.eliminarEvidencia();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Logs.info("Test exitoso: %s", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Logs.info("Test fallido: %s", result.getName());
        FileManager.tomarCaptura(result.getName());
        FileManager.tomarFuentePagina(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Logs.info("Test ignorando: %s", result.getName());
    }
}

