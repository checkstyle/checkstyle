/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 5/10/2002
 * Time: 15:54:58
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.puppycrawl.tools.checkstyle;

import antlr.ASTFactory;
import antlr.CommonAST;
import antlr.collections.AST;
import antlr.debug.misc.ASTFrame;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Reader;

public class TreeViewer
{
    public static void main(String[] args) {
        // Use a try/catch block for parser exceptions
        try {
            // if we have at least one command-line argument
            if (args.length > 0 ) {
                System.err.println("Parsing...");

                // for each directory/file specified on the command line
                for(int i=0; i< args.length;i++) {
                    parseFile(args[i]); // parse it
                }
            }
            else {
                System.err.println("Usage: java Main [-showtree] "+
                                   "<directory or file name>");
            }
        }
        catch(Exception e) {
            System.err.println("exception: "+e);
            e.printStackTrace(System.err);   // so we can get stack trace
        }
    }


    // Here's where we do the real work...
    public static void parseFile(String aFilename)
        throws Exception {
        try {
            final String[] lines = Utils.getLines(aFilename);
            final CommentManager cmgr = new CommentManager(lines);
            final Reader sar = new StringArrayReader(lines);
            final Java14Lexer jl = new Java14Lexer(sar);
            jl.setFilename(aFilename);
            jl.setCommentManager(cmgr);

            final Java14Recognizer jr = new Java14Recognizer(jl);
            jr.setFilename(aFilename);
            jr.setASTNodeClass(DetailAST.class.getName());
            jr.compilationUnit();
            final DetailAST rootAST = (DetailAST) jr.getAST();
            // do something with the tree
            doTreeAction(rootAST, jr.getTokenNames());
        }
        catch (Exception e) {
            System.err.println("parser exception: "+e);
            e.printStackTrace();   // so we can get stack trace
        }
    }

    public static void doTreeAction(AST t, String[] tokenNames)
    {
        if ( t==null ) return;

        ((CommonAST)t).setVerboseStringConversion(true, tokenNames);
        ASTFactory factory = new ASTFactory();
        AST r = factory.create(0,"AST ROOT");
        r.setFirstChild(t);
        final ASTFrame frame = new ASTFrame("Java AST", r);
        frame.setVisible(true);
        frame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing (WindowEvent e) {
                    frame.setVisible(false); // hide the Frame
                    frame.dispose();
                    System.exit(0);
                }
            }
        );
    }
}
