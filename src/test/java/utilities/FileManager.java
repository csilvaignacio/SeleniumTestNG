package utilities;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.jsoup.Jsoup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.bidi.log.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    private static final String screenshotPath = "src/test/resources/screenshots";
    private static final String pageStructurePath = "src/test/resources/pageStructure";

    public static void tomarCaptura(String screenshotName){
        Logs.debug("Tomando screenshot");

        final var archivoCapturaPantalla = ((TakesScreenshot) new WebDriverProvider().get())
                .getScreenshotAs(OutputType.FILE);

        final var path = String.format("%s/%s.png",screenshotPath,screenshotName);

        try {
            FileUtils.copyFile(archivoCapturaPantalla, new File(path));
        }catch (IOException ioException){
            Logs.error("Error al tomar scrrenshot: ", ioException.getLocalizedMessage());
        }
    }

    public static void tomarFuentePagina(String nombreArchivo){
        Logs.debug("Tomando el page source de la pagina");

        final var path = String.format("%s/page-source-%s.html",pageStructurePath,nombreArchivo);

        try {
            final var file = new File(path);
            Logs.debug("Creando las directorios padres si es que no existen");
            if (file.getParentFile().mkdir()){
                final var fileWriter = new FileWriter(file);
                final var pageSources = new WebDriverProvider().get().getPageSource();
                fileWriter.write(Jsoup.parse(pageSources).toString());
                fileWriter.close();
            }
        } catch (IOException e) {
                Logs.error("Error al obtener el page source: %s"+e.getMessage());
        }
    }

    public static void eliminarEvidencia(){
        try{
            Logs.debug("Eliminando evidencia anterior");
            FileUtils.deleteDirectory(new File(screenshotPath));
            FileUtils.deleteDirectory(new File(pageStructurePath));
        }catch (IOException ioException){
            Logs.error("Error al borrar la evidencia: "+ioException.getMessage());
        }
    }

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] getScreenshot(){
        return ((TakesScreenshot) new WebDriverProvider().get()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "pageSource" , type = "text/html" , fileExtension = "txt")
    public static String getPageSource(){
        return Jsoup.parse(new WebDriverProvider().get().getPageSource()).toString();
    }


}
