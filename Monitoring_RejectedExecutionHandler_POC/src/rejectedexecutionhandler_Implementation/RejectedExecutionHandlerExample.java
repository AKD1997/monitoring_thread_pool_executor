package rejectedexecutionhandler_Implementation;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class RejectedExecutionHandlerExample {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static int coreCount = Runtime.getRuntime().availableProcessors();
	// CyclicBarrier is a synchronizer that allows a set of threads to wait for each
	// other to reach a common execution point
	public static final CyclicBarrier gate = new CyclicBarrier(coreCount);
	public static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreCount);
	public static ThreadPoolExecutor alternateExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreCount);

	public static void main(String[] args) throws InterruptedException {

		RejectedExecutionHandler handler = new MyRejectedExecutionHandler();

		// Returns the current handler for unexecutable tasks
		executor.setRejectedExecutionHandler(handler);

		System.out.println("Starting ThreadPoolExecutor : " + LocalDateTime.now());

		// Here we creating task for Executor service
		Task task = new Task(10);

		for (int i = 0; i < 12; i++) {
			task = new Task(task.num + 5);
			executor.execute(task);
			// After execution 8 times all task going to the rejected task
			if (i == 8)
				executor.shutdown();// The executor is terminated intentionally to check the
									// RejectedExecutionHandler
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
			// Waiting for the termination of executor
		}

		alternateExecutor.shutdown();

		while (!alternateExecutor.isTerminated()) {
			// Waiting for the termination of alternateExecutor
		}
		System.out.println("Ending ThreadPoolExecutor : " + LocalDateTime.now());
	}

}
