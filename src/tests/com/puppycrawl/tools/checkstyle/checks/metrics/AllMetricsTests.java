package com.puppycrawl.tools.checkstyle.checks.metrics;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {BooleanExpressionComplexityCheckTest.class,
    ClassDataAbstractionCouplingCheckTest.class,
    ClassFanOutComplexityCheckTest.class, CyclomaticComplexityCheckTest.class,
    JavaNCSSCheckTest.class, NPathComplexityCheckTest.class})
public class AllMetricsTests
{
}
