/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

// violation below '.*'InputAbstractClassNameFormerFalsePositive' .* match .* '\^Abstract\.\+\$'.'
abstract public class InputAbstractClassNameFormerFalsePositive {
}

abstract class AbstractClassFP {
}

abstract class AbstractClassOtherFP {
    // violation below 'Name 'NonAbstractInnerClassFP' must match pattern '\^Abstract\.\+\$'.'
    abstract class NonAbstractInnerClassFP {
    }
}

class NonAbstractClassFP {
}

// violation below 'Class 'AbstractClassNameFP' must be declared as 'abstract'.'
class AbstractClassNameFP {
}

abstract class AbstractClassName2FP {
    // violation below 'Class 'AbstractInnerClassFP' must be declared as 'abstract'.'
    class AbstractInnerClassFP {
    }
}
