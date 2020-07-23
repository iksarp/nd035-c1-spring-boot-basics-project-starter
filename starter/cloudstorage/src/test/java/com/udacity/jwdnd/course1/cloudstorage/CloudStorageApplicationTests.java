package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private SignUpPage signUpPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private NotesPage notesPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testHomeNotLoggedInRedirect() {
		driver.get("http://localhost:" + this.port + "/home");

		assertEquals("Login", driver.getTitle());
	}

	@Test
	void logout() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signUpPage = new SignUpPage(driver);
		signUpPage.submitCredentials("test", "test");
		assertNotNull(signUpPage.getSuccessMessage());

		driver.get("http://localhost:" + port + "/login");

		loginPage = new LoginPage(driver);
		loginPage.submitCredentials("test", "test");

		assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logout();

		assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	void notesTab() throws InterruptedException {
		givenCreatedAccountAndLoggedIn();

		homePage = new HomePage(driver);
		homePage.clickNoteTab();

		notesPage = new NotesPage(driver);
		notesPage.clickAddNote();
		notesPage.submitNoteData("some title", "some desc");
		assertEquals("some title", notesPage.getNoteTitle(0));
		assertEquals("some desc", notesPage.getNoteDesc(0));
		assertEquals("Note added.", notesPage.getSuccessText());

		notesPage.clickEditNote(0);
		notesPage.submitNoteData("edited title", "edited desc");
		assertEquals("edited title", notesPage.getNoteTitle(0));
		assertEquals("edited desc", notesPage.getNoteDesc(0));
		assertEquals("Note updated.", notesPage.getSuccessText());

		notesPage.clickAddNote();
		notesPage.submitNoteData("some title 2", "some desc 2");
		assertEquals(2, notesPage.getNotesCount());
		assertEquals("some title 2", notesPage.getNoteTitle(1));
		assertEquals("some desc 2", notesPage.getNoteDesc(1));
		assertEquals("Note added.", notesPage.getSuccessText());

		notesPage.clickDeleteNote(0);
		assertEquals(1, notesPage.getNotesCount());
		assertEquals("Note deleted.", notesPage.getSuccessText());
	}

	private void givenCreatedAccountAndLoggedIn() throws InterruptedException {
		driver.get("http://localhost:" + port + "/signup");
		signUpPage = new SignUpPage(driver);
		signUpPage.submitCredentials("test", "test");
		driver.get("http://localhost:" + port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.submitCredentials("test", "test");
	}

}
