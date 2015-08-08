////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 * <p>Checks the padding of an empty for iterator; that is whether a
 * space is required at an empty for iterator, or such spaces are
 * forbidden. No check occurs if there is a line wrap at the iterator, as in
 * </p>
 * <pre class="body">
for (Iterator foo = very.long.line.iterator();
      foo.hasNext();
     )
   </pre>
 * <p>
 * The policy to verify is specified using the {@link PadOption} class and
 * defaults to {@link PadOption#NOSPACE}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EmptyForIteratorPad"/&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class EmptyForIteratorPadCheck
    extends AbstractOptionCheck<PadOption> {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String WS_FOLLOWED = "ws.followed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String WS_NOT_FOLLOWED = "ws.notFollowed";

    /**
     * Sets the paren pad otion to nospace.
     */
    public EmptyForIteratorPadCheck() {
        super(PadOption.NOSPACE, PadOption.class);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.FOR_ITERATOR,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.FOR_ITERATOR,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getChildCount() == 0) {
            //empty for iterator. test pad after semi.
            final DetailAST semi = ast.getPreviousSibling();
            final String line = getLines()[semi.getLineNo() - 1];
            final int after = semi.getColumnNo() + 1;
            //don't check if at end of line
            if (after < line.length()) {
                if (getAbstractOption() == PadOption.NOSPACE
                    && Character.isWhitespace(line.charAt(after))) {
                    log(semi.getLineNo(), after, WS_FOLLOWED, ";");
                }
                else if (getAbstractOption() == PadOption.SPACE
                         && !Character.isWhitespace(line.charAt(after))) {
                    log(semi.getLineNo(), after, WS_NOT_FOLLOWED, ";");
                }
            }
        }
    }
}
