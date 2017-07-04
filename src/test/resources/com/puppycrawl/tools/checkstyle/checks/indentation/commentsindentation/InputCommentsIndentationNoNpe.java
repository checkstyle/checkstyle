/*
 * Copyright (C) 2012 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Represents either a {@link Field}, a {@link Method} or a {@link Constructor}.
 * Provides convenience methods such as {@link #isPublic} and {@link #isPackagePrivate}.
 * 
 */
class InputCommentsIndentationNoNpe {

}
/* The Check should not throw NPE here! */
