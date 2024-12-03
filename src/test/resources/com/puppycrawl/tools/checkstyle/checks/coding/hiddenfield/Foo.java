package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

@FunctionalInterface
interface Function <A, B> {
    public B apply (A a, B b);
}

@FunctionalInterface
interface SomeFunction<One, Two> {
    public Two apply(One one, Two two);
}

@FunctionalInterface
public interface Foo {
    public String apply();
}

@FunctionalInterface
interface FunctionWithComplexGenerics<One, Two> {
    public Two foo(One one, Two two);
}