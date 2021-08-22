/*
AbstractClassName
format = ^NonAbstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

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

class AbstractClassCustom {
}


abstract class AbstractClassName2Custom { // violation
    class AbstractInnerClass {
    }
}
