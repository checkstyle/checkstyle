/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract public class InputAbstractClassName { // violation
}

abstract class NonAbstractClassName { // violation
}

abstract class AbstractClassOther { // ok
    abstract class NonAbstractInnerClass { // violation
    }
}

class NonAbstractClass { // ok
}


class AbstractClass { // ok
}

abstract class AbstractClassName2 { // ok
    class AbstractInnerClass { // ok
    }
}
