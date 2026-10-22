/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VisibilityModifier">
      <property name="allowPublicImmutableFields" value="true"/>
      <property name="immutableClassCanonicalNames"
                value="com.google.common.collect.ImmutableSet,
                       java.lang.String"/>
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

// xdoc section - start
final class Example6 {
  private int myPrivateField1;

  int field1;
  // violation above, must have visibility modifier ''field1' must be private'
  protected String field2;
  // violation above, protected not allowed ''field2' must be private'
  public int field3 = 42;
  // violation above, not final nor matching pattern 'must be private'
  public long serialVersionUID = 1L;

  public static final int field4 = 42;

  // ok, primitive field is immutable and the class is final
  public final int field5 = 42;

  // ok, String is immutable and the class is final
  public final java.lang.String notes = null;

  // violation below, HashSet is mutable 'must be private'
  public final Set<String> mySet1 = new HashSet<>();

  // ok, immutable type is configured and the class is final
  public final ImmutableSet<String> mySet2 = null;

  // violation below, immutable type not in config 'must be private'
  public final ImmutableMap<String, Object> objects1 = null;

  @java.lang.Deprecated
  String annotatedString;
  // violation above, annotation not configured ''annotatedString' must be private'
  @Deprecated
  String shortCustomAnnotated;
  // violation above, annotation not configured 'must be private'
  @com.google.common.annotations.VisibleForTesting
  public String testString = "";

  // ok, primitive field is immutable and the class is final
  public final int someIntValue = 0;

  // ok, immutable type is configured and the class is final
  public final ImmutableSet<String> includes = null;

  // violation below, immutable type not in config 'must be private'
  public final BigDecimal value = null;

  // violation below, mutable 'must be private'
  public final List list = null;
}
// xdoc section - end
