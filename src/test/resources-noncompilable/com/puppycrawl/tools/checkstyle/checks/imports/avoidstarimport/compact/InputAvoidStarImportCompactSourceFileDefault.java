/*
AvoidStarImport
excludes = (default)
allowClassImports = (default)false
allowStaticMemberImports = (default)false
maxAllowedStarImports = (default)0

*/

// non-compiled with javac: Compilable with Java25

import java.io.*; // violation 'Using the '.*' form of import should be avoided.'
import java.util.*; // violation 'Using the '.*' form of import should be avoided.'
import static java.lang.Math.*; // violation 'Using the '.*' form of import should be avoided.'
import java.sql.Connection;

void main() {
    System.out.println("compact");
}
