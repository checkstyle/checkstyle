/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

import java.lang.annotation.Inherited;
/**
 *
 * @author idubinin
 *@deprecated
 *@deprecated // violation 'Duplicate @deprecated tag.'
 *stuff
 *stuff
 */ // violation below 'Must include.*@java.lang.Deprecated annotation.*@deprecated.*description.'
public class InputMissingDeprecatedClass
{

}
