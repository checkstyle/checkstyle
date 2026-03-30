f = 'src/test/resources/com/puppycrawl/tools/checkstyle/treewalker/InputTreeWalkerMultiCheckOrder.java'
with open(f, 'r') as fh:
    lines = fh.readlines()
# Insert blank line after WhitespaceAroundCheck tokens (after line 17, index 16+1=17)
lines.insert(17, '\n')
# Insert blank line after WhitespaceAfterCheck tokens (now at index 23+1=24)
lines.insert(24, '\n')
# Remove blank line after package (now at index 26) to save 1 line
del lines[26]
# Remove boolean test = true line (now at index 28) to save 1 line
del lines[28]
# Fix if line to not need boolean variable
for i, line in enumerate(lines):
    if 'if(test)' in line:
        lines[i] = line.replace('if(test)', 'if(true)')
with open(f, 'w') as fh:
    fh.writelines(lines)
print('Done')
for i, line in enumerate(lines):
    print(i+1, repr(line))
