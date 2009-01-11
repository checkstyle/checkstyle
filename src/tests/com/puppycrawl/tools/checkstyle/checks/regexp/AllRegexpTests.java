package com.puppycrawl.tools.checkstyle.checks.regexp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {RegexpSinglelineCheckTest.class,
    RegexpSinglelineJavaCheckTest.class, RegexpMultilineCheckTest.class})
public class AllRegexpTests
{
}
