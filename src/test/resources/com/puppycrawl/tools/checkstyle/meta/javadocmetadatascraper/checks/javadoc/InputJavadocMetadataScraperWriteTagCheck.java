////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper.checks.javadoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Requires user defined Javadoc tag to be present in Javadoc comment with defined format.
 * To define the format for a tag, set property tagFormat to a regular expression.
 * Property tagSeverity is used for severity of events when the tag exists.
 * </p>
 * <ul>
 * <li>
 * Property {@code tag} - Specify the name of tag.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tagFormat} - Specify the regexp to match tag content.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tagSeverity} - Specify the severity level when tag is found and printed.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.SeverityLevel}.
 * Default value is {@code info}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check for printing author name:
 * </p>
 * <pre>
 * &lt;module name="WriteTag"&gt;
 *   &lt;property name="tag" value="@author"/&gt;
 *   &lt;property name="tagFormat" value="\S"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to print warnings if an "@incomplete" tag is found,
 * and not print anything if it is not found:
 * </p>
 * <pre>
 * skipped as not relevant for UTs
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.writeTag}
 * </li>
 * <li>
 * {@code type.missingTag}
 * </li>
 * <li>
 * {@code type.tagFormat}
 * </li>
 * </ul>
 *
 * @since 4.2
 */
@StatelessCheck
public abstract class InputJavadocMetadataScraperWriteTagCheck extends AbstractCheck {

}
