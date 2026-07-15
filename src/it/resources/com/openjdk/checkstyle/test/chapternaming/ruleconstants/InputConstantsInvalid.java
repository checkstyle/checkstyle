/*
 * Copyright (c) XXXX, YYYY, Some company and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact
 * or visit www.example.com if you need additional information or have any
 * questions.
 */

package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

public class InputConstantsInvalid {
    public final static int FIRST_CONsTANT1 = 10; // violation 'must match pattern'
    protected final static int SECOND_COnSTANT2 = 100; // violation 'must match pattern'
    final static int third_Constant3 = 1000; // violation 'must match pattern'
    private final static int fourth_Const4 = 50; // violation 'must match pattern'
    public final static int log = 10; // violation 'must match pattern'
    protected final static int logger = 50; // violation 'must match pattern'
    final static int loggerMYSELF = 5; // violation 'must match pattern'

    interface Inter {
        int MAx_ATTEMPTS = 5; // violation 'must match pattern'
    }

    enum Temp {
        LOw, // violation 'must match pattern'
        MEDiUM, // violation 'must match pattern'
        HIGh // violation 'must match pattern'
    }

}
