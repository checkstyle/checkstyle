<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text" />
<xsl:strip-space elements="*"/>
<xsl:template match="error"> 
<xsl:value-of select="../@name"/>, <xsl:value-of select="@line"/>, <xsl:value-of select="@column"/>, <xsl:value-of select="@severity"/>, <xsl:value-of select="@message"/>, <xsl:value-of select="@source"/>
<xsl:text>
</xsl:text>
</xsl:template>
</xsl:stylesheet>

