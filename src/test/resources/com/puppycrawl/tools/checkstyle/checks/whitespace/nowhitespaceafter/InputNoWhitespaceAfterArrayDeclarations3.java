package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputNoWhitespaceAfterArrayDeclarations3
{
    public void testWithAnnotationInMidle1(final char @AnnotationAfterTest [] a) {}//Correct
    public void testWithAnnotationInMidle2(final char@AnnotationAfterTest [] a) {}//Correct
    public void testWithAnnotationInMidle3(final char @AnnotationAfterTest[] a) {}//Correct
    public void testWithAnnotationInMidle4(final char@AnnotationAfterTest[]a) {}//Correct

    @Target(ElementType.TYPE_USE)
    @interface AnnotationAfterTest {
    }
}
