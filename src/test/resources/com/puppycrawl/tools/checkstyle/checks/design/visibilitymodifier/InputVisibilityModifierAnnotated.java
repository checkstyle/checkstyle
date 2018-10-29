package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import com.google.common.annotations.VisibleForTesting;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class InputVisibilityModifierAnnotated {
    @Rule
    public TemporaryFolder publicJUnitRule = new TemporaryFolder();

    @org.junit.Rule
    public TemporaryFolder fqPublicJUnitRule = new TemporaryFolder();

    @VisibleForTesting
    public String googleCommonsAnnotatedPublic;

    @VisibleForTesting
    String googleCommonsAnnotatedPackage;

    @VisibleForTesting
    protected String googleCommonsAnnotatedProtected;

    @com.google.common.annotations.VisibleForTesting
    public String fqGoogleCommonsAnnotatedPublic;

    @com.google.common.annotations.VisibleForTesting
    String fqGoogleCommonsAnnotatedPackage;

    @com.google.common.annotations.VisibleForTesting
    protected String fqGoogleCommonsAnnotatedProtected;

    @CustomAnnotation
    public String customAnnotatedPublic;

    @CustomAnnotation
    String customAnnotatedPackage;

    @CustomAnnotation
    protected String customAnnotatedProtected;

    public String unannotatedPublic;
    String unannotatedPackage;
    protected String unannotatedProtected;
    private String unannotatedPrivate;

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public @interface CustomAnnotation {
    }

    @ClassRule
    public static TemporaryFolder publicJUnitClassRule = new TemporaryFolder();

    @org.junit.ClassRule
    public static TemporaryFolder fqPublicJUnitClassRule = new TemporaryFolder();
}
