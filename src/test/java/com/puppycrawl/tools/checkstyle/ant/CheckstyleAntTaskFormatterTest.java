package com.puppycrawl.tools.checkstyle.ant;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;

/**
 * Test classes for CheckstyleAntTask.Formatter.
 *
 * @author m-mikula
 *
 */
public class CheckstyleAntTaskFormatterTest extends BaseCheckTestSupport {

  /**
   * Path to the log XML file;
   */
  private static final String OUTPUT_FILE = "ant/output.xml";

  /**
   * Generally testing Getters and Setters is a bad idea.
   */
  @Test
  public void SetTypeToTest() {
    try {
      CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
      File outputfile = new File(getSrcPath(OUTPUT_FILE));
      CheckstyleAntTask.FormatterType formatterType;
      formatterType = new CheckstyleAntTask.FormatterType();
      formatterType.setValue("xml");
      formatter.setType(formatterType);
      formatter.setUseFile(true);
      formatter.setTofile(outputfile);
      formatter.createListener(null);

    } catch (Exception e) {
      Assert.fail();
    }
  }

}
