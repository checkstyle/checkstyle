package com.puppycrawl.tools.checkstyle.checks.annotation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{ AnnotationUseStyleTest.class, MissingDeprecatedTest.class,
    MissingOverrideCheckTest.class, PackageAnnotationTest.class,
    SuppressWarningsTest.class })
public class AllAnnotationTests
{
}
