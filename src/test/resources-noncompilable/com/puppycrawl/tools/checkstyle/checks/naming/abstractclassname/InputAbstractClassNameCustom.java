package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

/* Config:
 * pattern = "^NonAbstract.+$"
 */
abstract class NonAbstractClassName { // ok
}

abstract class InputAbstractClassName { // violation
}

abstract class AbstractClassOther { // violation
}

abstract class AbstractClassName2 { // violation
}