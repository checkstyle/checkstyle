package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.util.function.Function;

public class InputAntlr4AstRegressionUnusualAnnotation {

    public class Inner<S> {
        public Inner() {}
        public <@A T> Inner(@B Object o) {}
        public <@C T> Object g(Inner<@D S> this, Object @E [] o) {
            return new @F int @G [5];
        }
    }

    public <@BL T extends @BO Inner<@BP Integer @BQ []>>
    @BR Inner<@BS Inner<@BT String>> func4() {
        return (@BU Inner<@BV Inner<@BW String>>)
            new <@BX Inner<@BY Integer>> @BZ Inner<@CA Inner<@CB String>>(null);
    }

     class Test {
        class InnerException extends Exception { }
        void foo() throws @C Test.@C InnerException {    }
    }

    @Target(ElementType.TYPE_USE) @interface C { }
}

@Retention(RUNTIME) @Target({TYPE_USE}) @interface A { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface B { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface C { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface D { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface E { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface F { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface G { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BL { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BM { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BN { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BO { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BP { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BQ { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BR { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BS { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BT { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BU { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BV { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BW { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BX { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BY { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface BZ { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface CA { }
@Retention(RUNTIME) @Target({TYPE_USE}) @interface CB { }
