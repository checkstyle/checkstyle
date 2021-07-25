/*
RedundantImport


*/

//non-compiled with javac: no package by design of test

import java.util.List;
import java.util.List; // violation
import java.util.Arrays;
import java.lang.String; // violation
import static java.lang.Math.PI;

public class InputRedundantImport_UnnamedPackage
{
    public static double pi=PI;
    public List myList;
}
