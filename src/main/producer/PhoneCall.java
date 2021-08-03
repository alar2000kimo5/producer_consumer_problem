package main.producer;

public class PhoneCall extends AnyTypeWork<Integer> {

	// 對話1~6 秒不確定
	@Override
	public boolean doAsk(Integer level) throws InterruptedException {
//		System.out.println("PhoneCall doAsk.");
//		Thread.sleep(getInt());
//		System.out.println("PhoneCall doAsk..");
//		Thread.sleep(getInt());
//		System.out.println("PhoneCall doAsk...");
//		Thread.sleep(getInt());
//		System.out.println("PhoneCall doAsk....done");
//		 boolean isS = isSuccess(level);
//		 System.out.println("is : " + isS);
//		return isS;
		return true;
	}
	
//	private int getInt() {
//		return (int) (Math.random() * 2) + 1;
//	}
//	
//	private boolean isSuccess(Integer level) {
//		int res = (int) (Math.random() * 6) + 1;
//		System.out.println("level : " + level);
//		if (res > level)
//			return true; // success
//		else
//			return false; // fail
//	}

}
