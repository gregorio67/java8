package func;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class Task2<T> implements Callable<T> {

	private int taskId = 0;
	public Task2(int id) {
		taskId = id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T call() throws Exception {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(Thread.currentThread().getName(), taskId);
		return (T)map;
	}

}
