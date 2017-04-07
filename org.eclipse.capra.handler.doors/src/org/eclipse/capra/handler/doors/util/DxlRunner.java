package org.eclipse.capra.handler.doors.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;

// Some explaination is on the IBM forums.
// https://www.ibm.com/developerworks/community/forums/html/topic?id=77777777-0000-0000-0000-000014675975
public class DxlRunner {
	private static DxlRunner instance = null;
	private DxlRunner() {
		
	}
	
	public static DxlRunner getInstance() {
		if (instance == null){
			instance = new DxlRunner();
		}
		
		return instance;
	}
	
	public String run(String dxl) {
		String result = null;
		try {
			ComThread.InitSTA();
			ActiveXComponent runningDoors = new ActiveXComponent("DOORS.Application");
			result = executeDXL(dxl, runningDoors);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ComThread.Release();
		}
		return result;
	}

	private String executeDXL(String dxl, ActiveXComponent runningDoors) {
		runningDoors.invoke("RunStr", dxl);
		String result = runningDoors.getPropertyAsString("Result");
		return result;
	}

}
