package com.puppycrawl.tools.checkstyle.checks.design;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {DesignForExtensionCheckTest.class,
    FinalClassCheckTest.class, HideUtilityClassConstructorCheckTest.class,
    InterfaceIsTypeCheckTest.class, MutableExceptionCheckTest.class,
    ThrowsCountCheckTest.class, VisibilityModifierCheckTest.class})
public class AllDesignTests
{
}
