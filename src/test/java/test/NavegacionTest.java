package test;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.jfr.Description;
import org.openqa.selenium.By;

import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseTest;
import utilities.Logs;

import java.time.Duration;

public class NavegacionTest extends BaseTest {

    protected String url = "https://www.saucedemo.com";

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
    }


    @Test
    public void usuarioInvalido() {
        final var url = "https://www.saucedemo.com";

        Logs.debug("Navegando a la pagina: "+url);
        driver.get(url);

        Logs.debug("Esperando 3 segundos para que la pagina cargue");

        final var userNameLocator = By.cssSelector("#user-name");
        final var userName = driver.findElement(userNameLocator);
        userName.sendKeys("standard_user");

        final var passLocator = By.cssSelector("#password");
        final var password = driver.findElement(passLocator);
        password.sendKeys("fail");

        final var buttomLocator = By.cssSelector("#login-button");
        final var buttom = driver.findElement(buttomLocator);
        buttom.click();

        final var errorLocator = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");
        final var error = driver.findElement(errorLocator);

        softAssert.assertTrue(error.isDisplayed());
        softAssert.assertEquals(error.getText(), "Epic sadface: Username and password do not match any user in this service");
        softAssert.assertAll();

    }

    @Test
    public void validarLogin(){

        final var url = "https://www.saucedemo.com/";

        Logs.debug("Navegando a "+url);
        driver.get(url);

        Logs.debug("generando espera de 3 segundos");

        rellenarFormulario("standard_user","secret_sauce");

        final var inventory_list = driver.findElement(By.className("inventory_list"));

        Logs.debug("Verificando que llegue a pagina principal");
        Assert.assertTrue(inventory_list.isDisplayed());
    }

    @Test
    public void detalleProductoTest(){
        rellenarFormulario("standard_user","secret_sauce");
        final var listaDescripcion = driver.findElements(By.cssSelector("img[class='inventory_item_img']"));

        listaDescripcion.get(0).click();

        Logs.debug("Validando elementos");
        Logs.debug("verificando titulo");
        softAssert.assertTrue(
                driver.findElement(By.className("inventory_details_name")).isDisplayed());
        Logs.debug("verificando descripcion");
        softAssert.assertTrue(
                driver.findElement(By.className("inventory_details_desc")).isDisplayed());
        Logs.debug("verificando precio");
        softAssert.assertTrue(
                driver.findElement(By.className("inventory_details_price")).isDisplayed());
        Logs.debug("verificando imagen");
        softAssert.assertTrue(
                driver.findElement(By.className("inventory_details_img")).isDisplayed());
        Logs.debug("verificando boton");
        softAssert.assertTrue(
                driver.findElement(By.className("btn_primary")).isEnabled());
        softAssert.assertAll();
    }

    @Test
    public void dropdownTest(){
        Logs.debug("Navegando a la pagina "+ this.url);
        driver.get(url);

        Logs.debug("Ingresando usuario y password valido");
        rellenarFormulario("standard_user","secret_sauce");

        softAssert.assertTrue(driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html"));

        final var selectElement = driver.findElement(By.className("product_sort_container"));
        final var select = new Select(selectElement);

        select.selectByValue("za");

        final var listaProductos = driver.findElements(By.cssSelector(".inventory_item_name"));
        softAssert.assertTrue(listaProductos.get(0).getText().equals("Test.allTheThings() T-Shirt (Red)"),"Texto posicion 0 debe ser: 'Test.allTheThings() T-Shirt (Red)' ");
        softAssert.assertTrue(listaProductos.get(5).getText().equals("Sauce Labs Backpack"), "Texto posicion 5 debe ser: 'Sauce Labs Backpack'");
        softAssert.assertAll();
    }

    @Test(groups = "regression")
    @Description("Validando valores en precios de producto una vez invertido su orden de menor a mayor")
    @Severity(SeverityLevel.NORMAL)
    public void dropdownPrecioTest(){
        driver.get(url);

        rellenarFormulario("standard_user","secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));

        final var tituloPrincipal = driver.findElement(By.cssSelector(".app_logo"));
        final var titulo = tituloPrincipal.getText();

        Assert.assertEquals(titulo,"Swag Labs");
        final var dropdown = driver.findElement(By.className(".product_sort_container"));
        final var selects = new Select(dropdown);

        selects.selectByValue("lohi");

        final var listaPrecios = driver.findElements(By.cssSelector(".inventory_item_price"));
        final var primerItem = Double.parseDouble(listaPrecios.get(0).getText().replace("$",""));
        final var ultimoItem = Double.parseDouble(listaPrecios.get(listaPrecios.size()-1).getText().replace("$",""));
        softAssert.assertEquals(primerItem, 7.99);
        softAssert.assertEquals(ultimoItem, 49.99);
        softAssert.assertAll();
    }

    @Test(groups = "regression")
    @Description("Validando hipervínculo Facebook")
    @Severity(SeverityLevel.NORMAL)
    public void facebookTest(){
        Logs.debug("Ingresando userName y password");
        rellenarFormulario("standard_user", "secret_sauce");

        Logs.debug("Verificando página principal");
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "La página principal no esta cargando correctamente");

        final var hiperFacebook = driver.findElement(By.cssSelector("[data-test='social-facebook']"));
        Logs.debug("Verificando hipervinculo Facebook");
        softAssert.assertTrue(hiperFacebook.isDisplayed(), "btn facebook no es visible");
        softAssert.assertTrue(hiperFacebook.isEnabled(), "btn facebook no está habilitado");
        softAssert.assertEquals(hiperFacebook.getDomAttribute("href"), "https://www.facebook.com/saucelabs","La pagina actual no es https://www.facebook.com/saucelabs");
        softAssert.assertAll();
    }

    @Test(groups = "regression")
    @Description("Validando hipervínculo Linkedin")
    @Severity(SeverityLevel.NORMAL)
    public void linkedinTest(){
        Logs.debug("Ingresando userName y password");
        rellenarFormulario("standard_user", "secret_sauce");

        Logs.debug("Verificando página principal");
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "La página principal no esta cargando correctamente");

        final var hiperLinkedin = driver.findElement(By.cssSelector("[data-test='social-linkedin']"));
        Logs.debug("Verificando hípervinculo linkedin");
        softAssert.assertTrue(hiperLinkedin.isDisplayed(), "btn facebook no es visible");
        softAssert.assertTrue(hiperLinkedin.isEnabled(), "btn facebook no está habilitado");
        softAssert.assertEquals(hiperLinkedin.getDomAttribute("href"), "https://www.linkedin.com/company/sauce-labs/","https://www.linkedin.com/company/sauce-labs/");
        softAssert.assertAll();
    }

    @Test(groups = "regression")
    @Description("Validando hipervínculo Linkedin")
    @Severity(SeverityLevel.NORMAL)
    public void aboutTest(){
        Logs.debug("Ingresando userName y password");
        rellenarFormulario("standard_user", "secret_sauce");

        Logs.debug("Verificando página principal");
        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "La página principal no esta cargando correctamente");

        driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]")).click();

        final var optionAbout = driver.findElement(By.cssSelector("#about_sidebar_link"));
        Logs.debug("Verificando hípervinculo linkedin");
        softAssert.assertTrue(optionAbout.isDisplayed(), "btn facebook no es visible");
        softAssert.assertTrue(optionAbout.isEnabled(), "btn facebook no está habilitado");
        softAssert.assertEquals(optionAbout.getDomAttribute("href"), "https://saucelabs.com/","La pagina actual no es https://saucelabs.com/");
        softAssert.assertAll();
    }

    @Test(groups = "regression")
    @Description("Verificar retorno a pagina de login al hacer clic en opcion ´logout´")
    @Severity(SeverityLevel.NORMAL)
    public void logoutTest() throws InterruptedException{
        Logs.info("Ingresando usuario y contraseña");
        rellenarFormulario("standard_user", "secret_sauce");


        Logs.info("Verificando que llegue a la pagina principal");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));

        Logs.info("Abriendo el burger menu");
        driver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]")).click();


        Logs.info("Clic en logout");
        driver.findElement(By.cssSelector("#logout_sidebar_link")).click();

        Logs.info("Verificando retorno a página de login");
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.saucedemo.com/"));

        Logs.info("Fin ejecucion");
    }



    private void rellenarFormulario(String userName, String pass){
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Logs.info("Navegando a la página principal");
        driver.get(url);

        Logs.info("Esperando que cargue la pagina principal");
        final var userNameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(userName)));

        final var passwordInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(pass)));

        final var loginButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));

        userNameInput.sendKeys(userName);
        passwordInput.sendKeys(pass);
        loginButton.click();

        if (userName.equals("https://www.saucedemo.com/inventory.html")){
            Logs.info("Esperando a que cargue la pagina de shopping");
            wait.until(ExpectedConditions.
                    visibilityOfElementLocated(By.className("inventory_list")));
        }


    }



}
