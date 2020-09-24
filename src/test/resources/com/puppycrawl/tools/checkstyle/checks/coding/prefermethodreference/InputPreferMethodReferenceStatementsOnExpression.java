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

/**
 * Config: detectForExpression = true
 * Braced statements cases
 */
public class InputPreferMethodReferenceStatementsOnExpression {
    int field;
    String field2;
    List<String> list;
    // statement lists
    Consumer<Object> sl = a -> {System.out.println(a);}; // violation
    Consumer<Object> sl1 = System.out::println; // ok
    BiConsumer<Long, Long> sl2 = (a, b) -> {Long.sum(a,b);}; // violation
    BiConsumer<Long, Long> sl29 = Long::sum; // ok
    Function<Long, Long> sl3 = a -> {return a.longValue();}; // violation
    Function<Long, Long> sl31 = Long::longValue; // ok
    Function<Long, Boolean> sl4 = a -> {return super.equals(a);}; // violation
    Function<Long, Boolean> sl41 = super::equals; // ok
    Function<String, Example> sl23 = a -> {return new Example(a);}; // violation
    Function<String, Example> sl231 = Example::new; // ok
    Function<Object, Object> sl6 = a -> {return this.methodOneArg(a);}; // violation
    Function<Object, Object> sl61 = this::methodOneArg; // ok
    BiFunction<String, String, Integer> sl7 = (a,b) -> {return a.lastIndexOf(b);}; // violation
    BiFunction<String, String, Integer> sl71 = String::lastIndexOf; // ok
    BiFunction<String, String, Example> sl24 =
        (a,b) -> {return new Example(a, b);}; // violation
    BiFunction<String, String, Example> sl241 = Example::new; // ok
    Runnable sl9 = () -> {System.out.println();}; // violation
    Runnable sl91 = System.out::println; // violation
    Supplier<Object> sl20 = () -> {return new Object();}; // violation
    Supplier<Object> sl201 = Object::new; // ok
    Function<Integer, Object> sl21 = a -> {return new int[a];}; // violation
    Function<Integer, Object> sl211 = int[]::new; // ok
    Function<Integer, Object> sl22 = a -> {return new int[a][];}; // violation
    Function<Integer, Object> sl221 = int[][]::new; // ok
    Function<Integer, Object> sl25 = a -> {{{return new int[a][];}}}; // violation
    Function<Integer, Object> sl251 = int[][]::new; // ok
    BiConsumer<Long, Long> bc = (a, b) -> {sum(a,b);}; // violation
    BiConsumer<Long, Long> bc1 = Long::sum; // ok
    Runnable r = () -> {System.out.println();}; // violation
    Runnable r1 = System.out::println; // ok
    BiFunction<String, String, Integer> f8 = (a,b) -> {return a.lastIndexOf(b);}; // violation
    BiFunction<String, String, Integer> f81 = String::lastIndexOf; // ok
    Function<String, String> f9 =
        concat -> {return field2.concat(field2).concat(concat);}; // violation
    Function<String, String> f91 = field2.concat(field2)::concat; // ok
    Function<String, String> f10 = a -> {return ((a)).trim();}; // violation
    Function<String, String> f101 = String::trim; // ok
    Function<Integer, Integer> f11 = a -> {return new ArrayList<Integer>(){}.get(a);}; // violation
    Function<Integer, Integer> f111 = new ArrayList<Integer>() {}::get; // ok
    Supplier<Integer> f12 = () -> {return int[].class.hashCode();}; // violation
    Supplier<Integer> f121= int[].class::hashCode; // ok
    Function<String, String> f13 = bool ->
    {return (bool() ? null : field2).concat(bool);}; // violation
    Function<String, String> f131 = (bool() ? null : field2)::concat; // ok
    Function<String, String> f14 = a -> {return "das".concat(a);}; // violation
    Function<String, String> f141 = "das"::concat; // ok
    Function<String, Boolean> f15 = (equals) ->
    {return (bool() ? list.removeIf(field2::equals) : list).equals(equals);}; // violation
    Function<String, Boolean> f151 = (bool() ? list.removeIf(field2::equals) : list)::equals; // ok
    Function<String, String> f16 = field2 -> {return this.field2.concat(field2);}; // violation
    Function<String, String> f161 = this.field2::concat; // ok
    Function<String, String> f17 = (String String) -> {return String.trim();}; // violation
    Function<String, String> f171 = String::trim; // ok
    BiFunction<String, Long, Boolean> f19 =
        (String String, Long Long) -> {return String.equals(Long);}; // violation
    BiFunction<String, Long, Boolean> f191 = String::equals; // ok
    Supplier<List<Integer>> f20 = () -> { return new ArrayList<>(); }; // violation
    Supplier<List<Integer>> f201 = ArrayList::new; // ok
    Supplier<List<Integer>> f21 = () -> { return new ArrayList<Integer>(); }; // violation
    Supplier<List<Integer>> f211 = ArrayList::new; // ok
    Consumer<String> constantExpr = a -> ((1 + "dasd") + "das").concat(a); // violation
    Consumer<String> constantExpr1 = ((1 + "dasd") + "das")::concat; // ok
    Supplier<Boolean> chain = () -> toString().trim().toLowerCase().trim().isEmpty(); // violation
    Supplier<Boolean> chain1 = toString().trim().toLowerCase().intern()::isEmpty; // ok

    // not violations
    Consumer<Object> methodCall = a -> {System.out.println(methodOneArg(a));};
    BiConsumer<Long, Long> callOnArgParens = (a, b) -> {a.sum(a,b);};
    Consumer<String> identIsUsedTwice = a -> (a + "dasd").concat(a);
    BiConsumer<Long, Long> bc2Braces = (a, b) -> {Long.sum(b,a);};
    Consumer<Object> fieldUseBraces = a -> {System.out.println(field);};
    BiFunction<String, String, Integer> constExprBraces =
        (a,b) -> {return a.lastIndexOf('a');};
    Function<Long, Long> constExpr2Braces = a -> {return a.longValue() + 1;};
    Function<Long, Integer> constExpr3Braces = a -> {return Objects.hash(a, null);};
    BiFunction<String, String, Integer> fieldUse2Braces =
        (a,b) -> {return a.lastIndexOf(a, field);};
    BiFunction<String, String, Integer> oneArgBraces = (a,b) -> {return a.length();};
    BiFunction<String, Long, Boolean> flippedArgs =
        (String String, Long Long) -> {return Long.equals(String);};
    BiFunction<String, String, Integer> twoStmts =
        (a,b) -> { int c = 10; return a.length();};
    Function<String, String> cast =
             a -> (String) "string".concat(a); // no violation because of cast
    Consumer<Object> twoStmts2 = a -> {
        System.out.println();
        System.out.println();
    };
    Function<String, Boolean> twoStmts3 = k  -> {
            System.out.println(k);
            return bool();
    };
    Consumer<Object> twoStmts4 = a -> {;System.out.println(a);};
    Consumer<Object> twoStmts5 = a -> {System.out.println(a);;};
    Consumer<Object> twoStmts6 = a -> {
        System.out.println(a);
        if(bool()) System.out.println(a);
    };
    Consumer<Object> twoStmts7 = a -> {
        System.out.println(a);
        switch (1) {default: System.out.println(a);}
    };
    Consumer<Object> twoStmts8 = a -> {
        System.out.println(a);
        for(;;){}
    };
    Consumer<Object> twoStmts9 = a -> {
        System.out.println(a);
        while(bool()){}
    };
    Consumer<Object> twoStmts10 = a -> {
        System.out.println(a);
        do {} while(bool());
    };
    Runnable twoStmts11 = () -> {
        System.out.println();
        int a = 10;
    };
    Runnable twoStmts12 = () -> {
        System.out.println();
        throw new RuntimeException();
    };
    Runnable twoStmts13 = () -> {
        System.out.println();
        try{int a = 10;} catch (Exception e) {}
    };

    Object methodOneArg(Object p){
        return p;
    }
    boolean bool(){
        return true;
    }

    private class Example {
        public Example(String b, String... varargs) {
        }
    }
}
