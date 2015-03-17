////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.lambda;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;
import java.io.File;

import static com.puppycrawl.tools.checkstyle.checks.lambda.DemandTypesInLambdaCheck.MSG_ERROR_DEMAND_TYPES_IN_LAMBDA;

public class DemandTypesInLambdaCheckTest extends BaseCheckTestSupport
{

    private String getJava8Path(String fileName) throws Exception
    {
        return new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/" + fileName).getCanonicalPath();
    }

    @Test
    public void testNoParameterLambda() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(DemandTypesInLambdaCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getJava8Path("lambda/InputNoParameterLambda.java"),
                expected);
    }

    @Test
    public void testOneGenericParameterLambda() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(DemandTypesInLambdaCheck.class);
        final String[] expected = {
            "9:22: " + getCheckMessage(MSG_ERROR_DEMAND_TYPES_IN_LAMBDA),
            "11:28: " + getCheckMessage(MSG_ERROR_DEMAND_TYPES_IN_LAMBDA),
        };
        verify(checkConfig, getJava8Path("lambda/InputOneGenericParameterLambda.java"),
                expected);
    }

    @Test
    public void testOneTypeParameterLambda() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(DemandTypesInLambdaCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getJava8Path("lambda/InputOneTypeParameterLambda.java"),
                expected);
    }

    @Test
    public void testFewGenericParameterLambda() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(DemandTypesInLambdaCheck.class);
        final String[] expected = {
            "9:31: " + getCheckMessage(MSG_ERROR_DEMAND_TYPES_IN_LAMBDA),
            "9:33: " + getCheckMessage(MSG_ERROR_DEMAND_TYPES_IN_LAMBDA),
        };
        verify(checkConfig, getJava8Path("lambda/InputFewGenericParameterLambda.java"),
                expected);
    }

    @Test
    public void testFewTypeParameterLambda() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(DemandTypesInLambdaCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getJava8Path("lambda/InputFewTypeParameterLambda.java"),
                expected);
    }
}
