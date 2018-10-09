package crawler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class Thread1 extends Thread {
	public static String sessionId;
	@Override
	public void run() {
		String url = "https://www.taodaxiang.com/credit2";
		System.setProperty("phantomjs.binary.path", "E:\\mine\\learning\\spider\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
		driver.get(url);
		sessionId = driver.manage().getCookieNamed("PHPSESSID").getValue();
		System.out.println(sessionId);
		driver.close();
	}
	
}
