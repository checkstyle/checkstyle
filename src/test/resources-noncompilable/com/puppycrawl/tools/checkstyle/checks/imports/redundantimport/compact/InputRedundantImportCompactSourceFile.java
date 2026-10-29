/*
RedundantImport


*/

// non-compiled with javac: Compilable with Java25

import java.lang.*; // violation 'Redundant import from the java.lang package'
import java.lang.String; // violation 'Redundant import from the java.lang package'
import java.util.List;
import java.util.List; // violation 'Duplicate import to line 11'
import static java.util.Arrays.asList;
import static java.util.Arrays.asList; // violation 'Duplicate import to line 13'
import module java.base;
import module java.base; // violation 'Duplicate import to line 15'
import java.sql.Connection;

void main() {
}
