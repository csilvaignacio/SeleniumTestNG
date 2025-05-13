package test;

import org.openqa.selenium.By;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

import java.time.Duration;

public class TutorialPoint extends BaseTest {

    protected String urlTutorialspoint = "https://www.tutorialspoint.com/selenium/practice/browser-windows.php";
    protected String urlIframes = "https://www.tutorialspoint.com/selenium/practice/nestedframes.php";

    @Test
    public void verificacionTest(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get(urlTutorialspoint);
        final var paginaPrincipal = driver.getWindowHandle();
        Logs.debug("tabId: %s",paginaPrincipal);

        Logs.info("Verificamos que el elemento sea clickeable y lo obtenemos ");
        final var btnNewTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[text()= 'New Tab']")));

        Logs.info("Damos clic en el boton");
        btnNewTab.click();

        final var windowHandlesSet = driver.getWindowHandles();
        Logs.debug("Window handle set: %s", windowHandlesSet);

        for (var Handle : windowHandlesSet){
            if (!Handle.equals(paginaPrincipal)) {
                driver.switchTo().window(Handle);
            }
        }

        Logs.info("Verificando el texto");
        Assert.assertTrue(
                driver.findElement(By.xpath("//h1[text()='New Tab']")).isDisplayed()
        );

        Logs.info("Cerrando pestaña actual");
        driver.close();

        Logs.debug("Regresando a la pagina principal");
        driver.switchTo().window(paginaPrincipal);

    }

    @Test
    public void test2(){
        driver.get(urlTutorialspoint);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        final var ventanaPrincipal = driver.getWindowHandle();
        Logs.debug("Pagina principa: %s",ventanaPrincipal);

        final var tituloPresente = driver.findElement(By.xpath("//button[text()='New Window']"));
        tituloPresente.click();

        final var windowHandleSet = driver.getWindowHandles();
        Logs.debug("Sets de ventanas: %s",windowHandleSet);

        for (String handle : windowHandleSet){
            if (!handle.equals(ventanaPrincipal)){
                driver.switchTo().window(handle);
            }
        }

        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='New Window']")).isDisplayed());

        Logs.info("Cerrando ventana");
        driver.quit();

        Logs.info("Regresando a la pagina principal");
        driver.switchTo().window(ventanaPrincipal);

        Logs.info("Verificando que se regreso a la pagina principal");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Browser Windows']")).isDisplayed());
    }

    @Test
    public void iframeTest(){
        Logs.info("navegando a %s",urlIframes);
        driver.get(urlIframes);

        Logs.debug("Nos posicionamos en el iframe");
        driver.switchTo().frame("frame1");

        Logs.info("Verificamos que el titulo sea 'New Tab'");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='New Tab']")).isDisplayed());
    }

}
