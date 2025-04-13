////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.filefilters;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;

/**
 * <div>
 * File filter {@code BeforeExecutionExclusionFileFilter} decides which files should be
 * excluded from being processed by the utility.
 * </div>
 *
 * <p>
 * By default, Checkstyle includes all files and subdirectories in a directory to be processed and
 * checked for violations. Users could have files that are in these subdirectories that shouldn't
 * be processed with their checkstyle configuration for various reasons, one of which is a valid
 * Java file that won't pass Checkstyle's parser. When Checkstyle tries to parse a Java file and
 * fails, it will throw an {@code Exception} and halt parsing any more files for violations.
 * An example of a valid Java file Checkstyle can't parse is JDK 9's {@code module-info.java}.
 * This file filter will exclude these problem files from being parsed, allowing the rest of the
 * files to run normal and be validated.
 * </p>
 *
 * <p>
 * <b>Note:</b> When a file is excluded from the utility, it is excluded from all Checks and no
 * testing for violations will be performed on them.
 * </p>
 * <ul>
 * <li>
 * Property {@code fileNamePattern} - Define regular expression to match the file name against.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.</li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 7.2
 */
public final class BeforeExecutionExclusionFileFilter extends AbstractAutomaticBean
    implements BeforeExecutionFileFilter {

    /** Define regular expression to match the file name against. */
    private Pattern fileNamePattern;

    /**
     * Setter to define regular expression to match the file name against.
     *
     * @param fileNamePattern regular expression of the excluded file.
     * @since 7.2
     */
    public void setFileNamePattern(Pattern fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public boolean accept(String uri) {
        return fileNamePattern == null || !fileNamePattern.matcher(uri).find();
    }

}
