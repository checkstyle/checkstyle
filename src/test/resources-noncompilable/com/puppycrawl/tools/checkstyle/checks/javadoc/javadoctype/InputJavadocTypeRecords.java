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

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Testing author and version tag patterns
 * ***    @author  Nick Mancuso
 *
 * @version 8.35
 */
class InputJavadocTypeRecords { // violation
}

/**
 * Testing author and version tag patterns
 * ***    @author  Nick Mancuso
 *
 * @version 8.37
 */
record MyRecord1() { // violation

}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author  Nick Mancuso
 * *@version 8.37
 */
record MyRecord2() { // violation

    public MyRecord2 {
    }
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 *
 * @author Nick Mancuso
 * @version 8.37
 */
record MyRecord3() { // violation

}

/**
 * Testing author and version tag patterns
 * ***    @author  Nick Mancuso
 *
 * @version 8.37
 */
record MyRecord4() { // violation

}

record MyRecord5() { // ok

}
