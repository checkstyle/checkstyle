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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// xdoc section -- start
class Example12 {
  private int myPrivateField1;

  int field1; // violation, 'must be private'

  protected String field2; // violation, 'must be private'

  // violation below, 'must be private'
  public int field3 = 42;

  public long serialVersionUID = 1L;

  public static final int field4 = 42;

  public final int field5 = 42;

  public final java.lang.String notes = null;

  public final Set<String> mySet1 = new HashSet<>();

  public final ImmutableSet<String> mySet2 = null;

  public final ImmutableMap<String, Object> objects1 = null;

  @java.lang.Deprecated
  String annotatedString; // violation, 'must be private'

  @Deprecated
  // violation below, 'must be private'
  String shortCustomAnnotated;

  @com.google.common.annotations.VisibleForTesting
  public String testString = "";

  public final int someIntValue = 0;

  public final ImmutableSet<String> includes = null;

  public final BigDecimal value = null;

  public final List list = null;
}
// xdoc section -- end
