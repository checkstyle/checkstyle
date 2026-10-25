/*
RecordTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.recordtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.junit.platform.engine.support.hierarchical.Node;


record InputXpathRecordTypeParameterNameTypeDeclared <foo extends Serializable & Cloneable> // violation 'Name 'foo' must match pattern '^[A-Z]$'.'
(LinkedHashMap<String, Node> linkedHashMap) {

}
