package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamOps {
	static int i = 1;

	public static void main(String[] args) {
		test3();

	}

	private static void test3() {
		List<List<Integer>> list = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4), Arrays.asList(5, 6));
		Stream<Stream<Integer>> mapStream = list.stream().map(e -> e.stream());
		Stream<Integer> flatMap = list.stream().flatMap(e -> e.stream());
		Stream<Long> map = list.stream().map(e -> e.stream().count());
		List<Long> collect1 = map.collect(Collectors.toList());
		List<Integer> collect2 = list.stream().flatMap(e -> e.stream()).collect(Collectors.toList());
		List<Integer> collect3 = flatMap.collect(Collectors.toList());
		System.out.println(collect1);
		System.out.println(collect2);
		System.out.println(list.stream().flatMap(e -> e.stream()).collect(Collectors.counting()));
	}

	private static void test2() {
		Set<Integer> set = IntStream.range(1, 10).boxed().collect(Collectors.toSet());
		boolean anyMatch =IntStream.range(1, 12).allMatch(e-> 
		{
			System.out.println(e);
			return set.contains(e);
		}
		);
		/*
		 * boolean anyMatch = IntStream.range(1, 10) .anyMatch(e-> {
		 * System.out.println(e); return e==3; } );
		 */
		System.out.println(anyMatch);
	}

	private static void test1() {
		IntStream range = IntStream.range(1, 10);
		IntStream filter = range.filter(e -> {
			System.out.println("filter : " + e);
			i++;
			return e % 2 == 0;
		});
		IntStream map = filter.map(e -> {
			System.out.println("map : " + e);
			i++;
			return e * 2;

		});
		map.forEach(e -> System.out.println(e + " i : " + i));
	}

}
