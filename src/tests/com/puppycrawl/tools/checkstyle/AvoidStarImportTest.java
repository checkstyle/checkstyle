/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 20/09/2002
 * Time: 17:18:38
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.puppycrawl.tools.checkstyle;

import junit.framework.TestCase;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.checks.AvoidStarImport;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;

public class AvoidStarImportTest
extends TestCase
{
    public AvoidStarImportTest(String aName)
    {
        super(aName);
    }

    public void testIt()
        throws Exception
    {
        final LocalizedMessages msgs = new LocalizedMessages(8);
        final TreeWalker walker = new TreeWalker(msgs);
        final CheckConfiguration config = new CheckConfiguration();
        config.setClassname(AvoidStarImport.class.getName());
        final Check c = config.createInstance(
            Thread.currentThread().getContextClassLoader());
        walker.registerCheck(c, config);
        final String fname = CheckerTest.getPath("InputImport.java");
        final String[] lines = getLines(fname);
        walker.walk(getAST(fname, lines), lines, fname);
        assertEquals(msgs.getMessages().length, 3);
        assertEquals(msgs.getMessages()[0].getLineNo(), 7);
        assertEquals(msgs.getMessages()[1].getLineNo(), 9);
        assertEquals(msgs.getMessages()[2].getLineNo(), 10);
        assertEquals(msgs.getMessages()[0].getMessage(), "help me Ronda");
    }

    /**
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws java.io.IOException error occurred
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

        return (String[]) lines.toArray(new String[0]);
    }

    private static DetailAST getAST(String aFilename, String[] aLines)
        throws Exception
    {
        final Reader r = new BufferedReader(new FileReader(aFilename));
        // Create a scanner that reads from the input stream passed to us
        JavaLexer lexer = new JavaLexer(r);
        lexer.setFilename(aFilename);
        lexer.setCommentManager(new CommentManager(aLines));

        // Create a parser that reads from the scanner
        JavaRecognizer parser = new JavaRecognizer(lexer);
        parser.setFilename(aFilename);
        parser.setASTNodeClass(DetailAST.class.getName());

        // start parsing at the compilationUnit rule
        parser.compilationUnit();
        return (DetailAST) parser.getAST();
    }
}
