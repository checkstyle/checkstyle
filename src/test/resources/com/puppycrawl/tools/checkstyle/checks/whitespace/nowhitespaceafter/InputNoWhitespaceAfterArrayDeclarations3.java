package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputNoWhitespaceAfterArrayDeclarations3
{
    public void testWithAnnotationInMiddle1(final char @AnnotationAfterTest [] a) {}//Correct
    public void testWithAnnotationInMiddle2(final char@AnnotationAfterTest [] a) {}//Correct
    public void testWithAnnotationInMiddle3(final char @AnnotationAfterTest[] a) {}//Correct
    public void testWithAnnotationInMiddle4(final char@AnnotationAfterTest[]a) {}//Correct

    @Target(ElementType.TYPE_USE)
    @interface AnnotationAfterTest {
    }
}
