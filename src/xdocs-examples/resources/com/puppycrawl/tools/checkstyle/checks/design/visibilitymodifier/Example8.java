/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = (default)false
immutableClassCanonicalNames = (default)java.io.File, java.lang.Boolean, java.lang.Byte, \
                               java.lang.Character, java.lang.Double, java.lang.Float, \
                               java.lang.Integer, java.lang.Long, java.lang.Short, \
                               java.lang.StackTraceElement, java.lang.String, \
                               java.math.BigDecimal, java.math.BigInteger, \
                               java.net.Inet4Address, java.net.Inet6Address, \
                               java.net.InetSocketAddress, java.net.URI, java.net.URL, \
                               java.util.Locale, java.util.UUID
ignoreAnnotationCanonicalNames = java.lang.Deprecated


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

class Example8 {
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
  String annotatedString;

  @Deprecated
  String shortCustomAnnotated;

  @com.google.common.annotations.VisibleForTesting
  public String testString = "";  // violation 'must be private'
}
