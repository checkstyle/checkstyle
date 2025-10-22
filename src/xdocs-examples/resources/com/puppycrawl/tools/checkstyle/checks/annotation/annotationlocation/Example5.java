/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="tokens" value="TYPE_ARGUMENT"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.misc.NotNull;

// xdoc section -- start
class Example5 {
  List<@NotNull String> names; // ok
  List<@NotNull @Immutable String> data;
  // violation above, 'Annotation 'Immutable' should be alone on line.'
  Map<@NotNull String, @NotNull Integer> map; // ok
}
// xdoc section -- end
