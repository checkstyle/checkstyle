package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;
import org.apache.regexp.RESyntaxException;
import org.xml.sax.SAXException;

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
    private final PrintStream mStream = new PrintStream(mBAOS);
    protected final Properties mProps = new Properties();

    public BaseCheckTestCase(String aName)
    {
        super(aName);
    }

    protected Checker createChecker(CheckConfiguration aCheckConfig)
        throws RESyntaxException, FileNotFoundException, IOException,
        ParserConfigurationException, SAXException, ClassNotFoundException,
        InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        final Checker c = new Checker(new Configuration(mProps, mStream),
                                      new CheckConfiguration[] {aCheckConfig});
        final AuditListener listener = new BriefLogger(mStream);
        c.addListener(listener);
        return c;
    }

    protected static String getPath(String aFilename)
        throws IOException
    {
        final File f = new File(System.getProperty("testinputs.dir"),
                                aFilename);
        return f.getCanonicalPath();
    }

    protected void verify(Checker aC, String aFilename, String[] aExpected)
        throws Exception
    {
        mStream.flush();
        final int errs = aC.processNEW(new String[] {aFilename});

        // process each of the lines
        final ByteArrayInputStream bais =
            new ByteArrayInputStream(mBAOS.toByteArray());
        final LineNumberReader lnr =
            new LineNumberReader(new InputStreamReader(bais));

        for (int i = 0; i < aExpected.length; i++) {
            assertEquals(aFilename + ":" + aExpected[i], lnr.readLine());
        }
        assertEquals(aExpected.length, errs);
        aC.destroy();
    }
}
