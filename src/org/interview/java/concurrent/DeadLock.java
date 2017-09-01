package org.interview.java.concurrent;

public class DeadLock {
	public static void main(String[] args) {
		Object obj1=new Object();
		Object obj2=new Object();
		
		Thread thread1=new Thread(new DeadLockThread(1, obj1, obj2));
		Thread thread2=new Thread(new DeadLockThread(2, obj1, obj2));
		thread1.start();
		thread2.start();
	}
}

class DeadLockThread implements Runnable{
	private int id;
	private Object obj1;
	private Object obj2;
	
	public DeadLockThread(int id, Object obj1, Object obj2) {
		this.id=id;
		this.obj1=obj1;
		this.obj2=obj2;
	}
	
	@Override
	public void run() {
		try {
			if(id==1) {
				synchronized(obj1) {
					Thread.sleep(100);
					synchronized(obj2) {
						System.out.println(this.id);
					}
				}
			}else {
				synchronized(obj2) {
					Thread.sleep(100);
					synchronized(obj1) {
						System.out.println(this.id);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}