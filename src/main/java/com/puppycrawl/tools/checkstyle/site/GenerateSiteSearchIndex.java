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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Generates a JSON search index from the checkstyle site HTML pages.
 * The resulting {@code search-index.json} is written to the site root directory.
 */
public final class GenerateSiteSearchIndex {

    /** Logger for diagnostic messages. */
    private static final Logger LOG =
            Logger.getLogger(GenerateSiteSearchIndex.class.getName());

    /** CSS selector and JSON entry key for the page title. */
    private static final String TITLE = "title";

    /** CSS selector and JSON entry key for a named section. */
    private static final String SECTION = "section";

    /** CSS selector for the main body column. */
    private static final String BODY_COLUMN = "#bodyColumn";

    /** Character set used when parsing HTML files. */
    private static final String CHARSET_UTF8 = "UTF-8";

    /** Text prefix that identifies a redirect page. */
    private static final String REDIRECTING_TO = "Redirecting to";

    /** Default site output directory used when no argument is supplied. */
    private static final String DEFAULT_SITE_DIR = "target/site";

    /**
     * Private constructor; this utility class is not meant to be instantiated.
     */
    private GenerateSiteSearchIndex() {
    }

    /**
     * Walks the site directory for HTML documentation pages, indexes their content,
     * and writes a {@code search-index.json} file to that directory.
     *
     * @param args optional; {@code args[0]} overrides the default path {@code target/site}
     * @throws IOException if the directory cannot be walked or the index cannot be written
     */
    public static void main(String[] args) throws IOException {
        final String siteDirPath;
        if (args.length > 0) {
            siteDirPath = args[0];
        }
        else {
            siteDirPath = DEFAULT_SITE_DIR;
        }

        final Path siteDir = Path.of(siteDirPath);
        final List<Map<String, String>> entries = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(siteDir)) {
            paths.filter(htmlPath -> htmlPath.toString().endsWith(".html"))
                 .filter(GenerateSiteSearchIndex::isDocPage)
                 .forEach(htmlFile -> {
                     try {
                         indexPage(siteDir, htmlFile, entries);
                     }
                     catch (IOException exc) {
                         if (LOG.isLoggable(Level.WARNING)) {
                             LOG.log(Level.WARNING, "Skipping " + htmlFile, exc);
                         }
                     }
                 });
        }

        final Path out = siteDir.resolve("search-index.json");
        Files.writeString(out, toJson(entries));
        if (LOG.isLoggable(Level.INFO)) {
            LOG.info("Search index: " + entries.size() + " entries -> " + out);
        }
    }

    /**
     * Returns {@code true} if the given HTML file should be included in the search index.
     *
     * @param file the HTML file to evaluate
     * @return {@code true} when the file is a genuine documentation page
     */
    private static boolean isDocPage(Path file) {
        final boolean result;
        if (isExcludedByFilename(file.getFileName().toString())) {
            result = false;
        }
        else {
            result = !isRedirectPage(file);
        }
        return result;
    }

    /**
     * Returns {@code true} when the filename matches a pattern that identifies
     * generated report artefacts which should be excluded from the search index.
     *
     * @param filename the simple file name (not a full path)
     * @return {@code true} if the file should be excluded
     */
    private static boolean isExcludedByFilename(String filename) {
        final boolean isReportArtifact = filename.contains("jacoco")
                || filename.contains("jdepend")
                || filename.contains("cpd-")
                || filename.contains("pmd-");
        final boolean isSiteArtifact = filename.contains("surefire")
                || filename.contains("checkstyle-result")
                || filename.contains("dependencies")
                || filename.contains("xref");
        return filename.startsWith("_") || isReportArtifact || isSiteArtifact;
    }

    /**
     * Returns {@code true} when the given HTML file is a redirect page that
     * should be excluded from the search index.
     * Parse errors are treated as exclusions.
     *
     * @param file the HTML file to inspect
     * @return {@code true} if the page is a redirect or cannot be parsed
     */
    private static boolean isRedirectPage(Path file) {
        boolean redirect = false;
        try {
            final Document doc = Jsoup.parse(file.toFile(), CHARSET_UTF8);
            final String titleText = doc.select(TITLE).text().trim();
            if (titleText.startsWith(REDIRECTING_TO)) {
                redirect = true;
            }
            else {
                final Element body = doc.select(BODY_COLUMN).first();
                if (body != null && body.text().trim().startsWith(REDIRECTING_TO)) {
                    redirect = true;
                }
            }
        }
        catch (IOException exc) {
            if (LOG.isLoggable(Level.WARNING)) {
                LOG.log(Level.WARNING, "Could not parse " + file, exc);
            }
            redirect = true;
        }
        return redirect;
    }

    /**
     * Parses the HTML file and appends one search entry per page and per section
     * to {@code entries}.
     *
     * @param siteDir the root site directory, used to compute relative paths
     * @param file    the HTML file to index
     * @param entries the list to which new entries are appended
     * @throws IOException if the file cannot be read
     */
    private static void indexPage(Path siteDir, Path file,
                                  List<Map<String, String>> entries) throws IOException {
        final Document doc = Jsoup.parse(file.toFile(), CHARSET_UTF8);

        final String pageTitle = doc.select(TITLE).text()
                .replaceAll("\\s*[\\u2013-]\\s*checkstyle.*$", "").trim();
        final String relPath = siteDir.relativize(file).toString().replace('\\', '/');
        final String category = categorize(relPath);

        Element body = doc.select(BODY_COLUMN).first();
        if (body == null) {
            body = doc.select("main").first();
        }

        if (body != null) {
            indexBody(body, pageTitle, relPath, category, entries);
        }
    }

    /**
     * Adds a top-level page entry and one entry per named section found in
     * {@code body} to {@code entries}.
     *
     * @param body      the parsed body element
     * @param pageTitle the title of the containing page
     * @param relPath   the relative URL of the page
     * @param category  the category label for all entries on this page
     * @param entries   the list to which new entries are appended
     */
    private static void indexBody(Element body, String pageTitle,
                                  String relPath, String category,
                                  List<Map<String, String>> entries) {
        final String fullText = body.text();
        entries.add(buildEntry(pageTitle, pageTitle, fullText, relPath, category));

        final List<Element> sections = body.select(SECTION);
        for (final Element sec : sections) {
            indexSection(sec, pageTitle, relPath, category, entries);
        }
    }

    /**
     * Appends a search entry for a single HTML section to {@code entries}, unless
     * the section heading is blank or duplicates the page title.
     *
     * @param sec       the section element to index
     * @param pageTitle the title of the containing page
     * @param relPath   the relative URL of the page
     * @param category  the category label
     * @param entries   the list to which a new entry may be appended
     */
    private static void indexSection(Element sec, String pageTitle,
                                     String relPath, String category,
                                     List<Map<String, String>> entries) {
        String anchor = "";
        final Element anchorEl = sec.selectFirst("a[id]");
        if (anchorEl != null) {
            anchor = anchorEl.attr("id");
        }

        String heading = "";
        final Element headingEl = sec.selectFirst("h2, h3");
        if (headingEl != null) {
            heading = headingEl.text();
        }

        if (!heading.isBlank() && !heading.equalsIgnoreCase(pageTitle)) {
            final String sectionText = sec.text();
            final String url;
            if (anchor.isBlank()) {
                url = relPath;
            }
            else {
                url = relPath + '#' + anchor;
            }
            entries.add(buildEntry(pageTitle, heading, sectionText, url, category));
        }
    }

    /**
     * Creates an ordered search-index entry map from the supplied fields.
     *
     * @param title    the page title
     * @param section  the section heading (equals {@code title} for the page-level entry)
     * @param text     the full text of the section
     * @param url      the relative URL, optionally including a fragment anchor
     * @param category the category label (e.g. {@code "Check"}, {@code "Filter"})
     * @return a map representing one search-index entry
     */
    private static Map<String, String> buildEntry(String title, String section,
                                                   String text, String url,
                                                   String category) {
        final Map<String, String> entry = new LinkedHashMap<>();
        entry.put(TITLE, title);
        entry.put(SECTION, section);
        entry.put("text", text);
        entry.put("url", url);
        entry.put("category", category);
        return entry;
    }

    /**
     * Derives a human-readable category label from a site-relative HTML path.
     *
     * @param path the relative path of the HTML file within the site directory
     * @return the category string
     */
    private static String categorize(String path) {
        final String result;
        if (path.startsWith("checks/")) {
            result = "Check";
        }
        else if (path.startsWith("filters/")) {
            result = "Filter";
        }
        else if (path.startsWith("filefilters/")) {
            result = "File Filter";
        }
        else if (path.contains("releasenotes")) {
            result = "Release Notes";
        }
        else if (path.contains("config")) {
            result = "Configuration";
        }
        else {
            result = "Documentation";
        }
        return result;
    }

    /**
     * Serialises the list of search-index entry maps to a JSON array string.
     *
     * @param entries the entry maps to serialise
     * @return a JSON array string
     */
    private static String toJson(List<Map<String, String>> entries) {
        final StringBuilder stringBuilder = new StringBuilder("[");
        for (int idx = 0; idx < entries.size(); idx++) {
            if (idx > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append('{');
            final Map<String, String> entry = entries.get(idx);
            boolean first = true;
            for (final Map.Entry<String, String> mapEntry : entry.entrySet()) {
                if (!first) {
                    stringBuilder.append(',');
                }
                stringBuilder.append('"').append(mapEntry.getKey()).append("\":")
                        .append('"')
                        .append(mapEntry.getValue()
                                .replace("\\", "\\\\")
                                .replace("\"", "\\\"")
                                .replace("\n", "\\n")
                                .replace("\r", "\\r"))
                        .append('"');
                first = false;
            }
            stringBuilder.append('}');
        }
        return stringBuilder.append(']').toString();
    }
}
