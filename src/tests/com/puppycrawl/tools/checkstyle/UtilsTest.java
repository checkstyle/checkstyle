package com.puppycrawl.tools.checkstyle;

import java.util.Properties;

import junit.framework.TestCase;
import org.apache.regexp.RE;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Utils;

public class UtilsTest
    extends TestCase
{
    /**
     * Test Utils.countCharInString.
     */
    public void testLengthExpandedTabs()
        throws Exception
    {
        String s1 = "\t";
        assertEquals(8, Utils.lengthExpandedTabs(s1, s1.length(), 8));

        String s2 = "  \t";
        assertEquals(8, Utils.lengthExpandedTabs(s2, s2.length(), 8));

        String s3 = "\t\t";
        assertEquals(16, Utils.lengthExpandedTabs(s3, s3.length(), 8));

        String s4 = " \t ";
        assertEquals(9, Utils.lengthExpandedTabs(s4, s4.length(), 8));

        assertEquals(0, Utils.lengthMinusTrailingWhitespace(""));
        assertEquals(0, Utils.lengthMinusTrailingWhitespace(" \t "));
        assertEquals(3, Utils.lengthMinusTrailingWhitespace(" 23"));
        assertEquals(3, Utils.lengthMinusTrailingWhitespace(" 23 \t "));

        final RE r1 = Utils.getRE("a");
        final RE r2 = Utils.getRE("a");
        assertEquals(r1, r2);
    }
    public void testReplacePropertiesNoReplace()
        throws CheckstyleException
    {
        final String[] testValues = {null, "", "a", "$a", "{a",
                                       "{a}", "a}", "$a}", "$", "a$b"};
        final Properties props = initProperties();
        for (int i = 0; i < testValues.length; i++) {
            final String value = Utils.replaceProperties(testValues[i], props);
            assertEquals("\"" + testValues[i] + "\"", value, testValues[i]);
        }
    }
    
    public void testReplacePropertiesSyntaxError()
    {
        final Properties props = initProperties();
        try {
            final String value = Utils.replaceProperties("${a", props);
            fail("expected to fail, instead got: " + value);
        }
        catch (CheckstyleException ex) {
            assertEquals("Syntax error in property: ${a", ex.getMessage());
        }
    }
    
    public void testReplacePropertiesMissingProperty()
    {
        final Properties props = initProperties();
        try {
            final String value = Utils.replaceProperties("${c}", props);
            fail("expected to fail, instead got: " + value);
        }
        catch (CheckstyleException ex) {
            assertEquals("Property ${c} has not been set", ex.getMessage());
        }
    }

    public void testReplacePropertiesReplace()
        throws CheckstyleException
    {
        final String[][] testValues = {
            {"${a}", "A"},
            {"x${a}", "xA"},
            {"${a}x", "Ax"},
            {"${a}${b}", "AB"},
            {"x${a}${b}", "xAB"},
            {"${a}x${b}", "AxB"},
            {"${a}${b}x", "ABx"},
            {"x${a}y${b}", "xAyB"},
            {"${a}x${b}y", "AxBy"},
            {"x${a}${b}y", "xABy"},
            {"x${a}y${b}z", "xAyBz"},
            {"$$", "$"},
            };
        final Properties props = initProperties();
        for (int i = 0; i < testValues.length; i++) {
            final String value =
                Utils.replaceProperties(testValues[i][0], props);
            assertEquals("\"" + testValues[i][0] + "\"",
                testValues[i][1], value);
        }
    }

    private Properties initProperties()
    {
        final Properties props = new Properties();
        props.put("a", "A");
        props.put("b", "B");
        return props;
    }
}
