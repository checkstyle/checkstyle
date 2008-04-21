package com.puppycrawl.tools.checkstyle.checks.coding;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {ArrayTrailingCommaCheckTest.class,
    AvoidInlineConditionalsCheckTest.class, CovariantEqualsCheckTest.class,
    DeclarationOrderCheckTest.class, DefaultComesLastCheckTest.class,
    DoubleCheckedLockingCheckTest.class, EmptyStatementCheckTest.class,
    EqualsHashCodeCheckTest.class, ExplicitInitializationCheckTest.class,
    FallThroughCheckTest.class, FinalLocalVariableCheckTest.class,
    HiddenFieldCheckTest.class, IllegalCatchCheckTest.class,
    IllegalInstantiationCheckTest.class, IllegalThrowsCheckTest.class,
    IllegalTokenCheckTest.class, IllegalTokenTextCheckTest.class,
    IllegalTypeCheckTest.class, InnerAssignmentCheckTest.class,
    JUnitTestCaseCheckTest.class, MagicNumberCheckTest.class,
    MissingCtorCheckTest.class, MissingSwitchDefaultCheckTest.class,
    ModifiedControlVariableCheckTest.class,
    MultipleStringLiteralsCheckTest.class,
    MultipleVariableDeclarationsCheckTest.class, NestedIfDepthCheckTest.class,
    NestedTryDepthCheckTest.class, NoCloneCheckTest.class, NoFinalizerCheckTest.class,
    PackageDeclarationCheckTest.class, ParameterAssignmentCheckTest.class,
    RedundantThrowsCheckTest.class, RequireThisCheckTest.class, ReturnCountCheckTest.class,
    SimplifyBooleanExpressionCheckTest.class,
    SimplifyBooleanReturnCheckTest.class, StringLiteralEqualityCheckTest.class,
    SuperCloneCheckTest.class, SuperFinalizeCheckTest.class,
    UnnecessaryParenthesesCheckTest.class})
public class AllCodingTests
{
}
