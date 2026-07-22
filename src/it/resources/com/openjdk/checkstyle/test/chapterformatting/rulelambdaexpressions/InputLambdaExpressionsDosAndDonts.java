package com.openjdk.checkstyle.test.chapterformatting.rulelambdaexpressions;

// violation first line 'Header mismatch'

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InputLambdaExpressionsDosAndDonts {

    Map<Long, Double> tempMap = new HashMap<>();

    class Person {
        String getFirstName() {
            return "";
        }

        String getLastName() {
            return "";
        }
    }

    public void appendFilter(Predicate<String> filter) {
    }

    public void trackTemperature(BiConsumer<Long, Double> temperatureRecorder) {
    }

    public void styleGuideDos(List<String> list) {

        Runnable r = () -> System.out.println("Hello World");

        Supplier<String> c = () -> "Hello World";

        appendFilter(list::contains);

        trackTemperature((time, temp) -> tempMap.put(time, temp));
        Function<Person, String> nameFunc = p -> p.getFirstName() + " " + p.getLastName();
    }

    public void styleGuideDonts(List<String> list) {

        // ok, for block until https://github.com/checkstyle/checkstyle/issues/20692
        Runnable r = () -> { System.out.println("Hello World"); };
        // 2 violations above:
        //    'Expression lambdas are preferred over single-line block lambdas.'
        //    ''{' at column 28 should have line break after.'

        // ok, for block until https://github.com/checkstyle/checkstyle/issues/20692
        Supplier<String> supp = () -> { return "Hello World"; };
        // 2 violations above:
        //    'Expression lambdas are preferred over single-line block lambdas.'
        //    ''{' at column 39 should have line break after.'

        // ok, until https://github.com/checkstyle/checkstyle/issues/20693
        appendFilter(s -> list.contains(s));

        // this can not be determined it is a subjective case
        trackTemperature(tempMap::put);

        // subject case cannot be determined whether type increase readability or not
        Function<Person, String> nameFunc = (Person p) -> p.getFirstName() + " " + p.getLastName();

        class Util {
            // ok, here because the current max allowed length of lambda is 10 or now
            static void printAllPersons(List<Person> persons) {
                persons.stream()
                        .map(p -> {
                            String first = p.getFirstName();
                            char initial = Character.toTitleCase(first.charAt(0));
                            return initial + first.substring(1);
                        })
                        .forEach(System.out::println);
            }
        }
    }
}
