import org.phprpc.PHPRPC_Client;
import org.phprpc.util.AssocArray;

public class IncClient {  
    public static void main(String[] args) {  
            PHPRPC_Client client = new PHPRPC_Client("http://app.17kaixin8.com/chengyu/chengyu.ser.php");  
            Object[] a = new Object[2];  
            a[0]="一鸣惊人";
            a[1]="UTF-8";
            AssocArray  object=  (AssocArray) client.invoke("ChengYu", a, true);  
            System.out.println(((AssocArray)object.get(0)).get(3));  
    }  
}  