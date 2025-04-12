package utilities;

import java.lang.reflect.Method;

import com.github.javafaker.Faker;
import listeners.SuiteListeners;
import listeners.TestListeners;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import utilities.Logs;

@Listeners({TestListeners.class, SuiteListeners.class})
public class BaseTest {
    protected Faker faker;
    protected SoftAssert softAssert;
    protected final String smoke = "regression";
    protected final String regression = "regression";
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void masterSetUp(Method method) {
        // Se registra el inicio del test, mostrando el nombre del metodo
        Logs.debug("Inicializando Driver");
        driver = new ChromeDriver();

        Logs.debug("Maximizando la pantalla");
        driver.manage().window().maximize();

        Logs.debug("Borrando las cookes");
        driver.manage().deleteAllCookies();

        new WebDriverProvider().set(driver);

        // Aquí puedes agregar configuraciones generales previas a cada test
        faker = new Faker();
        softAssert = new SoftAssert();

    }

    @AfterMethod(alwaysRun = true)
    public void masterTearDown(ITestResult result) {
        Logs.debug("Cerrando Driver");
        driver.quit();

        // Se registra el resultado del test
        String testName = result.getMethod().getMethodName();
        if (result.getStatus() == ITestResult.SUCCESS) {
            Logs.info("Test PASADO: " + testName);
        } else if (result.getStatus() == ITestResult.FAILURE) {
            Logs.error("Test FALLADO: " + testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            Logs.warn("Test OMITIDO: " + testName);
        }
    }
}
