package utilities;

import org.openqa.selenium.WebDriver;

public class WebDriverProvider {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public void set(WebDriver driver){
        driverThreadLocal.set(driver);
    }

    public WebDriver get(){
        return driverThreadLocal.get();
    }
}
