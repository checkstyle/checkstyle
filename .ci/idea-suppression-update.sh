#!/bin/bash

file="config/suppressions-xpath.xml"

if [ ! -f "$file" ]; then
    echo "File $file not found!"
    exit 1
fi

sed -i '/<\/suppressions>/d' "$file"
cat >> "$file" << 'EOF'
  <suppress-xpath
    files=".*"
    checks="WhitespaceAfterCheck"
    query="//ARRAY_INIT/COMMA[following-sibling::*[1][self::RCURLY]]"/>
  <suppress-xpath
    files=".*"
    checks="IndentationCheck"
    query="//VARIABLE_DEF//METHOD_CALL//ARRAY_INIT
    | //VARIABLE_DEF//METHOD_CALL//ARRAY_INIT//*
    | //VARIABLE_DEF//ARRAY_INIT | //ARRAY_INIT | //ARRAY_INIT//*"/>
</suppressions>
EOF

echo "Suppression added to $file"
