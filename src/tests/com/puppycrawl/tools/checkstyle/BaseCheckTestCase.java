/*
 * Created by IntelliJ IDEA.
 * User: lk
 * Date: Sep 22, 2002
 * Time: 8:06:12 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.puppycrawl.tools.checkstyle;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import org.apache.regexp.RESyntaxException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class BaseCheckTestCase
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
