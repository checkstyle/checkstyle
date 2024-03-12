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

abstract class AbstractClassOther {
    // violation below 'Name 'NonAbstractInnerClass' must match pattern '\^Abstract\.\+\$'.'
    abstract class NonAbstractInnerClass {
    }
}

class NonAbstractClass {
}


class AbstractClass {
}

abstract class AbstractClassName2 {
    class AbstractInnerClass {
    }
}
