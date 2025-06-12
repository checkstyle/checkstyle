// Java17
package org.checkstyle.suppressionxpathfilter.recordtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.junit.platform.engine.support.hierarchical.Node;


record InputXpathRecordTypeParameterNameTypeDeclared <foo extends Serializable & Cloneable> // warn
(LinkedHashMap<String, Node> linkedHashMap) {

}

