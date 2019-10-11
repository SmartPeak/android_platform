package com.pos.sdkdemo.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;


/**
 * 用来计算PBOC流程的效率，计算时间
 * Time of the calculation process
 * @author liudy
 *
 */
public class TimerCountTools {
	private Date timestart = null;
	private Date timestop = null;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public TimerCountTools()
	{
		
	}
	
	public void start()
	{
		timestart = new Date(System.currentTimeMillis());
	}
	
	public void stop()
	{
		timestop = new Date(System.currentTimeMillis());
	}
	
	/**
	 * 返回时间差，毫秒级别
	 * Returns the time difference,Unit: ms
	 * @return
	 */
	public long getProcessTime()
	{
		long mils = timestop.getTime() - timestart.getTime();
		return mils;
	}
}
