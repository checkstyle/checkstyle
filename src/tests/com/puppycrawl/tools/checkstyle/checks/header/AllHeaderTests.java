package com.puppycrawl.tools.checkstyle.checks.header;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {CrossLanguageRegexpHeaderCheckTest.class,
    HeaderCheckTest.class})
public class AllHeaderTests
{
}
