package tool;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * windows 下执行ipconfig /all ,然后匹配DNS Servers
 * unix下是去执行“cat /etc/resolv.conf”命令，然后匹配“nameserver”
 * 
 * @author Administrator
 * 
 */
public class GetDnsIp {
	public static void main(String[] args) throws Exception {
		System.out.println(getDnsIp());
	}

	public static String getDnsIp() {
		Process process;
		InputStream is = null;
		InputStreamReader isReader = null;
		BufferedReader bfReader = null;
		try {
			process = Runtime.getRuntime().exec(" ipconfig /all ");
			 is = process.getInputStream();
			 isReader = new InputStreamReader(is);
			 bfReader = new BufferedReader(isReader);
			String outString = null;
			while ((outString = bfReader.readLine()) != null) {
				if (outString.indexOf("DNS Servers") != -1) {
					return outString.substring(outString.lastIndexOf(":") + 1)
							.trim();
				}
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				bfReader.close();
				isReader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}

	}
}
