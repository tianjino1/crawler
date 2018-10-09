package crawler;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class Selenium {

	
	public void credit() {
		String url = "https://www.taodaxiang.com/credit2";
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.get(url);
		String PHPSESSID = driver.manage().getCookieNamed("PHPSESSID").getValue();
		System.out.println(PHPSESSID);
		driver.close();
	}
	public void credit2() {
		String url = "https://www.taodaxiang.com/credit2";
		
		System.setProperty("phantomjs.binary.path", "E:\\mine\\learning\\spider\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.get(url);
		String PHPSESSID = driver.manage().getCookieNamed("PHPSESSID").getValue();
		System.out.println(PHPSESSID);
		
		driver.close();
	}
	
	
	public static void main(String[] args) {
		Selenium s = new Selenium();
		s.credit2();
	}
}
