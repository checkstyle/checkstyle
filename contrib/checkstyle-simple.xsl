<?xml version="1.0"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
    <head>
    <title>Sun Coding Style Violations</title>
    </head>
    <body bgcolor="#FFFFEF">
    <p><b>Coding Style Check Results</b></p>
    <table border="1" cellspacing="0" cellpadding="2">
	<tr bgcolor="#CC9966">
	    <th colspan="2"><b>Summary</b></th>
	</tr>
	<tr bgcolor="#CCF3D0">
	    <td>Total files checked</td>
	    <td><xsl:number level="any" value="count(descendant::file)"/></td>
	</tr>
	<tr bgcolor="#F3F3E1">
	    <td>Files with errors</td>
	    <td><xsl:number level="any" value="count(descendant::file[error])"/></td>
	</tr>
	<tr bgcolor="#CCF3D0">
	    <td>Total errors</td>
	    <td><xsl:number level="any" value="count(descendant::error)"/></td>
	</tr>
	<tr bgcolor="#F3F3E1">
	    <td>Errors per file</td>
	    <td><xsl:number level="any" value="count(descendant::error) div count(descendant::file)"/></td>
	</tr>
    </table>
    <hr align="left" width="95%" size="1"/>
    <p>The following are violations of the Sun Coding-Style Standards:</p>
    <p/>
	<xsl:apply-templates/>
    </body>
  </html>
</xsl:template>

<xsl:template match="file[error]">
    <table bgcolor="#AFFFFF" width="95%" border="1" cellspacing="0" cellpadding="2">
	<tr>
	    <th> File: </th>
	    <td>
		<xsl:value-of select="@name"/>
	    </td>
	</tr>
    </table>
    <table bgcolor="#DFFFFF" width="95%" border="1" cellspacing="0" cellpadding="2">
	<tr>
	    <th> Line Number </th>
	    <th> Error Message </th>
	</tr>
	<xsl:apply-templates select="error"/>
    </table>
    <p/>
</xsl:template>

<xsl:template match="error">
    <tr>
	<td>
	    <xsl:value-of select="@line"/>
	</td>
	<td>
	    <xsl:value-of select="@message"/>
	</td>
    </tr>
</xsl:template>

</xsl:stylesheet>
