package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Properties;

import junit.framework.TestCase;

public class CheckerTest
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
    private final Properties mProps = new Properties();

    public CheckerTest(String name)
    {
        super(name);
    }

    protected void setUp()
        throws Exception
    {
        mProps.setProperty(Defn.LOCALE_COUNTRY_PROP,
                           Locale.ENGLISH.getCountry());
        mProps.setProperty(Defn.LOCALE_LANGUAGE_PROP,
                           Locale.ENGLISH.getLanguage());
    }

    static String getPath(String aFilename)
        throws IOException
    {
        final File f = new File(System.getProperty("testinputs.dir"),
                                aFilename);
        return f.getCanonicalPath();
    }

    protected Checker createChecker()
        throws Exception
    {
        final Configuration config = new Configuration(mProps, mStream);
        final Checker c = new Checker(config);
        final AuditListener listener = new BriefLogger(mStream);
        c.addListener(listener);
        return c;
    }

    private void verify(Checker aC, String aFilename, String[] aExpected)
        throws Exception
    {
        mStream.flush();
        final int errs = aC.process(new String[] {aFilename});

        // process each of the lines
        final ByteArrayInputStream bais =
            new ByteArrayInputStream(mBAOS.toByteArray());
        final LineNumberReader lnr =
            new LineNumberReader(new InputStreamReader(bais));

        for (int i = 0; i < aExpected.length; i++) {
            assertEquals(aExpected[i], lnr.readLine());
        }
        assertEquals(aExpected.length, errs);
        aC.destroy();
    }


    public void testWhitespace()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_CAST_WHITESPACE_PROP,
                           Boolean.FALSE.toString());

        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.IGNORE.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.IGNORE.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":29:14: '-' is followed by whitespace.",
            filepath + ":29:21: '+' is followed by whitespace.",
            filepath + ":30:14: '++' is preceeded with whitespace.",
            filepath + ":30:21: '--' is preceeded with whitespace.",
            filepath + ":31:15: '++' is followed by whitespace.",
            filepath + ":31:22: '--' is followed by whitespace.",
            filepath + ":88:21: 'cast' is not followed by whitespace.",
            filepath + ":111:22: '!' is followed by whitespace.",
            filepath + ":112:23: '~' is followed by whitespace.",
        };
        verify(c, filepath, expected);
        c.destroy();
    }

    public void testWhitespaceCastParenOff()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_CAST_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.IGNORE.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.IGNORE.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":29:14: '-' is followed by whitespace.",
            filepath + ":29:21: '+' is followed by whitespace.",
            filepath + ":30:14: '++' is preceeded with whitespace.",
            filepath + ":30:21: '--' is preceeded with whitespace.",
            filepath + ":31:15: '++' is followed by whitespace.",
            filepath + ":31:22: '--' is followed by whitespace.",
            filepath + ":111:22: '!' is followed by whitespace.",
            filepath + ":112:23: '~' is followed by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testBraces()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputBraces.java");
        final String[] expected = {
            filepath + ":58:23: ';' is not followed by whitespace.",
            filepath + ":58:29: ';' is not followed by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testInner()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":24:16: Name 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
        };
        verify(c, filepath, expected);
    }

    public void testIgnoreAccess()
        throws Exception
    {
        mProps.setProperty(Defn.ALLOW_PROTECTED_PROP, "true");
        mProps.setProperty(Defn.ALLOW_PACKAGE_PROP, "true");
        final Checker c = createChecker();
        final String filepath = getPath("InputInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":24:16: Name 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
        };
        verify(c, filepath, expected);
    }

    public void testSimple()
        throws Exception
    {
        mProps.setProperty(Defn.TODO_PATTERN_PROP, "FIXME:");
        final Checker c = createChecker();
        final String filepath = getPath("InputSimple.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":42:40: ',' is not followed by whitespace.",
            filepath + ":71:30: ',' is not followed by whitespace.",
            filepath + ":161: Comment matches to-do format 'FIXME:'.",
            filepath + ":162: Comment matches to-do format 'FIXME:'.",
            filepath + ":163: Comment matches to-do format 'FIXME:'.",
            filepath + ":167: Comment matches to-do format 'FIXME:'.",
        };
        verify(c, filepath, expected);
    }



    public void testPackageHtml()
        throws Exception
    {
        mProps.setProperty(Defn.REQUIRE_PACKAGE_HTML_PROP, "true");
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.PRIVATE.getName());
        final Checker c = createChecker();
        final String packageHtmlPath = getPath("package.html");
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
            packageHtmlPath + ":0: Missing package documentation file.",
        };
        verify(c, filepath, expected);
    }

    public void testAssertIdentifier()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputAssertIdentifier.java");
        assertNotNull(c);
        final String[] expected = {
        };
        verify(c, filepath, expected);
    }


    public void testOpWrapNL()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        mProps.setProperty(Defn.WRAP_OP_PROP, WrapOpOption.NL.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputOpWrap.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":15:19: '+' should be on a new line.",
            filepath + ":16:15: '-' should be on a new line.",
            filepath + ":24:18: '&&' should be on a new line.",
        };
        verify(c, filepath, expected);
    }

    public void testOpWrapEOL()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        mProps.setProperty(Defn.WRAP_OP_PROP, WrapOpOption.EOL.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputOpWrap.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":18:13: '-' should be on the previous line.",
            filepath + ":22:13: '&&' should be on the previous line.",
            filepath + ":27:13: '&&' should be on the previous line.",
        };
        verify(c, filepath, expected);
    }

    public void testOpWrapIgnore()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        mProps.setProperty(Defn.WRAP_OP_PROP, WrapOpOption.IGNORE.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputOpWrap.java");
        assertNotNull(c);
        final String[] expected = {
        };
        verify(c, filepath, expected);
    }
}
