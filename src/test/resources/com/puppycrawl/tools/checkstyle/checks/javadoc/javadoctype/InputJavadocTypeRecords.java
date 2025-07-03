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

// Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Testing author and version tag patterns
 * ***    @author  Nick Mancuso
 *
 * @version 8.35
 */
class InputJavadocTypeRecords { // violation 'missing @author tag.'
}

/**
 * Testing author and version tag patterns
 * ***    @author  Nick Mancuso
 *
 * @version 8.37
 */
record MyRecordOne() { // violation 'missing @author tag.'

}

/**
 * Testing author and version tag patterns (there are not tags :)
 * SomeText @author  Nick Mancuso
 * *@version 8.37
 */
record MyRecordTwo() { // violation 'missing @author tag.'

    public MyRecordTwo {
    }
}

/**
 * Testing author and version tag patterns.
 * tags are multi line ones
 *
 * @author Nick Mancuso
 * @version 8.37
 */
record MyRecordThree() { // violation 'tag @author must match pattern 'ABC'.'

}

/**
 * Testing author and version tag patterns
 * ***    @author  Nick Mancuso
 *
 * @version 8.37
 */
record MyRecordFour() { // violation 'missing @author tag.'

}

record MyRecordFive() {

}
