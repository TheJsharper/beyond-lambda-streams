package test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class Main {
	public static void main(String[] args) {
		Map<String, Integer> items = ImmutableMap.of("coin", 3, "glass", 4, "pencil", 1);
		//items.put("test", 100);
		items.entrySet().stream().forEach(System.out::println);

		List<String> fruits = Lists.newArrayList("orange", "banana", "kiwi", "mandarin", "date", "quince");

		fruits.forEach( f -> System.out.println("fruit iteraion===> %s".formatted(f)));
		 Function<String, String> addSomeStr = (String name)-> name.toUpperCase().concat(" ---> Default");
		 var result = addSomeStr.apply("Hello World");
		 System.out.println(result);
	}

	
}
