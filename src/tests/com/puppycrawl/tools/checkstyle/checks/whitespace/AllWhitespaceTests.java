package com.puppycrawl.tools.checkstyle.checks.whitespace;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {EmptyForInitializerPadCheckTest.class,
    EmptyForIteratorPadCheckTest.class, GenericWhitespaceCheckTest.class,
    MethodParamPadCheckTest.class, NoWhitespaceAfterCheckTest.class,
    NoWhitespaceBeforeCheckTest.class, OperatorWrapCheckTest.class,
    ParenPadCheckTest.class, TabCharacterCheckTest.class,
    TypecastParenPadCheckTest.class, WhitespaceAfterCheckTest.class,
    WhitespaceAroundTest.class})
public class AllWhitespaceTests
{
}
