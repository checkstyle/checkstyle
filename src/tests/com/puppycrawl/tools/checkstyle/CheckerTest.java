package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Properties;
import junit.framework.TestCase;
import org.apache.regexp.RESyntaxException;

public class CheckerTest
    extends TestCase
{
    /** a brief logger that only display info about errors */
    protected static class BriefLogger
        extends DefaultLogger
    {
        public BriefLogger(OutputStream out)
        {
            super(out);
        }
        public void auditStarted(AuditEvent evt) {}
        public void fileFinished(AuditEvent evt) {}
        public void fileStarted(AuditEvent evt) {}
        //public void auditFinished(AuditEvent evt) {

            //writer.flush();
        //}
    }

    private final ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
    private final PrintStream mStream = new PrintStream(mBAOS);
    private final Configuration mConfig = new Configuration();

    public CheckerTest(String name)
    {
        super(name);
    }

    protected void setUp()
        throws Exception
    {
        mConfig.setHeaderFile(getPath("java.header"));
    }

    protected String getPath(String aFilename)
        throws IOException
    {
        final File f = new File(System.getProperty("tests.dir"), aFilename);
        return f.getCanonicalPath();
    }

    protected Checker createChecker()
        throws RESyntaxException
    {
        final AuditListener listener = new BriefLogger(mStream);
        final Checker c = new Checker(mConfig, mStream);
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
    }


    public void testWhitespace()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":11: type Javadoc comment is missing an @author tag.",
            filepath + ":14: '=' is not preceeded with whitespace.",
            filepath + ":16: '=' is not proceeded with whitespace.",
            filepath + ":24: '=' is not preceeded with whitespace.",
            filepath + ":25: '=' is not preceeded with whitespace.",
            filepath + ":26: '+=' is not preceeded with whitespace.",
            filepath + ":27: '-' is proceeded with whitespace.",
            filepath + ":27: '-=' is not proceeded with whitespace.",
            filepath + ":28: '++' is preceeded with whitespace.",
            filepath + ":29: '++' is proceeded with whitespace."
        };
        verify(c, filepath, expected);
    }

    public void testWhitespaceOff()
        throws Exception
    {
        mConfig.setIgnoreWhitespace(true);
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":11: type Javadoc comment is missing an @author tag.",
        };
        verify(c, filepath, expected);
    }

    public void testBraces()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputBraces.java");
        final String[] expected = {
            filepath + ":29: 'do' construct must use '{}'s.",
            filepath + ":41: 'while' construct must use '{}'s.",
            filepath + ":41: ';' is not preceeded with whitespace.",
            filepath + ":42: 'while' construct must use '{}'s.",
            filepath + ":44: 'while' construct must use '{}'s.",
            filepath + ":45: 'if' construct must use '{}'s.",
            filepath + ":58: 'for' construct must use '{}'s.",
            filepath + ":58: ';' is not preceeded with whitespace.",
            filepath + ":59: 'for' construct must use '{}'s.",
            filepath + ":61: 'for' construct must use '{}'s.",
            filepath + ":62: 'if' construct must use '{}'s.",
            filepath + ":81: 'if' construct must use '{}'s.",
            filepath + ":81: ';' is not preceeded with whitespace.",
            filepath + ":82: 'if' construct must use '{}'s.",
            filepath + ":84: 'if' construct must use '{}'s.",
            filepath + ":84: 'else' construct must use '{}'s.",
            filepath + ":88: 'if' construct must use '{}'s.",
            filepath + ":93: 'else' construct must use '{}'s.",
            filepath + ":98: 'if' construct must use '{}'s.",
            filepath + ":99: 'if' construct must use '{}'s."
        };
        verify(c, filepath, expected);
    }

    public void testBracesOff()
        throws Exception
    {
        mConfig.setIgnoreBraces(true);
        final Checker c = createChecker();
        final String filepath = getPath("InputBraces.java");
        final String[] expected = {
            filepath + ":41: ';' is not preceeded with whitespace.",
            filepath + ":58: ';' is not preceeded with whitespace.",
            filepath + ":81: ';' is not preceeded with whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testTags()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputTags.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":8: type is missing a Javadoc comment.",
            filepath + ":11: variable 'mMissingJavadoc' missing Javadoc.",
            filepath + ":14: method is missing a Javadoc comment.",
            filepath + ":18: Unused @param tag for 'unused'.",
            filepath + ":24: Expected an @return tag.",
            filepath + ":33: Expected an @return tag.",
            filepath + ":40: Expected @throws tag for 'Exception'.",
            filepath + ":49: Expected @throws tag for 'Exception'.",
            filepath + ":53: Unused @throws tag for 'WrongException'.",
            filepath + ":55: Expected @throws tag for 'Exception'.",
            filepath + ":55: Expected @throws tag for 'NullPointerException'.",
            filepath + ":60: Expected @param tag for 'aOne'.",
            filepath + ":68: Expected @param tag for 'aOne'.",
            filepath + ":72: Unused @param tag for 'WrongParam'.",
            filepath + ":73: Expected @param tag for 'aOne'.",
            filepath + ":73: Expected @param tag for 'aTwo'.",
            filepath + ":78: Unused @param tag for 'Unneeded'.",
            filepath + ":79: Unused Javadoc tag.",
            filepath + ":87: Duplicate @return tag."
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
            filepath + ":14: type is missing a Javadoc comment.",
            filepath + ":17: variable 'fData' missing Javadoc.",
            filepath + ":21: type is missing a Javadoc comment.",
            filepath + ":24: variable 'data' missing Javadoc.",
            filepath + ":24: variable 'data' must match pattern '^[A-Z]([A-Z0-9_]*[A-Z0-9])?$'.",
            filepath + ":27: type is missing a Javadoc comment.",
            filepath + ":30: variable 'rData' missing Javadoc.",
            filepath + ":30: variable 'rData' must be private and have accessor methods."
        };
        verify(c, filepath, expected);
    }

    public void testIgnorePublic()
        throws Exception
    {
        mConfig.setPublicMemberPat("^r[A-Z]");
        final Checker c = createChecker();
        final String filepath = getPath("InputInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":14: type is missing a Javadoc comment.",
            filepath + ":17: variable 'fData' missing Javadoc.",
            filepath + ":17: variable 'fData' must be private and have accessor methods.",
            filepath + ":21: type is missing a Javadoc comment.",
            filepath + ":24: variable 'data' missing Javadoc.",
            filepath + ":24: variable 'data' must match pattern '^[A-Z]([A-Z0-9_]*[A-Z0-9])?$'.",
            filepath + ":27: type is missing a Javadoc comment.",
            filepath + ":30: variable 'rData' missing Javadoc.",
        };
        verify(c, filepath, expected);
    }

    public void testSimple()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputSimple.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":3: Line does not match expected header line of '// Created: 2001'.",
            filepath + ":18: line longer than 80 characters",
            filepath + ":19: line contains a tab character",
            filepath + ":25: variable 'badConstant' must match pattern '^[A-Z]([A-Z0-9_]*[A-Z0-9])?$'.",
            filepath + ":30: variable 'badStatic' must match pattern '^s[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":35: variable 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":39: variable 'mNumCreated2' must be private and have accessor methods.",
            filepath + ":45: variable 'sTest1' must be private and have accessor methods.",
            filepath + ":47: variable 'sTest3' must be private and have accessor methods.",
            filepath + ":49: variable 'sTest2' must be private and have accessor methods.",
            filepath + ":52: variable 'mTest1' must be private and have accessor methods.",
            filepath + ":54: variable 'mTest2' must be private and have accessor methods.",
            filepath + ":67: parameter 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":68: parameter 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":69: parameter 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'."
        };
        verify(c, filepath, expected);
    }

    public void testStrictJavadoc()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: type is missing a Javadoc comment.",
            filepath + ":9: type is missing a Javadoc comment.",
            filepath + ":11: variable 'CONST' missing Javadoc.",
            filepath + ":12: method is missing a Javadoc comment.",
            filepath + ":14: type is missing a Javadoc comment.",
            filepath + ":16: variable 'mData' missing Javadoc.",
            filepath + ":18: method is missing a Javadoc comment.",
            filepath + ":25: method is missing a Javadoc comment.",
            filepath + ":34: type is missing a Javadoc comment.",
            filepath + ":36: variable 'mDiff' missing Javadoc.",
            filepath + ":38: method is missing a Javadoc comment.",
            filepath + ":43: variable 'mSize' missing Javadoc.",
            filepath + ":44: variable 'mLen' missing Javadoc.",
            filepath + ":44: variable 'mLen' must be private and have accessor methods.",
            filepath + ":45: variable 'mDeer' missing Javadoc.",
            filepath + ":45: variable 'mDeer' must be private and have accessor methods.",
            filepath + ":46: variable 'aFreddo' missing Javadoc.",
            filepath + ":46: variable 'aFreddo' must be private and have accessor methods.",
            filepath + ":49: method is missing a Javadoc comment.",
            filepath + ":54: method is missing a Javadoc comment.",
            filepath + ":59: method is missing a Javadoc comment.",
            filepath + ":64: method is missing a Javadoc comment.",
            filepath + ":69: method is missing a Javadoc comment.",
            filepath + ":74: method is missing a Javadoc comment.",
            filepath + ":79: method is missing a Javadoc comment.",
            filepath + ":84: method is missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testRelaxedJavadoc()
        throws Exception
    {
        mConfig.setRelaxJavadoc(true);
        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: type is missing a Javadoc comment.",
            filepath + ":9: type is missing a Javadoc comment.",
            filepath + ":11: variable 'CONST' missing Javadoc.",
            filepath + ":12: method is missing a Javadoc comment.",
            filepath + ":14: type is missing a Javadoc comment.",
            filepath + ":34: type is missing a Javadoc comment.",
            filepath + ":44: variable 'mLen' must be private and have accessor methods.",
            filepath + ":45: variable 'mDeer' missing Javadoc.",
            filepath + ":45: variable 'mDeer' must be private and have accessor methods.",
            filepath + ":46: variable 'aFreddo' missing Javadoc.",
            filepath + ":46: variable 'aFreddo' must be private and have accessor methods.",
            filepath + ":59: method is missing a Javadoc comment.",
            filepath + ":64: method is missing a Javadoc comment.",
            filepath + ":79: method is missing a Javadoc comment.",
            filepath + ":84: method is missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testHeader()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("inputHeader.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":1: Missing a header - not enough lines in file.",
            filepath + ":1: type name 'inputHeader' must match pattern '^[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":1: type is missing a Javadoc comment.",
        };
        verify(c, filepath, expected);
    }

    public void testImport()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputImport.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: Avoid using the '.*' form of import.",
            filepath + ":7: Redundant import from the same package.",
            filepath + ":8: Redundant import from the same package.",
            filepath + ":9: Avoid using the '.*' form of import.",
            filepath + ":10: Avoid using the '.*' form of import.",
            filepath + ":10: Redundant import from the java.lang package.",
            filepath + ":11: Redundant import from the java.lang package.",
            filepath + ":13: Unused import - java.util.List",
            filepath + ":14: Duplicate import to line 13.",
            filepath + ":14: Unused import - java.util.List",
        };
        verify(c, filepath, expected);
    }
}
