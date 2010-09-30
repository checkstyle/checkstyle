package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ParameterAssignmentCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterAssignmentCheck.class);
        final String[] expected = {
            "9:15: Assignment of parameter 'field' is not allowed.",
            "10:15: Assignment of parameter 'field' is not allowed.",
            "12:14: Assignment of parameter 'field' is not allowed.",
            "20:30: Assignment of parameter 'field1' is not allowed.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputParameterAssignment.java"),
               expected);
    }
}
