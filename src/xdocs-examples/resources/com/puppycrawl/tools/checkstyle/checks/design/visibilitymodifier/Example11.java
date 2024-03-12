/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VisibilityModifier">
      <property name="allowPublicImmutableFields" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.List;

// xdoc section -- start
class Example11 {
  public final int someIntValue = 0; // violation 'must be private'

  public final ImmutableSet<String> includes = null; // violation 'must be private'

  public final java.lang.String notes = ""; // violation 'must be private'

  public final BigDecimal value = null; // violation 'must be private'

  public final List list = null; // violation 'must be private'
}
// xdoc section -- end
