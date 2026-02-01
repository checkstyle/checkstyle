/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import static java.lang.Math.PI; import static
        java.util.Collections.sort; // violation 'Only one statement per line allowed.'

import module java.base; import
        module java.sql; // violation 'Only one statement per line allowed.'

import java.util.List; import
        java.util.ArrayList; // violation 'Only one statement per line allowed.'

public class InputOneStatementPerLineImportStatements {
    int a = 2; int
      b = 3; // violation 'Only one statement per line allowed.'
}
