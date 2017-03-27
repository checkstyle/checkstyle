package com.puppycrawl.tools.checkstyle.grammars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
class InputRegressionJavaClass2 extends ArrayList implements List, Collection {
    public InputRegressionJavaClass2() { super(); }

    @Override
    public int size() { return super.size(); }
    @Override()
    public boolean isEmpty() { return InputRegressionJavaClass2.super.isEmpty(); }

    public class Inner1 {
        public void m() {
            class Inner2 {
            }
        };

        public InputRegressionJavaClass2 m2() {
            return InputRegressionJavaClass2.this;
        }
    }

    public void m() { this.new Inner1().m(); }
}
class _c1 {}
abstract class c2 {}
class c3<A> { public c3<A> m(){return null;} }
class c4<A,B> extends c3<A> {
    class c4a {}

    public c4() { <String>super(); }
    public c3<A> m() { return super.<A>m(); }
}
class c5 extends c4.c4a {
    c5() { new c4().super(); }
    c5(int a) { new c4().<String>super(); }
}
