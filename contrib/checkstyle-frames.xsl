<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:lxslt="http://xml.apache.org/xslt"
    xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
    extension-element-prefixes="redirect">

<!--
 The Apache Software License, Version 1.1

 Copyright (c) 2002 The Apache Software Foundation.  All rights
 reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in
    the documentation and/or other materials provided with the
    distribution.

 3. The end-user documentation included with the redistribution, if
    any, must include the following acknowlegement:
       "This product includes software developed by the
        Apache Software Foundation (http://www.apache.org/)."
    Alternately, this acknowlegement may appear in the software itself,
    if and wherever such third-party acknowlegements normally appear.

 4. The names "The Jakarta Project", "Ant", and "Apache Software
    Foundation" must not be used to endorse or promote products derived
    from this software without prior written permission. For written
    permission, please contact apache@apache.org.

 5. Products derived from this software may not be called "Apache"
    nor may "Apache" appear in their names without prior written
    permission of the Apache Group.

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.
 ====================================================================

 This software consists of voluntary contributions made by many
 individuals on behalf of the Apache Software Foundation.  For more
 information on the Apache Software Foundation, please see
 <http://www.apache.org/>.
 -->

    <xsl:output method="html" indent="yes" encoding="US-ASCII"/>
    <xsl:decimal-format decimal-separator="." grouping-separator="," />

    <xsl:param name="output.dir" select="'.'"/>

    <xsl:template match="checkstyle">
        <!-- create the index.html -->
        <redirect:write file="{$output.dir}/index.html">
            <xsl:call-template name="index.html"/>
        </redirect:write>

        <!-- create the stylesheet.css -->
        <redirect:write file="{$output.dir}/stylesheet.css">
            <xsl:call-template name="stylesheet.css"/>
        </redirect:write>

        <!-- create the overview-summary.html at the root -->
        <redirect:write file="{$output.dir}/overview-frame.html">
            <xsl:apply-templates select="." mode="overview"/>
        </redirect:write>

        <!-- create the all-classes.html at the root -->
        <redirect:write file="{$output.dir}/allclasses-frame.html">
            <xsl:apply-templates select="." mode="all.classes"/>
        </redirect:write>

        <!-- process all files -->
        <xsl:apply-templates select="file"/>
    </xsl:template>

    <xsl:template name="index.html">
        <html>
            <head>
                <title>CheckStyle Audit</title>
            </head>
            <frameset cols="20%,80%">
                <frame src="allclasses-frame.html" name="fileListFrame"/>
                <frame src="overview-frame.html" name="fileFrame"/>
            </frameset>
            <noframes>
                <h2>Frame Alert</h2>
                <p>
                    This document is designed to be viewed using the frames feature. If you see this message, you are using a non-frame-capable web client.
                </p>
            </noframes>
        </html>
    </xsl:template>

    <xsl:template name="pageHeader">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td class="text-align:right"><h2>CheckStyle Audit</h2></td>
            </tr>
            <tr>
                <td class="text-align:right">Designed for use with <a href='http://checkstyle.sourceforge.net/'>CheckStyle</a> and <a href='http://jakarta.apache.org'>Ant</a>.</td>
            </tr>
        </table>
        <hr size="1"/>
    </xsl:template>

    <xsl:template match="checkstyle" mode="overview">
        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="stylesheet.css"/>
            </head>
            <body>
                <!-- page header -->
                <xsl:call-template name="pageHeader"/>

                <!-- Summary part -->
                <xsl:apply-templates select="." mode="summary"/>
                <hr size="1" width="100%" align="left"/>

                <!-- File list part -->
                <xsl:apply-templates select="." mode="filelist"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="stylesheet.css">
        .bannercell {
        border: 0px;
        padding: 0px;
        }
        body {
        margin-left: 10;
        margin-right: 10;
        font:normal 80% arial,helvetica,sanserif;
        background-color:#FFFFFF;
        color:#000000;
        }
        .a td {
        background: #efefef;
        }
        .b td {
        background: #fff;
        }
        th, td {
        text-align: left;
        vertical-align: top;
        }
        th {
        font-weight:bold;
        background: #ccc;
        color: black;
        }
        table, th, td {
        font-size:100%;
        border: none
        }
        table.log tr td, tr th {

        }
        h2 {
        font-weight:bold;
        font-size:140%;
        margin-bottom: 5;
        }
        h3 {
        font-size:100%;
        font-weight:bold;
        background: #525D76;
        color: white;
        text-decoration: none;
        padding: 5px;
        margin-right: 2px;
        margin-left: 2px;
        margin-bottom: 0;
        }
    </xsl:template>

    <!--
    Creates an all-classes.html file that contains a link to all files.
    -->
    <xsl:template match="checkstyle" mode="all.classes">
        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="stylesheet.css"/>
            </head>
            <body>
                <h2>Files</h2>
                <p>
                    <table width="100%">
                        <!-- For each file create its part -->
                        <xsl:apply-templates select="file" mode="all.classes">
                            <xsl:sort select="@name"/>
                        </xsl:apply-templates>
                    </table>
                </p>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="checkstyle" mode="filelist">
        <h3>Files</h3>
        <table class="log" border="0" cellpadding="5" cellspacing="2" width="100%">
            <tr>
                <th>Name</th>
                <th>Errors</th>
            </tr>
            <xsl:apply-templates select="file" mode="filelist">
                <xsl:sort select="@name"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>

    <xsl:template match="file" mode="filelist">
        <tr>
            <xsl:call-template name="alternated-row"/>
            <td nowrap="nowrap">
                <a>
                    <xsl:attribute name="href">
                        <xsl:text>files/</xsl:text><xsl:value-of select="@name"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    <xsl:value-of select="@name"/>
                </a>
            </td>
            <td><xsl:value-of select="count(error)"/></td>
        </tr>
    </xsl:template>

    <xsl:template match="file" mode="all.classes">
        <tr>
            <td nowrap="nowrap">
                <a target="fileFrame">
                    <xsl:attribute name="href">
                        <xsl:text>files/</xsl:text><xsl:value-of select="@name"/><xsl:text>.html</xsl:text>
                    </xsl:attribute>
                    <xsl:value-of select="@name"/>
                </a>
            </td>
        </tr>
    </xsl:template>

    <!--
    transform string like a/b/c to ../../../
    @param path the path to transform into a descending directory path
    -->
    <xsl:template name="path">
        <xsl:param name="path"/>
        <xsl:if test="contains($path,'/')">
            <xsl:text>../</xsl:text>
            <xsl:call-template name="path">
                <xsl:with-param name="path"><xsl:value-of select="substring-after($path,'/')"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="not(contains($path,'/')) and not($path = '')">
            <xsl:text>../</xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="file">
        <redirect:write file="{$output.dir}/files/{@name}.html">
            <html>
                <head>
                    <link rel="stylesheet" type="text/css">
                        <xsl:attribute name="href"><xsl:call-template name="path"><xsl:with-param name="path" select="@name"/></xsl:call-template><xsl:text>stylesheet.css</xsl:text></xsl:attribute>
                    </link>
                </head>
                <body>
                    <xsl:call-template name="pageHeader"/>
                    <h3>File <xsl:value-of select="@name"/></h3>
                    <table class="log" border="0" cellpadding="5" cellspacing="2" width="100%">
                        <tr>
                            <th>Error Description</th>
                            <th>Line</th>
                        </tr>
                        <xsl:for-each select="error">
                            <tr>
                                <xsl:call-template name="alternated-row"/>
                                <td><xsl:value-of select="@message"/></td>
                                <td><xsl:value-of select="@line"/></td>
                            </tr>
                        </xsl:for-each>
                    </table>
                </body>
            </html>
        </redirect:write>
    </xsl:template>

    <xsl:template match="checkstyle" mode="summary">
        <h3>Summary</h3>
        <xsl:variable name="fileCount" select="count(file)"/>
        <xsl:variable name="errorCount" select="count(file/error)"/>
        <table class="log" border="0" cellpadding="5" cellspacing="2" width="100%">
            <tr>
                <th>Files</th>
                <th>Errors</th>
            </tr>
            <tr>
                <xsl:call-template name="alternated-row"/>
                <td><xsl:value-of select="$fileCount"/></td>
                <td><xsl:value-of select="$errorCount"/></td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template name="alternated-row">
        <xsl:attribute name="class">
            <xsl:if test="position() mod 2 = 1">a</xsl:if>
            <xsl:if test="position() mod 2 = 0">b</xsl:if>
        </xsl:attribute>
    </xsl:template>
</xsl:stylesheet>

