package com.puppycrawl.tools.checkstyle.checks.coding.prefermethodreference;

import static java.lang.Long.sum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

/**
 * Config = default
 * Method calls cases
 */
public class InputPreferMethodReferenceMethodCalls {
    String field;
    List<String> list;
    // method calls
    Consumer<Object> c = a -> System.out.println(a); // violation
    BiConsumer<Long, Long> bc = (a, b) -> sum(a,b); // violation
    BiConsumer<Long, Long> bc2 = (a, b) -> Long.sum(a,b); // violation
    Function<Long, Long> f = a -> a.longValue(); // violation
    Function<Long, Boolean> f4 = a -> super.equals(a); // violation
    Runnable r = () -> System.out.println(); // violation
    Function<Object, Object> f5 = a -> this.method(a); // violation
    BiFunction<String, String, Integer> f8 = (a,b) -> a.lastIndexOf(b); // violation
    Function<String, String> f9 = concat -> field.concat(field).concat(concat); // violation
    Function<String, String> f10 = a -> ((a)).trim(); // violation
    Function<Integer, Integer> f11 = a -> new ArrayList<Integer>(){}.get(a); // violation
    Supplier<Integer> f12 = () -> int[].class.hashCode(); // violation
    Function<String, String> f13 = bool -> (bool() ? null : field).concat(bool); // violation
    Function<String, String> f14 = a -> "das".concat(a); // violation
    Function<String, Boolean> f15 =
        (equals) -> (bool() ? list.removeIf(field::equals) : list).equals(equals); // violation
    Function<String, String> f16 = field -> this.field.concat(field); // violation
    Function<String, String> f17 = (String String) -> String.trim(); // violation
    BiFunction<String, Long, Boolean> f19 =
        (String String, Long Long) -> String.equals(Long); // violation
    Function<Object, Integer> func = (@Nonnull Object obj) -> obj.hashCode(); // violation

    // not violations
    BiConsumer<Long, Long> callOnArg = (a, b) -> a.sum(a,b);
    BiConsumer<Long, Long> reversed = (a, b) -> sum(b,a);
    BiConsumer<Long, Long> reversed2 = (a, b) -> b.compareTo(a);
    Function<Long, Boolean> inverted = a -> !super.equals(a);
    Function<Long, Long> inverted2 = a -> -a.longValue();
    Consumer<Object> noParamUse = a -> System.out.println(field);
    Consumer<String> paramUse = a -> ((a=null).concat(field)).equals(a);
    Consumer<String> paramUse2 = a -> ("das".concat(a)).equals(a);
    Function<String, String> paramUse3 = bool -> (bool() ? bool : field).trim();
    Function<String, Boolean> paramUse4 =
        (equals) -> (bool() ? list.removeIf(equals::equals) : list).equals(equals);
    Consumer<String> twoMethods = trim -> trim.trim().trim();
    Consumer<String> twoMethods2 = a -> a.trim().concat("");
    Runnable constUse = () -> System.out.println("das");
    BiFunction<String, String, Integer> constExpr = (a,b) -> a.lastIndexOf('a');
    Function<Long, Long> constExpr2 = a -> a.longValue() + 1;
    Function<Long, Integer> constExpr3 = a -> Objects.hash(a, null);
    BiFunction<String, String, Integer> fieldUse2 = (a,b) -> a.lastIndexOf(a, list.size());
    BiFunction<String, String, Integer> oneArg = (a,b) -> a.length();
    // we cannot infer types, so cases below are ignored
    // first case can be replaced, but second one not
    Function<String, Object> cast1 = a -> (Object) "".concat(a);
    Function<Object, Long> cast2 = a -> (Long) this.method(a);
    Function<Object, Long> cast3 = a -> ((Long) a).longValue();
    Runnable oneReturnInLambda = () -> {
        return;
    };
    Runnable noStmnts = () -> {};
    Object method(Object p){
        return p;
    }
    boolean bool() {
        return true;
    }
}
