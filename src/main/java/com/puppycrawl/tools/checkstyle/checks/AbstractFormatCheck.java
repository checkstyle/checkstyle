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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * <p> Abstract class for checks that verify strings using a
 * {@link Pattern regular expression}.  It
 * provides support for setting the regular
 * expression using the property name {@code format}.  </p>
 *
 * @author Oliver Burn
 */
public abstract class AbstractFormatCheck
    extends Check {
    /** the flags to create the regular expression with */
    private int compileFlags;
    /** the regexp to match against */
    private Pattern regexp;
    /** the format string of the regexp */
    private String format;

    /**
     * Creates a new <code>AbstractFormatCheck</code> instance. Defaults the
     * compile flag to 0 (the default).
     * @param defaultFormat default format
     * @throws ConversionException unable to parse defaultFormat
     */
    protected AbstractFormatCheck(String defaultFormat) {
        this(defaultFormat, 0);
    }

    /**
     * Creates a new <code>AbstractFormatCheck</code> instance.
     * @param defaultFormat default format
     * @param compileFlags the Pattern flags to compile the regexp with.
     * See {@link Pattern#compile(String, int)}
     * @throws ConversionException unable to parse defaultFormat
     */
    protected AbstractFormatCheck(String defaultFormat, int compileFlags) {
        updateRegexp(defaultFormat, compileFlags);
    }

    /**
     * Set the format to the specified regular expression.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse format
     */
    public final void setFormat(String format) {
        updateRegexp(format, compileFlags);
    }

    /**
     * Set the compile flags for the regular expression.
     * @param compileFlags the compile flags to use.
     */
    public final void setCompileFlags(int compileFlags) {
        updateRegexp(format, compileFlags);
    }

    /** @return the regexp to match against */
    public final Pattern getRegexp() {
        return regexp;
    }

    /** @return the regexp format */
    public final String getFormat() {
        return format;
    }

    /**
     * Updates the regular expression using the supplied format and compiler
     * flags. Will also update the member variables.
     * @param regexpFormat the format of the regular expression.
     * @param compileFlagsParam the compiler flags to use.
     */
    private void updateRegexp(String regexpFormat, int compileFlagsParam) {
        try {
            regexp = Pattern.compile(regexpFormat, compileFlagsParam);
            this.format = regexpFormat;
            compileFlags |= compileFlagsParam;
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + regexpFormat, e);
        }
    }
}
