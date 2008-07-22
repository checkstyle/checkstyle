package com.puppycrawl.tools.checkstyle.checks.sizes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {AnonInnerLengthCheckTest.class,
    ExecutableStatementCountCheckTest.class, FileLengthCheckTest.class,
    LineLengthCheckTest.class, MethodLengthCheckTest.class,
    ParameterNumberCheckTest.class, OuterTypeNumberCheckTest.class})
public class AllSizesTests
{
}
