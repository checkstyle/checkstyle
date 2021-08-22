/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract public class InputAbstractClassNameVariants { // violation
}

abstract class NonAbstractClassNameVa { // violation
}

abstract class AbstractClassOtherVa { // ok
    abstract class NonAbstractInnerClassVa { // violation
    }
}

class NonAbstractClassVa {
}


class AbstractClassVa { // violation
}

abstract class AbstractClassName2Va { // ok
    class AbstractInnerClassVa { // violation
    }
}
