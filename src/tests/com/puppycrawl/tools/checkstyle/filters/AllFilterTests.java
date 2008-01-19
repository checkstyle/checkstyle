package com.puppycrawl.tools.checkstyle.filters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {CSVFilterTest.class, FilterSetTest.class,
    IntMatchFilterTest.class, IntRangeFilterTest.class,
    SeverityMatchFilterTest.class, SuppressElementTest.class,
    SuppressionCommentFilterTest.class, SuppressionsLoaderTest.class})
public class AllFilterTests
{
}
