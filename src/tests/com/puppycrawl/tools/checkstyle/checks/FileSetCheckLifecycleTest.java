package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertTrue;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import java.util.List;
import org.junit.Test;

public class FileSetCheckLifecycleTest
    extends BaseCheckTestSupport
{
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public static class TestFileSetCheck extends AbstractFileSetCheck
    {
        private static boolean destroyed = false;

        @Override
        public void destroy()
        {
            destroyed = true;
        }

        public static boolean isDestroyed()
        {
            return destroyed;
        }

        @Override
        protected void processFiltered(File aFile, List<String> aLines)
        {
        }
    }

    @Test
    public void testTranslation()
         throws Exception
    {
        final Configuration checkConfig =
            createCheckConfig(TestFileSetCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);

        assertTrue("destroy() not called by Checker", TestFileSetCheck.isDestroyed());
    }

}
