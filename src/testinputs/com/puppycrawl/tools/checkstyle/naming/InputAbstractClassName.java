package com.puppycrawl.tools.checkstyle.checks.naming;

abstract public class InputAbstractClassName {
}

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
