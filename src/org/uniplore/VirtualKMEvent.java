package org.uniplore;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;

/*
 * 测试模拟鼠标事件
 */
public class VirtualKMEvent {
	
	private static Robot robot;
	

	public VirtualKMEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		try {
			robot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		robot.mouseMove(43, 136);
		for(int i=0;i<100;i++){
			robot.mouseMove(new Random().nextInt(800), new Random().nextInt(600));
			robot.delay(500);
		}
		
	}

}
