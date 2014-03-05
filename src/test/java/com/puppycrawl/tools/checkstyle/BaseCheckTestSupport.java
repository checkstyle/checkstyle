package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public abstract class BaseCheckTestSupport
{
    /** a brief logger that only display info about errors */
    protected static class BriefLogger
        extends DefaultLogger
    {
        public BriefLogger(OutputStream out)
        {
            super(out, true);
        }
        @Override
        public void auditStarted(AuditEvent evt) {}
        @Override
        public void fileFinished(AuditEvent evt) {}
        @Override
        public void fileStarted(AuditEvent evt) {}
    }

    protected final ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
    protected final PrintStream mStream = new PrintStream(mBAOS);
    protected final Properties mProps = new Properties();

    public static DefaultConfiguration createCheckConfig(Class<?> aClazz)
    {
        final DefaultConfiguration checkConfig =
            new DefaultConfiguration(aClazz.getName());
        return checkConfig;
    }

    protected Checker createChecker(Configuration aCheckConfig)
        throws Exception
    {
        final DefaultConfiguration dc = createCheckerConfig(aCheckConfig);
        final Checker c = new Checker();
        // make sure the tests always run with english error messages
        // so the tests don't fail in supported locales like german
        final Locale locale = Locale.ENGLISH;
        c.setLocaleCountry(locale.getCountry());
        c.setLocaleLanguage(locale.getLanguage());
        c.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        c.configure(dc);
        c.addListener(new BriefLogger(mStream));
        return c;
    }

    protected DefaultConfiguration createCheckerConfig(Configuration aConfig)
    {
        final DefaultConfiguration dc =
            new DefaultConfiguration("configuration");
        final DefaultConfiguration twConf = createCheckConfig(TreeWalker.class);
        // make sure that the tests always run with this charset
        dc.addAttribute("charset", "iso-8859-1");
        dc.addChild(twConf);
        twConf.addChild(aConfig);
        return dc;
    }

    protected static String getPath(String aFilename)
        throws IOException
    {
        return new File("src/test/resources/com/puppycrawl/tools/checkstyle/" + aFilename).getCanonicalPath();
    }

    protected static String getSrcPath(String aFilename) throws IOException
    {
        
        return new File("src/test/java/com/puppycrawl/tools/checkstyle/" + aFilename).getCanonicalPath();
    }

    protected void verify(Configuration aConfig, String aFileName, String[] aExpected)
            throws Exception
    {
        verify(createChecker(aConfig), aFileName, aFileName, aExpected);
    }

    protected void verify(Checker aC, String aFileName, String[] aExpected)
            throws Exception
    {
        verify(aC, aFileName, aFileName, aExpected);
    }

    protected void verify(Checker aC,
                          String aProcessedFilename,
                          String aMessageFileName,
                          String[] aExpected)
        throws Exception
    {
        verify(aC,
            new File[] {new File(aProcessedFilename)},
            aMessageFileName, aExpected);
    }

    protected void verify(Checker aC,
                          File[] aProcessedFiles,
                          String aMessageFileName,
                          String[] aExpected)
        throws Exception
    {
        mStream.flush();
        final List<File> theFiles = Lists.newArrayList();
        Collections.addAll(theFiles, aProcessedFiles);
        final int errs = aC.process(theFiles);

        // process each of the lines
        final ByteArrayInputStream bais =
            new ByteArrayInputStream(mBAOS.toByteArray());
        final LineNumberReader lnr =
            new LineNumberReader(new InputStreamReader(bais));
       
        for (int i = 0; i < aExpected.length; i++) {
            final String expected = aMessageFileName + ":" + aExpected[i];
            final String actual = lnr.readLine();
            assertEquals("error message " + i, expected, actual);
        }

        assertEquals("unexpected output: " + lnr.readLine(),
                     aExpected.length, errs);
        aC.destroy();
    }

    /**
     * Gets the check message 'as is' from appropriate 'messages.properties'
     * file.
     *
     * @param messageKey
     *            the key of message in 'messages.properties' file.
     */
    public String getCheckMessage(String messageKey)
    {
        Properties pr = new Properties();
        try {
            pr.load(getClass().getResourceAsStream("messages.properties"));
        }
        catch (IOException e) {
            return null;
        }
        return pr.getProperty(messageKey);
    }

}
