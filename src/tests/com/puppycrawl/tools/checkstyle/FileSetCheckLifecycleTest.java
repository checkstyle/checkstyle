package com.puppycrawl.tools.checkstyle;

import java.io.File;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class FileSetCheckLifecycleTest     extends BaseCheckTestCase
{
    protected DefaultConfiguration createCheckerConfig(Configuration aCheckConfig)
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

        public void process(File[] aFiles)
        {
        }
    }

    public void testTranslation()
         throws Exception
    {
        Configuration checkConfig = createCheckConfig(TestFileSetCheck.class);
        Checker c = createChecker(checkConfig);
        final String filepath = getPath("InputScopeAnonInner.java");

        final String[] expected = {
        };
        verify(c, filepath, filepath, expected);

        assertTrue("destroy() not called by Checker", TestFileSetCheck.isDestroyed());
    }

}
