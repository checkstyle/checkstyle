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
        mConfig.setLCurlyMethod(LeftCurlyOption.NL);
        mConfig.setLCurlyOther(LeftCurlyOption.NLOW);
        mConfig.setLCurlyType(LeftCurlyOption.NL);
        mConfig.setRCurly(RightCurlyOption.ALONE);
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
        final Checker c = new Checker(mConfig);
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
        mConfig.setIgnoreCastWhitespace(false);
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":5: '.' is preceeded with whitespace.",
            filepath + ":5: '.' is followed by whitespace.",
            filepath + ":13: type Javadoc comment is missing an @author tag.",
            filepath + ":16: '=' is not preceeded with whitespace.",
            filepath + ":16: '=' is not followed by whitespace.",
            filepath + ":18: '=' is not followed by whitespace.",
            filepath + ":26: '=' is not preceeded with whitespace.",
            filepath + ":27: '=' is not preceeded with whitespace.",
            filepath + ":27: '=' is not followed by whitespace.",
            filepath + ":28: '+=' is not preceeded with whitespace.",
            filepath + ":28: '+=' is not followed by whitespace.",
            filepath + ":29: '-=' is not followed by whitespace.",
            filepath + ":29: '-' is followed by whitespace.",
            filepath + ":29: '+' is followed by whitespace.",
            filepath + ":30: '++' is preceeded with whitespace.",
            filepath + ":30: '--' is preceeded with whitespace.",
            filepath + ":31: '++' is followed by whitespace.",
            filepath + ":31: '--' is followed by whitespace.",
            filepath + ":37: 'synchronized' is not followed by whitespace.",
            filepath + ":39: 'try' is not followed by whitespace.",
            filepath + ":41: 'catch' is not followed by whitespace.",
            filepath + ":58: '(' is followed by whitespace.",
            filepath + ":58: ')' is preceeded by whitespace.",
            filepath + ":58: 'if' is not followed by whitespace.",
            filepath + ":59: '{' should be on the previous line.",
            filepath + ":63: '{' should be on the previous line.",
            filepath + ":74: '(' is followed by whitespace.",
            filepath + ":74: ')' is preceeded by whitespace.",
            filepath + ":75: '{' should be on the previous line.",
            filepath + ":76: 'return' is not followed by whitespace.",
            filepath + ":79: '{' should be on the previous line.",
            filepath + ":88: cast needs to be followed by whitespace.",
            filepath + ":97: '?' is not preceeded with whitespace.",
            filepath + ":97: '?' is not followed by whitespace.",
            filepath + ":97: ':' is not preceeded with whitespace.",
            filepath + ":97: ':' is not followed by whitespace.",
            filepath + ":98: '==' is not preceeded with whitespace.",
            filepath + ":98: '==' is not followed by whitespace.",
            filepath + ":104: '*' is not followed by whitespace.",
            filepath + ":104: '*' is not preceeded with whitespace.",
            filepath + ":111: '!' is followed by whitespace.",
            filepath + ":112: '~' is followed by whitespace.",
            filepath + ":119: '%' is not preceeded with whitespace.",
            filepath + ":120: '%' is not followed by whitespace.",
            filepath + ":121: '%' is not preceeded with whitespace.",
            filepath + ":121: '%' is not followed by whitespace.",
            filepath + ":123: '/' is not preceeded with whitespace.",
            filepath + ":124: '/' is not followed by whitespace.",
            filepath + ":125: '/' is not preceeded with whitespace.",
            filepath + ":125: '/' is not followed by whitespace.",
            filepath + ":129: '.' is preceeded with whitespace.",
            filepath + ":129: '.' is followed by whitespace.",
            filepath + ":136: '.' is preceeded with whitespace.",
            filepath + ":136: '.' is followed by whitespace.",
            filepath + ":153: 'assert' is not followed by whitespace.",
            filepath + ":156: ':' is not preceeded with whitespace.",
            filepath + ":156: ':' is not followed by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testWhitespaceCastOff()
        throws Exception
    {
        mConfig.setIgnoreCastWhitespace(true);
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":5: '.' is preceeded with whitespace.",
            filepath + ":5: '.' is followed by whitespace.",
            filepath + ":13: type Javadoc comment is missing an @author tag.",
            filepath + ":16: '=' is not preceeded with whitespace.",
            filepath + ":16: '=' is not followed by whitespace.",
            filepath + ":18: '=' is not followed by whitespace.",
            filepath + ":26: '=' is not preceeded with whitespace.",
            filepath + ":27: '=' is not preceeded with whitespace.",
            filepath + ":27: '=' is not followed by whitespace.",
            filepath + ":28: '+=' is not preceeded with whitespace.",
            filepath + ":28: '+=' is not followed by whitespace.",
            filepath + ":29: '-=' is not followed by whitespace.",
            filepath + ":29: '-' is followed by whitespace.",
            filepath + ":29: '+' is followed by whitespace.",
            filepath + ":30: '++' is preceeded with whitespace.",
            filepath + ":30: '--' is preceeded with whitespace.",
            filepath + ":31: '++' is followed by whitespace.",
            filepath + ":31: '--' is followed by whitespace.",
            filepath + ":37: 'synchronized' is not followed by whitespace.",
            filepath + ":39: 'try' is not followed by whitespace.",
            filepath + ":41: 'catch' is not followed by whitespace.",
            filepath + ":58: '(' is followed by whitespace.",
            filepath + ":58: ')' is preceeded by whitespace.",
            filepath + ":58: 'if' is not followed by whitespace.",
            filepath + ":59: '{' should be on the previous line.",
            filepath + ":63: '{' should be on the previous line.",
            filepath + ":74: '(' is followed by whitespace.",
            filepath + ":74: ')' is preceeded by whitespace.",
            filepath + ":75: '{' should be on the previous line.",
            filepath + ":76: 'return' is not followed by whitespace.",
            filepath + ":79: '{' should be on the previous line.",
            filepath + ":97: '?' is not preceeded with whitespace.",
            filepath + ":97: '?' is not followed by whitespace.",
            filepath + ":97: ':' is not preceeded with whitespace.",
            filepath + ":97: ':' is not followed by whitespace.",
            filepath + ":98: '==' is not preceeded with whitespace.",
            filepath + ":98: '==' is not followed by whitespace.",
            filepath + ":104: '*' is not followed by whitespace.",
            filepath + ":104: '*' is not preceeded with whitespace.",
            filepath + ":111: '!' is followed by whitespace.",
            filepath + ":112: '~' is followed by whitespace.",
            filepath + ":119: '%' is not preceeded with whitespace.",
            filepath + ":120: '%' is not followed by whitespace.",
            filepath + ":121: '%' is not preceeded with whitespace.",
            filepath + ":121: '%' is not followed by whitespace.",
            filepath + ":123: '/' is not preceeded with whitespace.",
            filepath + ":124: '/' is not followed by whitespace.",
            filepath + ":125: '/' is not preceeded with whitespace.",
            filepath + ":125: '/' is not followed by whitespace.",
            filepath + ":129: '.' is preceeded with whitespace.",
            filepath + ":129: '.' is followed by whitespace.",
            filepath + ":136: '.' is preceeded with whitespace.",
            filepath + ":136: '.' is followed by whitespace.",
            filepath + ":153: 'assert' is not followed by whitespace.",
            filepath + ":156: ':' is not preceeded with whitespace.",
            filepath + ":156: ':' is not followed by whitespace.",
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
            filepath + ":13: type Javadoc comment is missing an @author tag.",
            filepath + ":59: '{' should be on the previous line.",
            filepath + ":63: '{' should be on the previous line.",
            filepath + ":75: '{' should be on the previous line.",
            filepath + ":79: '{' should be on the previous line.",
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
            filepath + ":41: 'while' is not followed by whitespace.",
            filepath + ":41: 'while' construct must use '{}'s.",
            filepath + ":42: 'while' construct must use '{}'s.",
            filepath + ":44: 'while' construct must use '{}'s.",
            filepath + ":45: 'if' construct must use '{}'s.",
            filepath + ":58: 'for' is not followed by whitespace.",
            filepath + ":58: ';' needs to be followed by whitespace.",
            filepath + ":58: ';' needs to be followed by whitespace.",
            filepath + ":58: 'for' construct must use '{}'s.",
            filepath + ":59: 'for' construct must use '{}'s.",
            filepath + ":61: 'for' construct must use '{}'s.",
            filepath + ":63: 'if' construct must use '{}'s.",
            filepath + ":82: 'if' construct must use '{}'s.",
            filepath + ":83: 'if' construct must use '{}'s.",
            filepath + ":85: 'if' construct must use '{}'s.",
            filepath + ":87: 'else' construct must use '{}'s.",
            filepath + ":89: 'if' construct must use '{}'s.",
            filepath + ":97: 'else' construct must use '{}'s.",
            filepath + ":99: 'if' construct must use '{}'s.",
            filepath + ":100: 'if' construct must use '{}'s."
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
            filepath + ":41: 'while' is not followed by whitespace.",
            filepath + ":58: 'for' is not followed by whitespace.",
            filepath + ":58: ';' needs to be followed by whitespace.",
            filepath + ":58: ';' needs to be followed by whitespace.",
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
            filepath + ":87: Duplicate @return tag.",
            filepath + ":109: Expected @param tag for 'aOne'.",
            filepath + ":109: Expected @param tag for 'aFour'.",
            filepath + ":109: Expected @param tag for 'aFive'.",
            filepath + ":129: '{' should be on the previous line.",
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
            filepath + ":30: variable 'rData' must be private and have accessor methods.",
            filepath + ":33: variable 'protectedVariable' must be private and have accessor methods.",
            filepath + ":36: variable 'packageVariable' must be private and have accessor methods."

        };
        verify(c, filepath, expected);
    }

    public void testIgnoreAccess()
        throws Exception
    {
        mConfig.setPublicMemberPat("^r[A-Z]");
        mConfig.setAllowProtected(true);
        mConfig.setAllowPackage(true);
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
        mConfig.setMaxFileLength(20);
        mConfig.setMaxMethodLength(19);
        mConfig.setMaxConstructorLength(9);
        mConfig.setParamPat("^a[A-Z][a-zA-Z0-9]*$");
        mConfig.setStaticPat("^s[A-Z][a-zA-Z0-9]*$");
        mConfig.setMemberPat("^m[A-Z][a-zA-Z0-9]*$");
        final Checker c = createChecker();
        final String filepath = getPath("InputSimple.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":1: file length is 151 lines (max allowed is 20).",
            filepath + ":3: Line does not match expected header line of '// Created: 2001'.",
            filepath + ":16: 'final' modifier out of order with the JLS suggestions.",
            filepath + ":18: line longer than 80 characters",
            filepath + ":19: line contains a tab character",
            filepath + ":25: variable 'badConstant' must match pattern '^[A-Z]([A-Z0-9_]*[A-Z0-9])?$'.",
            filepath + ":30: variable 'badStatic' must match pattern '^s[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":35: variable 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":39: variable 'mNumCreated2' must be private and have accessor methods.",
            filepath + ":42: ',' needs to be followed by whitespace.",
            filepath + ":49: variable 'sTest1' must be private and have accessor methods.",
            filepath + ":51: variable 'sTest3' must be private and have accessor methods.",
            filepath + ":53: variable 'sTest2' must be private and have accessor methods.",
            filepath + ":56: variable 'mTest1' must be private and have accessor methods.",
            filepath + ":58: variable 'mTest2' must be private and have accessor methods.",
            filepath + ":71: ',' needs to be followed by whitespace.",
            filepath + ":71: parameter 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":71: parameter 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":72: parameter 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":80: method length is 20 lines (max allowed is 19).",
            filepath + ":103: constructor length is 10 lines (max allowed is 9).",
            filepath + ":119: variable 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":123: variable 'CDE' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":127: '{' should be on the previous line.",
            filepath + ":130: variable 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":131: '{' should be on the previous line.",
            filepath + ":132: variable 'InnerBlockVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":137: method name 'ALL_UPPERCASE_METHOD' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":142: 'private' modifier out of order with the JLS suggestions.",
            filepath + ":148: 'private' modifier out of order with the JLS suggestions.",
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
            filepath + ":84: method is missing a Javadoc comment.",
            filepath + ":94: Expected @param tag for 'aA'."
        };
        verify(c, filepath, expected);
    }

    public void testNoJavadoc()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.NOTHING);
        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":44: variable 'mLen' must be private and have accessor methods.",
            filepath + ":45: variable 'mDeer' must be private and have accessor methods.",
            filepath + ":46: variable 'aFreddo' must be private and have accessor methods.",
        };
        verify(c, filepath, expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    public void testRelaxedJavadoc()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.PROTECTED);
        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: type is missing a Javadoc comment.",
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


    public void testScopeInnerInterfacesPublic()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.PUBLIC);
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerInterfaces.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: type is missing a Javadoc comment.",
            filepath + ":38: type is missing a Javadoc comment.",
            filepath + ":40: variable 'CA' missing Javadoc.",
            filepath + ":41: variable 'CB' missing Javadoc.",
            filepath + ":43: method is missing a Javadoc comment.",
            filepath + ":44: method is missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testScopeInnerClassesPackage()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.PACKAGE);
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerClasses.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":18: type is missing a Javadoc comment.",
            filepath + ":20: type is missing a Javadoc comment.",
            filepath + ":22: type is missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testScopeInnerClassesPublic()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.PUBLIC);
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerClasses.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":18: type is missing a Javadoc comment.",
        };
        verify(c, filepath, expected);
    }

    public void testScopeAnonInnerPrivate()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.PRIVATE);
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":37: '(' is followed by whitespace.",
            filepath + ":39: '(' is followed by whitespace.",
            filepath + ":39: ')' is preceeded by whitespace.",
            filepath + ":43: ')' is preceeded by whitespace.",
            filepath + ":51: '(' is followed by whitespace.",
            filepath + ":53: '(' is followed by whitespace.",
            filepath + ":53: ')' is preceeded by whitespace.",
            filepath + ":57: ')' is preceeded by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testScopeAnonInnerAnonInner()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.ANONINNER);
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":26: method is missing a Javadoc comment.",
            filepath + ":37: '(' is followed by whitespace.",
            filepath + ":39: '(' is followed by whitespace.",
            filepath + ":39: ')' is preceeded by whitespace.",
            filepath + ":39: method is missing a Javadoc comment.",
            filepath + ":43: ')' is preceeded by whitespace.",
            filepath + ":51: '(' is followed by whitespace.",
            filepath + ":53: '(' is followed by whitespace.",
            filepath + ":53: ')' is preceeded by whitespace.",
            filepath + ":53: method is missing a Javadoc comment.",
            filepath + ":57: ')' is preceeded by whitespace.",
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

    public void testRegexpHeader()
        throws Exception
    {
        final Checker c = createChecker();
        mConfig.setHeaderLinesRegexp(true);
        mConfig.setHeaderFile(getPath("regexp.header"));
        mConfig.setHeaderIgnoreLines("4,5");
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":3: Line does not match expected header line of '// Created: 2002'.",
            filepath + ":37: '(' is followed by whitespace.",
            filepath + ":39: '(' is followed by whitespace.",
            filepath + ":39: ')' is preceeded by whitespace.",
            filepath + ":43: ')' is preceeded by whitespace.",
            filepath + ":51: '(' is followed by whitespace.",
            filepath + ":53: '(' is followed by whitespace.",
            filepath + ":53: ')' is preceeded by whitespace.",
            filepath + ":57: ')' is preceeded by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testImport()
        throws Exception
    {
        mConfig.setIgnoreImportLength(true);
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

    public void testPackageHtml()
        throws Exception
    {
        mConfig.setRequirePackageHtml(true);
        mConfig.setJavadocScope(Scope.PRIVATE);
        final Checker c = createChecker();
        final String packageHtmlPath = getPath("package.html");
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
            packageHtmlPath + ":0: missing package documentation file.",
            filepath + ":37: '(' is followed by whitespace.",
            filepath + ":39: '(' is followed by whitespace.",
            filepath + ":39: ')' is preceeded by whitespace.",
            filepath + ":43: ')' is preceeded by whitespace.",
            filepath + ":51: '(' is followed by whitespace.",
            filepath + ":53: '(' is followed by whitespace.",
            filepath + ":53: ')' is preceeded by whitespace.",
            filepath + ":57: ')' is preceeded by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testLCurlyMethodIgnore()
        throws Exception
    {
        mConfig.setLCurlyMethod(LeftCurlyOption.IGNORE);
        mConfig.setJavadocScope(Scope.NOTHING);
        final Checker c = createChecker();
        final String filepath = getPath("InputLeftCurlyMethod.java");
        assertNotNull(c);
        final String[] expected = {
        };
        verify(c, filepath, expected);
    }

    public void testLCurlyMethodNL()
        throws Exception
    {
        mConfig.setLCurlyMethod(LeftCurlyOption.NL);
        mConfig.setJavadocScope(Scope.NOTHING);
        final Checker c = createChecker();
        final String filepath = getPath("InputLeftCurlyMethod.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":14: '{' should be on a new line.",
            filepath + ":21: '{' should be on a new line.",
            filepath + ":34: '{' should be on a new line.",
        };
        verify(c, filepath, expected);
    }

    public void testLCurlyOther()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.NOTHING);
        mConfig.setRCurly(RightCurlyOption.SAME);
        final Checker c = createChecker();
        final String filepath = getPath("InputLeftCurlyOther.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":19: '{' should be on the previous line.",
            filepath + ":21: '{' should be on the previous line.",
            filepath + ":23: '{' should be on the previous line.",
            filepath + ":25: '}' should be on the same line.",
            filepath + ":28: '}' should be on the same line.",
            filepath + ":30: '{' should be on the previous line.",
            filepath + ":34: '{' should be on the previous line.",
            filepath + ":40: '}' should be on the same line.",
            filepath + ":42: '{' should be on the previous line.",
            filepath + ":44: '}' should be on the same line.",
            filepath + ":46: '{' should be on the previous line.",
            filepath + ":52: '{' should be on the previous line.",
            filepath + ":54: '{' should be on the previous line.",
        };
        verify(c, filepath, expected);
    }

    public void testAssertIdentifier()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.NOTHING);
        final Checker c = createChecker();
        final String filepath = getPath("InputAssertIdentifier.java");
        assertNotNull(c);
        final String[] expected = {
        };
        verify(c, filepath, expected);
    }
}
