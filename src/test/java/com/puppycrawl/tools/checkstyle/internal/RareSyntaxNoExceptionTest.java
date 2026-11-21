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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Enforces that every TreeWalker check runs without throwing an exception over the shared
 * rare-syntax inputs, and that the inputs together cover every token type the grammar can
 * produce for them. The inputs are organised by enclosing construct, one file per construct,
 * and live in the folders named after this test under the compilable and
 * non-compilable test resources. See issue #22790.
 */
public class RareSyntaxNoExceptionTest {

    /** Name of the folders holding the shared rare-syntax inputs, after this test. */
    private static final String INPUT_FOLDER_NAME =
            "RareSyntaxNoException".toLowerCase(Locale.ENGLISH);

    /** Folders holding the shared rare-syntax inputs. */
    private static final List<Path> INPUT_FOLDERS = List.of(
        Path.of("src/test/resources/com/puppycrawl/tools/checkstyle/internal/"
                + INPUT_FOLDER_NAME),
        Path.of("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/internal/"
                + INPUT_FOLDER_NAME));

    /**
     * Token types that cannot appear in the shared inputs, so the token coverage test
     * allows them to be missing.
     */
    private static final Set<String> TOKENS_NOT_IN_INPUTS = Set.of(
        // `::` is a METHOD_REF node; the DOUBLE_COLON token never appears in the tree.
        "DOUBLE_COLON",
        // The `permits` keyword is consumed as the PERMITS_CLAUSE node itself.
        "LITERAL_PERMITS",
        // Unnamed patterns (`case _`) need preview compilation; no repo marker covers that.
        "UNNAMED_PATTERN_DEF",
        // Module directives live in module-info.java only, not in these inputs.
        "LITERAL_MODULE",
        "MODULE_IMPORT",
        "LITERAL_OPEN",
        "LITERAL_TRANSITIVE",
        "MODULE_DEF",
        "DIRECTIVE_BLOCK",
        "REQUIRES",
        "EXPORTS",
        "OPENS",
        "USES",
        "PROVIDES",
        "TO",
        "WITH");

    /**
     * Runs every TreeWalker check with default configuration over all shared rare-syntax
     * inputs and fails if any check throws an exception on any of them.
     *
     * @throws Exception if the inputs cannot be read or the Checker cannot be configured.
     */
    @Test
    public void testAllChecksRunWithoutExceptions() throws Exception {
        final List<File> inputs = collectInputs();
        final List<String> exceptions = new ArrayList<>();
        final DefaultConfiguration checkerConfig = new DefaultConfiguration("Checker");
        checkerConfig.addProperty("haltOnException", "false");
        final DefaultConfiguration treeWalkerConfig = new DefaultConfiguration("TreeWalker");
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            treeWalkerConfig.addChild(new DefaultConfiguration(check.getName()));
        }
        checkerConfig.addChild(treeWalkerConfig);
        final Checker checker = new Checker();
        checker.setModuleClassLoader(RareSyntaxNoExceptionTest.class.getClassLoader());
        checker.configure(checkerConfig);
        checker.addListener(new ExceptionCollectingListener(exceptions));
        try {
            checker.process(inputs);
        }
        finally {
            checker.destroy();
        }
        assertWithMessage("Checks threw exceptions on the shared rare-syntax inputs:\n"
                + String.join("\n", exceptions))
            .that(exceptions)
            .isEmpty();
    }

    /**
     * Requires every token type to appear somewhere in the shared rare-syntax inputs, so a
     * new construct added to the grammar is covered for all checks at once by growing the
     * inputs, not by touching every check.
     *
     * @throws Exception if an input file cannot be parsed.
     */
    @Test
    public void testAllTokenTypesAppearInInputs() throws Exception {
        final Set<Integer> seen = new TreeSet<>();
        for (File input : collectInputs()) {
            collectTokens(JavaParser.parseFile(input, JavaParser.Options.WITH_COMMENTS), seen);
        }
        final Set<String> missing = new TreeSet<>();
        for (int tokenId : TokenUtil.getAllTokenIds()) {
            if (!seen.contains(tokenId)) {
                missing.add(TokenUtil.getTokenName(tokenId));
            }
        }
        missing.removeAll(TOKENS_NOT_IN_INPUTS);
        assertWithMessage("Token types missing from the shared rare-syntax inputs: " + missing)
            .that(missing)
            .isEmpty();
    }

    /**
     * Collects the shared rare-syntax input files from both resource folders.
     *
     * @return the input files, sorted by path.
     * @throws Exception if a folder cannot be listed.
     */
    private static List<File> collectInputs() throws Exception {
        final List<File> inputs = new ArrayList<>();
        for (Path folder : INPUT_FOLDERS) {
            try (Stream<Path> files = Files.list(folder)) {
                inputs.addAll(files
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .map(Path::toFile)
                    .toList());
            }
        }
        return inputs.stream()
            .sorted()
            .collect(Collectors.toList());
    }

    /**
     * Adds the token type of every node in the given AST to the supplied set.
     *
     * @param ast the AST to walk.
     * @param seen the set collecting the token types.
     */
    private static void collectTokens(DetailAST ast, Set<Integer> seen) {
        DetailAST node = ast;
        while (node != null) {
            seen.add(node.getType());
            if (node.getFirstChild() != null) {
                collectTokens(node.getFirstChild(), seen);
            }
            node = node.getNextSibling();
        }
    }

    /**
     * Collects every exception a check throws while processing the inputs.
     *
     * @param exceptions the list the collected exception messages are added to.
     */
    private record ExceptionCollectingListener(List<String> exceptions)
            implements AuditListener {

        @Override
        public void auditStarted(AuditEvent event) {
            // do nothing
        }

        @Override
        public void auditFinished(AuditEvent event) {
            // do nothing
        }

        @Override
        public void fileStarted(AuditEvent event) {
            // do nothing
        }

        @Override
        public void fileFinished(AuditEvent event) {
            // do nothing
        }

        @Override
        public void addError(AuditEvent event) {
            if (event.getMessage().startsWith("Got an exception")) {
                exceptions.add(event.getFileName() + ": " + event.getMessage());
            }
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            exceptions.add(event.getFileName() + ": " + throwable.getMessage());
        }

    }

}
