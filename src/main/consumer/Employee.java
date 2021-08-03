package main.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import main.phoneCenter.PhoneCenter;
import main.producer.AnyTypeWork;

public abstract class Employee implements Runnable {
	
	private int status = 0;

	public abstract PhoneCenter getPhoneCenter();

	public abstract void setPhoneCenter(PhoneCenter pc);

	/* 服務者名稱 */
	public abstract String getEmpName();

	/* 當無法服務時，傳給下個服務者名稱 */
	public abstract String getNextTranName();

	@Override
	public void run() {
		PhoneCenter pc = getPhoneCenter();
		LinkedBlockingQueue<AnyTypeWork> this_que = pc.getCallQueue_byEmpName(getEmpName());
		LinkedBlockingQueue<AnyTypeWork> next_que = pc.getCallQueue_byEmpName(getNextTranName());
		while (true) {
			try {
				Thread.sleep(1000); // 每秒檢查一次通話狀態

				AnyTypeWork anyTypeWork = this_que.poll();
				if (anyTypeWork != null) {
					if (getStatus() == 0) {
						setStatus(1);
						dowork(next_que, anyTypeWork);
					} else {
						System.out.println(
								getEmpName() + Thread.currentThread().getId() + "服務中.. 轉接給 " + getNextTranName());
						next_que.offer(anyTypeWork);
					}
				}

				if (pc.checkAllAndStopService()) {
					System.out.println(getEmpName() + "服務停止");
					break; // return true then stop service
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void dowork(LinkedBlockingQueue<AnyTypeWork> next_que, AnyTypeWork anyTypeWork) {
		new Thread(() -> {
			try {
				boolean isSuccess = isSuccess();
				if (!isSuccess) {
					System.out
							.println(getEmpName() + Thread.currentThread().getId() + "服務失敗.. 轉接給 " + getNextTranName());
					next_que.offer(anyTypeWork);
				}
				setStatus(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
