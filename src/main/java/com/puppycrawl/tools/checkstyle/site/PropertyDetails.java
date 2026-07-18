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

package com.puppycrawl.tools.checkstyle.site;

import java.util.Collection;
import java.util.List;

/**
 * Immutable data object holding all pre-computed details for a single module property.
 * Built by {@link SiteUtil#buildPropertyDetails}; consumed by {@link PropertiesMacro}
 * purely for formatting. No internal Checkstyle AST or Javadoc tree types appear here.
 */
public final class PropertyDetails {

    /**
     * Describes how the type cell and default-value cell should be rendered
     * for token-related properties.
     */
    public enum TokenPropertyType {
        /** Normal property — renders a plain link to property_types.xml. */
        STANDARD,
        /**
         * {@code tokens} property that accepts every token —
         * renders "set of any supported tokens" with a link.
         */
        TOKEN_SET,
        /** {@code tokens} property with a configurable subset of TokenTypes. */
        TOKEN_SUBSET,
        /** {@code javadocTokens} property with a configurable subset of JavadocTokenTypes. */
        JAVADOC_TOKEN_SUBSET,
    }

    /** The property name. */
    private final String name;

    /**
     * The human-readable description, already rendered as xdoc-safe HTML.
     * Produced by {@code SiteUtil.getPropertyDescriptionForXdoc}.
     */
    private final String description;

    /**
     * The resolved type string, e.g. {@code "boolean"}, {@code "int"},
     * {@code "subset of tokens TokenTypes"}.
     * Meaningful only when {@link #tokenPropertyType} is {@link TokenPropertyType#STANDARD}.
     */
    private final String type;

    /**
     * Determines how the type cell is rendered by {@link PropertiesMacro}.
     * Defaults to {@link TokenPropertyType#STANDARD}.
     */
    private final TokenPropertyType tokenPropertyType;

    /**
     * The list of configurable token names used when
     * {@link #tokenPropertyType} is {@link TokenPropertyType#TOKEN_SUBSET}
     * or {@link TokenPropertyType#JAVADOC_TOKEN_SUBSET}.
     * Empty otherwise.
     */
    private final List<String> configurableTokens;

    /**
     * The pre-resolved default value string, e.g. {@code "{}"}, {@code "true"},
     * {@code "all files"}, {@code "TokenTypes"}.
     * When the default value cell must render individual token links,
     * {@link #defaultValueTokens} is used instead and this field is empty.
     */
    private final String defaultValue;

    /**
     * Pre-resolved list of token names used when the default-value cell must
     * render each token as an individual link.
     * Empty when {@link #defaultValue} should be rendered verbatim.
     */
    private final List<String> defaultValueTokens;

    /** The "since" version string, e.g. {@code "8.3"}. */
    private final String sinceVersion;

    /**
     * Creates a new {@code PropertyDetails} from the given builder.
     *
     * @param builder the builder to copy field values from.
     */
    private PropertyDetails(Builder builder) {
        name = builder.buildName;
        description = builder.buildDescription;
        type = builder.buildType;
        tokenPropertyType = builder.buildTokenPropertyType;
        configurableTokens = List.copyOf(builder.buildConfigurableTokens);
        defaultValue = builder.buildDefaultValue;
        defaultValueTokens = List.copyOf(builder.buildDefaultValueTokens);
        sinceVersion = builder.buildSinceVersion;
    }

    /**
     * Returns the property name.
     *
     * @return the property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the xdoc-safe HTML description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the resolved type string. Meaningful only when
     * {@link #getTokenPropertyType()} is {@link TokenPropertyType#STANDARD}.
     *
     * @return the type string, or {@code null} for token properties.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the rendering strategy for the type and default-value cells.
     *
     * @return the token property type.
     */
    public TokenPropertyType getTokenPropertyType() {
        return tokenPropertyType;
    }

    /**
     * Returns the list of configurable token names. Non-empty only when
     * {@link #getTokenPropertyType()} is {@link TokenPropertyType#TOKEN_SUBSET}
     * or {@link TokenPropertyType#JAVADOC_TOKEN_SUBSET}.
     *
     * @return an unmodifiable list of token names.
     */
    public List<String> getConfigurableTokens() {
        return configurableTokens;
    }

    /**
     * Returns the pre-resolved default value string. Empty when
     * {@link #getDefaultValueTokens()} should be used instead.
     *
     * @return the default value string.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the list of token names to render as individual links in the
     * default-value cell. Empty when {@link #getDefaultValue()} should be used instead.
     *
     * @return an unmodifiable list of token names.
     */
    public List<String> getDefaultValueTokens() {
        return defaultValueTokens;
    }

    /**
     * Returns the "since" version string.
     *
     * @return the since version.
     */
    public String getSinceVersion() {
        return sinceVersion;
    }

    /**
     * Builder for {@link PropertyDetails}.
     */
    public static final class Builder {

        /** The property name. */
        private String buildName;

        /** The xdoc-safe HTML description. */
        private String buildDescription;

        /** The resolved type string. */
        private String buildType;

        /** The token property type; defaults to STANDARD. */
        private TokenPropertyType buildTokenPropertyType = TokenPropertyType.STANDARD;

        /** The configurable token names. */
        private List<String> buildConfigurableTokens = List.of();

        /** The default value string. */
        private String buildDefaultValue = "";

        /** The default value token names. */
        private List<String> buildDefaultValueTokens = List.of();

        /** The since version string. */
        private String buildSinceVersion = "";

        /**
         * Creates a new {@code Builder} instance.
         */
        public Builder() {
            // no code by default
        }

        /**
         * Sets the property name.
         *
         * @param val the property name.
         * @return this builder.
         */
        public Builder name(String val) {
            buildName = val;
            return this;
        }

        /**
         * Sets the xdoc-safe HTML description.
         *
         * @param val the description.
         * @return this builder.
         */
        public Builder description(String val) {
            buildDescription = val;
            return this;
        }

        /**
         * Sets the resolved type string.
         *
         * @param val the type string.
         * @return this builder.
         */
        public Builder type(String val) {
            buildType = val;
            return this;
        }

        /**
         * Sets the token property type.
         *
         * @param val the token property type.
         * @return this builder.
         */
        public Builder tokenPropertyType(TokenPropertyType val) {
            buildTokenPropertyType = val;
            return this;
        }

        /**
         * Sets the configurable token names.
         *
         * @param val the list of token names.
         * @return this builder.
         */
        public Builder configurableTokens(Collection<String> val) {
            buildConfigurableTokens = List.copyOf(val);
            return this;
        }

        /**
         * Sets the default value string.
         *
         * @param val the default value string.
         * @return this builder.
         */
        public Builder defaultValue(String val) {
            buildDefaultValue = val;
            return this;
        }

        /**
         * Sets the default value token names.
         *
         * @param val the list of token names.
         * @return this builder.
         */
        public Builder defaultValueTokens(Collection<String> val) {
            buildDefaultValueTokens = List.copyOf(val);
            return this;
        }

        /**
         * Sets the since version string.
         *
         * @param val the since version.
         * @return this builder.
         */
        public Builder sinceVersion(String val) {
            buildSinceVersion = val;
            return this;
        }

        /**
         * Builds and returns the {@link PropertyDetails}.
         *
         * @return a new {@link PropertyDetails} instance.
         */
        public PropertyDetails build() {
            return new PropertyDetails(this);
        }
    }

}
