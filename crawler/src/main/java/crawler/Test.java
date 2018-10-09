package crawler;

import java.util.Scanner;



public class Test {

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
			c.request(s);
		}
		c.close();
	}
}
