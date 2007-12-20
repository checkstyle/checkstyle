package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.util.List;

import com.puppycrawl.tools.checkstyle.BaseCheckTestCase;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class FileSetCheckLifecycleTest
    extends BaseCheckTestCase
{
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

        public void destroy()
        {
            destroyed = true;
        }

        public static boolean isDestroyed()
        {
            return destroyed;
        }

        public void process(List<File> aFiles)
        {
        }
    }

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
