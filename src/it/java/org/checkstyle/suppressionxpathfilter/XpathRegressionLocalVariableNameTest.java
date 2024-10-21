package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import java.io.File;
import java.util.Collections;
import java.util.List;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck;

import org.junit.jupiter.api.Test;

public class XpathRegressionLocalVariableNameTest extends AbstractXpathTestSupport {

  @Override
  protected String getCheckName() {
    return LocalVariableNameCheck.class.getSimpleName();
  }

  @Test
  public void testMethod() throws Exception {
    final File fileToProcess = new File(getPath("InputXpathRegressionLocalVariableNameMethod.java"));

    final DefaultConfiguration moduleConfig =
        createModuleConfig(LocalVariableNameCheck.class);

    final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
    final String[] expectedViolations = {
        "5:9: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN, "VAR", pattern),
    };

    final List<String> expectedXpathQueries = Collections.singletonList(
        "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
          + "@text='InputXpathRegressionLocalVariableNameMethod']]"
          + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyMethod']]"
          + "/SLIST/VARIABLE_DEF/IDENT[@text='VAR']"
    );

    runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

  }

  @Test
  public void testIteration() throws Exception {
    final File fileToProcess = new File(getPath("InputXpathRegressionLocalVariableNameIteration.java"));

    final DefaultConfiguration moduleConfig = createModuleConfig(LocalVariableNameCheck.class);

    final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
    final String[] expectedViolations = {
        "7:14: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN, "var_1", pattern),
    };

    final List<String> expectedXpathQueries = Collections.singletonList(
        "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
          + "@text='InputXpathRegressionLocalVariableNameIteration']]"
          + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='MyMethod']]/"
          + "SLIST/LITERAL_FOR/FOR_INIT/VARIABLE_DEF/IDENT[@text='var_1']"
    );

    runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

  }

  @Test
  public void testInnerClass() throws Exception {
    final File fileToProcess = new File(getPath("InputXpathRegressionLocalVariableNameInnerClass.java"));

    final DefaultConfiguration moduleConfig = createModuleConfig(LocalVariableNameCheck.class);

    final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
    final String[] expectedViolations = {
        "6:11: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN, "VAR", pattern),
    };

    final List<String> expectedXpathQueries = Collections.singletonList(
        "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
          + "@text='InputXpathRegressionLocalVariableNameInnerClass']]"
          + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]/OBJBLOCK/"
          + "METHOD_DEF[./IDENT[@text='myMethod']]/SLIST/VARIABLE_DEF/IDENT[@text='VAR']"
    );

    runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);

  }

}
