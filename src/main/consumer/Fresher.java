package main.consumer;

public class Fresher extends Employee {

	@Override
	public String getEmpName() {
		return "Fresher";
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

	// �i���޿褣�P�ҥH�U�۹�@
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
	// System.out.println("fr �]�� status = 0");
	// TL_que.add(anyTypeWork);
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
}
