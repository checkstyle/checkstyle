/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

// non-compiled with javac: no package by design of test

import java.util.List;
import java.util.List;
import java.util.Arrays; // violation 'Unused import - java.util.Arrays.'
import java.lang.String; // violation 'Unused import - java.lang.String.'
import static java.lang.Math.PI;

public class InputUnusedImportsFileInUnnamedPackage
{
    public static double pi=PI;
    public List myList;
}
