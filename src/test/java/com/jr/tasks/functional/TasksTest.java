package com.jr.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    public WebDriver acessarAplicacao() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Chromium.app/Contents/MacOS/Chromium");

        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to("http://localhost:8001/tasks");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTarefaComSucesso() {
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
    public void naoDeveSalvarTarefaSemDescricao() {
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
    public void naoDeveSalvarTarefaSemData() {
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
    public void naoDeveSalvarTarefaComDataPassada() {
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
}
