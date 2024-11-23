package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException

	{
		// properties class

		Properties prop = new Properties();    // System.getProperty("user.dir")- current project path
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "//src//main//java//rahulshettyacademy//resources//GlobalData.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		//String browserName = prop.getProperty("browser");

		if (browserName.contains("chrome")) {   // equalsIgnoreCase
			ChromeOptions options = new ChromeOptions();
	//		WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")){
			options.addArguments("headless");
			}		
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));//full screen  // but this step is optional. if you find flakiness in test,element is not visible in test,then you can add this step.if not you can ignore this step. it depends on your system dimenssion screen.
                                                                      // when we excute test in headless mode (in maximize mode),selenium is not able to interact with place order button (because the place order button goes below)
		} else if (browserName.equalsIgnoreCase("firefox")) {                                                           // to interact with button , we keep full screen instead maximize.this setSize() is top of maximize()
	//		System.setProperty("webdriver.gecko.driver","/Users/rahulshetty//documents//geckodriver");
			driver = new FirefoxDriver();
			// Firefox
		} else if (browserName.equalsIgnoreCase("edge")) {  // Edge
//			System.setProperty("webdriver.edge.driver", "edge.exe");
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
	//read json to string
	String jsonContent = 	FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
	
	//String to HashMap- Jackson Databind (depend.)
	ObjectMapper mapper = new ObjectMapper();
	List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
      });
	return data;
	//{map, map}
	}
	
	public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	// extent report
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
}
