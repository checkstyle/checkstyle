///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that Javadoc comments avoid unnecessary {@code {@link}} and {@code {@linkplain}} tags
 * for APIs that are considered well-known. Linking well-known APIs can make comments harder to
 * read without adding much value for the reader.
 * </div>
 *
 * <p>
 * This check reports {@code {@link}} references to configured well-known APIs.
 * Two properties are supported:
 * {@code wellKnownQualifiedPackages} and {@code wellKnownSimpleNames}.
 * </p>
 *
 * <p>
 * Both properties are needed because Checkstyle does not resolve Javadoc link targets.
 * For example, {@code java.lang.String} contains the package name, so it can be
 * matched through {@code wellKnownQualifiedPackages}. However, {@code String}
 * only contains the simple name {@code String}, so it needs to be matched through
 * {@code wellKnownSimpleNames}. Resolution of imports is not a solution since
 * {@code java.lang} is implicitly imported.
 * </p>
 *
 * <p>
 * For {@code wellKnownQualifiedPackages}, only references to classes that are
 * direct members of a well-known package are reported. References to a member
 * (for example, {@code String#length()}), a nested class (for example,
 * {@code System.Logger}), a subpackage (for example, {@code java.lang.ref.WeakReference}),
 * and a package itself (for example, {@code java.lang.ref}) are not reported.
 * </p>
 *
 * @since 13.11.0
 */
@StatelessCheck
public class JavadocLinkWellKnownApiCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WELL_KNOWN_API = "javadoc.wellKnownApi";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WELL_KNOWN_PACKAGE = "javadoc.wellKnownPackage";

    /**
     * Dot.
     */
    private static final char DOT = '.';

    /**
     * Package names whose fully qualified API references should not be linked.
     */
    private Set<String> wellKnownQualifiedPackages = Set.of("java.lang");

    /**
     * Simple API names that should not be linked.
     */
    private Set<String> wellKnownSimpleNames = Set.of("String");

    /**
     * Creates a new {@code JavadocLinkWellKnownApiCheck} instance.
     */
    public JavadocLinkWellKnownApiCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.LINK_INLINE_TAG,
            JavadocCommentsTokenTypes.LINKPLAIN_INLINE_TAG,
        };
    }

    /**
     * Setter to specify package names whose fully qualified API references should not be
     * linked.
     *
     * @param values user's values.
     * @since 13.11.0
     */
    public final void setWellKnownQualifiedPackages(String... values) {
        wellKnownQualifiedPackages = Arrays.stream(values).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Setter to specify simple API names that should not be linked.
     *
     * @param values user's values.
     * @since 13.11.0
     */
    public final void setWellKnownSimpleNames(String... values) {
        wellKnownSimpleNames = Arrays.stream(values).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final DetailNode referenceNode = JavadocUtil.findFirstToken(ast,
                JavadocCommentsTokenTypes.REFERENCE);
        if (JavadocUtil.findFirstToken(referenceNode,
                JavadocCommentsTokenTypes.MEMBER_REFERENCE) == null) {
            final String apiName = referenceNode.getFirstChild().getText();
            if (isWellKnownQualified(apiName)) {
                log(ast, MSG_WELL_KNOWN_PACKAGE, apiName);
            }
            else if (wellKnownSimpleNames.contains(apiName)) {
                log(ast, MSG_WELL_KNOWN_API, apiName);
            }
        }
    }

    /**
     * Checks whether the given API name belongs to a well-known qualified package.
     *
     * @param apiName the API name to check
     * @return true if the API name belongs to a well-known qualified package
     */
    private boolean isWellKnownQualified(String apiName) {
        boolean result = false;
        for (String packageName : wellKnownQualifiedPackages) {
            final String prefix = packageName + DOT;
            final int prefixLength = prefix.length();
            if (apiName.startsWith(prefix)
                    && apiName.length() > prefixLength
                    && Character.isUpperCase(apiName.charAt(prefixLength))
                    && apiName.indexOf(DOT, prefixLength) == -1) {
                result = true;
                break;
            }
        }
        return result;
    }

}
