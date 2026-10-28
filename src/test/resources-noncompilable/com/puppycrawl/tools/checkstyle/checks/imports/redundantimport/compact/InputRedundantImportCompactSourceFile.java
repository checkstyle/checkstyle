/*
RedundantImport


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.compact;

import java.lang.*; // violation 'Redundant import from the java.lang package'
import java.lang.String; // violation 'Redundant import from the java.lang package'
import java.util.List;
import java.util.List; // violation 'Duplicate import to line 12'
import static java.util.Arrays.asList;
import static java.util.Arrays.asList; // violation 'Duplicate import to line 14'
import module java.base;
import module java.base; // violation 'Duplicate import to line 16'
import java.sql.Connection;

void main() {
}
