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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

/**
 * InheritConfiguration for inherit the config with parent attribute on root module.
 */
public final class InheritConfiguration extends DefaultConfiguration {

    private static final long serialVersionUID = 2331785991766158817L;
    private final Configuration parentConfig;

    public InheritConfiguration(final Configuration parent, final String name, final ThreadModeSettings threadModeSettings) {
        super(name, threadModeSettings);
        this.parentConfig = parent;
    }

    public InheritConfiguration(final Configuration parent, final String name) {
        super(name);
        this.parentConfig = parent;
    }

    void doMergeParent() {
        try {
            doMergeParent(parentConfig, this);
        } catch (CheckstyleException ex) {
            throw new IllegalStateException("doMergeParent error:" + ex.getMessage(), ex);
        }
    }

    /**
     * Merge parent  config to current config.
     * @param parent - Parent config
     * @param current - Current config
     * @throws CheckstyleException - 
     */
    static void doMergeParent(final Configuration parent, final DefaultConfiguration current) throws CheckstyleException {
        final String[] attributeNames = parent.getAttributeNames();
        for (final String attr : attributeNames) {
            if (!current.attributeMap.containsKey(attr)) {
                current.attributeMap.put(attr, parent.getAttribute(attr));
            }
        }
        final Map<String, String> messageMap = parent.getMessages();
        for (final Map.Entry<String, String> entry : messageMap.entrySet()) {
            final String key = entry.getKey();
            if (!current.messages.containsKey(key)) {
                current.messages.put(key, entry.getValue());
            }
        }
        final Map<String, Configuration> parents = toChildrenMap(parent.getChildren());
        final boolean parentHasChildren = !parents.isEmpty();
        if (parentHasChildren && current.children.size() < 1) {
            current.children.addAll(parents.values());
        } else if (parentHasChildren) {
            final Map<String, Configuration> currents = toChildrenMap(current.getChildren());
            final Collection<String> addKeys = Lists.newArrayList(parents.keySet());
            addKeys.removeAll(currents.keySet());
            for (final String id : addKeys) {
                current.children.add(parents.get(id));
            }
            final Collection<String> mergeKeys = Sets.intersection(parents.keySet(), currents.keySet());
            for (final String id : mergeKeys) {
                final Configuration parentChild = parents.get(id);
                final DefaultConfiguration currentChild = (DefaultConfiguration) currents.get(id);
                doMergeParent(parentChild, currentChild);
            }
        }
    }

    private static Map<String, Configuration> toChildrenMap(final Configuration[] children) {
        final Map<String, Configuration> map;
        if (children.length < 1) {
            map = Collections.emptyMap();
        } else {
            map = new HashMap<>();
            for (Configuration child : children) {
                String id = ((DefaultConfiguration) child).attributeMap.get("id");
                if (id == null) {
                    id = child.getName();
                }
                map.put(id, child);
            }
        }
        return map;
    }
}
