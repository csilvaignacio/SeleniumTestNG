package test;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.bidi.log.Log;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

public class NavegacionTest extends BaseTest {

    @Test(groups = {regression, smoke})
    public void ejercicio1Test() {
        final var url = "https://www.saucedemo.com/v1/inventory.html";

        Logs.debug("Navegando a: "+url);
        driver.get(url);

        Logs.info("Obteniendo la url actual");
        final var urlActual = driver.getCurrentUrl();

        Logs.info("Verificando que las urls sean iguales");
        Assert.assertEquals(url,urlActual);

    }

    @Test(groups = {regression, smoke})
    public void ejercicio2Test() {
        final var url = "https://www.saucedemo.com/v1/inventory.html";

        Logs.debug("Navegando a saucedemo");
        driver.get(url);

        Logs.debug("Navegando a google");
        driver.get("https://www.google.com");

        Logs.debug("Devolviendose a saucedemo");
        driver.navigate().back();

        final var urlActual = driver.getCurrentUrl();

        Logs.info("Verificando que url actual sea saucedemo");
        Assert.assertEquals(urlActual,url);
    }


    
}
