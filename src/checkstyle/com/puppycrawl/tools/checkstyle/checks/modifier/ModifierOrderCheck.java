////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Checks that the order of modifiers conforms to the suggestions in the
 * <a
 * href="http://java.sun.com/docs/books/jls/second_edition/html/classes.doc.html">
 * Java Language specification, sections 8.1.1, 8.3.1 and 8.4.3</a>.
 * The correct order is:</p>

<ol>
  <li><span class="code">public</span></li>
  <li><span class="code">protected</span></li>

  <li><span class="code">private</span></li>
  <li><span class="code">abstract</span></li>
  <li><span class="code">static</span></li>
  <li><span class="code">final</span></li>
  <li><span class="code">transient</span></li>
  <li><span class="code">volatile</span></li>

  <li><span class="code">synchronized</span></li>
  <li><span class="code">native</span></li>
  <li><span class="code">strictfp</span></li>
</ol>
 * In additional, modifiers are checked to ensure all annotations
 * are declared before all other modifiers.
 * <p>
 * Rationale: Code is easier to read if everybody follows
 * a standard.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ModifierOrder"/&gt;
 * </pre>
 * @author Lars Kühne
 */
public class ModifierOrderCheck
    extends Check
{
    /**
     * The order of modifiers as suggested in sections 8.1.1,
     * 8.3.1 and 8.4.3 of the JLS.
     */
    private static final String[] JLS_ORDER =
    {
        "public", "protected", "private", "abstract", "static", "final",
        "transient", "volatile", "synchronized", "native", "strictfp",
    };

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.MODIFIERS};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final List<DetailAST> mods = Lists.newArrayList();
        DetailAST modifier = aAST.getFirstChild();
        while (modifier != null) {
            mods.add(modifier);
            modifier = modifier.getNextSibling();
        }

        if (!mods.isEmpty()) {
            final DetailAST error = checkOrderSuggestedByJLS(mods);
            if (error != null) {
                if (error.getType() == TokenTypes.ANNOTATION) {
                    log(error.getLineNo(), error.getColumnNo(),
                            "annotation.order",
                             error.getFirstChild().getText()
                             + error.getFirstChild().getNextSibling()
                                .getText());
                }
                else {
                    log(error.getLineNo(), error.getColumnNo(),
                            "mod.order", error.getText());
                }
            }
        }
    }


    /**
     * Checks if the modifiers were added in the order suggested
     * in the Java language specification.
     *
     * @param aModifiers list of modifier AST tokens
     * @return null if the order is correct, otherwise returns the offending
     * *       modifier AST.
     */
    DetailAST checkOrderSuggestedByJLS(List<DetailAST> aModifiers)
    {
        int i = 0;
        DetailAST modifier;
        final Iterator<DetailAST> it = aModifiers.iterator();
        //No modifiers, no problems
        if (!it.hasNext()) {
            return null;
        }

        //Speed past all initial annotations
        do {
            modifier = it.next();
        }
        while (it.hasNext() && (modifier.getType() == TokenTypes.ANNOTATION));

        //All modifiers are annotations, no problem
        if (modifier.getType() == TokenTypes.ANNOTATION) {
            return null;
        }

        while (i < JLS_ORDER.length) {
            if (modifier.getType() == TokenTypes.ANNOTATION) {
                //Annotation not at start of modifiers, bad
                return modifier;
            }

            while ((i < JLS_ORDER.length)
                   && !JLS_ORDER[i].equals(modifier.getText()))
            {
                i++;
            }

            if (i == JLS_ORDER.length) {
                //Current modifier is out of JLS order
                return modifier;
            }
            else if (!it.hasNext()) {
                //Reached end of modifiers without problem
                return null;
            }
            else {
                modifier = it.next();
            }
        }

        return modifier;
    }
}
