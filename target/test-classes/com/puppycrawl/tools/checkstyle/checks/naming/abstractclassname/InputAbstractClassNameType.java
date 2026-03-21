/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract public class InputAbstractClassNameType {
}

abstract class NonAbstractClassNameType {
}

class AbstractClassType { // violation 'Class 'AbstractClassType' must be declared as 'abstract'.'
    abstract class NonAbstractInnerClass {
    }
}

abstract class NonAbstractClassType {
}


class AbstractClassTypes { // violation 'Class 'AbstractClassTypes' must be declared as 'abstract'.'
}

abstract class AbstractClassName2Type {
    abstract class AbstractInnerClassType {
    }
}
