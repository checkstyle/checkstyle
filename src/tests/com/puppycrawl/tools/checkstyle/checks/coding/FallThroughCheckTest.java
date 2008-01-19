package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class FallThroughCheckTest extends BaseCheckTestSupport
{

    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(FallThroughCheck.class);
        final String[] expected = {
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
            "367:11: Fall through from previous branch of the switch statement.",
            "370:11: Fall through from previous branch of the switch statement.",
            "372:40: Fall through from previous branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }

    @Test
    public void testLastCaseGroup() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(FallThroughCheck.class);
        checkConfig.addAttribute("checkLastCaseGroup", "true");
        final String[] expected = {
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from the last branch of the switch statement.",
            "367:11: Fall through from previous branch of the switch statement.",
            "370:11: Fall through from previous branch of the switch statement.",
            "372:40: Fall through from previous branch of the switch statement.",
            "374:11: Fall through from the last branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);
    }

    @Test
    public void testOwnPattern() throws Exception
    {
        final String ownPattern = "Continue with next case";
        final DefaultConfiguration checkConfig =
            createCheckConfig(FallThroughCheck.class);
        checkConfig.addAttribute("reliefPattern", ownPattern);

        final String[] expected = {
            "12:13: Fall through from previous branch of the switch statement.",
            "36:13: Fall through from previous branch of the switch statement.",
            "51:13: Fall through from previous branch of the switch statement.",
            "68:13: Fall through from previous branch of the switch statement.",
            "85:13: Fall through from previous branch of the switch statement.",
            "103:13: Fall through from previous branch of the switch statement.",
            "121:13: Fall through from previous branch of the switch statement.",
            "143:11: Fall through from previous branch of the switch statement.",
            "168:11: Fall through from previous branch of the switch statement.",
            "184:11: Fall through from previous branch of the switch statement.",
            "202:11: Fall through from previous branch of the switch statement.",
            "220:11: Fall through from previous branch of the switch statement.",
            "239:11: Fall through from previous branch of the switch statement.",
            "250:26: Fall through from previous branch of the switch statement.",
            "264:11: Fall through from previous branch of the switch statement.",
            "279:11: Fall through from previous branch of the switch statement.",
            "282:11: Fall through from previous branch of the switch statement.",
            "286:11: Fall through from previous branch of the switch statement.",
            "288:25: Fall through from previous branch of the switch statement.",
            "304:11: Fall through from previous branch of the switch statement.",
            "307:11: Fall through from previous branch of the switch statement.",
            "309:25: Fall through from previous branch of the switch statement.",
            "325:11: Fall through from previous branch of the switch statement.",
            "328:11: Fall through from previous branch of the switch statement.",
            "330:23: Fall through from previous branch of the switch statement.",
            "346:11: Fall through from previous branch of the switch statement.",
            "349:11: Fall through from previous branch of the switch statement.",
            "351:30: Fall through from previous branch of the switch statement.",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputFallThrough.java"),
               expected);

    }
}
