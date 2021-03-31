package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;
/* Config:
 * pattern = "^NonAbstract.+$"
 *
 */
abstract public class InputAbstractClassNameCustom { // violation
}

abstract class NonAbstractClassNameCustom { // ok
}

abstract class AbstractClassOtherCustom { // violation
    abstract class NonAbstractInnerClass { // ok
    }
}

class NonAbstractClassCustom { // ok
}

class AbstractClassCustom { // violation
}


abstract class AbstractClassName2Custom { // violation
    class AbstractInnerClass { // violation
    }
}
