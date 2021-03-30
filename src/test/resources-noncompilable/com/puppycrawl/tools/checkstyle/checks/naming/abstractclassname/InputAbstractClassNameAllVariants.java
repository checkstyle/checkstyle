package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;



import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

/* Config:
 * pattern = "^Abstract.+$"
 * modifier = abstract
 */
abstract class AbstractClassName { // ok
}

abstract class InputAbstractClassName { // violation
}

abstract class NonAbstractClassName { // violation
}

abstract class NonAbstractInnerClass { // violation
}

class AbstractClass { // violation
}

class AbstractInnerClass { // violation
}
