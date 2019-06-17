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
    BiConsumer<Long, Long> sl1 = (a, b) -> {Long.sum(a,b);}; // violation
    Function<Long, Long> sl3 = a -> {return a.longValue();}; // violation
    Function<Long, Boolean> sl4 = a -> {return super.equals(a);}; // violation
    Function<String, Example> sl23 = a -> {return new Example(a);}; // violation
    Function<Object, Object> sl6 = a -> {return this.methodOneArg(a);}; // violation
    BiFunction<String, String, Integer> sl7 = (a,b) -> {return a.lastIndexOf(b);}; // violation
    BiFunction<String, String, Example> sl24 =
        (a,b) -> {return new Example(a, b);}; // violation
    Runnable sl9 = () -> {System.out.println();}; // violation
    Supplier<Object> sl20 = () -> {return new Object();}; // violation
    Function<Integer, Object> sl21 = a -> {return new int[a];}; // violation
    Function<Integer, Object> sl22 = a -> {return new int[a][];}; // violation
    Function<Integer, Object> sl25 = a -> {{{return new int[a][];}}}; // violation
    BiConsumer<Long, Long> bc = (a, b) -> {sum(a,b);}; // violation
    Runnable r = () -> {System.out.println();}; // violation
    BiFunction<String, String, Integer> f8 = (a,b) -> {return a.lastIndexOf(b);}; // violation
    Function<String, String> f9 =
        concat -> {return field2.concat(field2).concat(concat);}; // violation
    Function<String, String> f10 = a -> {return ((a)).trim();}; // violation
    Function<Integer, Integer> f11 = a -> {return new ArrayList<Integer>(){}.get(a);}; // violation
    Supplier<Integer> f12 = () -> {return int[].class.hashCode();}; // violation
    Function<String, String> f13 = bool ->
    {return (bool() ? null : field2).concat(bool);}; // violation
    Function<String, String> f14 = a -> {return "das".concat(a);}; // violation
    Function<String, Boolean> f15 = (equals) ->
    {return (bool() ? list.removeIf(field2::equals) : list).equals(equals);}; // violation
    Function<String, String> f16 = field2 -> {return this.field2.concat(field2);}; // violation
    Function<String, String> f17 = (String String) -> {return String.trim();}; // violation
    BiFunction<String, Long, Boolean> f19 =
        (String String, Long Long) -> {return String.equals(Long);}; // violation
    Supplier<List<Integer>> f20 = () -> { return new ArrayList<>(); }; // violation
    Supplier<List<Integer>> f21 = () -> { return new ArrayList<Integer>(); }; // violation
    Consumer<String> constantExpr = a -> ((1 + "dasd") + "das").concat(a);

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
