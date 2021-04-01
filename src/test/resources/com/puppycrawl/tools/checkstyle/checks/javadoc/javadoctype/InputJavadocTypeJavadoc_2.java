////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/* Config:
 *
 * versionFormat = "\$Revision.*\$"
 */

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
class InputJavadocTypeJavadoc_2 // violation
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
class InputJavadocTypeJavadoc_12 // violation
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
class InputJavadocTypeJavadoc_22 // violation
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
class InputJavadocType_2 // violation
{
}

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
enum InputJavadocTypeEnum_2 // violation
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
enum InputJavadocTypeEnum_12 // violation
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
enum InputJavadocTypeEnum_22 // violation
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
@interface InputJavadocInterfaceType_2 // violation
{
}

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
@interface InputJavadocInterface_2 // violation
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
@interface InputJavadocInterface_12 // violation
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
@interface InputJavadocInterface_22
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
@interface InputJavadocInterfaceType_12 // violation
{
}
