////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
class InputJavadoc
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
class InputJavadoc1
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
class InputJavadoc2
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
class InputJavadocType
{
}

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
enum InputJavadocEnum
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
enum InputJavadocEnum1
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
enum InputJavadocEnum2
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
@interface InputJavadocInterfaceType
{
}

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
@interface InputJavadocInterface
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
@interface InputJavadocInterface1
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
@interface InputJavadocInterface2
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
@interface InputJavadocInterfaceType1
{
}
