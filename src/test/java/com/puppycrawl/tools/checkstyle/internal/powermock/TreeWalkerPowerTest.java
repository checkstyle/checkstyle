////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.checks.indentation.CommentsIndentationCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TreeWalker.class)
public class TreeWalkerPowerTest extends AbstractModuleTestSupport {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/treewalker";
    }

    @Test
    public void testBehaviourWithOnlyOrdinaryChecks() throws Exception {
        final TreeWalker treeWalkerSpy = spy(new TreeWalker());
        final Class<?> classAstState =
                Class.forName("com.puppycrawl.tools.checkstyle.TreeWalker$AstState");
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalkerSpy.configure(createModuleConfig(TypeNameCheck.class));
        treeWalkerSpy.setModuleFactory(factory);
        treeWalkerSpy.setupChild(createModuleConfig(TypeNameCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add("class Test {}");
        final FileText fileText = new FileText(file, lines);
        treeWalkerSpy.setFileContents(new FileContents(fileText));
        Whitebox.invokeMethod(treeWalkerSpy, "processFiltered", file, fileText);
        verifyPrivate(treeWalkerSpy, times(1)).invoke("walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        verifyPrivate(treeWalkerSpy, times(0)).invoke("getFilteredMessages",
                any(String.class), any(FileContents.class), any(DetailAST.class));
    }

    @Test
    public void testBehaviourWithOnlyCommentChecks() throws Exception {
        final TreeWalker treeWalkerSpy = spy(new TreeWalker());
        final Class<?> classAstState =
                Class.forName("com.puppycrawl.tools.checkstyle.TreeWalker$AstState");
        final PackageObjectFactory factory = new PackageObjectFactory(
                new HashSet<>(), Thread.currentThread().getContextClassLoader());
        treeWalkerSpy.configure(createModuleConfig(CommentsIndentationCheck.class));
        treeWalkerSpy.setModuleFactory(factory);
        treeWalkerSpy.setupChild(createModuleConfig(CommentsIndentationCheck.class));
        final File file = temporaryFolder.newFile("file.java");
        final List<String> lines = new ArrayList<>();
        lines.add("class Test {}");
        final FileText fileText = new FileText(file, lines);
        treeWalkerSpy.setFileContents(new FileContents(fileText));
        Whitebox.invokeMethod(treeWalkerSpy, "processFiltered", file, fileText);
        verifyPrivate(treeWalkerSpy, times(1)).invoke("walk",
                any(DetailAST.class), any(FileContents.class), any(classAstState));
        verifyPrivate(treeWalkerSpy, times(0)).invoke("getFilteredMessages",
                any(String.class), any(FileContents.class), any(DetailAST.class));
    }

}
