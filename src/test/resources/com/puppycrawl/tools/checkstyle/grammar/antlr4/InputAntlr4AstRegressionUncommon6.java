package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.util.Collections;

public class InputAntlr4AstRegressionUncommon6 {
    class SuperClassA{
        protected SuperClassA bs;
        protected SuperClassA s;
        Object s2;
    }
    class SuperClassA2{}


    /*
     * Note: inherited fields of a nested class shadow outer scope variables
     *      Note: only if they are accessible!
     */
    public class FieldAccessSuper extends SuperClassA {
        private SuperClassA s;

        public void foo() {
            // simple super field access
            // Primary[Prefix[Name[s]]]
            s = new SuperClassA();

            // access inherited field through primary
            // Primary[ Prefix[Primary[(this)]], Suffix[s], Suffix[s2] ]
            (this).s.s2 = new SuperClassA2();

            // access inherited field, second 's' has inherited field 's2'
            // Primary[Prefix[Name[s.s.s2]]]
            s.s.s2 = new SuperClassA2();

            // field access through super
            // Primary[Prefix["super"], Suffix["field"]]
            super.s = new SuperClassA();

            // fully qualified case
            // Primary[Prefix[Name[net...FieldAccessSuper]], Suffix[this], Suffix[s]]
            FieldAccessSuper.this.s
                    = new SuperClassA();
        }

        public class Nested extends SuperClassA {
            SuperClassA a;
            class SubscriptionAdapter{}
            final /* synthetic */ SubscriptionAdapter this$0 = null;
            final /* synthetic */ Object val$argHolder = null;

            public void foo() {
                // access enclosing super field
                // Primary[Prefix[Name[s]]]
                s = new SuperClassA();

                // access Nested inherited field
                // Primary[Prefix[Name[bs]]]
                bs = new SuperClassA();

                // access super field with fully qualified stuff
                // Primary[Prefix["FieldAccessSuper"], Suffix[Nested],
                //                  Suffix["super"], Suffix["bs"]]
                FieldAccessSuper.Nested.super.bs = new SuperClassA();

                // refers to the enclosing class's immediate super class's field
                // Primary[Prefix["FieldAccessSuper"], Suffix["super"], Suffix["s"]]
                FieldAccessSuper.super.s = new SuperClassA();
            }
            Object x = Collections .<String, Integer>emptyMap();

            Nested() {
                super();
            }

            int bar() {
                return super.hashCode();
            }

            boolean method() {
                return super.equals(new Object());
            }
        }
    }
}
