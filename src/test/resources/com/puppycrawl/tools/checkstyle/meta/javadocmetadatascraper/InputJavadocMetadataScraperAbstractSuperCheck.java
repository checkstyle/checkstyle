/*
com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper
writeXmlOutput = false


*/

package com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper;

import java.util.Deque;
import java.util.LinkedList;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/** //violation
 * <p>
 * Abstract class for checking that an overriding method with no parameters
 * invokes the super method.
 * </p>
 */
@FileStatefulCheck
public abstract class InputJavadocMetadataScraperAbstractSuperCheck extends AbstractCheck {
}
