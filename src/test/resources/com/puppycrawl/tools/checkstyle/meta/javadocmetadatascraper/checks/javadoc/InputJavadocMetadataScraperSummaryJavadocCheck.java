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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that
 * <a href="https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#firstsentence">
 * Javadoc summary sentence</a> does not contain phrases that are not recommended to use.
 * Summaries that contain only the {@code {@inheritDoc}} tag are skipped.
 * Check also violate Javadoc that does not contain first sentence.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations
 * if the Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code forbiddenSummaryFragments} - Specify the regexp for forbidden summary fragments.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^$"}.
 * </li>
 * <li>
 * Property {@code period} - Specify the period symbol at the end of first javadoc sentence.
 * Type is {@code java.lang.String}.
 * Default value is {@code "."}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check to validate that first sentence is not empty and first
 * sentence is not missing:
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
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * <li>
 * {@code summary.first.sentence}
 * </li>
 * <li>
 * {@code summary.javaDoc}
 * </li>
 * <li>
 * {@code summary.javaDoc.missing}
 * </li>
 * </ul>
 *
 * @since 6.0
 */
@StatelessCheck
public abstract class InputJavadocMetadataScraperSummaryJavadocCheck {

}
