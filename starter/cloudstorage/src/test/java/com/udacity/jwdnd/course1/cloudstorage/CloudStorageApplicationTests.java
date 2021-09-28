package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	public String baseURL;
	public static WebDriver driver;
	private String email = "mahdi@gmail.com";
	private String password = "157893";
	private String noteTitle ="how to become successfull";
	private String noteDescription = "Note Description Test";
	private String url = "google.com";
	private String userName = "wecllap.com";
	private String credPass = "123";

	@Autowired
	NoteService noteService;

	@Autowired
	CredentialService credentialsService;

	@Autowired
	EncryptionService encryptionService;


	@BeforeAll
	public static void loadDriver(){
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-minimized");
		driver = new ChromeDriver(options);
		driver.manage().window()
				.setPosition(new Point(-1100,0));


		driver.manage().window().maximize();


	}

	@BeforeEach
	public void beforeALL(){

		baseURL = "http://localhost:"+port;

		String firstname = "User";
		String lastname = "comic";

		WebDriverWait wait = new WebDriverWait(driver,2);
		driver.get(baseURL);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(email,credPass);
		wait.until(webDriver ->
				webDriver.findElement(By.tagName("title")));

		String titleTag = driver.getTitle();


		if(!titleTag.equals("Home")){

			driver.get(baseURL+"/signup");
			SignUpPage signupPage = new SignUpPage(driver);
			signupPage.signup(firstname,lastname,email,password);




		}
		else{
			HomePage homePage = new HomePage(driver);
			if(driver.getTitle().equals("Home"))

				homePage.logout();


		}




	}

	@AfterEach

	public void logout(){
		WebDriverWait wait = new WebDriverWait(driver,2);
		HomePage homePage = new HomePage(driver);
		String titleTag = driver.getTitle();
		if(titleTag.equals("Home"))
			homePage.logout();

		wait.until(webDriver -> webDriver.findElement(By.tagName("title")));
		Assertions.assertEquals("Login",driver.getTitle());




	}

	@AfterAll
	public static void quitDriver(){

		driver.quit();
		driver = null;


	}

	@Test
	public void getLoginPage(){
		driver.get(baseURL + "/login");
		Assertions.assertEquals("login",driver.getTitle());



	}

	@Test
	public void doLoginWithNonExistnUser(){
		WebDriverWait wait = new WebDriverWait(driver,2);
		driver.get(baseURL);

		LoginPage loginPage = new LoginPage(driver);

		loginPage.login("non existing user","justsomething");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login-error-msg")));
		String errorMsg = loginPage.getErrorMsg();
		Assertions.assertTrue(errorMsg.contains("Invalid username or password"));

		driver.get(baseURL+"/home");
		wait.until(webDriver ->
				webDriver.findElement(By.tagName("title")));

		Assertions.assertEquals("Login",driver.getTitle());

	}
	
	
	@Test
	public void doSuccessfulLoginAndLogout() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(driver,2);
		driver.get(baseURL);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(email,credPass);
		wait.until(webDriver->
				webDriver.findElement(By.tagName("title")));

		Assertions.assertEquals("Home",driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		wait.until(webDriver->
				webDriver.findElement(By.tagName("title")));

		Assertions.assertEquals("Login",driver.getTitle());

	}

	@Test
	public void createNoteAndVerifyDisplayed(){
		WebDriverWait wait = new WebDriverWait(driver,2);
		String noteTitle = "example note Title";
		String noteDesciption = "Description example";
		driver.get(baseURL);
		LoginPage loginPage  =  new LoginPage(driver);
		loginPage.login(email,credPass);

		wait.until(webDriver->
				webDriver.findElement(By.tagName("title")));

		NoteTab noteTab = new NoteTab(driver,noteService);

		Assertions.assertEquals("Home",driver.getTitle());

		noteTab.addNote(noteTitle,noteDesciption);
		ResultPage resultPage = new ResultPage(driver);
		Assertions.assertTrue(resultPage.checkSucessMessage());
		resultPage.clickContinue();

		Assertions.assertTrue(noteTab.checkIfNoteExists(noteTitle));
		Assertions.assertTrue(noteTab.chekNoteDescription(noteTitle,noteDesciption));








	}


}
