//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 *
 * @author IljaDubinin
 */
public class InputNewHandler
{

    public static void test() {
        method(ArrayList::new);
    }

    private static void method(Supplier<?> s) {
    }

}
