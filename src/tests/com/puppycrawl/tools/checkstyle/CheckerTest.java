package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
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
        aC.destroy();
    }


    public void testWhitespace()
        throws Exception
    {
        mConfig.setIgnoreCastWhitespace(false);
        mConfig.setParenPadOption(PadOption.NOSPACE);
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":5:12: '.' is preceeded with whitespace.",
            filepath + ":5:14: '.' is followed by whitespace.",
            filepath + ":13: type Javadoc comment is missing an @author tag.",
            filepath + ":16:22: '=' is not preceeded with whitespace.",
            filepath + ":16:23: '=' is not followed by whitespace.",
            filepath + ":18:24: '=' is not followed by whitespace.",
            filepath + ":26:14: '=' is not preceeded with whitespace.",
            filepath + ":27:10: '=' is not preceeded with whitespace.",
            filepath + ":27:11: '=' is not followed by whitespace.",
            filepath + ":28:10: '+=' is not preceeded with whitespace.",
            filepath + ":28:12: '+=' is not followed by whitespace.",
            filepath + ":29:13: '-=' is not followed by whitespace.",
            filepath + ":29:14: '-' is followed by whitespace.",
            filepath + ":29:21: '+' is followed by whitespace.",
            filepath + ":30:14: '++' is preceeded with whitespace.",
            filepath + ":30:21: '--' is preceeded with whitespace.",
            filepath + ":31:15: '++' is followed by whitespace.",
            filepath + ":31:22: '--' is followed by whitespace.",
            filepath + ":37:21: 'synchronized' is not followed by whitespace.",
            filepath + ":39:12: 'try' is not followed by whitespace.",
            filepath + ":41:14: 'catch' is not followed by whitespace.",
            filepath + ":58:11: 'if' is not followed by whitespace.",
            filepath + ":58:12: '(' is followed by whitespace.",
            filepath + ":58:36: ')' is preceeded by whitespace.",
            filepath + ":59:9: '{' should be on the previous line.",
            filepath + ":63:9: '{' should be on the previous line.",
            filepath + ":74:13: '(' is followed by whitespace.",
            filepath + ":74:18: ')' is preceeded by whitespace.",
            filepath + ":75:9: '{' should be on the previous line.",
            filepath + ":76:19: 'return' is not followed by whitespace.",
            filepath + ":79:9: '{' should be on the previous line.",
            filepath + ":88:21: cast needs to be followed by whitespace.",
            filepath + ":97:29: '?' is not preceeded with whitespace.",
            filepath + ":97:30: '?' is not followed by whitespace.",
            filepath + ":97:34: ':' is not preceeded with whitespace.",
            filepath + ":97:35: ':' is not followed by whitespace.",
            filepath + ":98:15: '==' is not preceeded with whitespace.",
            filepath + ":98:17: '==' is not followed by whitespace.",
            filepath + ":104:20: '*' is not followed by whitespace.",
            filepath + ":104:21: '*' is not preceeded with whitespace.",
            filepath + ":111:22: '!' is followed by whitespace.",
            filepath + ":112:23: '~' is followed by whitespace.",
            filepath + ":119:18: '%' is not preceeded with whitespace.",
            filepath + ":120:20: '%' is not followed by whitespace.",
            filepath + ":121:18: '%' is not preceeded with whitespace.",
            filepath + ":121:19: '%' is not followed by whitespace.",
            filepath + ":123:18: '/' is not preceeded with whitespace.",
            filepath + ":124:20: '/' is not followed by whitespace.",
            filepath + ":125:18: '/' is not preceeded with whitespace.",
            filepath + ":125:19: '/' is not followed by whitespace.",
            filepath + ":129:17: '.' is preceeded with whitespace.",
            filepath + ":129:24: '.' is followed by whitespace.",
            filepath + ":136:10: '.' is preceeded with whitespace.",
            filepath + ":136:12: '.' is followed by whitespace.",
            filepath + ":153:15: 'assert' is not followed by whitespace.",
            filepath + ":156:20: ':' is not preceeded with whitespace.",
            filepath + ":156:21: ':' is not followed by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testWhitespaceCastParenOff()
        throws Exception
    {
        mConfig.setIgnoreCastWhitespace(true);
        mConfig.setParenPadOption(PadOption.IGNORE);
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":5:12: '.' is preceeded with whitespace.",
            filepath + ":5:14: '.' is followed by whitespace.",
            filepath + ":13: type Javadoc comment is missing an @author tag.",
            filepath + ":16:22: '=' is not preceeded with whitespace.",
            filepath + ":16:23: '=' is not followed by whitespace.",
            filepath + ":18:24: '=' is not followed by whitespace.",
            filepath + ":26:14: '=' is not preceeded with whitespace.",
            filepath + ":27:10: '=' is not preceeded with whitespace.",
            filepath + ":27:11: '=' is not followed by whitespace.",
            filepath + ":28:10: '+=' is not preceeded with whitespace.",
            filepath + ":28:12: '+=' is not followed by whitespace.",
            filepath + ":29:13: '-=' is not followed by whitespace.",
            filepath + ":29:14: '-' is followed by whitespace.",
            filepath + ":29:21: '+' is followed by whitespace.",
            filepath + ":30:14: '++' is preceeded with whitespace.",
            filepath + ":30:21: '--' is preceeded with whitespace.",
            filepath + ":31:15: '++' is followed by whitespace.",
            filepath + ":31:22: '--' is followed by whitespace.",
            filepath + ":37:21: 'synchronized' is not followed by whitespace.",
            filepath + ":39:12: 'try' is not followed by whitespace.",
            filepath + ":41:14: 'catch' is not followed by whitespace.",
            filepath + ":58:11: 'if' is not followed by whitespace.",
            filepath + ":59:9: '{' should be on the previous line.",
            filepath + ":63:9: '{' should be on the previous line.",
            filepath + ":75:9: '{' should be on the previous line.",
            filepath + ":76:19: 'return' is not followed by whitespace.",
            filepath + ":79:9: '{' should be on the previous line.",
            filepath + ":97:29: '?' is not preceeded with whitespace.",
            filepath + ":97:30: '?' is not followed by whitespace.",
            filepath + ":97:34: ':' is not preceeded with whitespace.",
            filepath + ":97:35: ':' is not followed by whitespace.",
            filepath + ":98:15: '==' is not preceeded with whitespace.",
            filepath + ":98:17: '==' is not followed by whitespace.",
            filepath + ":104:20: '*' is not followed by whitespace.",
            filepath + ":104:21: '*' is not preceeded with whitespace.",
            filepath + ":111:22: '!' is followed by whitespace.",
            filepath + ":112:23: '~' is followed by whitespace.",
            filepath + ":119:18: '%' is not preceeded with whitespace.",
            filepath + ":120:20: '%' is not followed by whitespace.",
            filepath + ":121:18: '%' is not preceeded with whitespace.",
            filepath + ":121:19: '%' is not followed by whitespace.",
            filepath + ":123:18: '/' is not preceeded with whitespace.",
            filepath + ":124:20: '/' is not followed by whitespace.",
            filepath + ":125:18: '/' is not preceeded with whitespace.",
            filepath + ":125:19: '/' is not followed by whitespace.",
            filepath + ":129:17: '.' is preceeded with whitespace.",
            filepath + ":129:24: '.' is followed by whitespace.",
            filepath + ":136:10: '.' is preceeded with whitespace.",
            filepath + ":136:12: '.' is followed by whitespace.",
            filepath + ":153:15: 'assert' is not followed by whitespace.",
            filepath + ":156:20: ':' is not preceeded with whitespace.",
            filepath + ":156:21: ':' is not followed by whitespace.",
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
            filepath + ":59:9: '{' should be on the previous line.",
            filepath + ":63:9: '{' should be on the previous line.",
            filepath + ":75:9: '{' should be on the previous line.",
            filepath + ":79:9: '{' should be on the previous line.",
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
            filepath + ":41:14: 'while' is not followed by whitespace.",
            filepath + ":42: 'while' construct must use '{}'s.",
            filepath + ":44: 'while' construct must use '{}'s.",
            filepath + ":45: 'if' construct must use '{}'s.",
            filepath + ":58: 'for' construct must use '{}'s.",
            filepath + ":58:12: 'for' is not followed by whitespace.",
            filepath + ":58:23: ';' needs to be followed by whitespace.",
            filepath + ":58:29: ';' needs to be followed by whitespace.",
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
            filepath + ":41:14: 'while' is not followed by whitespace.",
            filepath + ":58:12: 'for' is not followed by whitespace.",
            filepath + ":58:23: ';' needs to be followed by whitespace.",
            filepath + ":58:29: ';' needs to be followed by whitespace.",
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
            filepath + ":11:17: variable 'mMissingJavadoc' missing Javadoc.",
            filepath + ":14:5: method is missing a Javadoc comment.",
            filepath + ":18: Unused @param tag for 'unused'.",
            filepath + ":24: Expected an @return tag.",
            filepath + ":33: Expected an @return tag.",
            filepath + ":40:16: Expected @throws tag for 'Exception'.",
            filepath + ":49:16: Expected @throws tag for 'Exception'.",
            filepath + ":53: Unused @throws tag for 'WrongException'.",
            filepath + ":55:16: Expected @throws tag for 'Exception'.",
            filepath + ":55:27: Expected @throws tag for 'NullPointerException'.",
            filepath + ":60:22: Expected @param tag for 'aOne'.",
            filepath + ":68:22: Expected @param tag for 'aOne'.",
            filepath + ":72: Unused @param tag for 'WrongParam'.",
            filepath + ":73:23: Expected @param tag for 'aOne'.",
            filepath + ":73:33: Expected @param tag for 'aTwo'.",
            filepath + ":78: Unused @param tag for 'Unneeded'.",
            filepath + ":79: Unused Javadoc tag.",
            filepath + ":87: Duplicate @return tag.",
            filepath + ":109:23: Expected @param tag for 'aOne'.",
            filepath + ":109:55: Expected @param tag for 'aFour'.",
            filepath + ":109:66: Expected @param tag for 'aFive'.",
            filepath + ":129:5: '{' should be on the previous line.",
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
            filepath + ":17:20: variable 'fData' missing Javadoc.",
            filepath + ":21: type is missing a Javadoc comment.",
            filepath + ":24:16: variable 'data' missing Javadoc.",
            filepath + ":24:16: variable 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":27: type is missing a Javadoc comment.",
            filepath + ":30:24: variable 'rData' missing Javadoc.",
            filepath + ":30:24: variable 'rData' must be private and have accessor methods.",
            filepath + ":33:27: variable 'protectedVariable' must be private and have accessor methods.",
            filepath + ":36:17: variable 'packageVariable' must be private and have accessor methods.",
            filepath + ":41:29: variable 'sWeird' must be private and have accessor methods.",
            filepath + ":43:19: variable 'sWeird2' must be private and have accessor methods.",
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
            filepath + ":17:20: variable 'fData' missing Javadoc.",
            filepath + ":17:20: variable 'fData' must be private and have accessor methods.",
            filepath + ":21: type is missing a Javadoc comment.",
            filepath + ":24:16: variable 'data' missing Javadoc.",
            filepath + ":24:16: variable 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":27: type is missing a Javadoc comment.",
            filepath + ":30:24: variable 'rData' missing Javadoc.",
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
        mConfig.setIgnoreLineLengthPat("^.*is OK.*regexp.*$");
        final Checker c = createChecker();
        final String filepath = getPath("InputSimple.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":1: file length is 146 lines (max allowed is 20).",
            filepath + ":3: Line does not match expected header line of '// Created: 2001'.",
            filepath + ":18: line longer than 80 characters",
            filepath + ":19:25: line contains a tab character",
            filepath + ":25:29: variable 'badConstant' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":30:24: variable 'badStatic' must match pattern '^s[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":35:17: variable 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":39:19: variable 'mNumCreated2' must be private and have accessor methods.",
            filepath + ":42:40: ',' needs to be followed by whitespace.",
            filepath + ":49:23: variable 'sTest1' must be private and have accessor methods.",
            filepath + ":51:26: variable 'sTest3' must be private and have accessor methods.",
            filepath + ":53:16: variable 'sTest2' must be private and have accessor methods.",
            filepath + ":56:9: variable 'mTest1' must be private and have accessor methods.",
            filepath + ":58:16: variable 'mTest2' must be private and have accessor methods.",
            filepath + ":71:19: parameter 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":71:30: ',' needs to be followed by whitespace.",
            filepath + ":71:34: parameter 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":72:25: parameter 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":80: method length is 20 lines (max allowed is 19).",
            filepath + ":103: constructor length is 10 lines (max allowed is 9).",
            filepath + ":119:13: variable 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":123:19: variable 'CDE' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":127:9: '{' should be on the previous line.",
            filepath + ":130:18: variable 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":131:9: '{' should be on the previous line.",
            filepath + ":132:20: variable 'InnerBlockVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":137:10: method name 'ALL_UPPERCASE_METHOD' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":142:30: variable 'BAD__NAME' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
        };
        verify(c, filepath, expected);
    }

    public void testModifierChecks()
        throws Exception
    {
        final Checker c = createChecker();
        final String filepath = getPath("InputModifier.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":14:10: 'final' modifier out of order with the JLS suggestions.",
            filepath + ":18:12: 'private' modifier out of order with the JLS suggestions.",
            filepath + ":24:14: 'private' modifier out of order with the JLS suggestions.",
            filepath + ":32:9: redundant 'public' modifier.",
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
            filepath + ":11:16: variable 'CONST' missing Javadoc.",
            filepath + ":12:9: method is missing a Javadoc comment.",
            filepath + ":14: type is missing a Javadoc comment.",
            filepath + ":16:25: variable 'mData' missing Javadoc.",
            filepath + ":18:13: method is missing a Javadoc comment.",
            filepath + ":25:13: method is missing a Javadoc comment.",
            filepath + ":34: type is missing a Javadoc comment.",
            filepath + ":36:21: variable 'mDiff' missing Javadoc.",
            filepath + ":38:9: method is missing a Javadoc comment.",
            filepath + ":43:17: variable 'mSize' missing Javadoc.",
            filepath + ":44:9: variable 'mLen' missing Javadoc.",
            filepath + ":44:9: variable 'mLen' must be private and have accessor methods.",
            filepath + ":45:19: variable 'mDeer' missing Javadoc.",
            filepath + ":45:19: variable 'mDeer' must be private and have accessor methods.",
            filepath + ":46:16: variable 'aFreddo' missing Javadoc.",
            filepath + ":46:16: variable 'aFreddo' must be private and have accessor methods.",
            filepath + ":49:5: method is missing a Javadoc comment.",
            filepath + ":54:5: method is missing a Javadoc comment.",
            filepath + ":59:5: method is missing a Javadoc comment.",
            filepath + ":64:5: method is missing a Javadoc comment.",
            filepath + ":69:5: method is missing a Javadoc comment.",
            filepath + ":74:5: method is missing a Javadoc comment.",
            filepath + ":79:5: method is missing a Javadoc comment.",
            filepath + ":84:5: method is missing a Javadoc comment.",
            filepath + ":94:32: Expected @param tag for 'aA'."
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
            filepath + ":44:9: variable 'mLen' must be private and have accessor methods.",
            filepath + ":45:19: variable 'mDeer' must be private and have accessor methods.",
            filepath + ":46:16: variable 'aFreddo' must be private and have accessor methods.",
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
            filepath + ":44:9: variable 'mLen' must be private and have accessor methods.",
            filepath + ":45:19: variable 'mDeer' missing Javadoc.",
            filepath + ":45:19: variable 'mDeer' must be private and have accessor methods.",
            filepath + ":46:16: variable 'aFreddo' missing Javadoc.",
            filepath + ":46:16: variable 'aFreddo' must be private and have accessor methods.",
            filepath + ":59:5: method is missing a Javadoc comment.",
            filepath + ":64:5: method is missing a Javadoc comment.",
            filepath + ":79:5: method is missing a Javadoc comment.",
            filepath + ":84:5: method is missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }


    public void testScopeInnerInterfacesPublic()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.PUBLIC);
        mConfig.setIgnorePublicInInterface(true);
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerInterfaces.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: type is missing a Javadoc comment.",
            filepath + ":38: type is missing a Javadoc comment.",
            filepath + ":40:23: variable 'CA' missing Javadoc.",
            filepath + ":41:16: variable 'CB' missing Javadoc.",
            filepath + ":43:9: method is missing a Javadoc comment.",
            filepath + ":44:9: method is missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testScopeInnerClassesPackage()
        throws Exception
    {
        mConfig.setJavadocScope(Scope.getInstance("package"));
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
            filepath + ":37:34: '(' is followed by whitespace.",
            filepath + ":39:42: '(' is followed by whitespace.",
            filepath + ":39:57: ')' is preceeded by whitespace.",
            filepath + ":43:14: ')' is preceeded by whitespace.",
            filepath + ":51:34: '(' is followed by whitespace.",
            filepath + ":53:42: '(' is followed by whitespace.",
            filepath + ":53:57: ')' is preceeded by whitespace.",
            filepath + ":57:14: ')' is preceeded by whitespace.",
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
            filepath + ":26:9: method is missing a Javadoc comment.",
            filepath + ":37:34: '(' is followed by whitespace.",
            filepath + ":39:17: method is missing a Javadoc comment.",
            filepath + ":39:42: '(' is followed by whitespace.",
            filepath + ":39:57: ')' is preceeded by whitespace.",
            filepath + ":43:14: ')' is preceeded by whitespace.",
            filepath + ":51:34: '(' is followed by whitespace.",
            filepath + ":53:17: method is missing a Javadoc comment.",
            filepath + ":53:42: '(' is followed by whitespace.",
            filepath + ":53:57: ')' is preceeded by whitespace.",
            filepath + ":57:14: ')' is preceeded by whitespace.",
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
            filepath + ":1: type is missing a Javadoc comment.",
            filepath + ":1:48: type name 'inputHeader' must match pattern '^[A-Z][a-zA-Z0-9]*$'.",
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
            filepath + ":37:34: '(' is followed by whitespace.",
            filepath + ":39:42: '(' is followed by whitespace.",
            filepath + ":39:57: ')' is preceeded by whitespace.",
            filepath + ":43:14: ')' is preceeded by whitespace.",
            filepath + ":51:34: '(' is followed by whitespace.",
            filepath + ":53:42: '(' is followed by whitespace.",
            filepath + ":53:57: ')' is preceeded by whitespace.",
            filepath + ":57:14: ')' is preceeded by whitespace.",
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
            filepath + ":15: Import from illegal package - sun.net.ftpclient.FtpClient",
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
            filepath + ":37:34: '(' is followed by whitespace.",
            filepath + ":39:42: '(' is followed by whitespace.",
            filepath + ":39:57: ')' is preceeded by whitespace.",
            filepath + ":43:14: ')' is preceeded by whitespace.",
            filepath + ":51:34: '(' is followed by whitespace.",
            filepath + ":53:42: '(' is followed by whitespace.",
            filepath + ":53:57: ')' is preceeded by whitespace.",
            filepath + ":57:14: ')' is preceeded by whitespace.",
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
            filepath + ":14:39: '{' should be on a new line.",
            filepath + ":21:20: '{' should be on a new line.",
            filepath + ":34:31: '{' should be on a new line.",
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
            filepath + ":19:9: '{' should be on the previous line.",
            filepath + ":21:13: '{' should be on the previous line.",
            filepath + ":23:17: '{' should be on the previous line.",
            filepath + ":25:17: '}' should be on the same line.",
            filepath + ":28:17: '}' should be on the same line.",
            filepath + ":30:17: '{' should be on the previous line.",
            filepath + ":34:17: '{' should be on the previous line.",
            filepath + ":40:13: '}' should be on the same line.",
            filepath + ":42:13: '{' should be on the previous line.",
            filepath + ":44:13: '}' should be on the same line.",
            filepath + ":46:13: '{' should be on the previous line.",
            filepath + ":52:9: '{' should be on the previous line.",
            filepath + ":54:13: '{' should be on the previous line.",
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
