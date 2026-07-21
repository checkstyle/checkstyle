/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

import java.lang.annotation.Inherited;

// violation 5 lines below 'Duplicate @deprecated tag.'
/**
 *
 * @author idubinin
 *@deprecated
 *@deprecated
 *stuff
 *stuff
 */ // violation below 'Must include.*@java.lang.Deprecated annotation.*@deprecated.*description.'
public class InputMissingDeprecatedClass
{

}
