package crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.alibaba.fastjson.JSONObject;

public class Credit2 {
	
	
	private String sessionId = null;
	private WebDriver driver;
	
	public Credit2() {
		System.setProperty("phantomjs.binary.path", "E:\\mine\\learning\\spider\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		driver = new PhantomJSDriver();
	}
	
	public void getSessionId() {
		String url = "https://www.taodaxiang.com/credit2";
		driver.get(url);
		sessionId = driver.manage().getCookieNamed("PHPSESSID").getValue();
		System.out.println(sessionId);
	}
	
	public void close() {
		driver.quit();
	}
	
	public JSONObject request(String account) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject data = new JSONObject();
		String url = "https://www.taodaxiang.com/credit2/index/get";
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("account", account));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			httpPost.setHeader("Cookie","PHPSESSID="+sessionId);
			CloseableHttpResponse resp2 = httpClient.execute(httpPost);
			HttpEntity he = resp2.getEntity();  
			String respContent = EntityUtils.toString(he,"UTF-8");
			System.out.println(respContent);
			data = JSONObject.parseObject(respContent);
			System.out.println(data.toJSONString());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static void main(String[] args) {
		Credit2 c = new Credit2();
		c.getSessionId();
		Scanner scan = new Scanner(System.in);
		boolean flag = true;
		while(flag) {
			System.out.print("请输入旺旺号：");
			String s = scan.next();
			if("quit".equals(s)) {
				break;
			}
			JSONObject jsonObj = c.request(s);
			System.out.println("account: "+jsonObj.get("account")+" sex: "+jsonObj.get("sex"));
		}
		c.close();
	}
}
