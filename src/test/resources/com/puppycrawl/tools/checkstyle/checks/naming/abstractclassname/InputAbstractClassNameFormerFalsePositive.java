/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract public class InputAbstractClassNameFormerFalsePositive { // violation
}

abstract class AbstractClassFP {
}

abstract class AbstractClassOtherFP { // ok
    abstract class NonAbstractInnerClassFP { // violation
    }
}

class NonAbstractClassFP {
}


class AbstractClassNameFP { // violation
}

abstract class AbstractClassName2FP { // ok
    class AbstractInnerClassFP { // violation
    }
}
