package com.puppycrawl.tools.checkstyle.ant;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;

/**
 * Test-class for internal property-class of {@link CheckstyleAntTask}.
 */
public class CheckstyleAntTaskPropertyTest extends BaseCheckTestSupport {

    /**
     * An example Key.
     */
    private final static String EXAMPLE_KEY = "myKey";
    /**
     * An example Value.
     */
    private final static String EXAMPLE_VALUE = "myValue";

    /**
     * An example Value.
     */
    private final static String EXAMPLE_FILENAME = "ant/testfille.properties";

    /**
     * Tests the getter and setter mechanism for the key field.
     */
    @Test
    public void setKeyTest() {
        CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setKey(EXAMPLE_KEY);
        Assert.assertTrue(property.getKey().equals(EXAMPLE_KEY));
    }

    /**
     * Tests the getter and setter mechanism for the value field.
     */
    @Test
    public void setValueTest() {
        CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setValue(EXAMPLE_VALUE);
        Assert.assertTrue(property.getValue().equals(EXAMPLE_VALUE));
    }

    /**
     * Tests the getter and setter mechanism for the file/value field.
     *
     * @throws IOException
     */
    @Test
    public void setFileValueByFileTest() throws IOException {
        CheckstyleAntTask.Property property = new CheckstyleAntTask.Property();
        property.setFile(new File(EXAMPLE_FILENAME));
        Assert.assertTrue(property.getValue().equals(new File(EXAMPLE_FILENAME).getAbsolutePath()));
    }
}
