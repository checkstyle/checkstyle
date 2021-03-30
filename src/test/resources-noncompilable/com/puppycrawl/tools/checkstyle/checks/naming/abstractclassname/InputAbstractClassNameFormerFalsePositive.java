package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;
/* Config:
 * pattern = "^Abstract.+$"
 * modifier = "abstract"
 */
public abstract class InputAbstractClassNameFormerFalsePositive { // violation
}

class AbstractClass { // violation
}
