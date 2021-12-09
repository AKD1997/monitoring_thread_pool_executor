package rejectedexecutionhandler_Implementation;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class MyRejectedExecutionHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable worker, ThreadPoolExecutor executor) {
		System.out.println(RejectedExecutionHandlerExample.ANSI_YELLOW + worker.toString() + " is Rejected : "
				+ LocalDateTime.now() + RejectedExecutionHandlerExample.ANSI_RESET);

		System.out.println("Retrying to Execute");
		
		try {
			// Re-executing with alternateExecutor
			RejectedExecutionHandlerExample.alternateExecutor.execute(worker);
			System.out.println(worker.toString() + " Execution Started : " + LocalDateTime.now());
		} catch (Exception e) {
			System.out.println("Failure to Re-exicute: " + e.getMessage());
		}
	}

}
