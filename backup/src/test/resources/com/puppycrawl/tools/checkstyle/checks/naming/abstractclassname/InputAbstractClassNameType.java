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

class AbstractClassType { // violation 'Class 'AbstractClassType' must be declared as 'abstract'.'
    abstract class NonAbstractInnerClass { // ok
    }
}

abstract class NonAbstractClassType { // ok
}


class AbstractClassTypes { // violation 'Class 'AbstractClassTypes' must be declared as 'abstract'.'
}

abstract class AbstractClassName2Type { // ok
    abstract class AbstractInnerClassType { // ok
    }
}
