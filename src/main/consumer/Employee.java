package main.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import main.phoneCenter.PhoneCenter;
import main.producer.AnyTypeWork;

public abstract class Employee {

	private int status = 0;

	private PhoneCenter pc;

	/* 服務者名稱 */
	public abstract String getEmpName();

	/* 當無法服務時，傳給下個服務者名稱 */
	public abstract String getNextTranName();

	public PhoneCenter getPhoneCenter() {
		return pc;
	}

	public void setPhoneCenter(PhoneCenter pc) {
		this.pc = pc;
	}

	public void doWork() {
		PhoneCenter pc = getPhoneCenter();
		LinkedBlockingQueue<AnyTypeWork> this_que = pc.getCallQueue_byEmpName(getEmpName());
		LinkedBlockingQueue<AnyTypeWork> next_que = pc.getCallQueue_byEmpName(getNextTranName());

		AnyTypeWork anyTypeWork = this_que.poll();
		if (anyTypeWork != null) {
			if (getStatus() == 0) {
				setStatus(1);
				dowork(next_que, anyTypeWork);
			} else {
				System.out.println(getEmpName() + Thread.currentThread().getId() + "服務中.. 轉接給 " + getNextTranName());
				next_que.offer(anyTypeWork);
			}
		}
	}

	private void dowork(LinkedBlockingQueue<AnyTypeWork> next_que, AnyTypeWork anyTypeWork) {
		new Thread(() -> { // fork出去做事，為了造成服務中狀態，讓電話轉接到下一位
			try {
				boolean isSuccess = isSuccess();
				if (!isSuccess) {
					System.out
							.println(getEmpName() + Thread.currentThread().getId() + "服務失敗.. 轉接給 " + getNextTranName());
					next_que.offer(anyTypeWork);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				setStatus(0); // 最後要切換狀態，不然報錯就永遠卡住
			}
		}).start();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/* 簡單判斷是否成功， 給各個職位實作 */
	public abstract boolean isSuccess() throws InterruptedException;

	/* 試做與客戶通話與決定是否成功解決客戶問題 */
	// public boolean em_doWork(Function<Integer, Boolean> s) {
	// boolean isDone = s.apply(getLevel());
	// return isDone;
	// }
	//
	// public boolean doing(AnyTypeWork anyTypeWork) {
	// return em_doWork((a_TypeWork) -> {
	// try {
	// return anyTypeWork.doAsk(a_TypeWork);
	// } catch (InterruptedException e1) {
	// e1.printStackTrace();
	// }
	// return false;
	// });
	// }
}
