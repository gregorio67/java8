package run;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import func.Task2;

public class MultiThread {

	public static void main(String args[]) throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(10);
		List<Map<String, Integer>> result = new ArrayList<Map<String, Integer>>();
		IntStream.range(0, 100).forEach(i -> {
			try {
			Future<Map<String, Integer>> future = service.submit(new Task2<Map<String, Integer>>(i));
			future.get().keySet().parallelStream().forEach(p -> {
				try {
					System.out.println(p + " " + future.get().get(p));
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});

		result.forEach(System.out::println);
		service.shutdown();			
		
		try {
			/** Wait a while for existing tasks to terminate **/
			if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
				service.shutdownNow();
			
				if (!service.awaitTermination(60,  TimeUnit.SECONDS)) {
					System.err.println("service did not terminated");
				}
			}			
		}
		catch(InterruptedException ie) {
			/** Recancel if current thread is also interrupted **/
			service.shutdown();
			
			Thread.currentThread().interrupt();
		}
	}
}



