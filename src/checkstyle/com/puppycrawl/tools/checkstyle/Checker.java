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
import java.io.File;

/**
 * This class provides the functionality to check a file.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class Checker
    implements Defn
{
    /** printstream to log to **/
    private final PrintStream mLog;

    /** cache file **/
    private final PropertyCacheFile mCache;

    /**
     * Constructs the object.
     * @param aConfig contains the configuration to check with
     * @param aLog the PrintStream to log messages to
     * @throws RESyntaxException unable to create a regexp object
     **/
    Checker(Configuration aConfig, PrintStream aLog)
        throws RESyntaxException
    {
        mLog = aLog;
        mCache = new PropertyCacheFile(aConfig.getCacheFile(), aLog);
        final Verifier v = new VerifierImpl(aConfig);
        VerifierSingleton.setInstance(v);
    }

    /** Cleans up the object **/
    void destroy()
    {
        mCache.destroy();
    }

    /**
     * Processes a specified file and prints out all errors found.
     * @return the number of errors found
     * @param aFileName the name of the file to process
     **/
    int process(String aFileName)
    {
        final long timestamp = new File(aFileName).lastModified();
        if (mCache.alreadyChecked(aFileName, timestamp)) {
            return 0;
        }

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

        if (errors.length == 0) {
            mCache.checkedOk(aFileName, timestamp);
        }
        else {
            displayErrors(aFileName, errors);
        }
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


}
