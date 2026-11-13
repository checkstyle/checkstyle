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

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class PropertiesFileEncodingTest {

    @Test
    public void testAllPropertiesFilesAreValidUtf8() throws Exception {
        final List<Path> messageFiles = new ArrayList<>();
        final Path[] paths = {
                Path.of("src/main/resources"),
                Path.of("src/xdocs-examples/resources"),
                Path.of("src/test/resources"),
        };
        for (Path path : paths) {
            try (Stream<Path> files = Files.walk(path)) {
                files.filter(Files::isRegularFile)
                        .filter(file -> {
                            final String name = file.getFileName().toString();
                            return name.startsWith("messages") && name.endsWith(".properties");
                        })
                        .forEach(messageFiles::add);
            }
        }

        assertWithMessage("No message properties files found")
                .that(messageFiles)
                .isNotEmpty();

        final List<String> invalidFiles = new ArrayList<>();

        for (Path path : messageFiles) {

            final CharsetDecoder decoder = StandardCharsets.UTF_8
                    .newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT);

            final byte[] bytes = Files.readAllBytes(path);

            try {
                decoder.decode(ByteBuffer.wrap(bytes));
            }
            catch (CharacterCodingException exception) {
                invalidFiles.add(path + ": " + exception.getMessage());
            }
        }

        assertWithMessage("Invalid UTF-8 files: " + invalidFiles)
                .that(invalidFiles)
                .isEmpty();
    }

}
