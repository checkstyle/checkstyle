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
    public final ImmutableMap<String, ArrayList<Long>> perfSeries; // violation
    public final ImmutableMap<java.lang.String, ArrayList<Long>> peopleMap; // violation
    public final ImmutableMap<String, java.util.ArrayList<Long>> someMap; // violation
    public final ImmutableMap<java.lang.String, java.util.ArrayList<Long>> newMap; // violation
    public final ImmutableMap<java.lang.String, java.math.BigDecimal> orders;
    public final Optional<java.lang.Object> optionalOfObject; // violation
    public final Optional<Object> obj; // violation

    public String rqUID; // violation
    public Optional<String> rqTime; // violation
    public ImmutableMap<String, BigDecimal> rates; // violation
    public ImmutableMap<String, ArrayList<Long>> loans; // violation
    public ImmutableMap<java.lang.String, ArrayList<Long>> cards; // violation
    public ImmutableMap<String, java.util.ArrayList<Long>> values; // violation
    public ImmutableMap<java.lang.String, java.util.ArrayList<Long>> permissions; // violation

    public final Map<String, String> mapOfStrings; // violation
    public final java.util.Map<String, String> names; // violation
    public final java.util.Map<String, Object> links; // violation
    public final Map<String, Object> presentations; // violation
    public final Map<String, java.lang.Object> collection; // violation

    public final com.google.common.collect.ImmutableMap<String, BigDecimal> prices;
    public final com.google.common.collect.ImmutableMap<String, Object> exceptions; // violation

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
