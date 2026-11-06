/*
RedundantImport


*/

package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import java.util.*;
import java.util.ArrayList; // violation 'Import .* is covered by wildcard import .*'
import java.util.List; // violation 'Import .* is covered by wildcard import .*'

import java.awt.Component; // violation 'Import .* is covered by wildcard import .*'
import java.awt.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import java.util.Map.*;
import java.util.Map.Entry; // violation 'Import .* is covered by wildcard import .*'

import java.sql.Date;

import static java.util.Collections.*;
import static java.util.Collections.emptyList;

class InputRedundantImportWildcard {
    private List<String> list = new ArrayList<>();
}
