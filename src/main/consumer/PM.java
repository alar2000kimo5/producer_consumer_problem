package main.consumer;

import main.phoneCenter.PhoneCenter;

public class PM extends Employee {

	private PhoneCenter pc;

	//private int status = 0;

	private String empName = "PM";

	@Override
	public PhoneCenter getPhoneCenter() {
		return pc;
	}

	@Override
	public void setPhoneCenter(PhoneCenter pc) {
		this.pc = pc;
	}

//	@Override
//	public int getStatus() {
//
//		return status;
//	}
//
//	@Override
//	public void setStatus(int status) {
//		this.status = status;
//	}

	@Override
	public String getEmpName() {
		return empName;
	}

	@Override
	public String getNextTranName() {
		return "PM";
	}

	@Override
	public boolean isSuccess() throws InterruptedException {
		Thread.sleep(3000);
		int res = (int) (Math.random() * 6) + 1;
		if (res > 0) {
			return true; // success
		} else {
			return false; // fail
		}
	}

	// 可能邏輯不同所以各自實作
	// @Override
	// public void runWork() {
	// LinkedBlockingQueue<AnyTypeWork> PM_que = pc.getCallQueue_byEmpName("PM");
	// while (true) {
	// try {
	// AnyTypeWork anyTypeWork = PM_que.take();
	//
	// if (getStatus() == 0) {
	// setStatus(1);
	//
	// isSuccess();
	// // doing(anyTypeWork);
	//
	// setStatus(0);
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
}
