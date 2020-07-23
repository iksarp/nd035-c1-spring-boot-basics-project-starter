package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void submitCredentials(final String username, final String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.submit();
    }

    public String getSuccessMessage() {
        return successMsg.getText();
    }
}
