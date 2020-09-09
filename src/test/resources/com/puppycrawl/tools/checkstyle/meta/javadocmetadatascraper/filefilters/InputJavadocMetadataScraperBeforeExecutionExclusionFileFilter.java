////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper.filefilters;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;

/**
 * <p>
 * File filter {@code BeforeExecutionExclusionFileFilter} decides which files should be
 * excluded from being processed by the utility.
 * </p>
 *
 * <p>
 * By default Checkstyle includes all files and sub-directories in a directory to be processed and
 * checked for violations. Users could have files that are in these sub-directories that shouldn't
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
 * To configure the filter to exclude all 'module-info.java' files:
 * </p>
 *
 * <pre>
 * skipped as not relevant for UTs
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 7.2
 */
public abstract class InputJavadocMetadataScraperBeforeExecutionExclusionFileFilter
        extends AutomaticBean
        implements BeforeExecutionFileFilter {

}
