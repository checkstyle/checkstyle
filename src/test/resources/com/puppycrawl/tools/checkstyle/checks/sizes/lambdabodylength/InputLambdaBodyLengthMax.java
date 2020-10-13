package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.List;
import java.util.function.Supplier;

/*
 * Config:
 * max = 3
 */
public class InputLambdaBodyLengthMax {
    {
        List<String> list = null;
        list.forEach(item -> { // violation
            System.out.println(1);
            System.out.println(2);
        });
        list.forEach(item -> System.out.println( // violation
            item.concat("2")
                .concat("3")
            )
        );
        list.forEach(item -> // ok, 3 lines
            System.
                out.
                println(item)
        );
        Supplier<Runnable> s = () -> // violation
            (

            ) -> { // ok, 3 lines

            };
        Supplier<String> s1 = () -> // violation
            "1"
                + "2"
                + "3"
                + "4"
            ;
        Supplier<String> s2 = () -> // ok
            "1"
                + "2"
                + "3"
            ;
    }
}
