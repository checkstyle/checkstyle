/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

// violation below 'Name 'InputAbstractClassName' must match pattern '\^Abstract\.\+\$'.'
abstract public class InputAbstractClassName {
}

// violation below 'Name 'NonAbstractClassName' must match pattern '\^Abstract\.\+\$'.'
abstract class NonAbstractClassName {
}

abstract class AbstractClassOther { // ok
    // violation below 'Name 'NonAbstractInnerClass' must match pattern '\^Abstract\.\+\$'.'
    abstract class NonAbstractInnerClass {
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
