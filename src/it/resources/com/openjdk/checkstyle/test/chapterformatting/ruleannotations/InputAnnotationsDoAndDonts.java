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

package com.openjdk.checkstyle.test.chapterformatting.ruleannotations;

public class InputAnnotationsDoAndDonts {
    class Temp {
        public void foo() {
        }

        public void foo1() {
        }

        public void foo2(String... arg) {

        }
    }

    class Do extends Temp {
        public interface Listener {
            void event1();

            void event2();

            void event3();

            void event4();
        }

        public void addListener(Listener listener) {
        }

        @Deprecated
        @Override
        public void foo() {
        }

        @Deprecated @Override
        public void foo1() {
        }

        public void setup() {
            addListener(new Listener() {
                // Ignored events
                @Override public void event1() { }

                @Override public void event2() { }

                @Override public void event3() { }

                // Important event
                @Override
                public void event4() {
                    System.out.println("Event 4 triggered!");
                }
            });
        }
    }

    // ok until https://github.com/checkstyle/checkstyle/issues/20209
    class Donts extends Temp {

        @Override @Deprecated public void foo() {
        }

        @Override @Deprecated
        @SafeVarargs
        public final void foo2(String... arg) {
        }
    }
}
