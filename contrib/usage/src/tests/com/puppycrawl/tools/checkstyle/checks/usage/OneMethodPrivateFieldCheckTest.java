package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class OneMethodPrivateFieldCheckTest
    extends BaseCheckTestCase
{
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(OneMethodPrivateFieldCheck.class);
        final String[] expected = {
            "6:24: Field 'SFIELD0' is used in only one method.",
            "7:24: Field 'SFIELD1' is used in only one method.",
            "8:24: Field 'SFIELD2' is used in only one method.",
            "9:24: Field 'SFIELD3' is used in only one method.",
            "11:17: Field 'mField0' is used in only one method.",
            "12:17: Field 'mField1' is used in only one method.",
            "13:17: Field 'mField2' is used in only one method.",
            "47:17: Field 'mField0' is used in only one method.",
            "48:17: Field 'mField1' is used in only one method.",
            "49:17: Field 'mField2' is used in only one method.",
            "105:19: Field 'mField' is used in only one method.",
        };
        verify(checkConfig, getPath("usage/InputOneMethodPrivateField.java"), expected);
    }
    
    public void testIgnoreFormat() throws Exception
        {
            final DefaultConfiguration checkConfig =
                createCheckConfig(OneMethodPrivateFieldCheck.class);
            checkConfig.addAttribute("ignoreFormat", "2$");
            final String[] expected = {
                "6:24: Field 'SFIELD0' is used in only one method.",
                "7:24: Field 'SFIELD1' is used in only one method.",
                "9:24: Field 'SFIELD3' is used in only one method.",
                "11:17: Field 'mField0' is used in only one method.",
                "12:17: Field 'mField1' is used in only one method.",
                "47:17: Field 'mField0' is used in only one method.",
                "48:17: Field 'mField1' is used in only one method.",
                "105:19: Field 'mField' is used in only one method.",
             };
            verify(checkConfig, getPath("usage/InputOneMethodPrivateField.java"), expected);
        }
}
