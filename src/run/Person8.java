package run;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import vo.Person;

public class Person8 {

	public static void main(String args[]) {
		
		List<Person> persons = Arrays.asList(new Person("kkimdoy", 5),
											  new Person("choi", 10),
											  new Person("park", 20));
		
		Person result1 = persons.stream()
						.filter(x -> "kkimdoy".equals(x.getName()) && x.getAge() > 5)
						.findAny()
						.orElse(null);
		
		System.out.println(result1 == null? "" : result1.getName());
		
		List<String> collect = persons.parallelStream()
							  .map(Person::getName)
							  .filter(x -> !"kkimdoy".equals(x))
							  .collect(Collectors.toList());
		
		collect.forEach(System.out::println);
		
		Collections.sort(persons,(p1, p2) -> {
			return p1.getName().compareTo(p2.getName());
		});
		
		System.out.println("Stream");
		Stream<Person> filtered_data = persons.parallelStream().filter(x ->x.getAge() >= 10);
		filtered_data.forEach(p->System.out.println(p.getName()));
		
	}
}
