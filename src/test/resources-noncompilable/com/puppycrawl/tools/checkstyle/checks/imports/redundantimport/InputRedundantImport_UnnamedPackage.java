/*
RedundantImport


*/

// non-compiled with javac: missing package. Used for Testing purpose.

import java.util.List;
import java.util.List; // violation 'Duplicate import to line 9'
import java.util.Arrays;
import java.lang.String; // violation 'Redundant import from the java.lang package'
import static java.lang.Math.PI;

public class InputRedundantImport_UnnamedPackage
{
    public static double pi=PI;
    public List myList;
}
