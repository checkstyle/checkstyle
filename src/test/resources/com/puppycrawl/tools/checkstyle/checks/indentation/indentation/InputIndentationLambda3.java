//a comment //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.Arrays; //indent:0 exp:0
import java.util.List; //indent:0 exp:0
import java.util.Optional; //indent:0 exp:0
import java.util.function.BinaryOperator; //indent:0 exp:0
import java.util.function.Consumer; //indent:0 exp:0
import java.util.stream.Collectors; //indent:0 exp:0
import java.util.stream.Stream; //indent:0 exp:0


public class InputIndentationLambda3 { //indent:0 exp:0
	public <T> Consumer<Integer> par(Consumer<Integer> f1, Consumer<Integer> f2) { //indent:4 exp:4
			return f2; //indent:12 exp:8 warn
	} //indent:4 exp:4

	private void print(int i) { //indent:4 exp:4
	} //indent:4 exp:4

	public Consumer<Integer> returnFunctionOfLambda() { //indent:4 exp:4
		return par( //indent:8 exp:8
					(x) -> print(x * 1), //indent:20 exp:20
				(x) -> print(x * 2) //indent:16 exp:16
		); //indent:8 exp:8
	} //indent:4 exp:4

	public static <T> BinaryOperator<T> returnLambda() { //indent:4 exp:4
			return (t1, t2) -> { //indent:12 exp:8 warn
			return t1; //indent:12 exp:16 warn
		}; //indent:8 exp:12 warn
	} //indent:4 exp:4

	class TwoParams { //indent:4 exp:4
		TwoParams(Consumer<Integer> c1, Consumer<Integer> c2) { //indent:8 exp:8
		} //indent:8 exp:8
	} //indent:4 exp:4

	public void makeTwoParams() { //indent:4 exp:4
		TwoParams t0 = new TwoParams( //indent:8 exp:8
				intValueA //indent:16 exp:16
						-> print(intValueA * 1), //indent:24 exp:24
					(x) -> //indent:20 exp:20
						print(x * 2) //indent:24 exp:24
		); //indent:8 exp:8

		TwoParams t1 = new TwoParams( //indent:8 exp:8
				x //indent:16 exp:16
						-> print(x * 1), //indent:24 exp:24
				(aggregateValue) -> //indent:16 exp:16
						print(aggregateValue * 2)); //indent:24 exp:24
	} //indent:4 exp:4

	// see https://github.com/checkstyle/checkstyle/issues/5969 //indent:4 exp:4
	List<Integer> test(List<String> input) { //indent:4 exp:4
		return input.stream() //indent:8 exp:8
				.flatMap(each -> Arrays.stream(each.split(""))) //indent:16 exp:16
				.flatMap(in -> Arrays.stream(in.split(""))) //indent:16 exp:16
				// only 2 char parameter has violation //indent:16 exp:16
				.map(Integer::valueOf) //indent:16 exp:16
				.collect(Collectors.toList()); //indent:16 exp:16
	} //indent:4 exp:4

	String test2(String input) { //indent:4 exp:4
			return Optional.ofNullable(input) //indent:12 exp:8 warn
				.filter(in //indent:16 exp:16
							-> //indent:28 exp:28
					in.contains("e")) //indent:20 exp:20
			.filter(inp -> inp.contains("e")) //indent:12 exp:12
				// only 3 char parameter has violation //indent:16 exp:16
				.orElse(null); //indent:16 exp:16
	} //indent:4 exp:4

	// see https://github.com/checkstyle/checkstyle/issues/7675 //indent:4 exp:4
	public void test(Stream<Inner<?>> stream) { //indent:4 exp:4
		stream //indent:8 exp:8
.sorted() //indent:0 exp:0
.filter(ps -> ps instanceof Inner) //indent:0 exp:0
				.map(ps -> ((Inner<?>) ps).getPropertyNames()) //indent:16 exp:16
				// This line above originally breaks //indent:16 exp:16
				.forEach(System.out::println); //indent:16 exp:16
	} //indent:4 exp:4

	private static class Inner<T> { //indent:4 exp:4
		String[] getPropertyNames() { //indent:8 exp:8
			return new String[] {"a", "b"}; //indent:12 exp:12
			} //indent:12 exp:8 warn
	} //indent:4 exp:4
} //indent:0 exp:0
