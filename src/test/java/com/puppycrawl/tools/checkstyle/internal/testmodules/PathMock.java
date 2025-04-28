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

package com.puppycrawl.tools.checkstyle.internal.testmodules;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.*;

public class PathMock implements Path, Serializable {

    /** A unique serial version identifier. */
    private static final long serialVersionUID = -7801807253540916684L;

    private final FileMock filemock;
    private final Throwable expectedThrowable;

    public PathMock(Throwable expectedThrowable) {
        this.expectedThrowable = expectedThrowable;
        this.filemock = new FileMock(expectedThrowable);
    }

    @Override
    public File toFile() {
        return filemock;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public FileSystem getFileSystem() {
        return null;
    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public Path getRoot() {
        return null;
    }

    @Override
    public Path getFileName() {
        return null;
    }

    @Override
    public Path getParent() {
        return null;
    }

    @Override
    public int getNameCount() {
        return 0;
    }

    @Override
    public Path getName(int i) {
        return null;
    }

    @Override
    public Path subpath(int i, int i1) {
        return null;
    }

    @Override
    public boolean startsWith(Path path) {
        return false;
    }

    @Override
    public boolean endsWith(Path path) {
        return false;
    }

    @Override
    public Path normalize() {
        return null;
    }

    @Override
    public Path resolve(Path path) {
        return null;
    }

    @Override
    public Path relativize(Path path) {
        return null;
    }

    @Override
    public URI toUri() {
        return null;
    }

    @Override
    public Path toAbsolutePath() {
        if (expectedThrowable instanceof Error) {
            throw (Error) expectedThrowable;
        } else if (expectedThrowable instanceof RuntimeException) {
            throw (RuntimeException) expectedThrowable;
        } else if (expectedThrowable != null) {
            throw new RuntimeException(expectedThrowable); // Wrap checked exceptions
        }
        return null; // Should not reach here if an exception was provided
    }

    @Override
    public Path toRealPath(LinkOption... linkOptions) {
        return null;
    }

    @Override
    public WatchKey register(WatchService watchService,
                             WatchEvent.Kind<?>[] kinds,
                             WatchEvent.Modifier... modifiers) {
        return null;
    }

    @Override
    public int compareTo(Path path) {
        return 0;
    }

    private static final class FileMock extends File {

        private static final long serialVersionUID = 1L;

        private final Throwable expectedThrowable; // Use Throwable

        public FileMock(Throwable expectedThrowable) { // Use Throwable
            super("FileMock");
            this.expectedThrowable = expectedThrowable;
        }
        @Override
        public long lastModified() {
            getAbsolutePath();
            return 0;
        }
        /**
         * Test is checking catch clause when exception is thrown.
         *
         * @noinspection ProhibitedExceptionThrown
         * @noinspectionreason ProhibitedExceptionThrown - we require mocked file to
         * throw exception as part of test
         */
        @Override
        public String getAbsolutePath() {
            if (expectedThrowable instanceof Error) {
                throw (Error) expectedThrowable;
            } else if (expectedThrowable instanceof RuntimeException) {
                throw (RuntimeException) expectedThrowable;
            } else if (expectedThrowable != null) {
                throw new RuntimeException(expectedThrowable);
            }
            return null;
        }
    }
}