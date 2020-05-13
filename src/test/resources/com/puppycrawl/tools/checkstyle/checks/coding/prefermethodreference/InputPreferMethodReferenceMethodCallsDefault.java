package com.puppycrawl.tools.checkstyle.checks.coding.prefermethodreference;

import java.util.function.*;
import javax.annotation.Nonnull;

/**
 * Config: default
 * Method calls cases
 */
public class InputPreferMethodReferenceMethodCallsDefault {

    Function<Long, Boolean> superCall = a -> super.equals(a); // violation
    Function<Object, Object> thisCall = a -> this.methodOneArg(a); // violation
    Function<String, String> parensCall = a -> ((a)).trim(); // violation
    Function<String, String> noParensCall = a -> a.trim(); // violation
    Function<String, String> literalCall = a -> "das".concat(a); // violation
    Function<Object, Integer> annotation = (@Nonnull Object obj) -> obj.hashCode(); // violation
    Function<String, String> type = (String String) -> String.trim(); // violation
    BiFunction<String, Long, Boolean> twoArgs1 =
        (String String, Long Long) -> String.equals(Long); // violation
    BiFunction<String, Long, Object> twoArgs2 =
        (String String, Long Long) -> methodTwoArgs(String, Long); // violation
    BiFunction<String, Long, Object> twoArgs3 =
        (String String, Long Long) -> this.methodTwoArgs(String, Long); // violation
    Runnable noArgs = () -> methodNoArgs(); // violation
    Runnable classLiteral = () -> int[].class.hashCode(); // violation
    Runnable classLiteral2 = () -> String.class.hashCode(); // violation

    // not violation, expression on left side
    Consumer<String> constantExpr = a -> ((1 + "dasd") + "das").concat(a);
    Runnable fieldExpression = () -> System.out.println();

    Object methodOneArg(Object p){
        return p;
    }
    Object methodTwoArgs(String p, Long l){
        return p;
    }
    void methodNoArgs() {
    }

}
