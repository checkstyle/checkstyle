/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract public class InputAbstractClassNameType { // ok
}

abstract class NonAbstractClassNameType { // ok
}

class AbstractClassType { // violation
    abstract class NonAbstractInnerClass { // ok
    }
}

abstract class NonAbstractClassType { // ok
}


class AbstractClassTypes { // violation
}

abstract class AbstractClassName2Type { // ok
    abstract class AbstractInnerClassType { // ok
    }
}
