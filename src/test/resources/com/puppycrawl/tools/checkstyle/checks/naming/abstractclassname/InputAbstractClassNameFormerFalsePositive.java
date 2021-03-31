package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;
/*
 * Config: default
 *
 */
abstract public class InputAbstractClassNameFormerFalsePositive { // violation
}

abstract class AbstractClassFP { // violation
}

abstract class AbstractClassOtherFP { // ok
    abstract class NonAbstractInnerClassFP { // violation
    }
}

class NonAbstractClassFP { // violation
}


class AbstractClassNameFP { // violation
}

abstract class AbstractClassName2FP { // ok
    class AbstractInnerClassFP { // violation
    }
}
