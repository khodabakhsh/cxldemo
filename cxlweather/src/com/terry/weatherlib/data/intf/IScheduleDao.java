package com.terry.weatherlib.data.intf;

import java.util.Date;
import java.util.List;

import com.terry.weatherlib.model.Schedule;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午04:32:36
 */
public interface IScheduleDao {
	public boolean saveSchedule(Schedule schedule);

	public Schedule getScheduleById(String id);

	public boolean deleteScheduleById(String id);

	public boolean deleteScheduleByIdAndAccount(String id, String account);

	public boolean updateScheduleById(String id, String email, String city,
			Date sdate, int type, int days, String remark);

	public int getScheduleCountByAccount(String account);

	public int getScheduleCount();

	public List<Schedule> getReadyToToSchedules();

	public List<Schedule> getSchedulesByAccount(String account, int start,
			int limit);
}
