package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import org.eclipse.jdt.annotation.NonNull;

public class InputNoWhitespaceAfterArrayDeclarations3
{
    public void testWithAnnotationInMidle1(final char @NonNull [] a) {}//Correct
    public void testWithAnnotationInMidle2(final char@NonNull [] a) {}//Correct
    public void testWithAnnotationInMidle3(final char @NonNull[] a) {}//Correct
    public void testWithAnnotationInMidle4(final char@NonNull[]a) {}//Correct
}
