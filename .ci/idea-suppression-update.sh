#!/bin/bash

file="config/suppressions-xpath.xml"

# Check if file exists
if [ ! -f "$file" ]; then
    echo "File $file not found!"
    exit 1
fi

# Remove closing tag and add new suppression with closing tag
sed -i '/<\/suppressions>/d' "$file"
cat >> "$file" << 'EOF'
  <suppress-xpath
    files=".*"
    checks="WhitespaceAfterCheck"
    query="//ARRAY_INIT/COMMA[following-sibling::*[1][self::RCURLY]]"/>
</suppressions>
EOF

echo "Suppression added to $file"
