////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static com.puppycrawl.tools.checkstyle.TreeWalker.parseWithComments;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Assert;

import antlr.ANTLRException;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

public final class TestUtils {

    private TestUtils() {
    }

    /**
     * Verifies that utils class has private constructor and invokes it to satisfy code coverage.
     * @param utilClass class to test for c-tor
     * @param checkConstructorIsPrivate flag to skip check for private visibility, it is useful
     *                                  for Classes that are mocked by PowerMockRunner that make
     *                                  private c-tors as public
     * @noinspection BooleanParameter
     */
    public static void assertUtilsClassHasPrivateConstructor(final Class<?> utilClass,
                                                             boolean checkConstructorIsPrivate)
            throws ReflectiveOperationException {
        final Constructor<?> constructor = utilClass.getDeclaredConstructor();
        if (checkConstructorIsPrivate && !Modifier.isPrivate(constructor.getModifiers())) {
            Assert.fail("Constructor is not private");
        }
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     * @return the default PackageObjectFactory.
     */
    public static PackageObjectFactory getPackageObjectFactory() throws CheckstyleException {
        final ClassLoader cl = TestUtils.class.getClassLoader();
        final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
        return new PackageObjectFactory(packageNames, cl);
    }

    /**
     * Finds node of specified type among root children, siblings, siblings children
     * on any deep level.
     * @param root      DetailAST
     * @param predicate predicate
     * @return {@link Optional} of {@link DetailAST} node which matches the predicate.
     */
    public static Optional<DetailAST> findTokenInAstByPredicate(DetailAST root,
                                                                Predicate<DetailAST> predicate) {
        DetailAST curNode = root;
        while (!predicate.test(curNode)) {
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }

            if (curNode == toVisit || curNode == root.getParent()) {
                curNode = null;
                break;
            }

            curNode = toVisit;
        }
        return Optional.ofNullable(curNode);
    }

    /**
     * Parses Java source file. Results in AST which contains comment nodes.
     * @param file file to parse
     * @return DetailAST tree
     * @throws NullPointerException if the text is null
     * @throws IOException          if the file could not be read
     * @throws ANTLRException       if parser or lexer failed
     */
    public static DetailAST parseFile(File file) throws IOException, ANTLRException {
        final FileText text = new FileText(file.getAbsoluteFile(), "UTF-8");
        final FileContents contents = new FileContents(text);
        return parseWithComments(contents);
    }
}
