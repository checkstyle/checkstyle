////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.regexp.RESyntaxException;

/**
 * This class provides the functionality to check a file.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class Checker
    implements Defn
{
    /** the pattern to match against parameter names **/
    private static final String PARAMETER_PATTERN = "^a[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against static names **/
    private static final String STATIC_PATTERN = "^s[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against constant names **/
    private static final String CONST_PATTERN = "^[A-Z]([A-Z0-9_]*[A-Z0-9])?$";
    /** the pattern to match against member names **/
    private static final String MEMBER_PATTERN = "^m[A-Z][a-zA-Z0-9]*$";
    /** the pattern to match against type names **/
    private static final String TYPE_PATTERN = "^[A-Z][a-zA-Z0-9]*$";
    /** The maximum line length **/
    private static final int MAX_LINE_LENGTH = 80;

    /** printstream to log to **/
    private final PrintStream mLog;

    /**
     * Constructs the object.
     * @param aProps contains the properties to define what to do
     * @param aLog the PrintStream to log messages to
     * @throws RESyntaxException unable to create a regexp object
     **/
    Checker(Properties aProps, PrintStream aLog)
        throws RESyntaxException
    {
        mLog = aLog;
        final Verifier v =
            new VerifierImpl(
                aProps.getProperty(PARAMETER_PATTERN_PROP,
                                   PARAMETER_PATTERN),
                aProps.getProperty(STATIC_PATTERN_PROP,
                                   STATIC_PATTERN),
                aProps.getProperty(CONST_PATTERN_PROP,
                                   CONST_PATTERN),
                aProps.getProperty(MEMBER_PATTERN_PROP,
                                   MEMBER_PATTERN),
                aProps.getProperty(TYPE_PATTERN_PROP,
                                   TYPE_PATTERN),
                getIntProperty(aProps, MAX_LINE_LENGTH_PROP, MAX_LINE_LENGTH),
                getAllowTabs(aProps),
                getAllowProtected(aProps),
                getAllowNoAuthor(aProps),
                getRelaxJavadoc(aProps),
                getCheckImports(aProps),
                getHeaderLines(aProps),
                getIntProperty(aProps, HEADER_IGNORE_LINE_PROP, -1));
        VerifierSingleton.setInstance(v);
    }

    /**
     * Processes a specified file and prints out all errors found.
     * @return the number of errors found
     * @param aFileName the name of the file to process
     **/
    int process(String aFileName)
    {
        LineText[] errors;
        try {
            VerifierSingleton.getInstance().clearMessages();
            VerifierSingleton.getInstance().setLines(getLines(aFileName));
            final AST ast = getAST(aFileName);
            processAST(ast);
            errors = VerifierSingleton.getInstance().getMessages();
        }
        catch (FileNotFoundException fnfe) {
            errors = new LineText[] {new LineText(0, "File not found!")};
        }
        catch (IOException ioe) {
            errors = new LineText[] {
                new LineText(0, "Got an IOException -" + ioe.getMessage())};
        }
        catch (RecognitionException re) {
            errors = new LineText[] {
                new LineText(0,
                             "Got a RecognitionException -" +
                             re.getMessage())};
        }
        catch (TokenStreamException te) {
            errors = new LineText[] {
                new LineText(0,
                             "Got a TokenStreamException -" + te.getMessage())};
        }

        displayErrors(aFileName, errors);
        return errors.length;
    }

    /**
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws IOException error occurred
     **/
    private String[] getLines(String aFileName)
        throws IOException
    {
        final LineNumberReader lnr =
            new LineNumberReader(new FileReader(aFileName));
        final ArrayList lines = new ArrayList();
        while (true) {
            final String l = lnr.readLine();
            if (l == null) {
                break;
            }
            lines.add(l);
        }

        return (String[]) lines.toArray(new String[] {});
    }

    /**
     * Parses and returns the AST for a file.
     * @return the AST
     * @param aFileName the name of the file to generate the AST
     * @throws FileNotFoundException error occurred
     * @throws RecognitionException error occurred
     * @throws TokenStreamException error occurred
     **/
    private AST getAST(String aFileName)
        throws FileNotFoundException, RecognitionException, TokenStreamException
    {
        // Create the lexer/parser stuff
        final GeneratedJavaLexer jl =
            new GeneratedJavaLexer(new FileReader(aFileName));
        jl.setFilename(aFileName);
        final GeneratedJavaRecognizer jr = new GeneratedJavaRecognizer(jl);
        jr.setFilename(aFileName);
        jr.setASTNodeClass(MyCommonAST.class.getName());

        // Parse the file
        jr.compilationUnit();
        return jr.getAST();
    }

    /**
     * Processes an AST for errors.
     * @param aAST the AST to process
     * @throws RecognitionException error occurred
     **/
    private void processAST(AST aAST)
        throws RecognitionException
    {
        final GeneratedJavaTreeParser jtp = new GeneratedJavaTreeParser();
        jtp.compilationUnit(aAST);
    }

    /**
     * Displays the errors associated with a file name. The errors are formatted
     * to be parsed by Emacs.
     * @param aFileName the file name to associate with the errors
     * @param aErrors the errors to display
     **/
    private void displayErrors(String aFileName, LineText[] aErrors)
    {
        for (int i = 0; i < aErrors.length; i++) {
            mLog.println(aFileName + ":" + aErrors[i].getLineNo() +
                         ": " + aErrors[i].getText());
        }
    }

    /**
     * @return whether tabs are allowed
     * @param aProps the property set to test for a value
     **/
    private boolean getAllowTabs(Properties aProps)
    {
        return (aProps.getProperty(ALLOW_TABS_PROP) != null);
    }

    /**
     * @return whether protected data is allowed
     * @param aProps the property set to test for a value
     **/
    private boolean getAllowProtected(Properties aProps)
    {
        return (aProps.getProperty(ALLOW_PROTECTED_PROP) != null);
    }

    /**
     * @return whether allow no author
     * @param aProps the property set to test for a value
     **/
    private boolean getAllowNoAuthor(Properties aProps)
    {
        return (aProps.getProperty(ALLOW_NO_AUTHOR_PROP) != null);
    }

    /**
     * @return whether to relax javadoc checking
     * @param aProps the property set to test for a value
     **/
    private boolean getRelaxJavadoc(Properties aProps)
    {
        return (aProps.getProperty(RELAX_JAVADOC_PROP) != null);
    }

    /**
     * @return whether to check imports
     * @param aProps the property set to test for a value
     **/
    private boolean getCheckImports(Properties aProps)
    {
        return (aProps.getProperty(IGNORE_IMPORTS_PROP) == null);
    }

    /**
     * @return the header lines specified by a file in the supplied properties
     *    set. If no file specified, or unable to read specified file, then an
     *    empty list is returned. Errors are reported.
     * @param aProps the property set to get the file name from
     **/
    private String[] getHeaderLines(Properties aProps)
    {
        String[] retVal = new String[] {};
        final String fname = aProps.getProperty(HEADER_FILE_PROP);
        if (fname != null) {
            try {
                retVal = getLines(fname);
            }
            catch (IOException ioe) {
                mLog.println("Unable to read '" + fname + "', got '" +
                             ioe.getMessage() + "'. Ignoring.");
            }
        }
        return retVal;
    }


    /**
     * @return the value of an integer property. If the property is not defined
     *    or cannot be parsed, then a default value is returned.
     * @param aProps the properties set to use
     * @param aName the name of the property to parse
     * @param aDefault the default value to use.
     **/
    private int getIntProperty(Properties aProps, String aName, int aDefault)
    {
        int retVal = aDefault;
        final String strRep = aProps.getProperty(aName);
        if (strRep != null) {
            try {
                retVal = Integer.parseInt(strRep);
            }
            catch (NumberFormatException nfe) {
                mLog.println("Unable to parse " + aName +
                             " property with value " + strRep +
                             ", defaulting to " + aDefault + ".");
            }
        }
        return retVal;
    }
}
