package main.consumer;

import main.phoneCenter.PhoneCenter;

public class TL extends Employee {

	private PhoneCenter pc;

	//private int status = 0;

	private String empName = "TL";

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
		if (res > 1) {
			return true; // success
		} else {
			return false; // fail
		}
	}

	// �i���޿褣�P�ҥH�U�۹�@
	// @Override
	// public void runWork() {
	// LinkedBlockingQueue<AnyTypeWork> TL_que =
	// pc.getCallQueue_byEmpName(getEmpName());
	// LinkedBlockingQueue<AnyTypeWork> PM_que =
	// pc.getCallQueue_byEmpName(getNextTranName());
	//
	// try {
	// AnyTypeWork anyTypeWork = TL_que.take();
	// if (getStatus() == 0) {
	// setStatus(1);
	//
	// // boolean isSuccess = doing(anyTypeWork);
	// boolean isSuccess = isSuccess();
	//
	// if (!isSuccess) {
	// PM_que.offer(anyTypeWork);
	// }
	//
	// setStatus(0);
	// } else {
	// System.out.println("tl �]�� status = 0");
	// PM_que.add(anyTypeWork);
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
}
