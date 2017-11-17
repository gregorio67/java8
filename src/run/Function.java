package run;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import func.Func;

public class Function {

	public static void main(String[] args) {
		Func add = (int a, int b) -> a + b;
		Func sub = (int a, int b) -> a - b;
		
		int result = add.calc(10, 20) + sub.calc(10, 20);
		System.out.println(result);
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		Arrays.asList(1,2,3).stream()
						.map( i -> i*i )
						.forEach(System.out::println);
		
		repeatMessage("Hello", 1000);
	}
	
	public static void repeatMessage(String text, int count) {
	     Runnable r = () -> {
	        for (int i = 0; i < count; i++) {
	           System.out.println(text);
	           Thread.yield();
	        }
	     };
	     new Thread(r).start();
	}
}
