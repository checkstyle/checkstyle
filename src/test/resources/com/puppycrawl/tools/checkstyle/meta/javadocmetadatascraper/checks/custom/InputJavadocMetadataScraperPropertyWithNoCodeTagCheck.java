package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper.checks.custom;

/**
 * <p>
 * Checks whether file contains code. Files which are considered to have no code:
 * </p>
 * <ul>
 * <li>
 * File with no text
 * </li>
 * <li>
 * File with single line comment(s)
 * </li>
 * <li>
 * File with a multi line comment(s).
 * </li>
 * </ul>
 * <p>
 * <ul>
 * <li>
 * Property
 * sampleProperty - Here the property name doesnt have a code tag around its name.
 * </li>
 * </ul>
 *
 * @since 8.33
 */
public class InputJavadocMetadataScraperPropertyWithNoCodeTagCheck {
}
