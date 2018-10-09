package crawler;

import java.io.IOException;
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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class Demo2 {

	private String PHPSESSID;
	
	public Demo2() {
	}

	public void get() {
		CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		String url = "https://www.taodaxiang.com/credit2/index/get";
		String onlineUrl = "http://amos.alicdn.com/online.aw";
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			if(resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();  
				String respContent = EntityUtils.toString(he,"UTF-8");
				//System.out.println(respContent);
			}
			List<Cookie> cookies = cookieStore.getCookies();
			PHPSESSID = cookies.get(0).getValue();
            for (int i = 0; i < cookies.size(); i++) {
            	System.out.println(cookies.get(i).getValue());
                /*if (cookies.get(i).getName().equals("JSESSIONID")) {
                    JSESSIONID = cookies.get(i).getValue();
                }
                if (cookies.get(i).getName().equals("cookie_user")) {
                    cookie_user = cookies.get(i).getValue();
                }*/
            }

			/*Header[] headers = resp.getAllHeaders();
			System.out.println(headers[7].getValue());
			System.out.println(headers[7].getValue().split(" ")[0].substring(10));
			PHPSESSID = headers[7].getValue().split(" ")[0].substring(10);*/
			resp.close();
			
			
			
			/*HttpGet onlinePost = new HttpGet(onlineUrl+"?v=2&uid=天机&site=cntaobao&s=2&charset=utf-8");
			List<NameValuePair> onlineNameValuePairs = new ArrayList<NameValuePair>();
			onlineNameValuePairs.add(new BasicNameValuePair("v", "2"));
			onlineNameValuePairs.add(new BasicNameValuePair("uid", "天机"));
			onlineNameValuePairs.add(new BasicNameValuePair("site", "cntaobao"));
			onlineNameValuePairs.add(new BasicNameValuePair("s", "2"));
			onlineNameValuePairs.add(new BasicNameValuePair("charset", "utf-8"));*/
			
			
			
			url = "https://www.taodaxiang.com/credit2/index/get";
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("account", "天机"));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			httpPost.setHeader("Cookie","PHPSESSID="+PHPSESSID);
			//httpPost.setHeader("Cookie","PHPSESSID=bba836clnh8po8j0eot0jfn993");
			CloseableHttpResponse resp2 = httpClient.execute(httpPost);
			HttpEntity he = resp2.getEntity();  
			String respContent = EntityUtils.toString(he,"UTF-8");
			JSONObject data = JSONObject.parseObject(respContent);
			//System.out.println(data.toJSONString());
			//System.out.println(data.get("account"));
			System.out.println(respContent);
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void urlConnectionGet() {

		try {
			URL url = new URL("https://www.taodaxiang.com/credit2");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			Map<String, List<String>> map = connection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				if("Set-Cookie".equals(entry.getKey())) {
					PHPSESSID = entry.getValue().get(0).substring(10,37);
					System.out.println(PHPSESSID);
				}
                System.out.println("Key : " + entry.getKey() + 
                        " ,Value : " + entry.getValue());
            }
			connection.connect();// 连接会话
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} // 把字符串转换为URL请求地址
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Demo2 d = new Demo2();
		d.get();
		d.urlConnectionGet();
	}
}
