/*
AvoidStarImport
excludes = java.io.*
allowClassImports = (default)false
allowStaticMemberImports = (default)false
maxAllowedStarImports = 1

*/

// non-compiled with javac: Compilable with Java25

import java.util.*;
import java.io.*;
import java.lang.*; // violation 'Only '1' star import is allowed per file.'
import static java.lang.Math.*; // violation 'Only '1' star import is allowed per file.'

void main() {
    System.out.println("compact");
}
