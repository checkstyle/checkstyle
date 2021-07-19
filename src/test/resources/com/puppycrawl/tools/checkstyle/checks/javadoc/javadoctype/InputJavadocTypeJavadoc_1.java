/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = ABC
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
class InputJavadocTypeJavadoc_1 // violation
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
class InputJavadocTypeJavadoc_11 // violation
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
class InputJavadocTypeJavadoc_21 // violation
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
class InputJavadocType_1
{
}

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
enum InputJavadocTypeEnum_1 // violation
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
enum InputJavadocTypeEnum_11 // violation
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
enum InputJavadocTypeEnum_21 // violation
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
@interface InputJavadocInterfaceType_1
{
}

/**
 * Testing author and version tag patterns
 ****    @author Oliver Burn
 * @version 1.0
 */
@interface InputJavadocInterface_1 // violation
{
}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author Oliver Burn
 * *@version 1.0
 */
@interface InputJavadocInterface_11 // violation
{
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 * @author Oliver Burn
 * @version 1.0 */
@interface InputJavadocInterface_21 // violation
{
}

//Testing tag on first comment line
/**
* @author ABC
* @version 1.1
*/
@interface InputJavadocInterfaceType_11
{
}
