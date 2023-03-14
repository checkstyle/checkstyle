/*
com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper
writeXmlOutput = false


*/

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder;

/**
 * <p>
 * Filter {@code SuppressWarningsFilter} uses annotation
 * {@code SuppressWarnings} to suppress audit events.
 * </p>
 * <p>
 * Rationale: Same as for {@code SuppressionCommentFilter}. In the contrary to it here,
 * comments are not used comments but the builtin syntax of {@code @SuppressWarnings}.
 * This can be perceived as a more elegant solution than using comments.
 * Also, this approach maybe supported by various IDE.
 * </p>
 * <p>
 * Usage: This filter only works in conjunction with a
 * <a href="https://checkstyle.org/config_annotation.html#SuppressWarningsHolder">
 * SuppressWarningsHolder</a>,
 * since that check finds the annotations in the Java files and makes them available for the filter.
 * Because of that, a configuration that includes this filter must also include
 * {@code SuppressWarningsHolder} as a child module of the {@code TreeWalker}.
 * Name of check in annotation is case-insensitive and should be written with
 * any dotted prefix or "Check" suffix removed.
 * </p>
 * <p>
 * SuppressWarningsFilter can suppress Checks that have Treewalker or
 * Checker as parent module.
 * </p>
 * <p>
 * To configure the check that makes tha annotations available to the filter.
 * </p>
 * <pre>
 * &lt;module name=&quot;TreeWalker&quot;&gt;
 *               ...
 * &lt;module name=&quot;SuppressWarningsHolder&quot; /&gt;
 *               ...
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure filter to suppress audit events for annotations add:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWarningsFilter&quot; /&gt;
 * </pre>
 * <pre>
 * &#64;SuppressWarnings({"memberName"})
 * private int J; // should NOT fail MemberNameCheck
 *
 * &#64;SuppressWarnings({"MemberName"})
 * &#64;SuppressWarnings({"NoWhitespaceAfter"})
 * private int [] ARRAY; // should NOT fail MemberNameCheck and NoWhitespaceAfterCheck
 * </pre>
 * <p>
 * It is possible to specify an ID of checks, so that it can be leveraged by
 * the SuppressWarningsFilter to skip validations. The following examples show how to
 * skip validations near code that has {@code @SuppressWarnings("checkstyle:&lt;ID&gt;")}
 * or just {@code @SuppressWarnings("&lt;ID&gt;")} annotation, where ID is the ID
 * of checks you want to suppress.
 * </p>
 * <p>
 * Example of Checkstyle check configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;RegexpSinglelineJava&quot;&gt;
 *   &lt;property name=&quot;id&quot; value=&quot;systemout&quot;/&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^.*System\.(out|err).*$&quot;/&gt;
 *   &lt;property name=&quot;message&quot;
 *     value=&quot;Don't use System.out/err, use SLF4J instead.&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To make the annotations available to the filter.
 * </p>
 * <pre>
 * &lt;module name=&quot;TreeWalker&quot;&gt;
 *   ...
 *   &lt;module name=&quot;SuppressWarningsHolder&quot; /&gt;
 *   ...
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure filter to suppress audit events for annotations add:
 * </p>
 * <pre>
 * skipped as not relevant for UTs
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 5.7
 */
public abstract class InputJavadocMetadataScraperSuppressWarningsFilter
    extends AutomaticBean
    implements Filter {

}
