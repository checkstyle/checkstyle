package com.puppycrawl.tools.checkstyle.checks;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;
import org.apache.commons.beanutils.ConversionException;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Checks the header of the source against a header file that contains a
 * regular expression for each line of the source header.
 *
 * <p>
 * Rationale: In some projects checking against a fixed header
 * is not sufficient (see {@link HeaderCheck}), e.g.
 * the header might require a copyright line where the year information
 * is not static.
 * </p>
 *
 * <p>
 * TODO: RFE 597676
 * </p>
 *
 * @author Lars Kühne
 */
public class RegexpHeaderCheck extends HeaderCheck
{
    /** the compiled regular expressions */
    private RE[] mHeaderRegexps = null;

    /** */
    public void setHeaderFile(String aFileName)
    {
        super.setHeaderFile(aFileName);
        final String[] headerLines = getHeaderLines();
        if (headerLines != null) {
            mHeaderRegexps = new RE[headerLines.length];
            for (int i = 0; i < headerLines.length; i++) {
                try {
                    // TODO: Not sure if chache in Utils is still necessary
                    mHeaderRegexps[i] = Utils.getRE(headerLines[i]);
                }
                catch (RESyntaxException ex) {
                    throw new ConversionException(
                            "line " + i + " in header file is not a regexp");
                }
            }
        }
    }


    /** @see HeaderCheck */
    protected boolean isMatch(int lineNumber)
    {
        final String[] lines = getLines();
        return mHeaderRegexps[lineNumber].match(lines[lineNumber]);
    }
}
