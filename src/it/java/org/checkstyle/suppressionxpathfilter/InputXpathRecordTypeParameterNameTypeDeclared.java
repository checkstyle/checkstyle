package org.checkstyle.suppressionxpathfilter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import org.junit.platform.engine.support.hierarchical.Node;

record InputXpathRecordTypeParameterNameTypeDeclared <foo extends Serializable & Cloneable> // warn
(LinkedHashMap<String, Node> linkedHashMap) {

}

