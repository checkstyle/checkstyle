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

  int field1; // violation 'must be private'

  protected String field2; // violation 'must be private'

  public int field3 = 42; // violation 'must be private'

  public long serialVersionUID = 1L;

  public static final int field4 = 42;

  // ok, allowPublicFinalFields is true
  public final int field5 = 42;

  // ok, allowPublicFinalFields is true
  public final java.lang.String notes = null;

  // ok, allowPublicFinalFields is true
  public final Set<String> mySet1 = new HashSet<>();

  // ok, allowPublicFinalFields is true
  public final ImmutableSet<String> mySet2 = null;

  // ok, allowPublicFinalFields is true
  public final ImmutableMap<String, Object> objects1 = null;

  @java.lang.Deprecated
  String annotatedString; // violation 'must be private'

  @Deprecated
  String shortCustomAnnotated;
  // violation above 'must be private'
  @com.google.common.annotations.VisibleForTesting
  public String testString = "";

  // ok, allowPublicFinalFields is true
  public final int someIntValue = 0;

  // ok, allowPublicFinalFields is true
  public final ImmutableSet<String> includes = null;

  // ok, allowPublicFinalFields is true
  public final BigDecimal value = null;

  // ok, allowPublicFinalFields is true
  public final List list = null;
}
// xdoc section -- end
