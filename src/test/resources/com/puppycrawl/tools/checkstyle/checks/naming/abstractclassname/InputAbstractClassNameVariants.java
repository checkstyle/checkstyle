/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

// violation below 'Name 'InputAbstractClassNameVariants' must match pattern '\^Abstract\.\+\$'.'
abstract public class InputAbstractClassNameVariants {
}

// violation below 'Name 'NonAbstractClassNameVa' must match pattern '\^Abstract\.\+\$'.'
abstract class NonAbstractClassNameVa {
}

abstract class AbstractClassOtherVa {
    // violation below 'Name 'NonAbstractInnerClassVa' must match pattern '\^Abstract\.\+\$'.'
    abstract class NonAbstractInnerClassVa {
    }
}

class NonAbstractClassVa {
}


class AbstractClassVa { // violation 'Class 'AbstractClassVa' must be declared as 'abstract'.'
}

abstract class AbstractClassName2Va {
    // violation below 'Class 'AbstractInnerClassVa' must be declared as 'abstract'.'
    class AbstractInnerClassVa {
    }
}
