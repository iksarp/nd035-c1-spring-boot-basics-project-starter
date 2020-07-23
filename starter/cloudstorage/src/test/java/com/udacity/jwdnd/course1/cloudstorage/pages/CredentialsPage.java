package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {

    @FindBy(id = "show-credential-modal")
    private WebElement showCredentialModal;

    @FindBy(id = "credential-url")
    private WebElement modalUrl;

    @FindBy(id = "credential-username")
    private WebElement modalUsername;

    @FindBy(id = "credential-password")
    private WebElement modalPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement modalSubmit;

    private final WebDriver driver;

    public CredentialsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickAddCredential() throws InterruptedException {
        showCredentialModal.click();
        Thread.sleep(500);
    }

    public void submitCredentialsData(String url, String username, String password) throws InterruptedException {
        modalUrl.clear();
        modalUrl.sendKeys(url);
        modalUsername.clear();
        modalUsername.sendKeys(username);
        modalPassword.clear();
        modalPassword.sendKeys(password);
        modalSubmit.submit();
        Thread.sleep(500);
    }

    public void clickEditCredential(int index) throws InterruptedException {
        driver.findElements(By.id("rowCredentialEdit")).get(index).click();
        Thread.sleep(500);
    }

    public void clickDeleteCredential(int index) throws InterruptedException {
        driver.findElements(By.id("rowCredentialDelete")).get(index).click();
        Thread.sleep(500);
    }

    public String getCredentialUrl(int index) {
        return driver.findElements(By.id("rowCredentialUrl")).get(index).getText();
    }

    public String getCredentialUsername(int index) {
        return driver.findElements(By.id("rowCredentialUsername")).get(index).getText();
    }

    public String getCredentialPassword(int index) {
        return driver.findElements(By.id("rowCredentialPassword")).get(index).getText();
    }

    public int getCredentialsCount() {
        return driver.findElements(By.id("rowCredentialUrl")).size();
    }

    public String getSuccessText() {
        return driver.findElement(By.id("credential-success-msg")).getText();
    }
}
