f = 'src/test/resources/com/puppycrawl/tools/checkstyle/treewalker/InputTreeWalkerMultiCheckOrder.java'
with open(f, 'r') as fh:
    lines = fh.readlines()
# Add blank line after WhitespaceAfterCheck tokens, before */ (currently index 23)
lines.insert(23, '\n')
# Remove blank line after */ (now at index 25 after insert)
del lines[25]
with open(f, 'w') as fh:
    fh.writelines(lines)
for i, line in enumerate(lines):
    print(i+1, repr(line))
