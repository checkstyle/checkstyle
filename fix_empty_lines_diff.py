import subprocess
import os

# Get all files modified in the last commit
files = subprocess.check_output(['git', 'diff', '--name-only', 'HEAD~1']).decode('utf-8').splitlines()

for f in files:
    if not f.endswith('.java'): continue
    
    # Get original lines from HEAD~1
    try:
        orig_content = subprocess.check_output(['git', 'show', 'HEAD~1:' + f]).decode('utf-8')
    except subprocess.CalledProcessError:
        continue # File didn't exist or error
    orig_lines = orig_content.splitlines()
    
    with open(f, 'r') as file:
        lines = file.read().splitlines()
    
    new_lines = []
    i = 0
    while i < len(lines):
        line = lines[i]
        new_lines.append(line)
        
        # If line ends with {, check if next line is empty
        if line.strip().endswith('{') and i + 1 < len(lines) and lines[i+1].strip() == '':
            # Found an empty line right after {. Was it there in original?
            # Find this `{` in original lines
            found_in_orig = False
            for j, orig_line in enumerate(orig_lines):
                if orig_line == line:
                    if j + 1 < len(orig_lines) and orig_lines[j+1].strip() == '':
                        found_in_orig = True
                        break
            
            if not found_in_orig:
                # It wasn't in original, so we skip adding the empty line
                i += 1
        
        i += 1

    if new_lines != lines:
        with open(f, 'w') as file:
            file.write('\n'.join(new_lines) + '\n')

