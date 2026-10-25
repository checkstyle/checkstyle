/*
RecordTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.recordtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.junit.platform.engine.support.hierarchical.Node;


// violation below 'Name 'foo' must match pattern '^[A-Z]$'.'
record InputXpathRecordTypeParameterNameTypeDeclared <foo extends Serializable & Cloneable>
(LinkedHashMap<String, Node> linkedHashMap) {

}
