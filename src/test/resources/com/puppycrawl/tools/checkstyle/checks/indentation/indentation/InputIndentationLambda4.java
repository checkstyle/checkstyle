//a comment //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.Arrays; //indent:0 exp:0
import java.util.List; //indent:0 exp:0
import java.util.Optional; //indent:0 exp:0
import java.util.function.BinaryOperator; //indent:0 exp:0
import java.util.function.Consumer; //indent:0 exp:0
import java.util.stream.Collectors; //indent:0 exp:0


public class InputIndentationLambda4 { //indent:0 exp:0
	public <T> Consumer<Integer> par(Consumer<Integer> f1, Consumer<Integer> f2) { //indent:4 exp:4
		return f2; //indent:8 exp:8
	} //indent:4 exp:4

	private void print(int i) { //indent:4 exp:4
	} //indent:4 exp:4

	public Consumer<Integer> returnFunctionOfLambda() { //indent:4 exp:4
		return par( //indent:8 exp:8
				(x) -> print(x * 1), //indent:16 exp:16
				(x) -> print(x * 2) //indent:16 exp:16
		); //indent:8 exp:8
	} //indent:4 exp:4

	public static <T> BinaryOperator<T> returnLambda() { //indent:4 exp:4
		return (t1, t2) -> { //indent:8 exp:8
			return t1; //indent:12 exp:12
		}; //indent:8 exp:8
	} //indent:4 exp:4

	class TwoParams { //indent:4 exp:4
		TwoParams(Consumer<Integer> c1, Consumer<Integer> c2) { //indent:8 exp:8
		} //indent:8 exp:8
	} //indent:4 exp:4

	public void makeTwoParams() { //indent:4 exp:4
		TwoParams t0 = new TwoParams( //indent:8 exp:8
				(x) -> print(x * 1), //indent:16 exp:16
				(x) -> print(x * 2) //indent:16 exp:16
		); //indent:8 exp:8

		TwoParams t1 = new TwoParams( //indent:8 exp:8
				(x) -> print(x * 1), //indent:16 exp:16
				(x) -> print(x * 2)); //indent:16 exp:16
	} //indent:4 exp:4

	// see https://github.com/checkstyle/checkstyle/issues/5969 //indent:4 exp:4
	List<Integer> test(List<String> input) { //indent:4 exp:4
		return input.stream() //indent:8 exp:8
				.flatMap(in -> Arrays.stream(in.split(""))) //indent:16 exp:16
				// only 2 char parameter has violation //indent:16 exp:16
				.map(Integer::valueOf) //indent:16 exp:16
				.collect(Collectors.toList()); //indent:16 exp:16
	} //indent:4 exp:4

	String test2(String input) { //indent:4 exp:4
		return Optional.ofNullable(input) //indent:8 exp:8
				.filter(inp -> inp.contains("e")) //indent:16 exp:16
				// only 3 char parameter has violation //indent:16 exp:16
				.orElse(null); //indent:16 exp:16
	} //indent:4 exp:4
} //indent:0 exp:0
