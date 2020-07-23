package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickNoteTab() throws InterruptedException {
        notesTab.click();
        Thread.sleep(500);
    }

    public void logout() throws InterruptedException {
        logout.submit();
        Thread.sleep(500);
    }
}
