package com.ctrip.xpipe.utils;

import java.util.Properties;

/**
 * @author wenchao.meng
 *
 * 2016年3月29日 下午3:33:17
 */
public class OsUtils {
	
	private static final int CPU_COUNT;
	private static long startTime = System.currentTimeMillis();
			
	static{
		
		String cpuCount = System.getProperty("CPU_COUNT"); 
		if( cpuCount != null){
			CPU_COUNT = Integer.parseInt(cpuCount);
		}else{
			CPU_COUNT = Runtime.getRuntime().availableProcessors();
		}
	}
			
	public static int getCpuCount(){
		return CPU_COUNT;
	}

	public static String osInfo(){
		
		Properties props=System.getProperties();   
		String osName = props.getProperty("os.name");   
		String osArch = props.getProperty("os.arch");   
		String osVersion = props.getProperty("os.version");
		
		return String.format("%s %s %s", osName, osVersion, osArch);
	}

	
	/**
	 * time should be larger than system starttime
	 * @param strTime
	 * @return
	 */
	public static long getCorrentTime(String strTime){
		
		try{
			Long time = Long.parseLong(strTime);
			if(time < startTime || time > System.currentTimeMillis()){
				return -1;
			}
			return time;
		}catch(Exception e){
			return -1L;
		}
	}
}
