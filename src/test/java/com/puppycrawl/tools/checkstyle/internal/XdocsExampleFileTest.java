///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;

public class XdocsExampleFileTest {

    private static final Set<String> COMMON_PROPERTIES = Set.of(
        "severity",
        "id",
        "fileExtensions",
        "tabWidth",
        "fileContents",
        "tokens",
        "javadocTokens",
        "violateExecutionOnNonTightHtml"
    );

    // This list is temporarily suppressed.
    // Until: https://github.com/checkstyle/checkstyle/issues/17449
    private static final Map<String, Set<String>> SUPPRESSED_PROPERTIES_BY_CHECK = Map.ofEntries(
            Map.entry("MissingJavadocTypeCheck", Set.of("skipAnnotations")),
            Map.entry("JavadocStyleCheck", Set.of("endOfSentenceFormat", "checkEmptyJavadoc")),
            Map.entry("ConstantNameCheck", Set.of("applyToPackage", "applyToPrivate")),
            Map.entry("JavaNCSSCheck", Set.of("recordMaximum")),
            Map.entry("WhitespaceAroundCheck", Set.of("allowEmptySwitchBlockStatements")),
            Map.entry("SuppressWarningsHolder", Set.of("aliasList")),
            Map.entry("IllegalTokenTextCheck", Set.of("message")),
            Map.entry("IndentationCheck", Set.of(
                    "basicOffset",
                    "lineWrappingIndentation",
                    "throwsIndent",
                    "arrayInitIndent",
                    "braceAdjustment"
            )),
            Map.entry("MethodCountCheck", Set.of("maxPrivate", "maxPackage", "maxProtected")),
            Map.entry("ClassMemberImpliedModifierCheck", Set.of(
                    "violateImpliedStaticOnNestedEnum",
                    "violateImpliedStaticOnNestedRecord",
                    "violateImpliedStaticOnNestedInterface"
            )),
            Map.entry("DescendantTokenCheck", Set.of("minimumMessage")),
            Map.entry("InterfaceMemberImpliedModifierCheck", Set.of(
                    "violateImpliedFinalField",
                    "violateImpliedPublicField",
                    "violateImpliedStaticField",
                    "violateImpliedPublicMethod",
                    "violateImpliedAbstractMethod"
            ))
    );

    @Test
    public void testAllCheckPropertiesAreUsedInXdocsExamples() throws Exception {
        final Map<String, Set<String>> usedPropertiesByCheck =
            XdocUtil.extractUsedPropertiesFromXdocsExamples();
        final List<String> failures = new ArrayList<>();

        for (Class<?> checkClass : CheckUtil.getCheckstyleChecks()) {
            final String checkSimpleName = checkClass.getSimpleName();

            final Set<String> definedProperties = Arrays.stream(
                    PropertyUtils.getPropertyDescriptors(checkClass))
                .filter(descriptor -> descriptor.getWriteMethod() != null)
                .map(PropertyDescriptor::getName)
                .filter(property -> !COMMON_PROPERTIES.contains(property))
                .collect(Collectors.toUnmodifiableSet());

            final Set<String> usedProperties =
                usedPropertiesByCheck.getOrDefault(checkSimpleName, Collections.emptySet());

            final Set<String> suppressedProps =
                SUPPRESSED_PROPERTIES_BY_CHECK.getOrDefault(
                    checkSimpleName, Collections.emptySet());

            for (String property : definedProperties) {
                if (!usedProperties.contains(property)
                        && !suppressedProps.contains(property)) {
                    failures.add("Missing property in xdoc: '"
                            + property + "' of " + checkSimpleName);
                }
            }
        }
        if (!failures.isEmpty()) {
            assertWithMessage("Xdocs are missing properties:\n" + String.join("\n", failures))
                    .fail();
        }
    }
}
