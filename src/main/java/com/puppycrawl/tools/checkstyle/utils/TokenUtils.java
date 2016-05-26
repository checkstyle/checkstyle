////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.ResourceBundle;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for tokens.
 *
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public final class TokenUtils {

    /** Maps from a token name to value. */
    private static final ImmutableMap<String, Integer> TOKEN_NAME_TO_VALUE;
    /** Maps from a token value to name. */
    private static final String[] TOKEN_VALUE_TO_NAME;

    /** Array of all token IDs. */
    private static final int[] TOKEN_IDS;

    /** Prefix for exception when getting token by given id. */
    private static final String TOKEN_ID_EXCEPTION_PREFIX = "given id ";

    /** Prefix for exception when getting token by given name. */
    private static final String TOKEN_NAME_EXCEPTION_PREFIX = "given name ";

    // initialise the constants
    static {
        final ImmutableMap.Builder<String, Integer> builder =
                ImmutableMap.builder();
        final Field[] fields = TokenTypes.class.getDeclaredFields();
        String[] tempTokenValueToName = CommonUtils.EMPTY_STRING_ARRAY;
        for (final Field field : fields) {
            // Only process the int declarations.
            if (field.getType() != Integer.TYPE) {
                continue;
            }

            final String name = field.getName();
            final int tokenValue = getIntFromField(field, name);
            builder.put(name, tokenValue);
            if (tokenValue > tempTokenValueToName.length - 1) {
                final String[] temp = new String[tokenValue + 1];
                System.arraycopy(tempTokenValueToName, 0,
                        temp, 0, tempTokenValueToName.length);
                tempTokenValueToName = temp;
            }
            tempTokenValueToName[tokenValue] = name;
        }

        TOKEN_NAME_TO_VALUE = builder.build();
        TOKEN_VALUE_TO_NAME = tempTokenValueToName;
        TOKEN_IDS = Ints.toArray(TOKEN_NAME_TO_VALUE.values());
    }

    /** Stop instances being created. **/
    private TokenUtils() {
    }

    /**
     * Gets the value of a static or instance field of type int or of another primitive type
     * convertible to type int via a widening conversion. Does not throw any checked exceptions.
     * @param field from which the int should be extracted
     * @param object to extract the int value from
     * @return the value of the field converted to type int
     * @throws IllegalStateException if this Field object is enforcing Java language access control
     *         and the underlying field is inaccessible
     * @see Field#getInt(Object)
     */
    public static int getIntFromField(Field field, Object object) {
        try {
            return field.getInt(object);
        }
        catch (final IllegalAccessException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Get all token IDs that are available in TokenTypes.
     * @return array of token IDs
     */
    public static int[] getAllTokenIds() {
        final int[] safeCopy = new int[TOKEN_IDS.length];
        System.arraycopy(TOKEN_IDS, 0, safeCopy, 0, TOKEN_IDS.length);
        return safeCopy;
    }

    /**
     * Returns the name of a token for a given ID.
     * @param id the ID of the token name to get
     * @return a token name
     */
    public static String getTokenName(int id) {
        if (id > TOKEN_VALUE_TO_NAME.length - 1) {
            throw new IllegalArgumentException(TOKEN_ID_EXCEPTION_PREFIX + id);
        }
        final String name = TOKEN_VALUE_TO_NAME[id];
        if (name == null) {
            throw new IllegalArgumentException(TOKEN_ID_EXCEPTION_PREFIX + id);
        }
        return name;
    }

    /**
     * Returns the ID of a token for a given name.
     * @param name the name of the token ID to get
     * @return a token ID
     */
    public static int getTokenId(String name) {
        final Integer id = TOKEN_NAME_TO_VALUE.get(name);
        if (id == null) {
            throw new IllegalArgumentException(TOKEN_NAME_EXCEPTION_PREFIX + name);
        }
        return id;
    }

    /**
     * Returns the short description of a token for a given name.
     * @param name the name of the token ID to get
     * @return a short description
     */
    public static String getShortDescription(String name) {
        if (!TOKEN_NAME_TO_VALUE.containsKey(name)) {
            throw new IllegalArgumentException(TOKEN_NAME_EXCEPTION_PREFIX + name);
        }

        final String tokenTypes =
            "com.puppycrawl.tools.checkstyle.api.tokentypes";
        final ResourceBundle bundle = ResourceBundle.getBundle(tokenTypes, Locale.ROOT);
        return bundle.getString(name);
    }

    /**
     * Is argument comment-related type (SINGLE_LINE_COMMENT,
     * BLOCK_COMMENT_BEGIN, BLOCK_COMMENT_END, COMMENT_CONTENT).
     * @param type
     *        token type.
     * @return true if type is comment-related type.
     */
    public static boolean isCommentType(int type) {
        return type == TokenTypes.SINGLE_LINE_COMMENT
                || type == TokenTypes.BLOCK_COMMENT_BEGIN
                || type == TokenTypes.BLOCK_COMMENT_END
                || type == TokenTypes.COMMENT_CONTENT;
    }

    /**
     * Is argument comment-related type name (SINGLE_LINE_COMMENT,
     * BLOCK_COMMENT_BEGIN, BLOCK_COMMENT_END, COMMENT_CONTENT).
     * @param type
     *        token type name.
     * @return true if type is comment-related type name.
     */
    public static boolean isCommentType(String type) {
        return isCommentType(getTokenId(type));
    }

}
