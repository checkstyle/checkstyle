package com.puppycrawl.tools.checkstyle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import junit.framework.TestCase;
import java.util.Properties;

public class CheckerTest
    extends TestCase
{
    private final ByteArrayOutputStream mBAOS = new ByteArrayOutputStream();
    private final PrintStream mStream = new PrintStream(mBAOS);
    private final Properties mProps = new Properties();

    public CheckerTest(String name)
    {
        super(name);
    }

    protected void setUp()
    {
        mProps.setProperty(Checker.HEADER_FILE_PROP, "java.header");
    }

    private void verify(Checker aC, String aFilename, String[] aExpected)
        throws Exception
    {
        final int errs = aC.process(aFilename);

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
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputWhitespace.java:11: type Javadoc comment is missing an @author tag.",
            "InputWhitespace.java:14: '=' is not preceeded with whitespace.",
            "InputWhitespace.java:16: '=' is not proceeded with whitespace.",
            "InputWhitespace.java:24: '=' is not preceeded with whitespace.",
            "InputWhitespace.java:25: '=' is not preceeded with whitespace.",
            "InputWhitespace.java:26: '+=' is not preceeded with whitespace.",
            "InputWhitespace.java:27: '-' is proceeded with whitespace.",
            "InputWhitespace.java:27: '-=' is not proceeded with whitespace.",
            "InputWhitespace.java:28: '++' is preceeded with whitespace.",
            "InputWhitespace.java:29: '++' is proceeded with whitespace."
        };
        verify(c, "InputWhitespace.java", expected);
    }

    public void testBraces()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        final String[] expected = {
            "InputBraces.java:29: 'do' construct must use '{}'s.",
            "InputBraces.java:41: 'while' construct must use '{}'s.",
            "InputBraces.java:41: ';' is not preceeded with whitespace.",
            "InputBraces.java:42: 'while' construct must use '{}'s.",
            "InputBraces.java:44: 'while' construct must use '{}'s.",
            "InputBraces.java:45: 'if' construct must use '{}'s.",
            "InputBraces.java:58: 'for' construct must use '{}'s.",
            "InputBraces.java:58: ';' is not preceeded with whitespace.",
            "InputBraces.java:59: 'for' construct must use '{}'s.",
            "InputBraces.java:61: 'for' construct must use '{}'s.",
            "InputBraces.java:62: 'if' construct must use '{}'s.",
            "InputBraces.java:81: 'if' construct must use '{}'s.",
            "InputBraces.java:81: ';' is not preceeded with whitespace.",
            "InputBraces.java:82: 'if' construct must use '{}'s.",
            "InputBraces.java:84: 'if' construct must use '{}'s.",
            "InputBraces.java:84: 'else' construct must use '{}'s.",
            "InputBraces.java:88: 'if' construct must use '{}'s.",
            "InputBraces.java:93: 'else' construct must use '{}'s.",
            "InputBraces.java:98: 'if' construct must use '{}'s.",
            "InputBraces.java:99: 'if' construct must use '{}'s."
        };
        verify(c, "InputBraces.java", expected);
    }

    public void testTags()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputTags.java:8: type is missing a Javadoc comment.",
            "InputTags.java:11: variable 'mMissingJavadoc' missing Javadoc.",
            "InputTags.java:14: method is missing a Javadoc comment.",
            "InputTags.java:18: Unused @param tag for 'unused'.",
            "InputTags.java:24: Expected an @return tag.",
            "InputTags.java:33: Expected an @return tag.",
            "InputTags.java:40: Expected @throws tag for 'Exception'.",
            "InputTags.java:49: Expected @throws tag for 'Exception'.",
            "InputTags.java:53: Unused @throws tag for 'WrongException'.",
            "InputTags.java:55: Expected @throws tag for 'Exception'.",
            "InputTags.java:55: Expected @throws tag for 'NullPointerException'.",
            "InputTags.java:60: Expected @param tag for 'aOne'.",
            "InputTags.java:68: Expected @param tag for 'aOne'.",
            "InputTags.java:72: Unused @param tag for 'WrongParam'.",
            "InputTags.java:73: Expected @param tag for 'aOne'.",
            "InputTags.java:73: Expected @param tag for 'aTwo'.",
            "InputTags.java:78: Unused @param tag for 'Unneeded'.",
            "InputTags.java:79: Unused Javadoc tag.",
            "InputTags.java:87: Duplicate @return tag."
        };

        verify(c, "InputTags.java", expected);
    }

    public void testInner()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputInner.java:14: type is missing a Javadoc comment.",
            "InputInner.java:17: variable 'data' missing Javadoc.",
            "InputInner.java:17: variable 'data' must be private and have accessor methods.",
            "InputInner.java:21: type is missing a Javadoc comment.",
            "InputInner.java:24: variable 'data' missing Javadoc.",
            "InputInner.java:24: variable 'data' must match pattern '^[A-Z]([A-Z0-9_]*[A-Z0-9])?$'.",
            "InputInner.java:27: type is missing a Javadoc comment.",
            "InputInner.java:30: variable 'data' missing Javadoc.",
            "InputInner.java:30: variable 'data' must be private and have accessor methods."
        };
        verify(c, "InputInner.java", expected);
    }

    public void testSimple()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputSimple.java:3: Line does not match expected header line of '// Created: 2001'.",
            "InputSimple.java:18: line longer than 80 characters",
            "InputSimple.java:19: line contains a tab character",
            "InputSimple.java:25: variable 'badConstant' must match pattern '^[A-Z]([A-Z0-9_]*[A-Z0-9])?$'.",
            "InputSimple.java:30: variable 'badStatic' must match pattern '^s[A-Z][a-zA-Z0-9]*$'.",
            "InputSimple.java:35: variable 'badMember' must match pattern '^m[A-Z][a-zA-Z0-9]*$'.",
            "InputSimple.java:39: variable 'mNumCreated2' must be private and have accessor methods.",
            "InputSimple.java:45: variable 'sTest1' must be private and have accessor methods.",
            "InputSimple.java:47: variable 'sTest3' must be private and have accessor methods.",
            "InputSimple.java:49: variable 'sTest2' must be private and have accessor methods.",
            "InputSimple.java:52: variable 'mTest1' must be private and have accessor methods.",
            "InputSimple.java:54: variable 'mTest2' must be private and have accessor methods.",
            "InputSimple.java:67: parameter 'badFormat1' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "InputSimple.java:68: parameter 'badFormat2' must match pattern '^a[A-Z][a-zA-Z0-9]*$'.",
            "InputSimple.java:69: parameter 'badFormat3' must match pattern '^a[A-Z][a-zA-Z0-9]*$'."
        };
        verify(c, "InputSimple.java", expected);
    }

    public void testStrictJavadoc()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputPublicOnly.java:7: type is missing a Javadoc comment.",
            "InputPublicOnly.java:9: type is missing a Javadoc comment.",
            "InputPublicOnly.java:11: variable 'CONST' missing Javadoc.",
            "InputPublicOnly.java:12: method is missing a Javadoc comment.",
            "InputPublicOnly.java:14: type is missing a Javadoc comment.",
            "InputPublicOnly.java:16: variable 'mData' missing Javadoc.",
            "InputPublicOnly.java:18: method is missing a Javadoc comment.",
            "InputPublicOnly.java:25: method is missing a Javadoc comment.",
            "InputPublicOnly.java:34: type is missing a Javadoc comment.",
            "InputPublicOnly.java:36: variable 'mDiff' missing Javadoc.",
            "InputPublicOnly.java:38: method is missing a Javadoc comment.",
            "InputPublicOnly.java:43: variable 'mSize' missing Javadoc.",
            "InputPublicOnly.java:44: variable 'mLen' missing Javadoc.",
            "InputPublicOnly.java:44: variable 'mLen' must be private and have accessor methods.",
            "InputPublicOnly.java:45: variable 'mDeer' missing Javadoc.",
            "InputPublicOnly.java:45: variable 'mDeer' must be private and have accessor methods.",
            "InputPublicOnly.java:46: variable 'aFreddo' missing Javadoc.",
            "InputPublicOnly.java:46: variable 'aFreddo' must be private and have accessor methods.",
            "InputPublicOnly.java:49: method is missing a Javadoc comment.",
            "InputPublicOnly.java:54: method is missing a Javadoc comment.",
            "InputPublicOnly.java:59: method is missing a Javadoc comment.",
            "InputPublicOnly.java:64: method is missing a Javadoc comment.",
            "InputPublicOnly.java:69: method is missing a Javadoc comment.",
            "InputPublicOnly.java:74: method is missing a Javadoc comment.",
            "InputPublicOnly.java:79: method is missing a Javadoc comment.",
            "InputPublicOnly.java:84: method is missing a Javadoc comment."
        };
        verify(c, "InputPublicOnly.java", expected);
    }

    public void testRelaxedJavadoc()
        throws Exception
    {
        mProps.setProperty(Checker.RELAX_JAVADOC_PROP, "on");
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputPublicOnly.java:7: type is missing a Javadoc comment.",
            "InputPublicOnly.java:9: type is missing a Javadoc comment.",
            "InputPublicOnly.java:11: variable 'CONST' missing Javadoc.",
            "InputPublicOnly.java:12: method is missing a Javadoc comment.",
            "InputPublicOnly.java:14: type is missing a Javadoc comment.",
            "InputPublicOnly.java:34: type is missing a Javadoc comment.",
            "InputPublicOnly.java:44: variable 'mLen' must be private and have accessor methods.",
            "InputPublicOnly.java:45: variable 'mDeer' missing Javadoc.",
            "InputPublicOnly.java:45: variable 'mDeer' must be private and have accessor methods.",
            "InputPublicOnly.java:46: variable 'aFreddo' missing Javadoc.",
            "InputPublicOnly.java:46: variable 'aFreddo' must be private and have accessor methods.",
            "InputPublicOnly.java:59: method is missing a Javadoc comment.",
            "InputPublicOnly.java:64: method is missing a Javadoc comment.",
            "InputPublicOnly.java:79: method is missing a Javadoc comment.",
            "InputPublicOnly.java:84: method is missing a Javadoc comment."
        };
        verify(c, "InputPublicOnly.java", expected);
    }

    public void testHeader()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "inputHeader.java:1: Missing a header - not enough lines in file.",
            "inputHeader.java:1: type is missing a Javadoc comment.",
            "inputHeader.java:1: type name 'inputHeader' must match pattern '^[A-Z][a-zA-Z0-9]*$'."
        };
        verify(c, "inputHeader.java", expected);
    }

    public void testImport()
        throws Exception
    {
        final Checker c = new Checker(mProps, mStream);
        assertNotNull(c);
        final String[] expected = {
            "InputImport.java:7: Unused import - java.util.List",
            "InputImport.java:8: Duplicate import to line 7.",
            "InputImport.java:8: Unused import - java.util.List",
            "InputImport.java:9: Avoid using the '.*' form of import.",
            "InputImport.java:10: Redundant import from the java.lang package.",
        };
        verify(c, "InputImport.java", expected);
    }
}
