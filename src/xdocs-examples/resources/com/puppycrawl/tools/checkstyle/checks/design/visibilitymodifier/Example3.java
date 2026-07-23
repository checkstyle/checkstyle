/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VisibilityModifier">
      <property name="protectedAllowed" value="true"/>
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
class Example3 {
  private int myPrivateField1;

  int field1; // violation, must have visibility modifier 'must be private'

  protected String field2; // ok, protectedAllowed is true

  public int field3 = 42;
  // violation above, not final nor matching pattern 'must be private'
  public long serialVersionUID = 1L;

  public static final int field4 = 42;

  // violation below, public immutable fields are not allowed 'must be private'
  public final int field5 = 42;

  // violation below, public immutable fields are not allowed 'must be private'
  public final java.lang.String notes = null;

  // violation below, HashSet is mutable 'must be private'
  public final Set<String> mySet1 = new HashSet<>();

  // violation below, immutable type not in config 'must be private'
  public final ImmutableSet<String> mySet2 = null;

  // violation below, immutable type not in config 'must be private'
  public final ImmutableMap<String, Object> objects1 = null;

  @java.lang.Deprecated
  String annotatedString; // violation, annotation not configured 'must be private'

  @Deprecated
  String shortCustomAnnotated;
  // violation above, annotation not configured 'must be private'
  @com.google.common.annotations.VisibleForTesting
  public String testString = "";

  // violation below 'must be private'
  public final int someIntValue = 0;

  // violation below 'must be private'
  public final ImmutableSet<String> includes = null;

  // violation below 'must be private'
  public final BigDecimal value = null;

  // violation below 'must be private'
  public final List list = null;
}
// xdoc section -- end
