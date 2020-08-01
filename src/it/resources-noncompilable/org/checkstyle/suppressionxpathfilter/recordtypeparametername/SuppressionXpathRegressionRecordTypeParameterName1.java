//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

import java.io.Serializable;
import java.util.LinkedHashMap;

record Other <foo extends Serializable & Cloneable> // warn
(LinkedHashMap<String, Node> linkedHashMap) {

}

