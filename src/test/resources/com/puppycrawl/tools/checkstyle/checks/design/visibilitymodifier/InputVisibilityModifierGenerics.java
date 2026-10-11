/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = (default)^serialVersionUID$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = true
immutableClassCanonicalNames = com.google.common.collect.ImmutableMap, java.lang.String, \
                               java.util.Optional, java.math.BigDecimal
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

public final class InputVisibilityModifierGenerics {

    public final String name;
    public final Optional<String> keyword;
    public final ImmutableMap<String, BigDecimal> uuidMap;
    public final ImmutableMap<String, ArrayList<Long>> perfSeries;
    // violation above 'Variable 'perfSeries' must be private and have accessor methods.'
    public final ImmutableMap<java.lang.String, ArrayList<Long>> peopleMap;
    // violation above 'Variable 'peopleMap' must be private and have accessor methods.'
    public final ImmutableMap<String, java.util.ArrayList<Long>> someMap;
    // violation above 'Variable 'someMap' must be private and have accessor methods.'
    public final ImmutableMap<java.lang.String, java.util.ArrayList<Long>> newMap;
    // violation above 'Variable 'newMap' must be private and have accessor methods.'
    public final ImmutableMap<java.lang.String, java.math.BigDecimal> orders;
    public final Optional<java.lang.Object> optionalOfObject;
    // violation above 'Variable 'optionalOfObject' must be private and have accessor methods.'
    public final Optional<Object> obj;
    // violation above 'Variable 'obj' must be private and have accessor methods.'

    public String rqUID;
    // violation above 'Variable 'rqUID' must be private and have accessor methods.'
    public Optional<String> rqTime;
    // violation above 'Variable 'rqTime' must be private and have accessor methods.'
    public ImmutableMap<String, BigDecimal> rates;
    // violation above 'Variable 'rates' must be private and have accessor methods.'
    public ImmutableMap<String, ArrayList<Long>> loans;
    // violation above 'Variable 'loans' must be private and have accessor methods.'
    public ImmutableMap<java.lang.String, ArrayList<Long>> cards;
    // violation above 'Variable 'cards' must be private and have accessor methods.'
    public ImmutableMap<String, java.util.ArrayList<Long>> values;
    // violation above 'Variable 'values' must be private and have accessor methods.'
    public ImmutableMap<java.lang.String, java.util.ArrayList<Long>> permissions;
    // violation above 'Variable 'permissions' must be private and have accessor methods.'

    public final Map<String, String> mapOfStrings;
    // violation above 'Variable 'mapOfStrings' must be private and have accessor methods.'
    public final java.util.Map<String, String> names;
    // violation above 'Variable 'names' must be private and have accessor methods.'
    public final java.util.Map<String, Object> links;
    // violation above 'Variable 'links' must be private and have accessor methods.'
    public final Map<String, Object> presentations;
    // violation above 'Variable 'presentations' must be private and have accessor methods.'
    public final Map<String, java.lang.Object> collection;
    // violation above 'Variable 'collection' must be private and have accessor methods.'

    public final com.google.common.collect.ImmutableMap<String, BigDecimal> prices;
    public final com.google.common.collect.ImmutableMap<String, Object> exceptions;
    // violation above 'Variable 'exceptions' must be private and have accessor methods.'

    public InputVisibilityModifierGenerics() {
        this.name = "John Doe";
        this.keyword = Optional.empty();
        this.perfSeries = ImmutableMap.of();
        this.uuidMap = ImmutableMap.of();
        this.peopleMap = ImmutableMap.of();
        this.someMap = ImmutableMap.of();
        this.newMap = ImmutableMap.of();
        this.orders = ImmutableMap.of();
        this.optionalOfObject = Optional.empty();
        this.obj = Optional.empty();
        this.mapOfStrings = new HashMap<>(1);
        this.names = new HashMap<>(1);
        this.links = new HashMap<>(1);
        this.presentations = new HashMap<>(1);
        this.collection = new HashMap<>(1);
        this.prices = ImmutableMap.of();
        this.exceptions =  ImmutableMap.of();
    }
}
