/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VisibilityModifier">
      <property name="allowPublicFinalFields" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.List;

// xdoc section -- start
class Example12 {
  public final int someIntValue = 0;

  public final ImmutableSet<String> includes = null;

  public final java.lang.String notes = "";

  public final BigDecimal value = null;

  public final List list = null;
}
// xdoc section -- end
