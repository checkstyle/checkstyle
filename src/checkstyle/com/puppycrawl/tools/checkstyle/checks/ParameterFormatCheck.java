package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks that the header of the source file is correct.
 *
 * <p>
 * Rationale: In most projects each file must have a fixed header,
 * usually the header contains copyright information.
 * </p>
 *
 * @author Oliver Burn
 */
public class ParameterFormatCheck
    extends Check
{
    private RE mRegexp;
    private String mFormat;

    public ParameterFormatCheck()
    {
        setFormat("^[a-z][a-zA-Z0-9]*$");
    }

    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.PARAMETER_DEF};
    }

    public void visitToken(DetailAST aAST)
    {
        final DetailAST nameAST =
            (DetailAST) aAST.getFirstChild().getNextSibling().getNextSibling();
        if (!mRegexp.match(nameAST.getText())) {
            log(nameAST.getLineNo(),
                nameAST.getColumnNo(),
                "name.invalidPattern",
                nameAST.getText(),
                mFormat);
        }

    }

    public void setFormat(String aFormat)
    {
        try {
            mRegexp = Utils.getRE(aFormat);
            mFormat = aFormat;
        }
        catch (RESyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }
}
