package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/*
 * Created by IntelliJ IDEA.
 * User: lk
 * Date: Oct 21, 2002
 * Time: 8:59:11 AM
 * To change this template use Options | File Templates.
 */
public class SimplifyBooleanReturnCheckTest
    extends BaseCheckTestCase
{
    public void testIt()
            throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(SimplifyBooleanReturnCheck.class);
        final String[] expected = {
            "20:9: Remove conditional logic.",
            "33:9: Remove conditional logic.",
        };
        verify(checkConfig, getPath("InputSimplifyBoolean.java"), expected);
    }
}
