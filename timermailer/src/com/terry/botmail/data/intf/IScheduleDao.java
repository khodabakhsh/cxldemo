package com.terry.botmail.data.intf;

import java.util.List;

import com.terry.botmail.model.Schedule;

/**
 * @author xinghuo.yao E-mail: yaoxinghuo at 126 dot com
 * @version create: 2010-2-3 下午04:32:36
 */
public interface IScheduleDao {
	public boolean saveSchedule(Schedule schedule);

	public Schedule getScheduleById(String id);

	public boolean deleteScheduleById(String id);

	public int getScheduleCount(String account);

	public List<Schedule> getReadyToToSchedules();

	public List<Schedule> getSchedulesByAccount(String account);
}
