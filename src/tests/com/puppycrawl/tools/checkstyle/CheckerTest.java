package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;
import org.apache.regexp.RESyntaxException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;
import java.lang.reflect.InvocationTargetException;

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
        mProps.setProperty(Defn.ALLOW_NO_AUTHOR_PROP, Boolean.TRUE.toString());
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
        throws RESyntaxException, FileNotFoundException, IOException,
               ParserConfigurationException, SAXException,
               ClassNotFoundException, InstantiationException,
               IllegalAccessException, InvocationTargetException,
               NoSuchMethodException
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
        mProps.setProperty(Defn.ALLOW_NO_AUTHOR_PROP, Boolean.FALSE.toString());

        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.IGNORE.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.IGNORE.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":5:12: '.' is preceeded with whitespace.",
            filepath + ":5:14: '.' is followed by whitespace.",
            filepath + ":13: Type Javadoc comment is missing an @author tag.",
            filepath + ":29:14: '-' is followed by whitespace.",
            filepath + ":29:21: '+' is followed by whitespace.",
            filepath + ":30:14: '++' is preceeded with whitespace.",
            filepath + ":30:21: '--' is preceeded with whitespace.",
            filepath + ":31:15: '++' is followed by whitespace.",
            filepath + ":31:22: '--' is followed by whitespace.",
            filepath + ":88:21: 'cast' is not followed by whitespace.",
            filepath + ":111:22: '!' is followed by whitespace.",
            filepath + ":112:23: '~' is followed by whitespace.",
            filepath + ":129:17: '.' is preceeded with whitespace.",
            filepath + ":129:24: '.' is followed by whitespace.",
            filepath + ":136:10: '.' is preceeded with whitespace.",
            filepath + ":136:12: '.' is followed by whitespace.",
        };
        verify(c, filepath, expected);
        c.destroy();
    }

    public void testWhitespaceCastParenOff()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_CAST_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.ALLOW_NO_AUTHOR_PROP, "false");
        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.IGNORE.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.IGNORE.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":5:12: '.' is preceeded with whitespace.",
            filepath + ":5:14: '.' is followed by whitespace.",
            filepath + ":13: Type Javadoc comment is missing an @author tag.",
            filepath + ":29:14: '-' is followed by whitespace.",
            filepath + ":29:21: '+' is followed by whitespace.",
            filepath + ":30:14: '++' is preceeded with whitespace.",
            filepath + ":30:21: '--' is preceeded with whitespace.",
            filepath + ":31:15: '++' is followed by whitespace.",
            filepath + ":31:22: '--' is followed by whitespace.",
            filepath + ":111:22: '!' is followed by whitespace.",
            filepath + ":112:23: '~' is followed by whitespace.",
            filepath + ":129:17: '.' is preceeded with whitespace.",
            filepath + ":129:24: '.' is followed by whitespace.",
            filepath + ":136:10: '.' is preceeded with whitespace.",
            filepath + ":136:12: '.' is followed by whitespace.",
        };
        verify(c, filepath, expected);
    }

    public void testWhitespaceOff()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.ALLOW_NO_AUTHOR_PROP, "false");
        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.IGNORE.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.IGNORE.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputWhitespace.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":13: Type Javadoc comment is missing an @author tag.",
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
            filepath + ":42: 'while' construct must use '{}'s.",
            filepath + ":44: 'while' construct must use '{}'s.",
            filepath + ":45: 'if' construct must use '{}'s.",
            filepath + ":58: 'for' construct must use '{}'s.",
            filepath + ":58:23: ';' is not followed by whitespace.",
            filepath + ":58:29: ';' is not followed by whitespace.",
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
        mProps.setProperty(Defn.IGNORE_BRACES_PROP, "true");
        final Checker c = createChecker();
        final String filepath = getPath("InputBraces.java");
        final String[] expected = {
            filepath + ":58:23: ';' is not followed by whitespace.",
            filepath + ":58:29: ';' is not followed by whitespace.",
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
            filepath + ":8: Missing a Javadoc comment.",
            filepath + ":11:17: Missing a Javadoc comment.",
            filepath + ":14:5: Missing a Javadoc comment.",
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
            filepath + ":178: Unused @throws tag for 'ThreadDeath'.",
            filepath + ":179: Unused @throws tag for 'ArrayStoreException'.",
        };

        verify(c, filepath, expected);
    }

    public void testTagsWithResolver()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECK_UNUSED_THROWS_PROP, "true");
        final Checker c = createChecker();
        final String filepath = getPath("InputTags.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":8: Missing a Javadoc comment.",
            filepath + ":11:17: Missing a Javadoc comment.",
            filepath + ":14:5: Missing a Javadoc comment.",
            filepath + ":18: Unused @param tag for 'unused'.",
            filepath + ":24: Expected an @return tag.",
            filepath + ":33: Expected an @return tag.",
            filepath + ":40:16: Expected @throws tag for 'Exception'.",
            filepath + ":49:16: Expected @throws tag for 'Exception'.",
            filepath + ":53: Unable to get class information for @throws tag 'WrongException'.",
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
            filepath + ":14: Missing a Javadoc comment.",
            filepath + ":17:20: Missing a Javadoc comment.",
            filepath + ":21: Missing a Javadoc comment.",
            filepath + ":24:16: Missing a Javadoc comment.",
            filepath + ":24:16: Name 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":27: Missing a Javadoc comment.",
            filepath + ":30:24: Missing a Javadoc comment.",
        };
        verify(c, filepath, expected);
    }

    public void testIgnoreAccess()
        throws Exception
    {
        mProps.setProperty(Defn.PUBLIC_MEMBER_PATTERN_PROP, "^r[A-Z]");
        mProps.setProperty(Defn.ALLOW_PROTECTED_PROP, "true");
        mProps.setProperty(Defn.ALLOW_PACKAGE_PROP, "true");
        final Checker c = createChecker();
        final String filepath = getPath("InputInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":14: Missing a Javadoc comment.",
            filepath + ":17:20: Missing a Javadoc comment.",
            filepath + ":21: Missing a Javadoc comment.",
            filepath + ":24:16: Missing a Javadoc comment.",
            filepath + ":24:16: Name 'data' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":27: Missing a Javadoc comment.",
            filepath + ":30:24: Missing a Javadoc comment.",
        };
        verify(c, filepath, expected);
    }

    public void testSimple()
        throws Exception
    {
        mProps.setProperty(Defn.MAX_METHOD_LENGTH_PROP, "19");
        mProps.setProperty(Defn.MAX_CONSTRUCTOR_LENGTH_PROP, "9");
        mProps.setProperty(Defn.STATIC_PATTERN_PROP, "^s[A-Z][a-zA-Z0-9]*$");
        mProps.setProperty(Defn.MEMBER_PATTERN_PROP, "^m[A-Z][a-zA-Z0-9]*$");
        mProps.setProperty(Defn.IGNORE_LINE_LENGTH_PATTERN_PROP,
                           "^.*is OK.*regexp.*$");
        mProps.setProperty(Defn.TODO_PATTERN_PROP, "FIXME:");
        mProps.setProperty(Defn.MEMBER_PATTERN_PROP, "^m[A-Z][a-zA-Z0-9]*$");
        mProps.setProperty(Defn.LOCAL_FINAL_VAR_PATTERN_PROP, "[A-Z]+");
        final Checker c = createChecker();
        final String filepath = getPath("InputSimple.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":18: Line is longer than 80 characters.",
            filepath + ":25:29: Name 'badConstant' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":30:24: Name 'badStatic' must match pattern '^s[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":35:17: Name 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
            filepath + ":42:40: ',' is not followed by whitespace.",
            filepath + ":71:30: ',' is not followed by whitespace.",
            filepath + ":80: Method length is 20 lines (max allowed is 19).",
            filepath + ":103: Constructor length is 10 lines (max allowed is 9).",
            filepath + ":119:13: Name 'ABC' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":122:19: Name 'cde' must match pattern '[A-Z]+'.",
            filepath + ":130:18: Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":132:20: Name 'InnerBlockVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            filepath + ":142:30: Name 'BAD__NAME' must match pattern '^[A-Z](_?[A-Z0-9]+)*$'.",
            filepath + ":145: Line is longer than 80 characters.",
            filepath + ":161: Comment matches to-do format 'FIXME:'.",
            filepath + ":162: Comment matches to-do format 'FIXME:'.",
            filepath + ":163: Comment matches to-do format 'FIXME:'.",
            filepath + ":167: Comment matches to-do format 'FIXME:'.",
        };
        verify(c, filepath, expected);
    }

    public void testStrictJavadoc()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_WHITESPACE_PROP, "true");
        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: Missing a Javadoc comment.",
            filepath + ":9: Missing a Javadoc comment.",
            filepath + ":11:16: Missing a Javadoc comment.",
            filepath + ":12:9: Missing a Javadoc comment.",
            filepath + ":14: Missing a Javadoc comment.",
            filepath + ":16:25: Missing a Javadoc comment.",
            filepath + ":18:13: Missing a Javadoc comment.",
            filepath + ":25:13: Missing a Javadoc comment.",
            filepath + ":34: Missing a Javadoc comment.",
            filepath + ":36:21: Missing a Javadoc comment.",
            filepath + ":38:9: Missing a Javadoc comment.",
            filepath + ":43:17: Missing a Javadoc comment.",
            filepath + ":44:9: Missing a Javadoc comment.",
            filepath + ":45:19: Missing a Javadoc comment.",
            filepath + ":46:16: Missing a Javadoc comment.",
            filepath + ":49:5: Missing a Javadoc comment.",
            filepath + ":54:5: Missing a Javadoc comment.",
            filepath + ":59:5: Missing a Javadoc comment.",
            filepath + ":64:5: Missing a Javadoc comment.",
            filepath + ":69:5: Missing a Javadoc comment.",
            filepath + ":74:5: Missing a Javadoc comment.",
            filepath + ":79:5: Missing a Javadoc comment.",
            filepath + ":84:5: Missing a Javadoc comment.",
            filepath + ":94:32: Expected @param tag for 'aA'."
        };
        verify(c, filepath, expected);
    }

    public void testNoJavadoc()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
        };
        verify(c, filepath, expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    public void testRelaxedJavadoc()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP,
                           Scope.PROTECTED.getName());

        final Checker c = createChecker();
        final String filepath = getPath("InputPublicOnly.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: Missing a Javadoc comment.",
            filepath + ":45:19: Missing a Javadoc comment.",
            filepath + ":46:16: Missing a Javadoc comment.",
            filepath + ":59:5: Missing a Javadoc comment.",
            filepath + ":64:5: Missing a Javadoc comment.",
            filepath + ":79:5: Missing a Javadoc comment.",
            filepath + ":84:5: Missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }


    public void testScopeInnerInterfacesPublic()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.PUBLIC.getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerInterfaces.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":7: Missing a Javadoc comment.",
            filepath + ":38: Missing a Javadoc comment.",
            filepath + ":40:23: Missing a Javadoc comment.",
            filepath + ":41:16: Missing a Javadoc comment.",
            filepath + ":43:9: Missing a Javadoc comment.",
            filepath + ":44:9: Missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testScopeInnerClassesPackage()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP,
                           Scope.getInstance("package").getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerClasses.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":18: Missing a Javadoc comment.",
            filepath + ":20: Missing a Javadoc comment.",
            filepath + ":22: Missing a Javadoc comment."
        };
        verify(c, filepath, expected);
    }

    public void testScopeInnerClassesPublic()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.PUBLIC.getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeInnerClasses.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":18: Missing a Javadoc comment.",
        };
        verify(c, filepath, expected);
    }

    public void testScopeAnonInnerPrivate()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.PRIVATE.getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
        };
        verify(c, filepath, expected);
    }

    public void testScopeAnonInnerAnonInner()
        throws Exception
    {
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP,
                           Scope.ANONINNER.getName());
        final Checker c = createChecker();
        final String filepath = getPath("InputScopeAnonInner.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":26:9: Missing a Javadoc comment.",
            filepath + ":39:17: Missing a Javadoc comment.",
            filepath + ":53:17: Missing a Javadoc comment.",
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

    public void testSemantic()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.STMT.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.STMT.toString());
        mProps.setProperty(Defn.FINALLY_BLOCK_PROP, BlockOption.STMT.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputSemantic.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":51:65: Must have at least one statement.",
            filepath + ":53:41: Must have at least one statement.",
            filepath + ":70:38: Must have at least one statement.",
            filepath + ":71:52: Must have at least one statement.",
            filepath + ":72:45: Must have at least one statement.",
            filepath + ":74:13: Must have at least one statement.",
            filepath + ":76:17: Must have at least one statement.",
            filepath + ":78:13: Must have at least one statement.",
            filepath + ":81:17: Must have at least one statement.",
        };
        verify(c, filepath, expected);
    }

    public void testSemantic2()
        throws Exception
    {
        mProps.setProperty(Defn.IGNORE_WHITESPACE_PROP, "true");
        mProps.setProperty(Defn.JAVADOC_CHECKSCOPE_PROP, Scope.NOTHING.getName());
        mProps.setProperty(Defn.TRY_BLOCK_PROP, BlockOption.TEXT.toString());
        mProps.setProperty(Defn.CATCH_BLOCK_PROP, BlockOption.TEXT.toString());
        mProps.setProperty(Defn.FINALLY_BLOCK_PROP, BlockOption.TEXT.toString());
        final Checker c = createChecker();
        final String filepath = getPath("InputSemantic.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":51:65: Empty catch block.",
            filepath + ":71:52: Empty catch block.",
            filepath + ":72:45: Empty catch block.",
            filepath + ":74:13: Empty try block.",
            filepath + ":76:17: Empty finally block.",
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

    public void testNoAuthor()
        throws Exception
    {
        mProps.setProperty(Defn.WRAP_OP_PROP, WrapOpOption.NL.toString());
        mProps.setProperty(Defn.ALLOW_NO_AUTHOR_PROP, "false");
        final Checker c = createChecker();
        final String filepath = getPath("InputJavadoc.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":11: Type Javadoc comment is missing an @author tag."
        };
        verify(c, filepath, expected);
    }

    public void testNoVersion()
        throws Exception
    {
        mProps.setProperty(Defn.WRAP_OP_PROP, WrapOpOption.NL.toString());
        mProps.setProperty(Defn.ALLOW_NO_AUTHOR_PROP, "true");
        mProps.setProperty(Defn.REQUIRE_VERSION_PROP, "true");
        final Checker c = createChecker();
        final String filepath = getPath("InputJavadoc.java");
        assertNotNull(c);
        final String[] expected = {
            filepath + ":11: Type Javadoc comment is missing an @version tag."
        };
        verify(c, filepath, expected);
    }
}
