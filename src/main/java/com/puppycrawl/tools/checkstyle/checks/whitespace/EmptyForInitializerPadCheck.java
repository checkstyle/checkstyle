////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>Checks the padding of an empty for initializer; that is whether a
 * space is required at an empty for initializer, or such spaces are
 * forbidden. No check occurs if there is a line wrap at the initializer, as in
 * </p>
 * <pre class="body">
for (
      ; i &lt; j; i++, j--)
   </pre>
 * <p>
 * The policy to verify is specified using the {@link PadOption} class and
 * defaults to {@link PadOption#NOSPACE}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EmptyForInitializerPad"/&gt;
 * </pre>
 *
 * @author lkuehne
 */
public class EmptyForInitializerPadCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_NOT_PRECEDED = "ws.notPreceded";

    /** Semicolon literal. */
    private static final String SEMICOLON = ";";

    /** The policy to enforce. */
    private PadOption option = PadOption.NOSPACE;

    /**
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = PadOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + optionStr, iae);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.FOR_INIT};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getChildCount() == 0) {
            //empty for initializer. test pad before semi.
            final DetailAST semi = ast.getNextSibling();
            final int semiLineIdx = semi.getLineNo() - 1;
            final String line = getLines()[semiLineIdx];
            final int before = semi.getColumnNo() - 1;
            //don't check if semi at beginning of line
            if (!CommonUtils.hasWhitespaceBefore(before, line)) {
                if (option == PadOption.NOSPACE
                    && Character.isWhitespace(line.charAt(before))) {
                    log(semi.getLineNo(), before, MSG_PRECEDED, SEMICOLON);
                }
                else if (option == PadOption.SPACE
                         && !Character.isWhitespace(line.charAt(before))) {
                    log(semi.getLineNo(), before, MSG_NOT_PRECEDED, SEMICOLON);
                }
            }
        }
    }
}
