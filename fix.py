f = 'src/test/java/com/puppycrawl/tools/checkstyle/TreeWalkerTest.java'
with open(f, 'r') as fh:
    content = fh.read()
content = content.replace('"28:9: "', '"29:9: "')
with open(f, 'w') as fh:
    fh.write(content)
print('Done')
