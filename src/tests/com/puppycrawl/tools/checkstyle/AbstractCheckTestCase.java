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
import java.util.ArrayList;

import junit.framework.TestCase;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public abstract class AbstractCheckTestCase extends TestCase
{
    public abstract void testIt()
        throws Exception;

    /**
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws java.io.IOException error occurred
     **/
    protected String[] getLines(String aFileName)
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

        return (String[]) lines.toArray(new String[0]);
    }

    protected static DetailAST getAST(String aFilename, String[] aLines)
        throws Exception
    {
        // TODO: try with JavaLexer/JavaRecognizer if Java14 fails
        // see c.p.t.c.Checker's respone to initial RecognitionException

        final Reader r = new BufferedReader(new FileReader(aFilename));
        // Create a scanner that reads from the input stream passed to us
        Java14Lexer lexer = new Java14Lexer(r);
        lexer.setFilename(aFilename);
        lexer.setCommentManager(new CommentManager(aLines));

        // Create a parser that reads from the scanner
        Java14Recognizer parser = new Java14Recognizer(lexer);
        parser.setFilename(aFilename);
        parser.setASTNodeClass(DetailAST.class.getName());

        // start parsing at the compilationUnit rule
        parser.compilationUnit();
        return (DetailAST) parser.getAST();
    }

    protected void verifyMessage(LocalizedMessages aMsgs,
            int aIdx, int aLine, int aCol, String aExpected)
    {
        LocalizedMessage localizedMessage = aMsgs.getMessages()[aIdx];
        assertEquals(aLine, localizedMessage.getLineNo());
        assertEquals(aCol, localizedMessage.getColumnNo());
        assertEquals(aExpected, localizedMessage.getMessage());

    }

    public AbstractCheckTestCase(String s)
    {
        super(s);
    }
}
