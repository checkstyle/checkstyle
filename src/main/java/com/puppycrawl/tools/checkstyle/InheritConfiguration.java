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

package com.puppycrawl.tools.checkstyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * InheritConfiguration for inherit the config with parent attribute on root module.
 */
public final class InheritConfiguration extends DefaultConfiguration {

    private static final long serialVersionUID = 2331785991766158817L;

    /**
     * The parent configuration.
     */
    private final Configuration parentConfig;

    /**
     * Instantiates a configuration with parent.
     *
     * @param parent The parent configuration.
     * @param name the name for this InheritConfiguration.
     * @param threadModeSettings the thread mode configuration.
     */
    public InheritConfiguration(final Configuration parent, final String name,
        final ThreadModeSettings threadModeSettings) {
        super(name, threadModeSettings);
        parentConfig = parent;
    }

    /**
     * Instantiates a configuration with parent.
     *
     * @param parent The parent configuration.
     * @param name the name for this InheritConfiguration.
     */
    public InheritConfiguration(final Configuration parent, final String name) {
        super(name);
        parentConfig = parent;
    }

    void doMergeParent() {
        try {
            doMergeParent(parentConfig, this);
        }
        catch (CheckstyleException ex) {
            throw new IllegalStateException("doMergeParent error:" + ex.getMessage(), ex);
        }
    }

    /**
     * Merge parent config to current config.
     *
     * @param parent Parent config.
     * @param current  Current config.
     * @throws CheckstyleException if there is a parent configuration error.
     */
    static void doMergeParent(final Configuration parent, final DefaultConfiguration current)
            throws CheckstyleException {
        mergeAttributes(parent, current);
        mergeMessages(parent, current);
        mergeChildren(parent, current);
    }

    private static void mergeChildren(final Configuration parent,
            final DefaultConfiguration current) throws CheckstyleException {
        final Configuration[] children = parent.getChildren();
        final Map<String, Configuration> parents = toChildrenMap(children);
        final boolean parentHasChildren = !parents.isEmpty();
        final List<Configuration> currentChildren = current.theChildren();
        if (parentHasChildren && currentChildren.size() < 1) {
            currentChildren.addAll(parents.values());
        }
        else if (parentHasChildren) {
            final Map<String, Configuration> currents = toChildrenMap(current.getChildren());
            final Collection<String> addKeys = new ArrayList<>(parents.keySet());
            addKeys.removeAll(currents.keySet());
            for (final String id : addKeys) {
                currentChildren.add(parents.get(id));
            }
            final Collection<String> mergeKeys = intersection(parents.keySet(), currents.keySet());
            for (final String id : mergeKeys) {
                final Configuration parentChild = parents.get(id);
                final DefaultConfiguration currentChild = (DefaultConfiguration) currents.get(id);
                doMergeParent(parentChild, currentChild);
            }
        }
    }

    private static void mergeMessages(final Configuration parent,
            final DefaultConfiguration current) {
        final Map<String, String> currentMessages = current.getMessages();
        final Map<String, String> messageMap;
        if (parent instanceof DefaultConfiguration) {
            messageMap = ((DefaultConfiguration) parent).theMessages();
        }
        else {
            messageMap = parent.getMessages();
        }
        for (final Map.Entry<String, String> entry : messageMap.entrySet()) {
            final String key = entry.getKey();
            if (!currentMessages.containsKey(key)) {
                currentMessages.put(key, entry.getValue());
            }
        }
    }

    private static void mergeAttributes(final Configuration parent,
            final DefaultConfiguration current) throws CheckstyleException {
        final String[] attributeNames = parent.getAttributeNames();
        final Map<String, String> currentAttributeMap = current.theAttributeMap();
        for (final String attr : attributeNames) {
            if (!currentAttributeMap.containsKey(attr)) {
                currentAttributeMap.put(attr, parent.getAttribute(attr));
            }
        }
    }

    private static Collection<String> intersection(final Collection<String> list1,
        final Collection<String> list2) {
        final List<String> ret = new ArrayList<>();
        for (final String name : list1) {
            if (list2.contains(name)) {
                ret.add(name);
            }
        }
        return ret;
    }

    private static Map<String, Configuration> toChildrenMap(final Configuration... children) {
        final Map<String, Configuration> map;
        if (children.length < 1) {
            map = Collections.emptyMap();
        }
        else {
            map = new HashMap<>();
            for (Configuration child : children) {
                String id = ((DefaultConfiguration) child).theAttributeMap().get("id");
                if (id == null) {
                    id = child.getName();
                }
                map.put(id, child);
            }
        }
        return map;
    }
}
