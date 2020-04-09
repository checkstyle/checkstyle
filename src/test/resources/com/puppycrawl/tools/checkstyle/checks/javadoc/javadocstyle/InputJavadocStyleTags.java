/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleTags {

    /** @author xyz */
    private String singleLineJavadoc(final String name) {
        return null;
    }

    /**
     * @author xyz
     * @param name name
     */
    private String onlyTags(final String name) {
        return null;
    }

    /**
     * desc.
     * @author xyz
     * @param name name
     */
    private String simpleViolation(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *
     * @param name given name
     * @return string
     * @noinspection WeakerAccess
     */
    private String default1(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *
     */
    private String noTags(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *
     * @return string sample
     * @noinspection WeakerAccess
     */
    private String default2(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *     desc1
     *desc2
     * @param name given name
     * @return string
     * @noinspection WeakerAccess
     */
    private String diffTypeofNonEmptyLines(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *     desc1
     *             desc2
     * @param name given name
     * @return string
     * @noinspection WeakerAccess
     */
    private String diffTypeofNonEmptyLines2(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *     desc1
     *desc2 {@code} xyz
     * @param name given name
     * @return string
     * @noinspection WeakerAccess
     */
    private String tagRegexPresent(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *
     *@param name given name
     * @return string
     * @noinspection WeakerAccess
     */
    private String noSpaceParam(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *
     *  @param name given name
     *
     * @return string
     *
     * @noinspection WeakerAccess
     */
    private String multipleSpaceTags(final String name) {
        return null;
    }

    /**
     * Looking if a given name is alias.
     *
     *  @param name given name
     *
     * @return string
     *
     * @noinspection WeakerAccess
     */
    private String multipleSpaceTags2(final String name) {
        return null;
    }
}
