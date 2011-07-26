import java.util.Calendar;
import java.util.Date;

public class JustMain {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(calendar.getTime());

	}

}
