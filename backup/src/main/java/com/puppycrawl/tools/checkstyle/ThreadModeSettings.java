///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.io.Serializable;

/**
 * Thread mode settings for the checkstyle modules.
 *
 * @noinspection SerializableHasSerializationMethods
 * @noinspectionreason SerializableHasSerializationMethods - we only need serialVersionUID
 *      to differentiate between threads
 */
public class ThreadModeSettings implements Serializable {

    /** A checker module name. */
    public static final String CHECKER_MODULE_NAME = Checker.class.getSimpleName();

    /** A multi thread checker module name. */
    public static final String MULTI_THREAD_CHECKER_MODULE_NAME =
            Checker.class.getSimpleName();

    /** A three walker module name. */
    public static final String TREE_WALKER_MODULE_NAME = TreeWalker.class.getSimpleName();

    /** A multi thread three walker module name. */
    public static final String MULTI_THREAD_TREE_WALKER_MODULE_NAME =
            TreeWalker.class.getSimpleName();

    /** A single thread mode settings instance. */
    public static final ThreadModeSettings SINGLE_THREAD_MODE_INSTANCE =
            new ThreadModeSettings(1, 1);

    /** A unique serial version identifier. */
    private static final long serialVersionUID = 1L;

    /** The checker threads number. */
    private final int checkerThreadsNumber;
    /** The tree walker threads number. */
    private final int treeWalkerThreadsNumber;

    /**
     * Initializes the thread mode configuration.
     *
     * @param checkerThreadsNumber the Checker threads number
     * @param treeWalkerThreadsNumber the TreeWalker threads number
     */
    public ThreadModeSettings(int checkerThreadsNumber, int treeWalkerThreadsNumber) {
        this.checkerThreadsNumber = checkerThreadsNumber;
        this.treeWalkerThreadsNumber = treeWalkerThreadsNumber;
    }

    /**
     * Gets the number of threads for the Checker module.
     *
     * @return the number of threads for the Checker module.
     */
    public int getCheckerThreadsNumber() {
        return checkerThreadsNumber;
    }

    /**
     * Gets the number of threads for the TreeWalker module.
     *
     * @return the number of threads for the TreeWalker module.
     */
    public int getTreeWalkerThreadsNumber() {
        return treeWalkerThreadsNumber;
    }

    /**
     * Resolves the module name according to the thread settings.
     *
     * @param name The original module name.
     * @return resolved module name.
     * @throws IllegalArgumentException when name is Checker or TreeWalker
     */
    public final String resolveName(String name) {
        if (checkerThreadsNumber > 1) {
            if (CHECKER_MODULE_NAME.equals(name)) {
                throw new IllegalArgumentException(
                        "Multi thread mode for Checker module is not implemented");
            }
            if (TREE_WALKER_MODULE_NAME.equals(name)) {
                throw new IllegalArgumentException(
                        "Multi thread mode for TreeWalker module is not implemented");
            }
        }

        return name;
    }

}
