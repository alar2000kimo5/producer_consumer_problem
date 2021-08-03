package main.consumer;

import main.phoneCenter.PhoneCenter;

public class Fresher extends Employee {

	private PhoneCenter pc;

	//private int status = 0;

	private String empName = "Fresher";

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
		return "TL";
	}

	@Override
	public boolean isSuccess() throws InterruptedException {
		Thread.sleep(3000);
		int res = (int) (Math.random() * 6) + 1;
		if (res > 2) {
			return true; // success
		} else {
			return false; // fail
		}
	}

	// 可能邏輯不同所以各自實作
	// @Override
	// public void runWork() {
	// LinkedBlockingQueue<AnyTypeWork> op_que =
	// pc.getCallQueue_byEmpName(getEmpName());
	// LinkedBlockingQueue<AnyTypeWork> TL_que =
	// pc.getCallQueue_byEmpName(getNextTranName());
	//
	// try {
	// AnyTypeWork anyTypeWork = op_que.take();
	// if (getStatus() == 0) {
	// setStatus(1);
	//
	// // boolean isSuccess = doing(anyTypeWork);
	// boolean isSuccess = isSuccess();
	// if (!isSuccess) {
	// TL_que.add(anyTypeWork);
	// }
	//
	// setStatus(0);
	// } else {
	// System.out.println("fr 因為 status = 0");
	// TL_que.add(anyTypeWork);
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
}
