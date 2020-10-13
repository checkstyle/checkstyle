package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.List;
import java.util.function.*;

/*
* Config = default
 */
public class InputLambdaBodyLengthDefault {
    {
        List<String> list = null;
        list.forEach(item -> { // violation
            System.out.println(1);
            System.out.println(2);
            System.out.println(3);
            System.out.println(4);
            System.out.println(5);
            System.out.println(6);
            System.out.println(7);
            System.out.println(8);
            System.out.println(9);
            System.out.println(10);
        });
        list.forEach(item -> System.out.println( // violation
            item.trim()
                .trim()
                .trim()
                .trim()
                .trim()
                .trim()
                .trim()
                .trim()
                .trim()
                .trim()
            )
        );
        list.forEach(item -> // violation
            System

                .

                    out

                .
                    println
                        (
                            item
                        )
        );
        Supplier<Runnable> s = () -> // violation
            (

            ) -> { // violation

                // some lambda body code







            };
        Supplier<String> s1 = () -> // violation, 11 lines
            "1"
                + "2"
                + "3"
                + "4"
                + "5"
                + "6"
                + "7"
                + "8"
                + "9"
                + "0"
                + "1"
            ;
        Supplier<String> s2 = () -> // ok, 10 lines
            "1"
                + "2"
                + "3"
                + "4"
                + "5"
                + "6"
                + "7"
                + "8"
                + "9"
                + "0"
            ;
        list.forEach(item -> { // ok, 10 lines
            System.out.println(1);
            System.out.println(2);
            System.out.println(3);
            System.out.println(4);
            System.out.println(5);
            System.out.println(6);
            System.out.println(7);
            System.out.println(8);
        });
    }
}
