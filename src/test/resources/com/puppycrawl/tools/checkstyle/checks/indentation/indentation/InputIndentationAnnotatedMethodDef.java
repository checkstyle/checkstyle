package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

public class InputIndentationAnnotatedMethodDef {                           //indent:0 exp:0

    @SuppressWarnings(                                                      //indent:4 exp:4
        value=""                                                            //indent:8 exp:8
    )                                                                       //indent:4 exp:4
public void testStartOfSequence1() {                                        //indent:0 exp:4 warn
    }                                                                       //indent:4 exp:4

    @SuppressWarnings(value="")                                             //indent:4 exp:4
public static void testStartOfSequence2() {                                 //indent:0 exp:4 warn
    }                                                                       //indent:4 exp:4

public void testStartOfSequence3() {                                        //indent:0 exp:4 warn
    }                                                                       //indent:4 exp:4

    @SuppressWarnings(value="")                                             //indent:4 exp:4
    public void testCorrectlyIndented() {                                   //indent:4 exp:4
    }                                                                       //indent:4 exp:4

    @SuppressWarnings(value="")                                             //indent:4 exp:4
void testNoModifier() {                                                     //indent:0 exp:4 warn
    }                                                                       //indent:4 exp:4

        @SuppressWarnings(value="")                                         //indent:8 exp:4 warn
    public void testAnnotationWrongIndent() {                               //indent:4 exp:4
    }                                                                       //indent:4 exp:4

        @SuppressWarnings(value="")                                         //indent:8 exp:4 warn
public void testBothWrongIndent() {                                         //indent:0 exp:4 warn
    }                                                                       //indent:4 exp:4

    @Deprecated                                                             //indent:4 exp:4
public InputIndentationAnnotatedMethodDef() {                               //indent:0 exp:4 warn
    }                                                                       //indent:4 exp:4

    @SuppressWarnings(value="") public void testAnnotationOnSameLine() {    //indent:4 exp:4
    }                                                                       //indent:4 exp:4

    static class Nested {                                                   //indent:4 exp:4

        @Deprecated                                                         //indent:8 exp:8
        @SuppressWarnings(value="")                                         //indent:8 exp:8
    public void testStackedAnnotationsNested() {                            //indent:4 exp:8 warn
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    @SuppressWarnings(value={                                               //indent:4 exp:4
        "unchecked"                                                         //indent:8 exp:8
    }) void testTypeOnAnnotationClosingLine() {                             //indent:4 exp:4
    }                                                                       //indent:4 exp:4

    <T extends Comparable &                                                 //indent:4 exp:4
        java.io.Serializable>                                               //indent:8 exp:8
        void testGenericWrappedTypeParams() {                               //indent:8 exp:8
    }                                                                       //indent:4 exp:4

    @SuppressWarnings(value="")                                             //indent:4 exp:4
<K, V>                                                                      //indent:0 exp:4 warn
        void testGenericWrappedAfterAnnotation() {                          //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0
