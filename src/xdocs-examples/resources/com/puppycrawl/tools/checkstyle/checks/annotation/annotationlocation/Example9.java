/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="IMPLEMENTS_CLAUSE"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.io.Serializable;

import org.antlr.v4.runtime.misc.NotNull;

@interface Validated {}

// xdoc section -- start
class Example9 implements @NotNull Serializable { } // ok
class Example9a implements @NotNull @Validated Serializable { }
// violation above, 'Annotation 'Validated' should be alone on line.'
// xdoc section -- end
