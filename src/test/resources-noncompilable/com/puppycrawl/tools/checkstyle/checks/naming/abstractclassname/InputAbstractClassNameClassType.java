package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

/* Config:
 * modifier = abstract
 */
abstract class AbstractClassName { // ok
}

class AbstractClass { // violation
}

class AbstractInnerClass { // violation
}
