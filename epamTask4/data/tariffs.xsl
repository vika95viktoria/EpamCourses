<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" />
    <xsl:template match="/">
        <Tariffs>
            <xsl:apply-templates />
        </Tariffs>
    </xsl:template>
    <xsl:template match="tariff">

    </xsl:template>
</xsl:stylesheet>