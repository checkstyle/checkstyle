package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractViolationReporterTest;
import com.puppycrawl.tools.checkstyle.api.DetailASTTest;
import com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheckTest;
import com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheckTest;
import com.puppycrawl.tools.checkstyle.checks.FileSetCheckLifecycleTest;
import com.puppycrawl.tools.checkstyle.checks.FinalParametersCheckTest;
import com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheckTest;
import com.puppycrawl.tools.checkstyle.checks.HeaderCheckTest;
import com.puppycrawl.tools.checkstyle.checks.ModifierOrderCheckTest;
import com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheckTest;
import com.puppycrawl.tools.checkstyle.checks.RedundantModifierTest;
import com.puppycrawl.tools.checkstyle.checks.TodoCommentCheckTest;
import com.puppycrawl.tools.checkstyle.checks.TranslationCheckTest;
import com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheckTest;
import com.puppycrawl.tools.checkstyle.checks.UpperEllCheckTest;
import com.puppycrawl.tools.checkstyle.checks.blocks.AvoidNestedBlocksCheckTest;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheckTest;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheckTest;
import com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheckTest;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.AvoidInlineConditionalsCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.CovariantEqualsCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.DoubleCheckedLockingCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.JUnitTestCaseCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.RedundantThrowsCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanReturnCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.StringLiteralEqualityCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.SuperCloneCheckTest;
import com.puppycrawl.tools.checkstyle.checks.coding.SuperFinalizeCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.InterfaceIsTypeCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheckTest;
import com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheckTest;
import com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportTest;
import com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheckTest;
import com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheckTest;
import com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheckTest;
import com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.EntityBeanCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.FinalStaticCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.LocalHomeInterfaceCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.LocalInterfaceCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.MessageBeanCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.RemoteHomeInterfaceCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.RemoteInterfaceCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.SessionBeanCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.ThisParameterCheckTest;
import com.puppycrawl.tools.checkstyle.checks.j2ee.ThisReturnCheckTest;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheckTest;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheckTest;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheckTest;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheckTest;
import com.puppycrawl.tools.checkstyle.checks.javadoc.PackageHtmlCheckTest;
import com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheckTest;
import com.puppycrawl.tools.checkstyle.checks.sizes.AnonInnerLengthCheckTest;
import com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheckTest;
import com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheckTest;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheckTest;
import com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheckTest;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheckTest;
import com.puppycrawl.tools.checkstyle.checks.usage.OneMethodPrivateFieldCheckTest;
import com.puppycrawl.tools.checkstyle.checks.usage.UnusedLocalVariableCheckTest;
import com.puppycrawl.tools.checkstyle.checks.usage.UnusedParameterCheckTest;
import com.puppycrawl.tools.checkstyle.checks.usage.UnusedPrivateFieldCheckTest;
import com.puppycrawl.tools.checkstyle.checks.usage.UnusedPrivateMethodCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForIteratorPadCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.TabCharacterCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheckTest;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundTest;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite for all tests.
 * @author Rick Giles
 * @version 22-Nov-2002
 */
public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.class);
    }

    public static Test suite() {
        TestSuite suite =
            new TestSuite("Test for com.puppycrawl.tools.checkstyle");
        //$JUnit-BEGIN$
        suite.addTest(new TestSuite(AbstractClassNameCheckTest.class));
        suite.addTest(new TestSuite(AbstractViolationReporterTest.class));
        suite.addTest(new TestSuite(AnonInnerLengthCheckTest.class));
        suite.addTest(new TestSuite(ArrayTrailingCommaCheckTest.class));
        suite.addTest(new TestSuite(ArrayTypeStyleCheckTest.class));
        suite.addTest(new TestSuite(AvoidInlineConditionalsCheckTest.class));
        suite.addTest(new TestSuite(AvoidNestedBlocksCheckTest.class));
        suite.addTest(new TestSuite(AvoidStarImportTest.class));
        suite.addTest(new TestSuite(CheckerTest.class));
        suite.addTest(new TestSuite(ConfigurationLoaderTest.class));
        suite.addTest(new TestSuite(ConstantNameCheckTest.class));
        suite.addTest(new TestSuite(CovariantEqualsCheckTest.class));
        suite.addTest(new TestSuite(CyclomaticComplexityCheckTest.class));
        suite.addTest(new TestSuite(DescendantTokenCheckTest.class));
        suite.addTest(new TestSuite(DesignForExtensionCheckTest.class));
        suite.addTest(new TestSuite(DetailASTTest.class));
        suite.addTest(new TestSuite(DoubleCheckedLockingCheckTest.class));
        suite.addTest(new TestSuite(EmptyBlockCheckTest.class));
        suite.addTest(new TestSuite(EmptyForIteratorPadCheckTest.class));
        suite.addTest(new TestSuite(EmptyStatementCheckTest.class));
        suite.addTest(new TestSuite(EqualsHashCodeCheckTest.class));
        suite.addTest(new TestSuite(FileLengthCheckTest.class));
        suite.addTest(new TestSuite(FileSetCheckLifecycleTest.class));
        suite.addTest(new TestSuite(FinalClassCheckTest.class));
        suite.addTest(new TestSuite(FinalParametersCheckTest.class));
        suite.addTest(new TestSuite(GenericIllegalRegexpCheckTest.class));
        suite.addTest(new TestSuite(HeaderCheckTest.class));
        suite.addTest(new TestSuite(HiddenFieldCheckTest.class));
        suite.addTest(new TestSuite(HideUtilityClassConstructorCheckTest.class));
        suite.addTest(new TestSuite(IllegalCatchCheckTest.class));
        suite.addTest(new TestSuite(IllegalImportCheckTest.class));
        suite.addTest(new TestSuite(IllegalInstantiationCheckTest.class));
        suite.addTest(new TestSuite(IllegalTokenCheckTest.class));
        suite.addTest(new TestSuite(IllegalTokenTextCheckTest.class));
        suite.addTest(new TestSuite(IllegalTypeCheckTest.class));
        suite.addTest(new TestSuite(IndentationCheckTest.class));
        suite.addTest(new TestSuite(InnerAssignmentCheckTest.class));
        suite.addTest(new TestSuite(InterfaceIsTypeCheckTest.class));
        suite.addTest(new TestSuite(JUnitTestCaseCheckTest.class));
        suite.addTest(new TestSuite(JavadocMethodCheckTest.class));
        suite.addTest(new TestSuite(JavadocStyleCheckTest.class));
        suite.addTest(new TestSuite(JavadocTypeCheckTest.class));
        suite.addTest(new TestSuite(JavadocVariableCheckTest.class));
        suite.addTest(new TestSuite(LeftCurlyCheckTest.class));
        suite.addTest(new TestSuite(LineLengthCheckTest.class));
        suite.addTest(new TestSuite(LocalFinalVariableNameCheckTest.class));
        suite.addTest(new TestSuite(LocalVariableNameCheckTest.class));
        suite.addTest(new TestSuite(MagicNumberCheckTest.class));
        suite.addTest(new TestSuite(MemberNameCheckTest.class));
        suite.addTest(new TestSuite(MethodLengthCheckTest.class));
        suite.addTest(new TestSuite(MethodNameCheckTest.class));
        suite.addTest(new TestSuite(MissingSwitchDefaultCheckTest.class));
        suite.addTest(new TestSuite(ModifierOrderCheckTest.class));
        suite.addTest(new TestSuite(MutableExceptionCheckTest.class));
        suite.addTest(new TestSuite(NeedBracesCheckTest.class));
        suite.addTest(new TestSuite(NestedIfDepthCheckTest.class));
        suite.addTest(new TestSuite(NestedTryDepthCheckTest.class));
        suite.addTest(new TestSuite(NewlineAtEndOfFileCheckTest.class));
        suite.addTest(new TestSuite(NoWhitespaceAfterCheckTest.class));
        suite.addTest(new TestSuite(NoWhitespaceBeforeCheckTest.class));
        suite.addTest(new TestSuite(OperatorWrapCheckTest.class));
        suite.addTest(new TestSuite(OptionTest.class));
        suite.addTest(new TestSuite(PackageDeclarationCheckTest.class));
        suite.addTest(new TestSuite(PackageHtmlCheckTest.class));
        suite.addTest(new TestSuite(PackageNameCheckTest.class));
        suite.addTest(new TestSuite(PackageNamesLoaderTest.class));
        suite.addTest(new TestSuite(PackageObjectFactoryTest.class));
        suite.addTest(new TestSuite(ParameterNameCheckTest.class));
        suite.addTest(new TestSuite(ParameterNumberCheckTest.class));
        suite.addTest(new TestSuite(ParenPadCheckTest.class));
        suite.addTest(new TestSuite(RedundantImportCheckTest.class));
        suite.addTest(new TestSuite(RedundantModifierTest.class));
        suite.addTest(new TestSuite(RedundantThrowsCheckTest.class));
        suite.addTest(new TestSuite(ReturnCountCheckTest.class));
        suite.addTest(new TestSuite(RightCurlyCheckTest.class));
        suite.addTest(new TestSuite(SimplifyBooleanExpressionCheckTest.class));
        suite.addTest(new TestSuite(SimplifyBooleanReturnCheckTest.class));
        suite.addTest(new TestSuite(ExecutableStatementCountCheckTest.class));
        suite.addTest(new TestSuite(StaticVariableNameCheckTest.class));
        suite.addTest(new TestSuite(StringArrayReaderTest.class));
        suite.addTest(new TestSuite(StringLiteralEqualityCheckTest.class));
        suite.addTest(new TestSuite(SuperCloneCheckTest.class));
        suite.addTest(new TestSuite(SuperFinalizeCheckTest.class));
        suite.addTest(new TestSuite(TabCharacterCheckTest.class));
        suite.addTest(new TestSuite(ThrowsCountCheckTest.class));
        suite.addTest(new TestSuite(TodoCommentCheckTest.class));
        suite.addTest(new TestSuite(TranslationCheckTest.class));
        suite.addTest(new TestSuite(TypeNameCheckTest.class));
        suite.addTest(new TestSuite(TypecastParenPadCheckTest.class));
        suite.addTest(new TestSuite(UncommentedMainCheckTest.class));
        suite.addTest(new TestSuite(UnusedImportsCheckTest.class));
        suite.addTest(new TestSuite(UpperEllCheckTest.class));
        suite.addTest(new TestSuite(UtilsTest.class));
        suite.addTest(new TestSuite(VisibilityModifierCheckTest.class));
        suite.addTest(new TestSuite(WhitespaceAfterCheckTest.class));
        suite.addTest(new TestSuite(WhitespaceAroundTest.class));
        suite.addTest(new TestSuite(XMLLoggerTest.class));
        
        // j2ee tests-BEGIN
        suite.addTest(new TestSuite(EntityBeanCheckTest.class));
        suite.addTest(new TestSuite(FinalStaticCheckTest.class));
        suite.addTest(new TestSuite(LocalHomeInterfaceCheckTest.class));
        suite.addTest(new TestSuite(LocalInterfaceCheckTest.class));
        suite.addTest(new TestSuite(MessageBeanCheckTest.class));
        suite.addTest(new TestSuite(RemoteHomeInterfaceCheckTest.class));
        suite.addTest(new TestSuite(RemoteInterfaceCheckTest.class));
        suite.addTest(new TestSuite(SessionBeanCheckTest.class));
        suite.addTest(new TestSuite(ThisParameterCheckTest.class));
        suite.addTest(new TestSuite(ThisReturnCheckTest.class));
        // j2ee tests-END
        
        // usage tests-BEGIN
        suite.addTest(new TestSuite(OneMethodPrivateFieldCheckTest.class));
        suite.addTest(new TestSuite(UnusedLocalVariableCheckTest.class));
        suite.addTest(new TestSuite(UnusedParameterCheckTest.class));
        suite.addTest(new TestSuite(UnusedPrivateFieldCheckTest.class));
        suite.addTest(new TestSuite(UnusedPrivateMethodCheckTest.class));
        // usage tests-END

        //$JUnit-END$
        return suite;
    }
}
