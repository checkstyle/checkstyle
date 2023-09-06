/*
com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper
writeXmlOutput = false


*/

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;



/**
 * <p>
 * Checks whether file contains code. Files which are considered to have no code:
 * </p>
 * <ul>
 * <li>
 * File with no text
 * </li>
 * <li>
 * File with single-line comment(s)
 * </li>
 * <li>
 * File with a multi line comment(s).
 * </li>
 * </ul>
 * <p>
 * To configure the check:
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
 * {@code nocode.in.file}
 * </li>
 * </ul>
 *
 * @since 8.33
 */
@StatelessCheck
public abstract class InputJavadocMetadataScraperNoCodeInFileCheck extends AbstractCheck {

}
