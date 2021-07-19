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
ignoreAnnotationCanonicalNames =


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.annotations.VisibleForTesting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class InputVisibilityModifierAnnotated3 {
    @Rule
    public TemporaryFolder publicJUnitRule = new TemporaryFolder(); // violation

    @Rule
    public TemporaryFolder fqPublicJUnitRule = new TemporaryFolder(); // violation

    @VisibleForTesting
    public String googleCommonsAnnotatedPublic; // violation

    @VisibleForTesting
    String googleCommonsAnnotatedPackage; // violation

    @VisibleForTesting
    protected String googleCommonsAnnotatedProtected; // violation

    @VisibleForTesting
    public String fqGoogleCommonsAnnotatedPublic; // violation

    @VisibleForTesting
    String fqGoogleCommonsAnnotatedPackage; // violation

    @VisibleForTesting
    protected String fqGoogleCommonsAnnotatedProtected; // violation

    @CustomAnnotation
    public String customAnnotatedPublic; // violation

    @CustomAnnotation
    String customAnnotatedPackage; // violation

    @CustomAnnotation
    protected String customAnnotatedProtected; // violation

    public String unannotatedPublic; // violation
    String unannotatedPackage; // violation
    protected String unannotatedProtected; // violation
    private String unannotatedPrivate;

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public @interface CustomAnnotation {
    }

    @ClassRule
    public static TemporaryFolder publicJUnitClassRule = new TemporaryFolder(); // violation

    @ClassRule
    public static TemporaryFolder fqPublicJUnitClassRule = new TemporaryFolder(); // violation
}
