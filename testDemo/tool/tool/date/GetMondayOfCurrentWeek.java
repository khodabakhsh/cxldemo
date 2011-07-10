package tool.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 获取周一和周日
 * @author Administrator
 *
 */
public class GetMondayOfCurrentWeek {

	public static void getMondayOfCurrentWeek() {
		Calendar calendar = new GregorianCalendar();
		// 取得本周一
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);//设置一周起始日期为周一，否则默认是周日
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		System.out.println("本周周一： "+calendar.getTime());

		// 取得本周日
		System.out.println("本周周日： "+new Date(calendar.getTime().getTime()
				+ (6 * 24 * 60 * 60 * 1000)));
		
	}
	public static void main(String[] args) {
		getMondayOfCurrentWeek();;
	}

}
