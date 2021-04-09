package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;
/*
 * Config: pattern = "^Abstract.+$"
 *         modifier = abstract
 */
abstract public class InputAbstractClassNameVariants { // violation
}

abstract class NonAbstractClassNameVa { // violation
}

abstract class AbstractClassOtherVa { // ok
    abstract class NonAbstractInnerClassVa { // violation
    }
}

class NonAbstractClassVa { // violation
}


class AbstractClassVa { // violation
}

abstract class AbstractClassName2Va { // ok
    class AbstractInnerClassVa { // violation
    }
}
