package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import junit.framework.TestCase;

public abstract class BaseCheckTestCase
    extends TestCase
{
    /** a brief logger that only display info about errors */
    protected static class BriefLogger
        extends DefaultLogger
    {
        public BriefLogger(OutputStream out)
        {
            super(out, true);
        }
        public void auditStarted(AuditEvent evt) {}
        public void fileFinished(AuditEvent evt) {}
        public void fileStarted(AuditEvent evt) {}
    }

    private final ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
    protected final PrintStream mStream = new PrintStream(mBAOS);
    protected final Properties mProps = new Properties();

    public static DefaultConfiguration createCheckConfig(Class aClazz)
    {
        final DefaultConfiguration checkConfig =
            new DefaultConfiguration("test");
        checkConfig.addAttribute("classname", aClazz.getName());
        return checkConfig;
    }

    protected Checker createChecker(Configuration aCheckConfig)
        throws Exception
    {
        DefaultConfiguration dc = createCheckerConfig(aCheckConfig);
        final Checker c = new Checker();
        c.configure(dc);
        c.addListener(new BriefLogger(mStream));
        return c;
    }

    protected DefaultConfiguration createCheckerConfig(Configuration aConfig)
    {
        final DefaultConfiguration dc =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        dc.addChild(twConf);
        twConf.addChild(aConfig);
        return dc;
    }

    protected static String getPath(String aFilename)
        throws IOException
    {
        final File f = new File(System.getProperty("testinputs.dir"),
                                aFilename);
        return f.getCanonicalPath();
    }

    protected void verify(Checker aC, String aFileName, String[] aExpected)
            throws Exception
    {
        verify(aC, aFileName, aFileName, aExpected);
    }

    protected void verify(Checker aC, String aProcessedFilename,
            String aMessageFileName, String[] aExpected)
        throws Exception
    {
        mStream.flush();
        final int errs = aC.process(new File[] {new File(aProcessedFilename)});

        // process each of the lines
        final ByteArrayInputStream bais =
            new ByteArrayInputStream(mBAOS.toByteArray());
        final LineNumberReader lnr =
            new LineNumberReader(new InputStreamReader(bais));

        for (int i = 0; i < aExpected.length; i++) {
            assertEquals(aMessageFileName + ":" + aExpected[i], lnr.readLine());
        }
        assertEquals(aExpected.length, errs);
        aC.destroy();
    }
}
