/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = com.google.common.collect.ImmutableSet, java.lang.String
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

class Example6 {
  private int myPrivateField1;

  int field1; // violation 'must be private'

  protected String field2; // violation 'must be private'

  public int field3 = 42; // violation 'must be private'

  public long serialVersionUID = 1L;

  public static final int field4 = 42;

  public final int field5 = 42; // violation 'must be private'

  public final java.lang.String notes = null; // violation 'must be private'

  public final Set<String> mySet1 = new HashSet<>(); // violation 'must be private'

  public final ImmutableSet<String> mySet2 = null; // violation 'must be private'

  public final ImmutableMap<String, Object> objects1 = null; // violation 'must be private'

  @java.lang.Deprecated
  String annotatedString; // violation 'must be private'

  @Deprecated
  String shortCustomAnnotated; // violation 'must be private'

  @com.google.common.annotations.VisibleForTesting
  public String testString = "";
}
