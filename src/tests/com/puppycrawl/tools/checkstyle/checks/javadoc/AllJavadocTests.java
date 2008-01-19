package com.puppycrawl.tools.checkstyle.checks.javadoc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {JavadocMethodCheckTest.class,
    JavadocPackageCheckTest.class, JavadocStyleCheckTest.class,
    JavadocTypeCheckTest.class, JavadocVariableCheckTest.class,
    WriteTagCheckTest.class})
public class AllJavadocTests
{
}
