# Enable all rules
all

# The maximum allowed line length is 100
rule 'MD013', :line_length => 100

# We do not use some parsers, our md files are for Github mostly, and it works fine with bare URLs
exclude_rule 'MD034'
