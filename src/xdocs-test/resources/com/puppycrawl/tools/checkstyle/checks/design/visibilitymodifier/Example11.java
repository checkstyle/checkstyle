/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = (default)java.io.File, java.lang.Boolean, java.lang.Byte, \
                               java.lang.Character, java.lang.Double, java.lang.Float, \
                               java.lang.Integer, java.lang.Long, java.lang.Short, \
                               java.lang.StackTraceElement, java.lang.String, \
                               java.math.BigDecimal, java.math.BigInteger, \
                               java.net.Inet4Address, java.net.Inet6Address, \
                               java.net.InetSocketAddress, java.net.URI, java.net.URL, \
                               java.util.Locale, java.util.UUID
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

class Example11 {
  public final int someIntValue; // violation '.* must be private .*'
  public final ImmutableSet<String> includes; // violation '.* must be private .*'
  public final java.lang.String notes; // violation '.* must be private .*'
  public final BigDecimal value; // violation '.* must be private .*'
  public final List list; // violation '.* must be private .*'

  Example11(Collection<String> includes,
                   BigDecimal value, String notes, int someValue, List l) {
    this.includes = ImmutableSet.copyOf(includes);
    this.value = value;
    this.notes = notes;
    this.someIntValue = someValue;
    this.list = l;
  }
}
