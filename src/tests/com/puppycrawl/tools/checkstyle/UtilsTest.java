package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;

import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.ConversionException;
import org.junit.Test;

public class UtilsTest
{
    /**
     * Test Utils.countCharInString.
     */
    @Test
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

        final Pattern r1 = Utils.getPattern("a");
        final Pattern r2 = Utils.getPattern("a");
        assertEquals(r1, r2);
    }

    @Test(expected = ConversionException.class)
    public void testBadRegex()
    {
        Utils.createPattern("[");
    }
}
