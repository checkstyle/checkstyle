/*
com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck
processJavadoc = false
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

*/

package com.puppycrawl.tools.checkstyle.api.fullident;

import static java.io.File. // violation 'Unused import - java.io.File.createTempFile.'
    createTempFile;

import static java.util // violation 'Unused import - java.util.Set.'
    .Set;

import java.util. // violation 'Unused import - java.util.Date.'
    Date;

import java.util // violation 'Unused import - java.util.Calendar.'
    .Calendar;

public class InputFullIdentCommentInFullIdent {
}
