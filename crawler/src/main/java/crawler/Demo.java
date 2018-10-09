package crawler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


public class Demo {
	
	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	private static String PHPSESSID;
	//创建一个HttpContext对象，用来保存Cookie
    private static HttpClientContext httpClientContext = HttpClientContext.create();
    private static CookieStore cookieStore1;
	
	public static void dataSpider(String account) {
		String url = "https://www.taodaxiang.com/credit2/index/get";
		//post请求
		HttpPost httpPost = new HttpPost(url);
		httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore1).build();
		//参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("account", account));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		httpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
		httpPost.setHeader("Accept-Encoding","gzip, deflate, br");
		httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
		httpPost.setHeader("Connection","keep-alive");
		httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader("Host","www.taodaxiang.com");
		httpPost.setHeader("Origin","https://www.taodaxiang.com");
		httpPost.setHeader("Referer","https://www.taodaxiang.com/credit2");
		httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		//httpPost.setHeader("Cookie","PHPSESSID=v4cqohm479g4j36qtdm2i9v8q5");
		//httpPost.setHeader("Cookie","PHPSESSID=oacg8u8cp3ql67u4qh0kgnap24;");
		httpPost.setHeader("Cookie","PHPSESSID="+PHPSESSID);
		System.out.println(PHPSESSID);
		try {
			HttpResponse resp = httpClient.execute(httpPost,httpClientContext);
			if(resp.getStatusLine().getStatusCode() == 200) {            
				HttpEntity he = resp.getEntity();  
				String respContent = EntityUtils.toString(he,"UTF-8");
				JSONObject data = JSONObject.parseObject(respContent);
				//System.out.println(data.toJSONString());
				//System.out.println(data.get("account"));
				System.out.println(respContent);
			}else {
				dataSpider(account);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void credit2() {
		String url = "https://www.taodaxiang.com/credit2";
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
		httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.9");
		httpGet.setHeader("Connection","keep-alive");
		httpGet.setHeader("Host","www.taodaxiang.com");
		httpGet.setHeader("Referer","https://www.taodaxiang.com/credit2");
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36");
		httpGet.setHeader("Upgrade-Insecure-Requests","1");
		try {
			HttpResponse resp = httpClient.execute(httpGet,httpClientContext);
			CookieStore cookieStore = httpClientContext.getCookieStore();
			saveCookieStore(cookieStore,"cookie");
			try {
				CookieStore cookieStore1 = readCookieStore("cookie");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();  
				String respContent = EntityUtils.toString(he,"UTF-8");
				//System.out.println(respContent);
			}
			Header[] headers = resp.getAllHeaders();
			System.out.println(headers[6].getValue());
			System.out.println(headers[6].getValue().split(" ")[0].substring(10));
			PHPSESSID = headers[6].getValue().split(" ")[0].substring(10);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void get() {
		try {
			URL url = new URL("https://www.taodaxiang.com/credit2");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			Map<String, List<String>> map = connection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				if("Set-Cookie".equals(entry.getKey())) {
					PHPSESSID = entry.getValue().get(0).substring(10,40);
					System.out.println(PHPSESSID);
				}
                System.out.println("Key : " + entry.getKey() + 
                        " ,Value : " + entry.getValue());
            }
			dataSpider("天机");
			connection.connect();// 连接会话
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} // 把字符串转换为URL请求地址
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//使用序列化的方式保存CookieStore到本地文件，方便后续的读取使用
	  private static void saveCookieStore( CookieStore cookieStore, String savePath ) throws IOException {

	    FileOutputStream fs = new FileOutputStream(savePath);
	    ObjectOutputStream os =  new ObjectOutputStream(fs);
	    os.writeObject(cookieStore);
	    os.close();

	  }

	  //读取Cookie的序列化文件，读取后可以直接使用
	  private static CookieStore readCookieStore( String savePath ) throws IOException, ClassNotFoundException {

	    FileInputStream fs = new FileInputStream("cookie");//("foo.ser");
	    ObjectInputStream ois = new ObjectInputStream(fs);
	    CookieStore cookieStore = (CookieStore) ois.readObject();
	    ois.close();
	    return cookieStore;


	  }

	public static void main(String[] args) {
		//credit2();
		//dataSpider("天机");
		get();
	}
}
