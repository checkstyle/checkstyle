package com.puppycrawl.tools.checkstyle.checks.coding.prefermethodreference;

import java.util.function.*;
import javax.annotation.Nonnull;

/**
 * Config: default
 * Method calls cases
 */
public class InputPreferMethodReferenceMethodCallsDefault {

    Function<Long, Boolean> superCall = a -> super.equals(a); // violation
    Function<Long, Boolean> superCall1 = super::equals; // ok
    Function<Object, Object> thisCall = a -> this.methodOneArg(a); // violation
    Function<Object, Object> thisCall1 = this::methodOneArg; // ok
    Function<String, String> parensCall = a -> ((a)).trim(); // violation
    Function<String, String> parensCall1 = String::trim; // ok
    Function<String, String> noParensCall = a -> a.trim(); // violation
    Function<String, String> noParensCall1 = String::trim; // ok
    Function<String, String> literalCall = a -> "das".concat(a); // violation
    Function<String, String> literalCall1 = "das"::concat; // ok
    Function<Object, Integer> annotation = (@Nonnull Object obj) -> obj.hashCode(); // violation
    Function<Object, Integer> annotation1 = Object::hashCode; // ok
    Function<String, String> type = (String String) -> String.trim(); // violation
    Function<String, String> type1 = String::trim; // ok
    BiFunction<String, Long, Boolean> twoArgs1 =
        (String String, Long Long) -> String.equals(Long); // violation
    BiFunction<String, Long, Boolean> twoArgs11 = String::equals; // ok
    BiFunction<String, Long, Object> twoArgs2 =
        (String String, Long Long) -> methodTwoArgs(String, Long); // violation
    BiFunction<String, Long, Object> twoArgs21 =
        this::methodTwoArgs; // ok
    BiFunction<String, Long, Object> twoArgs3 =
        (String String, Long Long) -> this.methodTwoArgs(String, Long); // violation
    BiFunction<String, Long, Object> twoArgs31 =
        this::methodTwoArgs; // ok
    Runnable noArgs = () -> methodNoArgs(); // violation
    Runnable noArgs1 = this::methodNoArgs; // ok
    Runnable classLiteral = () -> int[].class.hashCode(); // violation
    Runnable classLiteral1 = int[].class::hashCode; // ok
    Runnable classLiteral2 = () -> String.class.hashCode(); // violation
    Runnable classLiteral21 = String.class::hashCode; // ok

    // not violation, expression on left side
    Consumer<String> constantExpr = a -> ((1 + "dasd") + "das").concat(a);
    Runnable fieldExpression = () -> System.out.println();
    Supplier<Boolean> longChain = () -> "string".trim().toLowerCase().intern().isEmpty();

    Object methodOneArg(Object p){
        return p;
    }
    Object methodTwoArgs(String p, Long l){
        return p;
    }
    void methodNoArgs() {
    }

}
