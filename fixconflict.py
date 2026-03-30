f = 'src/test/java/com/puppycrawl/tools/checkstyle/bdd/InlineConfigParser.java'
with open(f, 'r') as fh:
    lines = fh.readlines()

# Remove conflict markers and keep our version (without WhitespaceAfterCheck)
new_lines = []
skip = False
for line in lines:
    if '<<<<<<< HEAD' in line:
        skip = True
        continue
    if '=======' in line and skip:
        skip = False
        continue
    if '>>>>>>> ' in line:
        continue
    if skip:
        continue
    new_lines.append(line)

with open(f, 'w') as fh:
    fh.writelines(new_lines)
print('Done')
