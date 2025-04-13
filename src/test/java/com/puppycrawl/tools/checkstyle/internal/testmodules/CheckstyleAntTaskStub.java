///
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
///

package com.puppycrawl.tools.checkstyle.internal.testmodules;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.ant.CheckstyleAntTask;

public class CheckstyleAntTaskStub extends CheckstyleAntTask {

    @Override
    protected List<File> scanFileSets() {
        return Collections.singletonList(new MockFile());
    }

    private static final class MockFile extends File {

        /** A unique serial version identifier. */
        private static final long serialVersionUID = -2903929010510199407L;

        private MockFile() {
            super("mock");
        }

        /** This method is overridden to simulate an exception. */
        @Override
        public long lastModified() {
            throw new SecurityException("mock");
        }

    }

}
