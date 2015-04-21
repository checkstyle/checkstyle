package com.puppycrawl.tools.checkstyle.gui;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextArea;

public class CodeSelector
{
    /** DetailAST node*/
    private final DetailAST ast;
    /** editor */
    private final JTextArea editor;
    /** mapping */
    private final List<Integer> lines2position;

    /**
     * Constructor
     * @param ast
     * @param editor
     * @param lines2position
     */
    public CodeSelector(final DetailAST ast, final JTextArea editor,
                        final List<Integer> lines2position)
    {
        this.ast = ast;
        this.editor = editor;
        this.lines2position = lines2position;
    }

    /**
     * Set a selection position from AST line and Column
     */
    public void select() {
        int start = lines2position.get(ast.getLineNo()) + ast.getColumnNo();
        int end = findLastPosition(ast);

        editor.setSelectedTextColor(Color.blue);
        editor.requestFocusInWindow();
        editor.setSelectionStart(start);
        editor.setSelectionEnd(end);
        editor.transferFocusBackward();
    }

    private int findLastPosition(final DetailAST ast)
    {
        if (ast.getChildCount() == 0)
        {
            return lines2position.get(ast.getLineNo()) + ast.getColumnNo()
                + ast.getText().length();
        }
        else
        {
            return findLastPosition(ast.getLastChild());
        }
    }
}
