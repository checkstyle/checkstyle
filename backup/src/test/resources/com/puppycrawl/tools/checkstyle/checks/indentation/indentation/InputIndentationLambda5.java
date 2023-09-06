package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0
// see https://github.com/checkstyle/checkstyle/issues/7675 //indent:0 exp:0
import java.util.stream.Stream; //indent:0 exp:0
public class InputIndentationLambda5 { //indent:0 exp:0
	public void test(Stream<Inner<?>> stream) { //indent:3 exp:3
		stream //indent:6 exp:6
				.filter(ps -> ps instanceof Inner) //indent:12 exp:12
				.map(ps -> ((Inner<?>) ps).getPropertyNames()) //indent:12 exp:12
				.forEach(System.out::println); //indent:12 exp:12
	} //indent:3 exp:3

	private static class Inner<T> { //indent:3 exp:3
		String[] getPropertyNames() { //indent:6 exp:6
			return new String[] {"a", "b"}; //indent:9 exp:9
		} //indent:6 exp:6
	} //indent:3 exp:3
} //indent:0 exp:0
