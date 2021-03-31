package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;
/* Config:
 * pattern = "^Abstract.+$"
 *
 */
abstract public class InputAbstractClassName { // violation
}
// abstract --> ^Abstract.*$
abstract class NonAbstractClassName { // violation
}

abstract class AbstractClassOther { // ok
    abstract class NonAbstractInnerClass { // violation
    }
}

class NonAbstractClass { // ok
}

//^Abstract.*$ --> abstract
class AbstractClass { // ok
}

abstract class AbstractClassName2 { // ok
    class AbstractInnerClass { // ok
    }
}
