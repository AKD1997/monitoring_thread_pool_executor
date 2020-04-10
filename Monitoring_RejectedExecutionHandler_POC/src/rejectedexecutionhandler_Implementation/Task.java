package rejectedexecutionhandler_Implementation;

import java.time.LocalDateTime;
import java.util.concurrent.BrokenBarrierException;

public class Task implements Runnable {

	int num = 10;
	String name = "Task";

	public Task(int num) {
		this.num = num;
	}

	@Override
	public void run() {

		try {
			// waiting for others to reach barrier
			RejectedExecutionHandlerExample.gate.await();
			System.out.println(Thread.currentThread().getName() + "  : Task Sum " + num + "   Created :"
					+ " Currently Executing " + LocalDateTime.now());

			Thread.sleep(1000);

			System.out.println(RejectedExecutionHandlerExample.ANSI_GREEN + Thread.currentThread().getName()
					+ "  : Task Sum " + num + "   Created :" + " Completed " + LocalDateTime.now()
					+ RejectedExecutionHandlerExample.ANSI_RESET);

		} catch (InterruptedException | BrokenBarrierException e) {

			System.out.println(e.getMessage());
		}
	}

	public int getId() {
		return num;
	}

	public void setId(int id) {
		this.num = id;
	}

	@Override
	public String toString() {

		String s = name + " Sum: " + num;
		return s;
	}

}
