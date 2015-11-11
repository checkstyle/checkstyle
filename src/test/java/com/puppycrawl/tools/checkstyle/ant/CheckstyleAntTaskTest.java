package com.puppycrawl.tools.checkstyle.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Ant.Reference;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;

/**
 * Test classes for ChecktyleAntTask.
 *
 * @author m-mikula
 */
public class CheckstyleAntTaskTest extends BaseCheckTestSupport {

  /**
   * Path to a File, that should pass all custom sun checks.
   */
  private static final String FLAWLESS_EXAMPLE = "ant/AFlawlessExample.java";

  /**
   * Path to a File, that should pass the custom sun checks, but with warnings.
   */
  private static final String WARNING_EXAMPLE = "ant/AWarningExample.java";

  /**
   * Path to a File, that should not pass the sun checks.
   */
  private static final String ERRONEOUS_EXAMPLE = "ant/AnErroneousExample.java";

  /**
   * Path to the sun checks file, that is used for the FLAWLESS_EXAMPLE file.
   */
  private static final String SUN_CHECKS_FILE = "ant/custom_sun_checks.xml";

  /**
   * Path to the log XML file;
   */
  private static final String LOG_FILE = "ant/log.xml";

  /**
   * Path to a file not containing any check info.
   */
  private static final String EMPTY_CHECK_FILE = "ant/emptyCheck.xml";
  /**
   * Path to a not existing checks file.
   */
  private static final String NOT_EXISTING_FILE = "ant/not_existing.file";

  /**
   * Path to a properties File.
   */
  private static final String PROPERTIES_FILE = "ant/test.properties";

  /**
   * First line of a typical audit file.
   */
  private static final String LOG_FIRST_LINE = "Starting audit...";

  /**
   * Name of the failure property to set.
   */
  private static final String FAILURE_PROPERTY_NAME = "myProperty";

  /**
   * Default for failure property.
   */
  private static final String FAILURE_PROPERTY_VALUE = "myValue";

  /**
   * Override value for the failure property.
   */
  private static final String OVERRIDE_PROPERTY_VALUE = "overrideValue";

  /**
   * This test configures the task to check the FLAWLESS_EXAMPLE using the
   * SUN_CHECKS_FILE.
   *
   * @throws IOException
   *           see: {@link BaseCheckTestSupport}
   */
  private static File sunChecksConfigurationFile = null;

  /**
   * The file to be examined during the Test.
   */
  private static File examinationFile = null;

  /**
   * As alternative to the examinationFile this file-set will be examined.
   */
  private static FileSet examinationFileSet = null;

  /**
   * An instance of the test-subject class to be used.
   */
  private static CheckstyleAntTask testSubjectInstance = null;

  /**
   * The {@link CheckstyleAntTaskTest} usually runs in an ant Environment. This
   * task will only execute properly if it has an ant-project set.
   */
  private static Project project = null;

  /**
   * Checks if the test-subject is null. It makes any test fail and throws an
   * {@link IllegalArgumentException}
   */
  private void checkTestSubjectExists() {
    if (testSubjectInstance == null) {
      Assert.fail("testSubject is null");
      throw new IllegalArgumentException("testSubject is null");
    }
  }

  /**
   * Configures the test-subject to use the custom version of sun checks.
   */
  private void configureConfigurationFileToSun() {
    configureConfigurationFile(SUN_CHECKS_FILE);
  }

  /**
   * Configures the test-subject to use a non existing file.
   */
  private void configureConfigurationFileToNonExisting() {
    configureConfigurationFile(NOT_EXISTING_FILE);
  }

  /**
   * Configures the test-subject to use the file named by fileName as
   * configuration file.
   *
   * @param fileName
   *          name of the file to be used as configuration file for the task.
   */
  private void configureConfigurationFile(final String fileName) {
    checkTestSubjectExists();
    try {
      sunChecksConfigurationFile = new File(getSrcPath(fileName));
      testSubjectInstance.setConfig(sunChecksConfigurationFile);
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Configures the test-subject to use the file named by filename as
   * examination file.
   *
   * @param fileName
   *          name of the file to be examined by the tasks-checker.
   */
  private void configureExaminationFile(final String fileName) {
    checkTestSubjectExists();
    try {
      examinationFile = new File(getSrcPath(fileName));
      testSubjectInstance.setFile(examinationFile);
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Configures the test-subject to use the "flawless example" as examination
   * file.
   */
  private void configureExaminationFileToFlawless() {
    configureExaminationFile(FLAWLESS_EXAMPLE);
  }

  /**
   * Configures the test-subject to use the "warning example" as examination
   * file.
   */
  private void configfureExaminationFileToWarning() {
    configureExaminationFile(WARNING_EXAMPLE);
  }

  /**
   * Configures the test-subject to use the "erroneous example" as examination
   * file.
   */
  private void configureExaminationFileToErroneous() {
    configureExaminationFile(ERRONEOUS_EXAMPLE);
  }

  /**
   * Configures the test-subject to use the examination file-set for
   * examination. The configured file-set will only contain the
   * flawless-example.
   */
  private void configureExaminationFileset() {
    checkTestSubjectExists();
    try {
      examinationFileSet = new FileSet();
      examinationFile = new File(getSrcPath(FLAWLESS_EXAMPLE));
      examinationFileSet.setFile(examinationFile);
      testSubjectInstance.addFileset(examinationFileSet);
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Sets a new instance as testSubjectInstance. Configures the test-subject to
   * use the "flawless example" for examination and the custom sun checks as
   * configuration. Also configures the project.
   */
  private void configureToFlawlessConfiguration() {
    testSubjectInstance = new CheckstyleAntTask();
    configureConfigurationFileToSun();
    configureExaminationFileToFlawless();
    configureProject();
  }

  /**
   * Sets a mock-up as ant environment.
   */
  private void configureProject() {
    checkTestSubjectExists();
    project = new Project();
    File baseDir = new File("/");
    project.setBaseDir(baseDir);
    testSubjectInstance.setProject(project);
  }

  /**
   * This test should never fail. If it fails there is something terribly wrong
   * with the base configuration.
   */
  @Test
  public final void smokeTest() {
    configureToFlawlessConfiguration();
    testSubjectInstance.execute();
  }

  /**
   * Test behavior when no configuration is given.
   */
  @Test(expected = BuildException.class)
  public final void noConfigFileTest() {
    testSubjectInstance = new CheckstyleAntTask();
    configureExaminationFileToFlawless();
    configureProject();
    testSubjectInstance.execute();
  }

  /**
   * Test behavior when configuration file does not exists.
   */
  @Test(expected = BuildException.class)
  public final void nonExistingConfigTest() {
    testSubjectInstance = new CheckstyleAntTask();
    configureConfigurationFileToNonExisting();
    configureExaminationFileToFlawless();
    configureProject();
    testSubjectInstance.execute();
  }

  /**
   * Test behavior when no configuration is given.
   */
  @Test(expected = BuildException.class)
  public final void emptyConfigFileTest() {
    testSubjectInstance = new CheckstyleAntTask();
    configureExaminationFileToFlawless();
    configureConfigurationFile(EMPTY_CHECK_FILE);
    configureProject();
    testSubjectInstance.execute();
  }

  /**
   * Test behavior when no examination file is given.
   */
  @Test(expected = BuildException.class)
  public final void noFileTest() {
    testSubjectInstance = new CheckstyleAntTask();
    configureConfigurationFileToSun();
    configureProject();
    testSubjectInstance.execute();
  }

  /**
   * Test behavior when file-set is used instead of file.
   */
  @Test
  public final void smokeTestWithFileSet() {
    testSubjectInstance = new CheckstyleAntTask();
    configureExaminationFileset();
    configureConfigurationFileToSun();
    configureProject();
    testSubjectInstance.execute();
  }

  /**
   * Test the effectiveness of the MaxWarnings option.
   */
  @Test(expected = BuildException.class)
  public final void maxWarningExeededTest() {
    configureToFlawlessConfiguration();
    configfureExaminationFileToWarning();
    testSubjectInstance.setMaxWarnings(1);
    testSubjectInstance.execute();
  }

  /**
   * Test the effectiveness of the MaxErrors option.
   */
  @Test(expected = BuildException.class)
  public final void maxErrorsExeededTest() {
    configureToFlawlessConfiguration();
    configureExaminationFileToErroneous();
    testSubjectInstance.setMaxErrors(1);
    testSubjectInstance.execute();
  }

  /**
   * Test the effectiveness of the failOnViolation option.
   */
  @Test
  public final void failOnViolationTest() {
    configureToFlawlessConfiguration();
    configureExaminationFileToErroneous();
    testSubjectInstance.setFailOnViolation(false);
    testSubjectInstance.execute();
  }

  /**
   * Tests if the given failureProperty will be set in the project.
   */
  @Test
  public final void failurePropertyTest() {
    configureToFlawlessConfiguration();
    configureExaminationFileToErroneous();
    project.setProperty(FAILURE_PROPERTY_NAME, FAILURE_PROPERTY_VALUE);
    testSubjectInstance.setFailOnViolation(false);
    testSubjectInstance.setFailureProperty(FAILURE_PROPERTY_NAME);
    testSubjectInstance.execute();
    Hashtable<String, Object> hashtable = project.getProperties();
    Object propertyValue = hashtable.get(FAILURE_PROPERTY_NAME);
    Assert.assertFalse(FAILURE_PROPERTY_VALUE.equals(propertyValue));
  }

  /**
   * It is not possible to proof the effectiveness of this parameter without a
   * real ant environment. I have currently no idea how to test this.
   */
  @Test
  public final void overridePropertyTest() {
    // TODO: think about it.
    configureToFlawlessConfiguration();
    project.setProperty(FAILURE_PROPERTY_NAME, FAILURE_PROPERTY_VALUE);
    CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
    property.setKey(FAILURE_PROPERTY_NAME);
    property.setValue(OVERRIDE_PROPERTY_VALUE);
    testSubjectInstance.addProperty(property);
    testSubjectInstance.execute();
  }

  /**
   * Checker should omit ignored modules by default. Setting the
   * omitIgnoredModules should result in more errors but does not. Maybe my
   * information is wrong here.
   */
  @Test(expected = BuildException.class)
  public final void omitIgnoredModulesTest() {
    // TODO: get more info about this
    configureToFlawlessConfiguration();
    configureExaminationFileToErroneous();
    testSubjectInstance.setOmitIgnoredModules(false);
    testSubjectInstance.setMaxErrors(5);
    testSubjectInstance.setFailOnViolation(true);
    testSubjectInstance.execute();
  }

  /**
   * Test if configuration is possible via URL.
   */
  @Test
  public final void configurationByUrlTest() {
    try {
      testSubjectInstance = new CheckstyleAntTask();
      configureExaminationFileToFlawless();
      configureProject();
      URL url = new File(getSrcPath(SUN_CHECKS_FILE)).toURI().toURL();
      testSubjectInstance.setConfigURL(url);
      testSubjectInstance.execute();
    } catch (Exception ex) {
      Assert.fail();
    }
  }

  /**
   * The behavior of the {@link CheckstyleAntTask} is unusual. The thrown
   * {@linḱ BuildException} message is also misleading.
   */
  @Test
  public final void simultaneousCnfigurationTest() {
    int buildExceptionCounter = 0;
    final int expectedBuildExceptionCount = 2;
    try {
      testSubjectInstance = new CheckstyleAntTask();
      URL url = new File(getSrcPath(SUN_CHECKS_FILE)).toURI().toURL();
      testSubjectInstance.setConfigURL(url);
      configureConfigurationFileToSun();
    } catch (BuildException buildEx) {
      buildExceptionCounter++;
    } catch (Exception ex) {
      Assert.fail();
    }
    try {
      testSubjectInstance = new CheckstyleAntTask();
      URL url = new File(getSrcPath(SUN_CHECKS_FILE)).toURI().toURL();
      configureConfigurationFileToSun();
      testSubjectInstance.setConfigURL(url);
    } catch (BuildException buildEx) {
      buildExceptionCounter++;
    } catch (Exception ex) {
      Assert.fail();
    }
    Assert.assertTrue(buildExceptionCounter == expectedBuildExceptionCount);
  }

  /**
   * Since we have no real ant environment there is no proof that the properties
   * file has been loaded. {@link CheckstyleAntTaskTest.overridePropertyTest}
   */
  @Test
  public final void setPropertiesFileTest() {
    configureToFlawlessConfiguration();
    try {
      testSubjectInstance.setProperties(new File(getSrcPath(PROPERTIES_FILE)));
    } catch (Exception ex) {
      Assert.fail();
    }
    testSubjectInstance.execute();
  }

  /**
   * Test behavior when non_existing properties file is given.
   */
  @Test(expected = BuildException.class)
  public final void setPropertiesFileNonExistingTest() {
    configureToFlawlessConfiguration();
    try {
      testSubjectInstance.setProperties(new File(getSrcPath(NOT_EXISTING_FILE)));
    } catch (Exception ex) {
      Assert.fail();
    }
    testSubjectInstance.execute();
  }

  /**
   * I don't see any possibility of approving that the class-path has been
   * edited. Maybe someone else has a good idea. Also the method's name is
   * misleading -> It should be renamed to addClasspath.
   */
  @Test
  public final void setClasspathTest() {
    configureToFlawlessConfiguration();

    Path classpath = new Path(project, "/../checks/modifier/");
    Path anotherPath = new Path(project, "/../checks/modifier/");
    testSubjectInstance.setClasspath(classpath);
    testSubjectInstance.setClasspath(anotherPath);
    // TODO: load something from the new classpath source below does not work.
    // ClassLoader loader = testSubjectInstance.getClass().getClassLoader();
    // URL url = loader.getResource("ModifierOrderCheckCheck.class");
    // System.out.println(url);
  }

  /**
   * Same problem as stated at setClasspathTest.
   */
  @Test
  public final void setClasspathRefTest() {
    configureToFlawlessConfiguration();
    Reference ref = new Reference();
    ref.setProject(project);
    testSubjectInstance.setClasspathRef(ref);
  }

  /**
   * Same problem as stated at setClasspathTest.
   */
  @Test
  public final void setClasspathAndClasspathRefTest() {
    configureToFlawlessConfiguration();
    Path classpath = new Path(project, "/../checks/modifier/");
    testSubjectInstance.setClasspath(classpath);
    Reference ref = new Reference();
    ref.setProject(project);
    testSubjectInstance.setClasspathRef(ref);
  }

  /**
   *
   */
  @Test
  public final void addFormatterWithLogfileTest() {

    try {
      configureToFlawlessConfiguration();
      CheckstyleAntTask.Formatter formatter = new CheckstyleAntTask.Formatter();
      File logfile = new File(getSrcPath(LOG_FILE));
      formatter.setTofile(logfile);
      testSubjectInstance.addFormatter(formatter);
      testSubjectInstance.execute();
      BufferedReader fileReader = new BufferedReader(new FileReader(logfile));
      boolean result = LOG_FIRST_LINE.equals(fileReader.readLine());
      fileReader.close();
      logfile.deleteOnExit();
      logfile.delete();
      Assert.assertTrue(result);

    } catch (IOException e) {
      Assert.fail();
    }
  }
}