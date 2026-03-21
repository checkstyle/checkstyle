/*
LambdaBodyLength
max = 3


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.List;
import java.util.function.Supplier;

public class InputLambdaBodyLengthMax {
    {
        List<String> list = null;
        list.forEach(item -> { // violation 'Lambda body length is 4 lines (max allowed is 3).'
            System.out.println(1);
            System.out.println(2);
        });
        // violation below 'Lambda body length is 4 lines (max allowed is 3).'
        list.forEach(item -> System.out.println(
            item.concat("2")
                .concat("3")
            )
        );
        list.forEach(item -> // ok, 3 lines
            System.
                out.
                println(item)
        );
        // violation below 'Lambda body length is 5 lines (max allowed is 3).'
        Supplier<Runnable> s = () ->
            (

            ) -> {

            };
        // violation below 'Lambda body length is 4 lines (max allowed is 3).'
        Supplier<String> s1 = () ->
            "1"
                + "2"
                + "3"
                + "4"
            ;
        Supplier<String> s2 = () ->
            "1"
                + "2"
                + "3"
            ;
    }
}
