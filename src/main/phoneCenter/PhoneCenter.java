package main.phoneCenter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import main.consumer.Employee;
import main.producer.AnyTypeWork;

public class PhoneCenter {

	/* 依設定的員工class，各自有一個phone queue */
	private HashMap<String, LinkedBlockingQueue<AnyTypeWork>> callQueueMap = new HashMap();

	/* 人員的queue */
	private LinkedBlockingQueue<Employee> empQueue = new LinkedBlockingQueue<>();

	/* 設定電話暫存列 與 人員列 */
	public void setCallQueueAndEmpQueue(Map<Class<?>, Integer> initSet, PhoneCenter pc) {

		initSet.forEach((cs, count) -> {

			try {
				// 建立依職位名稱的電話暫存
				create_PhoneQueueByName(cs);

				// 建立依設定建立職位人物與數量，並將電話暫存中心加入人物建構
				for (int op = 0; op < count; op++) {
					Employee emp = (Employee) cs.newInstance();
					emp.setPhoneCenter(pc);
					addEmp(emp);
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
	}

	/* 建立每個職位的通話保留QUE*/
	private void create_PhoneQueueByName(Class<?> cs) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Employee ob = (Employee) cs.newInstance();
		Method method = ob.getClass().getMethod("getEmpName");
		String empName = (String) method.invoke(ob, null);
		callQueueMap.put(empName, new LinkedBlockingQueue<AnyTypeWork>());
	}

	/* 取得每個人員的通話保留，如果都沒有通話了，則停止電話服務 */
	public boolean checkAllAndStopService() {
		for (String empName : callQueueMap.keySet()) {
			LinkedBlockingQueue<AnyTypeWork> que = callQueueMap.get(empName);
			if (que.size() > 0) {
				return false;
			}
		}
		return true; // if all queue.size = 0 , return true
	}

	public LinkedBlockingQueue<AnyTypeWork> getCallQueue_byEmpName(String empName) {
		return callQueueMap.get(empName);
	}

	public LinkedBlockingQueue<Employee> getEmpQueue() {
		return empQueue;
	}

	public void addEmp(Employee e) {
		this.empQueue.add(e);
	}

}
