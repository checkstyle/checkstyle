package com.puppycrawl.tools.checkstyle.naming;

abstract public class InputAbstractClassName {
}
// abstract --> ^Abstract.*$
abstract class NonAbstractClassName {
}

abstract class AbstractClassOther {
    abstract class NonAbstractInnerClass {
    }
}

class NonAbstractClass {
}

//^Abstract.*$ --> abstract
class AbstractClass {
}

abstract class AbstractClassName2 {
    class AbstractInnerClass {
    }
}
