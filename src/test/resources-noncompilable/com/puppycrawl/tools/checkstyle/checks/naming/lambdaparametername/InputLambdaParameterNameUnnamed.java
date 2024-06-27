/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.lambdaparametername;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputLambdaParameterNameUnnamed {

    void method(Object o) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers = numbers.stream()
                .map(_ -> 0)
                .toList();
        List<String> strings = Arrays.asList("a", "b", "c");

        System.out.println(
                strings.stream().collect(Collectors.toMap(String::toUpperCase,
                                   _ -> "NODATA"))); // ok, unnamed lambda parameter

        System.out.println(
                strings.stream().collect(Collectors.toMap(String::toUpperCase,
                                   __ -> "NODATA"))); // violation, 'Name '__' must match.*'

        System.out.println(
                strings.stream().collect(Collectors.toMap(String::toUpperCase,
                                   _BAD -> "NODATA"))); // violation, 'Name '_BAD' must match.*'
        System.out.println(
                strings.stream().collect(Collectors.toMap(String::toUpperCase,
                                   BAD_ -> "NODATA"))); // violation, 'Name 'BAD_' must match.*'

        switch (o) {
            case Integer __ -> {} // ok, this is pattern variable not a lambda parameter
            case String _ -> {}
            default -> {}
        }

    }

}
