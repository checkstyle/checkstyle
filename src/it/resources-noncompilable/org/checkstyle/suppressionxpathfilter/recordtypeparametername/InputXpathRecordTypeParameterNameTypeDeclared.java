//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

record InputXpathRecordTypeParameterNameTypeDeclared <foo extends Serializable & Cloneable> // warn
(LinkedHashMap<String, Node> linkedHashMap) {

}

