package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPage {

    @FindBy(id = "show-note-modal")
    private WebElement showNoteModal;

    @FindBy(name = "noteTitle")
    private WebElement modalTitleNote;

    @FindBy(name = "noteDescription")
    private WebElement modalDescNote;

    @FindBy(id = "noteSubmit")
    private WebElement modalNoteSubmit;

    private final WebDriver driver;

    public NotesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickAddNote() throws InterruptedException {
        showNoteModal.click();
        Thread.sleep(500);
    }

    public void submitNoteData(String title, String description) throws InterruptedException {
        modalTitleNote.clear();
        modalTitleNote.sendKeys(title);
        modalDescNote.clear();
        modalDescNote.sendKeys(description);
        modalNoteSubmit.submit();
        Thread.sleep(500);
    }

    public void clickEditNote(int index) throws InterruptedException {
        driver.findElements(By.id("rowNoteEdit")).get(index).click();
        Thread.sleep(500);
    }

    public void clickDeleteNote(int index) throws InterruptedException {
        driver.findElements(By.id("rowNoteDelete")).get(index).click();
        Thread.sleep(500);
    }

    public String getNoteTitle(int noteIndex) {
        return driver.findElements(By.id("rowNoteTitle")).get(noteIndex).getText();
    }

    public String getNoteDesc(int noteIndex) {
        return driver.findElements(By.id("rowNoteDesc")).get(noteIndex).getText();
    }

    public int getNotesCount() {
        return driver.findElements(By.id("rowNoteTitle")).size();
    }

    public String getSuccessText() {
        return driver.findElement(By.id("note-success-msg")).getText();
    }

    public String getErrorText() {
        return driver.findElement(By.id("note-error-msg")).getText();
    }
}
