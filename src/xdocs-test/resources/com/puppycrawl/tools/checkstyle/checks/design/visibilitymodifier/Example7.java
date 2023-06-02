/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = com.google.common.collect.ImmutableSet, java.lang.String, \
                               com.google.common.collect.ImmutableMap
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;

import java.util.HashSet;
import java.util.Set;

class Example7 {
  private int myPrivateField1;

  int field1; // violation '.* must be private .*'

  protected String field2; // violation '.* must be private .*'

  public int field3 = 42; // violation '.* must be private .*'

  public long serialVersionUID = 1L;

  public static final int field4 = 42;

  public final int field5 = 42;

  public final java.lang.String notes;

  public final Set<String> mySet1 = new HashSet<>(); // violation '.* must be private .*'

  public final ImmutableSet<String> mySet2;

  public final ImmutableMap<String, Object> objects1; // violation '.* must be private .*'

  @com.puppycrawl.tools.checkstyle.XdocsPropertyType(PropertyType.STRING)
  String annotatedString; // violation '.* must be private .*'

  @XdocsPropertyType(PropertyType.STRING)
  String shortCustomAnnotated; // violation '.* must be private .*'

  @com.google.common.annotations.VisibleForTesting
  public String testString = "";

  Example7(String notes, ImmutableSet<String> mySet2, ImmutableMap<String, Object> objects1) {
    this.notes = notes;
    this.mySet2 = mySet2;
    this.objects1 = objects1;
  }
}
