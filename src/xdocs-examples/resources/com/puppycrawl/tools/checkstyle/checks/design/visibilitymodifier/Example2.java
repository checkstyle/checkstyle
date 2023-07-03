/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="VisibilityModifier">
      <property name="packageAllowed" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

// xdoc section -- start
class Example2 {
  private int myPrivateField1;

  int field1;

  protected String field2; // violation, protected not allowed 'must be private'

  // violation below, not final nor matching pattern 'must be private'
  public int field3 = 42;

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
  String annotatedString;

  @Deprecated
  String shortCustomAnnotated;

  @com.google.common.annotations.VisibleForTesting
  public String testString = "";
}
// xdoc section -- end
