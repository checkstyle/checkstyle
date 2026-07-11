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

package com.openjdk.checkstyle.test.chapternaming.rulenaming;

public class InputNamingTwo {
    private int hidden = 0;

    public InputNamingTwo() {
        int hidden = 0; // violation, ''hidden' hides a field'
    }

    public InputNamingTwo(int hidden) {
    }

    public void shadow() {
        int hidden = 0; // violation, ''hidden' hides a field'
    }

    public void shadowFor() {
        for (int hidden = 0; hidden < 1; hidden++) {
        // violation above, ''hidden' hides a field'
        }
    }

    public void shadowParam(int hidden) {
        // violation above, ''hidden' hides a field'
    }

    public class Inner {
        private int innerHidden = 0;

        public Inner() {
            int innerHidden = 0; // violation, ''innerHidden' hides a field'
        }

        public Inner(int innerHidden) {
        }

        private void innerShadow() {
            int innerHidden = 0; // violation, ''innerHidden' hides a field'
            int hidden = 0; // violation, ''hidden' hides a field'
        }

        private void innerShadowFor() {
            for (int innerHidden = 0; innerHidden < 1; innerHidden++) {
            // violation above, ''innerHidden' hides a field'
            }
            for (int hidden = 0; hidden < 1; hidden++) { // violation, ''hidden' hides a field'
            }
        }

        private void shadowParam(int innerHidden, int hidden) {
            // 2 violations above:
            // ''innerHidden' hides a field'
            // ''hidden' hides a field'
        }

        {
            int innerHidden = 0; // violation, ''innerHidden' hides a field'
            int hidden = 0; // violation, ''hidden' hides a field'
        }
    }

    {
        int hidden = 0; // violation, ''hidden' hides a field'
    }
}
