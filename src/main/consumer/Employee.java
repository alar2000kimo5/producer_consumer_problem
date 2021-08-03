package main.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import main.phoneCenter.PhoneCenter;
import main.producer.AnyTypeWork;

public abstract class Employee implements Runnable {
	
	private int status = 0;

	public abstract PhoneCenter getPhoneCenter();

	public abstract void setPhoneCenter(PhoneCenter pc);

	/* �A�Ȫ̦W�� */
	public abstract String getEmpName();

	/* ��L�k�A�ȮɡA�ǵ��U�ӪA�Ȫ̦W�� */
	public abstract String getNextTranName();

	@Override
	public void run() {
		PhoneCenter pc = getPhoneCenter();
		LinkedBlockingQueue<AnyTypeWork> this_que = pc.getCallQueue_byEmpName(getEmpName());
		LinkedBlockingQueue<AnyTypeWork> next_que = pc.getCallQueue_byEmpName(getNextTranName());
		while (true) {
			try {
				Thread.sleep(1000); // �C���ˬd�@���q�ܪ��A

				AnyTypeWork anyTypeWork = this_que.poll();
				if (anyTypeWork != null) {
					if (getStatus() == 0) {
						setStatus(1);
						dowork(next_que, anyTypeWork);
					} else {
						System.out.println(
								getEmpName() + Thread.currentThread().getId() + "�A�Ȥ�.. �౵�� " + getNextTranName());
						next_que.offer(anyTypeWork);
					}
				}

				if (pc.checkAllAndStopService()) {
					System.out.println(getEmpName() + "�A�Ȱ���");
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
							.println(getEmpName() + Thread.currentThread().getId() + "�A�ȥ���.. �౵�� " + getNextTranName());
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

	/* ²��P�_�O�_���\�A ���U��¾���@ */
	public abstract boolean isSuccess() throws InterruptedException;

	/* �հ��P�Ȥ�q�ܻP�M�w�O�_���\�ѨM�Ȥ���D */
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
