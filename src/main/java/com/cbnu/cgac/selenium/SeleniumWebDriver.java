package com.cbnu.cgac.selenium;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SeleniumWebDriver {



    public static void main(String args[]){

        /*

        SeleniumWebDriver googleChromeRemoting = new SeleniumWebDriver();
        WebDriver driver = googleChromeRemoting.openGogleDocs();

        //googleChromeRemoting.startVoiceTyping(driver);

        driver.get("https://docs.google.com/document/d/10_VY5nw2P0IPgy_iiD7cK0QYLM_Og9dZlpHBJrq1uUQ/edit");
        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.findElement(By.tagName("body")).getText());

        */
    }





    public WebDriver openGogleDocs(String usrEmail, String usrPassword){

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        
        ///System.setProperty("webdriver.chrome.driver", ""+new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/chromedriver.exe"))));
       
        System.setProperty("webdriver.firefox.marionette","src/main/resources/geckodriver.exe");
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        
        ChromeDriverManager.getInstance().setup();

//        ChromeOptions options = new ChromeOptions();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("test-type");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--disable-user-media-security");
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-fake-ui-for-media-stream");

//        WebDriver driver = new ChromeDriver(options);
        WebDriver driver = new FirefoxDriver(options);

        driver.get("https://docs.google.com/document/create");

       // driver.get("https://docs.google.com/document/d/16EJ3ajEwoc8ODInoDAyPveVnuHIjM8IdjKoFIfYujGc/edit");

        // driver.get("https://docs.google.com/document/d/12ThaiKoQA89-KYd4_YVs_EBcgJJ1Lx_Z9ThOERF7YfA/edit");

        //TODO: TO LOGIN WITH GMAIL TA PU
        //driver.findElement(By.id("identifierId")).sendKeys("YOUR EMAIL");
        driver.findElement(By.id("identifierId")).sendKeys(usrEmail);
        driver.findElement(By.id("identifierNext")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //what is "//input[@name='password']")) SELECT INPUT name='password' for sendKey Password tov
        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(password));
        //password.sendKeys("YOUR EMAIL PASSWORD");
        password.sendKeys(usrPassword);
        driver.findElement(By.id("passwordNext")).click();



        //Then invoke method again for your second request(I am not try this code maybe you need to create new driver object)

        /// ERROR THIS PLACE IT DOESN'T OPEN THIS LINK OPEN BY SELF TIC TOV AND TRY TO FIND THIS ERROR PONG WHY???
        //driver.get("https://docs.google.com/document/d/12ThaiKoQA89-KYd4_YVs_EBcgJJ1Lx_Z9ThOERF7YfA/edit");
        //https://docs.google.com/document/d/1XIiiJwBsWYqPo-fzVPRGPKVgn-e5b8lR2RAhXvqWjUI/edit


        //driver.findElement(By.className("docs-homescreen-templates-templateview-preview docs-homescreen-templates-templateview-preview-showcase")).click();



        //driver.findElement(By.className("docs-title-input")).sendKeys("Title");

        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.TAB));
        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.TAB));
        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.TAB));
        // driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.ENTER));





        /*
        while(true){
            try {
                if(driver.findElement(By.cssSelector("docs-mic-control-recording")) == null){
                    driver.findElement(By.cssSelector("docs-mic-control")).click();
                }
            }catch(Exception ex){
                ex.printStackTrace();
                driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
            }
        }
        */

        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));

        return driver;

    }


    public void stopViceTyping(WebDriver driver){
        try {
            if(driver.findElement(By.cssSelector("docs-mic-control-recording")) != null){
                driver.findElement(By.cssSelector("docs-mic-control-recording")).click();
                System.out.println("Stop voice typing");
            }
        }catch(Exception ex){
            ex.printStackTrace();
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
        }
    }


    public void startVoiceTyping(  WebDriver driver){

        Timer timer = new Timer();
        timer.schedule( new TimerTask()
        {
            public void run() {
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            // do your work
                            System.out.println("Checked Voice typing");
                            try {
                                if(driver.findElement(By.cssSelector("docs-mic-control-recording")) == null){
                                    driver.findElement(By.cssSelector("docs-mic-control")).click();
                                    System.out.println("Clicked");
                                }
                            }catch(Exception ex){
                                ex.printStackTrace();
                                driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
                            }
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            System.err.println("Error on Thread Sleep");
                        }
                    }
                 }.start();
            }
        }, 0, 30*(1000*1));

/*
        while(true){
            try {
                if(driver.findElement(By.cssSelector("docs-mic-control-recording")) == null){
                    driver.findElement(By.cssSelector("docs-mic-control")).click();
                }
            }catch(Exception ex){
                ex.printStackTrace();
                driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
            }
        }

           */

/*
        Timer timer = new Timer();
        timer.schedule( new TimerTask()
        {
            public void run() {
                // do your work
                System.out.println("Checked");
                try {
                    if(driver.findElement(By.cssSelector("docs-mic-control-recording")) == null){
                        driver.findElement(By.cssSelector("docs-mic-control")).click();
                        System.out.println("Clicked");
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                    driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
                }

            }
        }, 0, 30*(1000*1));
*/


    }



    public void clickVoiceTypingOneTime(WebDriver driver){

        new Thread() {
            @Override
            public void run() {
                try {
                    // do your work
                    System.out.println("Checked Voice typing One");
                    try {
                        if (driver.findElement(By.cssSelector("docs-mic-control-recording")) == null) {
                            driver.findElement(By.cssSelector("docs-mic-control")).click();
                            System.out.println("Clicked");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
                    }
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.err.println("Error on Thread Sleep");
                }
            }
        }.start();
            /*
            try {
                if(driver.findElement(By.cssSelector("docs-mic-control-recording")) == null){
                    driver.findElement(By.cssSelector("docs-mic-control")).click();
                    System.out.println("Clicked");
                }
            }catch(Exception ex){
                ex.printStackTrace();
                driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "s"));
            }
            */
    }


    public void ceateNewDocs(WebDriver driver){

        driver.get("https://docs.google.com/document/create");

    }



    public void setGoogleDocsTitle(WebDriver driver, String title){

        new Thread() {
            @Override
            public void run() {
                try {
                    // do your work
                    System.out.println("Set google docs title");
                    driver.findElement(By.className("docs-title-input")).sendKeys(title);
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.err.println("Error on Thread Sleep");
                }
            }
        }.start();

    }


}
