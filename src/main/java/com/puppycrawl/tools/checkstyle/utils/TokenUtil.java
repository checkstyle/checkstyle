////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Contains utility methods for tokens.
 *
 */
public final class TokenUtil {

    /** Maps from a token name to value. */
    private static final Map<String, Integer> TOKEN_NAME_TO_VALUE;
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
        TOKEN_NAME_TO_VALUE = nameToValueMapFromPublicIntFields(TokenTypes.class);
        TOKEN_VALUE_TO_NAME = valueToNameArrayFromNameToValueMap(TOKEN_NAME_TO_VALUE);
        TOKEN_IDS = TOKEN_NAME_TO_VALUE.values().stream().mapToInt(Integer::intValue).toArray();
    }

    /** Stop instances being created. **/
    private TokenUtil() {
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
     * Creates a map of 'field name' to 'field value' from all {@code public} {@code int} fields
     * of a class.
     * @param cls source class
     * @return unmodifiable name to value map
     */
    public static Map<String, Integer> nameToValueMapFromPublicIntFields(Class<?> cls) {
        final Map<String, Integer> map = Arrays.stream(cls.getDeclaredFields())
            .filter(fld -> Modifier.isPublic(fld.getModifiers()) && fld.getType() == Integer.TYPE)
            .collect(Collectors.toMap(Field::getName, fld -> getIntFromField(fld, fld.getName())));
        return Collections.unmodifiableMap(map);
    }

    /**
     * Creates an array of map keys for quick value-to-name lookup for the map.
     * @param map source map
     * @return array of map keys
     */
    public static String[] valueToNameArrayFromNameToValueMap(Map<String, Integer> map) {
        String[] valueToNameArray = CommonUtil.EMPTY_STRING_ARRAY;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            final int value = entry.getValue();
            // JavadocTokenTypes.EOF has value '-1' and is handled explicitly.
            if (value >= 0) {
                if (value >= valueToNameArray.length) {
                    final String[] temp = new String[value + 1];
                    System.arraycopy(valueToNameArray, 0, temp, 0, valueToNameArray.length);
                    valueToNameArray = temp;
                }
                valueToNameArray[value] = entry.getKey();
            }
        }
        return valueToNameArray;
    }

    /**
     * Get total number of TokenTypes.
     * @return total number of TokenTypes.
     */
    public static int getTokenTypesTotalNumber() {
        return TOKEN_IDS.length;
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
     * @throws IllegalArgumentException when id is not valid
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
     * @throws IllegalArgumentException when id is null
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
     * @throws IllegalArgumentException when name is unknown
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

    /**
     * Finds the first {@link Optional} child token of {@link DetailAST} root node
     * which matches the given predicate.
     * @param root root node.
     * @param predicate predicate.
     * @return {@link Optional} of {@link DetailAST} node which matches the predicate.
     */
    public static Optional<DetailAST> findFirstTokenByPredicate(DetailAST root,
                                                                Predicate<DetailAST> predicate) {
        Optional<DetailAST> result = Optional.empty();
        for (DetailAST ast = root.getFirstChild(); ast != null; ast = ast.getNextSibling()) {
            if (predicate.test(ast)) {
                result = Optional.of(ast);
                break;
            }
        }
        return result;
    }

    /**
     * Performs an action for each child of {@link DetailAST} root node
     * which matches the given token type.
     * @param root root node.
     * @param type token type to match.
     * @param action action to perform on the nodes.
     */
    public static void forEachChild(DetailAST root, int type, Consumer<DetailAST> action) {
        for (DetailAST ast = root.getFirstChild(); ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == type) {
                action.accept(ast);
            }
        }
    }

    /**
     * Determines if two ASTs are on the same line.
     *
     * @param ast1   the first AST
     * @param ast2   the second AST
     *
     * @return true if they are on the same line.
     */
    public static boolean areOnSameLine(DetailAST ast1, DetailAST ast2) {
        return ast1.getLineNo() == ast2.getLineNo();
    }

}
