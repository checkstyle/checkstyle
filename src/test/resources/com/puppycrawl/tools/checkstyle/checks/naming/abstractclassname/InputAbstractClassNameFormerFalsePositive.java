package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;
/* Config:
 * default
 *
 */
abstract public class InputAbstractClassNameFormerFalsePositive { // violation
}
// abstract --> ^Abstract.*$
abstract class AbstractClassFP { // violation
}

abstract class AbstractClassOtherFP { // ok
    abstract class NonAbstractInnerClassFP { // violation
    }
}

class NonAbstractClassFP { // g
}

//^Abstract.*$ --> abstract
class AbstractClassNameFP { // violation
}

abstract class AbstractClassName2FP { // ok
    class AbstractInnerClassFP { // violation
    }
}
