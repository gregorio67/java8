package func;

import java.lang.Thread.State;

public class Task implements Runnable {

	private int taskId;
	
	public Task(int id) {
		this.taskId = id;
	}
	@Override
	public void run() {
		System.out.println("Task ID " + taskId + " " + Thread.currentThread().getName());
	}
	
	public void shutdown() throws InterruptedException {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				System.out.println("Thread is alive");
				
			}			
		}
		finally {
			System.out.println("Thread is dead");
		}
		
		if(Thread.currentThread().getState() == State.TERMINATED) {
			Thread.currentThread().stop();
		}
	}
}