package com.puppycrawl.tools.checkstyle.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {AbstractViolationReporterTest.class,
    AutomaticBeanTest.class, DetailASTTest.class, ScopeTest.class,
    SeverityLevelTest.class, TokenTypesTest.class, FastStackTest.class})
public class AllApiTests
{
}
