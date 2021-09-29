package com.jr.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {

        // ChromeOptions options = new ChromeOptions();
        // options.setBinary("/Applications/Chromium.app/Contents/MacOS/Chromium");

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        // cap.setCapability(ChromeOptions.CAPABILITY, options);

        // WebDriver driver = new ChromeDriver(options);
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.107:4444/wd/hub"), cap);
        // driver.navigate().to("http://localhost:8001/tasks");
        driver.navigate().to("http://192.168.1.107:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            // click on add button
            driver.findElement(By.id("addTodo")).click();

            // write description
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            // write date
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            // click on save button
            driver.findElement(By.id("saveButton")).click();

            // validate success message
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            // quit browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            // click on add button
            driver.findElement(By.id("addTodo")).click();

            // write date
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            // click on save button
            driver.findElement(By.id("saveButton")).click();

            // validate message
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            // quit browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            // click on add button
            driver.findElement(By.id("addTodo")).click();

            // write description
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            // click on save button
            driver.findElement(By.id("saveButton")).click();

            // validate message
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            // quit browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            // click on add button
            driver.findElement(By.id("addTodo")).click();

            // write description
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            // write date
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2010");

            // click on save button
            driver.findElement(By.id("saveButton")).click();

            // validate success message
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            // quit browser
            driver.quit();
        }
    }

    @Test
    public void deveRemoverTarefaComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        try {
            // add task
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);

            // remove task
            driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
            message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        } finally {
            // quit browser
            driver.quit();
        }
    }
}
