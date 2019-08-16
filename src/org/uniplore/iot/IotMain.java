package org.uniplore.iot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IotMain {

	public IotMain() {

	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
				PostDataByMqtt pd = new PostDataByMqtt("e2Eml32jsLaUi0b2SraL", "localhost");
				pd.postTelemetryFileThread(1000, "e:\\14_data.csv");
			}
			
		};
		executor.execute(runnable);
		
		
		Runnable runnable2 = new Runnable(){

			@Override
			public void run() {
				PostDataByMqtt pd2 = new PostDataByMqtt("pLnHKrTD3zmZ0M1haJ8L", "localhost");
				pd2.postTelemetryFileThread(1000, "e:\\10_data.csv");
			}
			
		};
		executor.execute(runnable2);
		
	}

}
