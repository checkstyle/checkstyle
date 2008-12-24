package com.puppycrawl.tools.checkstyle.checks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {ArrayTypeStyleCheckTest.class, ClassResolverTest.class,
    DescendantTokenCheckTest.class, FileSetCheckLifecycleTest.class,
    FinalParametersCheckTest.class, GenericIllegalRegexpCheckTest.class,
    NewlineAtEndOfFileCheckTest.class, RegexpCheckTest.class,
    TodoCommentCheckTest.class, TrailingCommentCheckTest.class,
    TranslationCheckTest.class, UncommentedMainCheckTest.class,
    UpperEllCheckTest.class})
public class AllChecksTests
{
}
