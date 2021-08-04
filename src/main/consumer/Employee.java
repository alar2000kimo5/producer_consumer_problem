package main.consumer;

import java.util.concurrent.LinkedBlockingQueue;

import main.phoneCenter.PhoneCenter;
import main.producer.AnyTypeWork;

public abstract class Employee  {

	private int status = 0;
	
	private PhoneCenter pc;

	/* �A�Ȫ̦W�� */
	public abstract String getEmpName();

	/* ��L�k�A�ȮɡA�ǵ��U�ӪA�Ȫ̦W�� */
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
				System.out.println(
						getEmpName() + Thread.currentThread().getId() + "�A�Ȥ�.. �౵�� " + getNextTranName());
				next_que.offer(anyTypeWork);
			}
		}
	}

	private void dowork(LinkedBlockingQueue<AnyTypeWork> next_que, AnyTypeWork anyTypeWork) {
		new Thread(() -> { // ���o�䤣�ݭnfork�X�h���Ʊ�, �ΦP��thread���ƴN�n�F, �]�]��status���ΦP��]
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
