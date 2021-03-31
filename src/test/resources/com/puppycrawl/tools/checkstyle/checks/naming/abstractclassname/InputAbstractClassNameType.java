package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;
/* Config:
 * modifier = abstract
 *
 */
abstract public class InputAbstractClassNameType { // ok
}
// abstract --> ^Abstract.*$
abstract class NonAbstractClassNameType { // ok
}

class AbstractClassType { // violation
    abstract class NonAbstractInnerClass { // ok
    }
}

abstract class NonAbstractClassType { // ok
}

//^Abstract.*$ --> abstract
class AbstractClassTypes { // violation
}

abstract class AbstractClassName2Type { // ok
    abstract class AbstractInnerClassType { // ok
    }
}
