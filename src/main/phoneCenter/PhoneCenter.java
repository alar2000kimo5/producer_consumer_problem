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

	/* �̳]�w�����uclass�A�U�ۦ��@��phone queue */
	private HashMap<String, LinkedBlockingQueue<AnyTypeWork>> callQueueMap = new HashMap();

	/* �H����queue */
	private LinkedBlockingQueue<Employee> empQueue = new LinkedBlockingQueue<>();

	/* �]�w�q�ܼȦs�C �P �H���C */
	public void setCallQueueAndEmpQueue(Map<Class<?>, Integer> initSet, PhoneCenter pc) {

		initSet.forEach((cs, count) -> {

			try {
				// �إߨ�¾��W�٪��q�ܼȦs
				create_PhoneQueueByName(cs);

				// �إߨ̳]�w�إ�¾��H���P�ƶq�A�ñN�q�ܼȦs���ߥ[�J�H���غc
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

	/* �إߨC��¾�쪺�q�ܫO�dQUE*/
	private void create_PhoneQueueByName(Class<?> cs) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Employee ob = (Employee) cs.newInstance();
		Method method = ob.getClass().getMethod("getEmpName");
		String empName = (String) method.invoke(ob, null);
		callQueueMap.put(empName, new LinkedBlockingQueue<AnyTypeWork>());
	}

	/* ���o�C�ӤH�����q�ܫO�d�A�p�G���S���q�ܤF�A�h����q�ܪA�� */
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
