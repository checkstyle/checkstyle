package com.puppycrawl.tools.checkstyle.naming;

abstract public class InputAbstractClassName {
}
// abstract --> ^Abstract.*$|^.*Factory$
abstract class NonAbstractClassName {
}

abstract class FactoryWithBadName {
}

abstract class AbstractClassName {
    abstract class NonAbstractInnerClass {
    }
}

abstract class ClassFactory {
    abstract class WellNamedFactory {
    }
}

class NonAbstractClass {
}

//^Abstract.*$|^.*Factory$ --> abstract
class AbstractClass {
}

class Class1Factory {
}

abstract class AbstractClassName2 {
    class AbstractInnerClass {
    }
}

abstract class Class2Factory {
    class WellNamedFactory {
    }
}
