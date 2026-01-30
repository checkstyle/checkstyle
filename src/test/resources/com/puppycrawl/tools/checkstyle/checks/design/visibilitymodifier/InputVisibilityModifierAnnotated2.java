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
ignoreAnnotationCanonicalNames = com.puppycrawl.tools.checkstyle.checks.design.\
                                 InputVisibilityModifierAnnotated.CustomAnnotation


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

public class InputVisibilityModifierAnnotated2 {
    @Rule
    public TemporaryFolder rule = new TemporaryFolder();
    // violation above 'Variable 'rule' must be private and have accessor methods.'

    @Rule
    public TemporaryFolder ruleFull = new TemporaryFolder();
    // violation above 'Variable 'ruleFull' must be private and have accessor methods.'

    @VisibleForTesting
    public String publicGoogle;
    // violation above 'Variable 'publicGoogle' must be private and have accessor methods.'

    @VisibleForTesting
    String packageGoogle;
    // violation above 'Variable 'packageGoogle' must be private and have accessor methods.'

    @VisibleForTesting
    protected String protectedGoogle;
    // violation above 'Variable 'protectedGoogle' must be private and have accessor methods.'

    @VisibleForTesting
    public String publicGoogleFull;
    // violation above 'Variable 'publicGoogleFull' must be private and have accessor methods.'

    @VisibleForTesting
    String packageGoogleFull;
    // violation above 'Variable 'packageGoogleFull' must be private and have accessor methods.'

    @VisibleForTesting
    protected String protectedGoogleFull;
    // violation above 'Variable 'protectedGoogleFull' must be private and have accessor methods.'

    @CustomAnnotation
    public String publicCustom;

    @CustomAnnotation
    String packageCustom;

    @CustomAnnotation
    protected String protectedCustom;

    public String unannotatedPublic;
    // violation above 'Variable 'unannotatedPublic' must be private and have accessor methods.'
    String unannotatedPackage;
    // violation above 'Variable 'unannotatedPackage' must be private and have accessor methods.'
    protected String unannotatedProtected;
    // violation above 'Variable 'unannotatedProtected' must be private and have accessor methods.'
    private String unannotatedPrivate;

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public @interface CustomAnnotation {
    }

    @ClassRule
    public static TemporaryFolder classRule = new TemporaryFolder();
    // violation above 'Variable 'classRule' must be private and have accessor methods.'

    @ClassRule
    public static TemporaryFolder classRuleFull = new TemporaryFolder();
    // violation above 'Variable 'classRuleFull' must be private and have accessor methods.'
}
