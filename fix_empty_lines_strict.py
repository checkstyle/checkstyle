import subprocess
import os

files = subprocess.check_output(['git', 'diff', '--name-only', 'HEAD~1']).decode('utf-8').splitlines()

for f in files:
    if not f.endswith('.java'): continue
    
    try:
        orig_content = subprocess.check_output(['git', 'show', 'HEAD~1:' + f]).decode('utf-8')
    except subprocess.CalledProcessError:
        continue
    
    orig_lines = orig_content.splitlines()
    with open(f, 'r') as file:
        new_lines = file.read().splitlines()
    
    # We want to identify empty lines in new_lines that follow `{` and are NOT in orig_lines.
    # To do this safely, we can just check if an empty line after `{` was added.
    
    res = []
    i = 0
    while i < len(new_lines):
        line = new_lines[i]
        res.append(line)
        
        if line.strip().endswith('{'):
            # Check if next lines are empty
            while i + 1 < len(new_lines) and new_lines[i+1].strip() == '':
                empty_line = new_lines[i+1]
                # Was this empty line after this `{` in orig_lines?
                # Let's find the matching `{` in orig_lines
                found_in_orig = False
                for j, o_line in enumerate(orig_lines):
                    if o_line == line:
                        if j + 1 < len(orig_lines) and orig_lines[j+1].strip() == '':
                            found_in_orig = True
                        break
                
                if found_in_orig:
                    # Keep it
                    res.append(empty_line)
                    i += 1
                else:
                    # Skip it (remove)
                    i += 1
        i += 1
        
    if res != new_lines:
        with open(f, 'w') as file:
            file.write('\n'.join(res) + '\n')

