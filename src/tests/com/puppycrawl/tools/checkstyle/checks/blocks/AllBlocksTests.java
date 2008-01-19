package com.puppycrawl.tools.checkstyle.checks.blocks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {AvoidNestedBlocksCheckTest.class,
    EmptyBlockCheckTest.class, LeftCurlyCheckTest.class,
    NeedBracesCheckTest.class, RightCurlyCheckTest.class})
public class AllBlocksTests
{
}
