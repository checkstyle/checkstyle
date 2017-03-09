//start line index in expected file is 2
package com.puppycrawl.tools.checkstyle.grammars;
;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InputRegressionJava8Class1 {
    static class Inner1 { static class Inner2<V> { public void m() {} } }
    static class Inner3<T> { public void m() {} }

    public void m() {}
    public static void sm() {}
    void m1() throws @Nullable Exception {}
    public static <T> void m2(T @Nullable [] array) {}
    public void m3() throws NullPointerException, @Nullable ArrayIndexOutOfBoundsException {}
    public void m4(InputRegressionJava8Class1 this) {}
    public void m5(@Nullable InputRegressionJava8Class1 this) {}

    {
        List<String> vlist = new ArrayList<String>();
    }

    public void instructions() {
        // used to let inputs compile
        boolean b = Math.random() > 0;

        int vint;
        Object o = null;
        List<String> vlist;
        vlist = new ArrayList<String>();
        Supplier<?> s;

        // annotations
        Map.@Nullable Entry e;
        String str = (@Nullable String) "";
        (new Inner3()).<@Nullable String>m();

        // method reference
        IntBinaryOperator ibo = Math::max;
        s = InputRegressionJava8Class1::new;
        s = Inner1.Inner2<String>::new;

        // lambda
        Runnable r1 = () -> m();
        Runnable r2 = () -> { m(); } ;
        Collections.sort(vlist, (l,  r) -> l == r ? 0 : 1);
        Predicate<?> t = (b) ? null : object -> o.equals(object);
        Double mPi = Math.PI;
    }

    static final Comparator<?> f = (Comparator<?>) (dateTime1, dateTime2) -> { return 0; };

    private class Inner {
        public Inner(InputRegressionJava8Class1 InputRegressionJava8Class1.this) {}
    }
}
;
class InputRegressionJava8TypeParam <@Nullable T> {
}
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE_USE })
@interface Nullable {
}
