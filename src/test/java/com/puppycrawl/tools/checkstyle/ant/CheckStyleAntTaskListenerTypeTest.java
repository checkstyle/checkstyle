package com.puppycrawl.tools.checkstyle.ant;

import org.apache.tools.ant.BuildException;
import org.junit.Test;

/**
 * this class contains some tests for the {@link CheckStyleAntTask.ListenerType}
 *
 * @author m-mikula
 *
 */
public class CheckStyleAntTaskListenerTypeTest {
  @Test
  public void setValidType() {
    // the formatter types should be publicly available
    CheckstyleAntTask.FormatterType t = new CheckstyleAntTask.FormatterType();
    t.setValue("xml");
  }

  @Test(expected = BuildException.class)
  public void setInvalidType() {
    // the formatter types should be publicly available
    CheckstyleAntTask.FormatterType t = new CheckstyleAntTask.FormatterType();
    t.setValue("abc");
  }
}
