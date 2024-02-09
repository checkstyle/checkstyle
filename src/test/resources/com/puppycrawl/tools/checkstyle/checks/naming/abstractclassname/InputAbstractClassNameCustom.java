/*
AbstractClassName
format = ^NonAbstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

// violation below 'Name 'InputAbstractClassNameCustom' must match pattern '\^NonAbstract\.\+\$'.'
abstract public class InputAbstractClassNameCustom {
}

abstract class NonAbstractClassNameCustom {
}

// violation below 'Name 'AbstractClassOtherCustom' must match pattern '\^NonAbstract\.\+\$'.'
abstract class AbstractClassOtherCustom {
    abstract class NonAbstractInnerClass {
    }
}

class NonAbstractClassCustom {
}

class AbstractClassCustom {
}

// violation below 'Name 'AbstractClassName2Custom' must match pattern '\^NonAbstract\.\+\$'.'
abstract class AbstractClassName2Custom {
    class AbstractInnerClass {
    }
}
