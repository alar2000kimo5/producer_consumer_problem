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

		int stopPhoneCallBy_callCount = 100; // �}��call in 100�q�q��
		String first_EmpName = "Fresher"; // �Ĥ@�ӱ��q�ܪ����u¾�W

		// �]�w���u�ƶq
		LinkedHashMap<Class<?>, Integer> initSetting = new LinkedHashMap<>();
		initSetting.put(PM.class, 1); // PM �ƶq
		initSetting.put(TL.class, 1); // TL �ƶq
		initSetting.put(Fresher.class, 3); // op���u�ƶq

		// �]�w�q�ܤ���
		PhoneCenter pc = init(initSetting);

		// �q�ܤ��߶}��call in
		startCallin(pc, first_EmpName, stopPhoneCallBy_callCount);

		// �q�ܤ��߶}�l�A��
		startPhoneCallService(pc);

	}

	/* �}�l�q�ܪA��, �]�wthreadpool(10) �O���F�s�y���u�q�ܤ������p */
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

	/* �إ߳q�ܤ��� */
	private static PhoneCenter init(LinkedHashMap<Class<?>, Integer> initMap)
			throws ClassNotFoundException, InstantiationException {
		PhoneCenter pc = new PhoneCenter();
		pc.setCallQueueAndEmpQueue(initMap, pc);
		return pc;
	}

	/* �إ�call in �i�Ӫ��W�v�A�P���� call in ���ɶ� */
	private static void startCallin(PhoneCenter pc, String first_EmpName, int stopPhoneCallBy_callCount)
			throws InterruptedException {
		LinkedBlockingQueue<AnyTypeWork> callq2 = pc.getCallQueue_byEmpName(first_EmpName);
		System.out.println("�}�� call in!");
		for (int call = 0; call < stopPhoneCallBy_callCount; call++) {
			callq2.add(new PhoneCall());
		}
		System.out.println("call in �ɶ���! �@�@��" + callq2.size() + "�� call in ");
	}
	
	/* debug ��, �C���ˬd�@�� �O�d�q�ܪ��A �P �H�����A*/
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
//							System.out.println("checkdebug ����");
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
