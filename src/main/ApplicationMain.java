package main;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.omg.Messaging.SyncScopeHelper;

import main.consumer.Employee;
import main.consumer.Fresher;
import main.consumer.PM;
import main.consumer.TL;
import main.phoneCenter.PhoneCenter;
import main.producer.AnyTypeWork;
import main.producer.PhoneCall;

public class ApplicationMain {
	
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, InterruptedException, ExecutionException {

		int stopPhoneCallBy_callCount = 100; // 開放call in 100通電話
		String first_EmpName = "Fresher"; // 第一個接電話的員工職名

		// 設定員工數量
		LinkedHashMap<Class<?>, Integer> initSetting = new LinkedHashMap<>();
		initSetting.put(PM.class, 1); // PM 數量
		initSetting.put(TL.class, 1); // TL 數量
		initSetting.put(Fresher.class, 3); // op員工數量

		// 設定電話中心
		PhoneCenter pc = init(initSetting);

		// 電話中心開放call in
		startCallin(pc, first_EmpName, stopPhoneCallBy_callCount);

		// 電話中心開始服務
		startPhoneCallService(pc);

	}

	/* 開始電話服務, 設定threadpool(10) 是為了製造員工電話中的狀況 */
	private static void startPhoneCallService(PhoneCenter pc) {
		LinkedBlockingQueue<? extends Employee> empque = pc.getEmpQueue();
		ExecutorService emp = Executors.newFixedThreadPool(empque.size()); 
		for (Employee em : empque) {
			emp.execute(em);
		}
		while (true) {
			if (pc.checkAllAndStopService()) {
				break; // return true then stop service
			}
		}

		emp.shutdown();
		System.out.println("no phone call , stop service !");
	}

	/* 建立通話中心 */
	private static PhoneCenter init(LinkedHashMap<Class<?>, Integer> initMap)
			throws ClassNotFoundException, InstantiationException {
		PhoneCenter pc = new PhoneCenter();
		pc.setCallQueueAndEmpQueue(initMap, pc);
		return pc;
	}

	/* 建立call in 進來的頻率，與停止 call in 的時間 */
	private static void startCallin(PhoneCenter pc, String first_EmpName, int stopPhoneCallBy_callCount)
			throws InterruptedException {
		LinkedBlockingQueue<AnyTypeWork> callq2 = pc.getCallQueue_byEmpName(first_EmpName);
		System.out.println("開放 call in!");
		for (int call = 0; call < stopPhoneCallBy_callCount; call++) {
			callq2.add(new PhoneCall());
		}
		System.out.println("call in 時間到! 一共有" + callq2.size() + "筆 call in ");
	}
	
	/* debug 用, 每秒檢查一次 保留通話狀態 與 人員狀態*/
//	private static void checkdebug(PhoneCenter pc) {
//		HashMap<String, LinkedBlockingQueue<AnyTypeWork>> callQueueMap = pc.getCallQueueMap();
//		LinkedBlockingQueue<Employee> empque = pc.getEmpQueue();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true) {
//					try {
//						Thread.sleep(1000);
//						for (Employee em : empque) {
//							System.out.println(em.getEmpName() + " status:" + em.getStatus());
//						}
//						for (String key : callQueueMap.keySet()) {
//							LinkedBlockingQueue<AnyTypeWork> que = callQueueMap.get(key);
//							if (que.size() > 0) {
//								System.out.println(key + " size :" + que.size());
//							}
//						}
//						if (pc.checkAllAndStopService()) {
//							System.out.println("checkdebug 停止");
//							break; // return true then stop service
//						}
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//	}
}
