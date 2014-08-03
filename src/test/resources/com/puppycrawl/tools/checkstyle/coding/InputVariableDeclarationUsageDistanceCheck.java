package com.puppycrawl.tools.checkstyle.design;

public class InputVariableDeclarationUsageDistanceCheck {

	private static final int test1;

	static {
		int b;
		int d;
		{
			d = ++b;
		}
	}

	static {
		int c;
		int a = 3;
		int b = 2;
		{
			a = a + b;
			c = b;
		}
		{
			c--;
		}
		a = 7;
	}

	static {
		int a = -1;
		int b = 2;
		b++;
		int c = --b;
		a = b; // DECLARATION OF VARIABLE 'a' SHOULD BE HERE (distance = 2)
	}

	public InputVariableDeclarationUsageDistanceCheck(int test1) {
		int temp = -1;
		this.test1 = test1;
		temp = test1; // DECLARATION OF VARIABLE 'temp' SHOULD BE HERE (distance = 2)
	}

	public boolean testMethod() {
		int temp = 7;
		new InputVariableDeclarationUsageDistanceCheck(2);
		InputVariableDeclarationUsageDistanceCheck(temp); // DECLARATION OF VARIABLE 'temp' SHOULD BE HERE (distance = 2)
		boolean result = false;
		String str = "";
		if (test1 > 1) {
			str = "123";
			result = true;
		}
		return result;
	}

	public void testMethod2() {
		int count;
		int a = 3;
		int b = 2;
		{
			a = a
					+ b
					- 5
					+ 2
					* a;
			count = b; // DECLARATION OF VARIABLE 'count' SHOULD BE HERE (distance = 2)
		}
	}

	public void testMethod3() {
		int count;
		int a = 3;
		int b = 3;
		a = a + b;
		b = a + a;
		testMethod2();
		count = b; // DECLARATION OF VARIABLE 'count' SHOULD BE HERE (distance = 4)
	}

	public void testMethod4(int arg) {
		int d;
		for (int i = 0; i < 10; i++) {
			d++;
			if (i > 5) {
				d += arg;
			}
		}

		String ar[] = { "1", "2" };
		for (String st : ar) {
			System.out.println(st);
		}
	}

	public void testMethod5() {
		int arg = 7;
		boolean b = true;
		boolean bb = false;
		if (b)
			if (!bb)
				b = false;
		testMethod4(arg); // DECLARATION OF VARIABLE 'arg' SHOULD BE HERE (distance = 2)
	}

	public void testMethod6() {
		int blockNumWithSimilarVar = 3;
		int dist = 0;
		int index = 0;
		int block = 0;

		if (blockNumWithSimilarVar <= 1) {
			do {
				dist++;
				if (block > 4) {
					break;
				}
				index++;
				block++;
			} while (index < 7);
		} else {
			while (index < 8) {
				dist += block;
				index++;
				block++;
			}
		}
	}

	public boolean testMethod7(int a) {
		boolean res;
		switch (a) {
		case 1:
			res = true;
			break;
		default:
			res = false;
		}
		return res;
	}

	public void testMethod8() {
		int b;
		int c;
		int m;
		int n;
		{
			c++;
			b++;
		}
		{
			n++; // DECLARATION OF VARIABLE 'n' SHOULD BE HERE (distance = 2)
			m++; // DECLARATION OF VARIABLE 'm' SHOULD BE HERE (distance = 3)
			b++;
		}
	}

	public void testMethod9() {
		boolean result = false;
		boolean b1 = true;
		boolean b2 = false;
		if (b1) {
			if (!b2) {
				result = true;
			}
			result = true;
		}
	}

	public boolean testMethod10() {
		boolean result;
		try {
			result = true;
		} catch (IOException e) {
			result = false;
		} finally {
			result = false;
		}
		return result;
	}

	public void testMethod11() {
		int a = 0;
		int b = 10;
		boolean result;
		try {
			b--;
		} catch (IOException e) {
			b++;
			result = false; // DECLARATION OF VARIABLE 'result' SHOULD BE HERE (distance = 2)
		} finally {
			a++;
		}
	}

	public void testMethod12() {
		boolean result = false;
		boolean b3 = true;
		boolean b1 = true;
		boolean b2 = false;
		if (b1) {
			if (b3) {
				if (!b2) {
					result = true;
				}
				result = true;
			}
		}
	}

	public void testMethod13() {
		int i = 9;
		int j = 6;
		int g = i + 8;
		int k = j + 10;
	}

	public void testMethod14() {
		Session s = openSession();
		Transaction t = s.beginTransaction();
		A a = new A();
		E d1 = new E();
		C1 c = new C1();
		E d2 = new E();
		a.setForward(d1);
		d1.setReverse(a);
		c.setForward(d2); // DECLARATION OF VARIABLE 'c' SHOULD BE HERE (distance = 3)
							// DECLARATION OF VARIABLE 'd2' SHOULD BE HERE (distance = 3)
		d2.setReverse(c);
		Serializable aid = s.save(a);
		Serializable d2id = s.save(d2);
		t.commit(); // DECLARATION OF VARIABLE 't' SHOULD BE HERE (distance = 5)
		s.close();
	}

	public boolean isCheckBoxEnabled(TreePath path) {
		DataLabelModel model = (DataLabelModel) getModel();
		if (recursiveState) {
			for (int index = 0; index < path.getPathCount(); ++index) {
				int nodeIndex = model.getNodeIndex(path.getPathComponent(index));
				if (disabled.contains(nodeIndex)) {
					return false;
				}
			}
		} else {
			int nodeIndex = model.getNodeIndex(path.getLastPathComponent());
			if (disabled.contains(nodeIndex)) {
				return false;
			}
		}
		return true;
	}

	public Object readObject(IObjectInputStream in) throws IOException {
		SimpleDay startDay = new SimpleDay(in.readInt());
		SimpleDay endDay = new SimpleDay(in.readInt());
		return new SimplePeriod(startDay, endDay);
	}

	public int[] getSelectedIndices() {
		int[] selected = new int[paths.length];
		DataLabelModel model = (DataLabelModel) getModel();
		int a = 0;
		a++;
		for (int index = 0; index < paths.length; ++index) {
			selected[index] = model.getNodeIndex(paths[index].getLastPathComponent()); // DECLARATION OF VARIABLE 'selected' SHOULD BE HERE (distance = 2)
																						// DECLARATION OF VARIABLE 'model' SHOULD BE HERE (distance = 2)
		}
		return selected;
	}

	public void testMethod15() {
		String confDebug = subst(element.getAttribute(CONFIG_DEBUG_ATTR));
		if (!confDebug.equals("") && !confDebug.equals("null")) {
			LogLog.warn("The \"" + CONFIG_DEBUG_ATTR + "\" attribute is deprecated.");
			LogLog.warn("Use the \"" + INTERNAL_DEBUG_ATTR + "\" attribute instead.");
			LogLog.setInternalDebugging(OptionConverter.toBoolean(confDebug, true));
		}

		int i = 0;
		int k = 7;
		boolean b = false;
		for (; i < k; i++) {
			b = true;
			k++;
		}

		int sw;
		switch (i) {
		case 0:
			k++;
			sw = 0; // DECLARATION OF VARIABLE 'sw' SHOULD BE HERE (distance = 2)
			break;
		case 1:
			b = false;
			break;
		default:
			b = true;
		}

		int wh;
		b = true;
		do {
			k--;
			i++;
		} while (wh > 0); // DECLARATION OF VARIABLE 'wh' SHOULD BE HERE (distance = 2)

		if (wh > 0) {
			k++;
		} else if (!b) {
			i++;
		} else {
			i--;
		}
	}

	public void testMethod16() {
		int wh = 1;
		if (i > 0) {
			k++;
		} else if (wh > 0) {
			i++;
		} else {
			i--;
		}
	}
	
	protected JMenuItem createSubMenuItem(LogLevel level) {
	    final JMenuItem result = new JMenuItem(level.toString());
	    final LogLevel logLevel = level;
	    result.setMnemonic(level.toString().charAt(0));
	    result.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        showLogLevelColorChangeDialog(result, logLevel); // DECLARATION OF VARIABLE 'logLevel' SHOULD BE HERE (distance = 2)
	      }
	    });

	    return result;

	  }
	
	public static Color darker(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));

        if (red < 0) {
            red = 0;
        } else if (red > 255) {
            red = 255;
        }
        if (green < 0) { // DECLARATION OF VARIABLE 'green' SHOULD BE HERE (distance = 2)
            green = 0;
        } else if (green > 255) {
            green = 255;
        }
        if (blue < 0) { // DECLARATION OF VARIABLE 'blue' SHOULD BE HERE (distance = 3)
            // blue = 0;
        }

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }
	
	public void testFinal() {
		AuthUpdateTask authUpdateTask = null;
		final long intervalMs = 30 * 60000L; // 30 min

        authUpdateTask = new AuthUpdateTask(authCheckUrl, authInfo, new IAuthListener() {
            @Override
            public void authTokenChanged(String cookie, String token) {
                fireAuthTokenChanged(cookie, token);
            }
        });

        Timer authUpdateTimer = new Timer("Auth Guard", true);
        authUpdateTimer.schedule(authUpdateTask, intervalMs / 2, intervalMs); // DECLARATION OF VARIABLE 'intervalMs' SHOULD BE HERE (distance = 2)
	}
	
	public void testForCycle() {
		int filterCount = 0;
		for (int i = 0; i < 10; i++, filterCount++) {
			int abc = 0;
			System.out.println(abc);

			for (int j = 0; j < 10; j++) {
				abc = filterCount;
				System.out.println(abc);
			}
		}
	}
	
	public void testIssue32_1()
    {
        Option srcDdlFile = OptionBuilder.create("f");
        Option logDdlFile = OptionBuilder.create("o");
        Option help = OptionBuilder.create("h");

        Options options = new Options();
        options.something();
        options.something();
        options.something();
        options.something();
        options.addOption(srcDdlFile, logDdlFile, help); // distance=1
    }

    public void testIssue32_2()
    {
        int mm = Integer.parseInt(time.substring(div + 1).trim());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeNow);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, hh);
        cal.set(Calendar.MINUTE, mm); // distance=1
    }
    
    public void testIssue32_3(MyObject[] objects) {
        Calendar cal = Calendar.getInstance();
        for(int i=0; i<objects.length; i++) {
            objects[i].setEnabled(true);
            objects[i].setColor(0x121212);
            objects[i].setUrl("http://google.com");
            objects[i].setSize(789);
            objects[i].setCalendar(cal); // distance=1
        }
    }
    
    public void testIssue32_4(boolean flag) {
        StringBuilder builder = new StringBuilder();
        builder.append("flag is ");
        builder.append(flag);
        final String line = ast.getLineNo();
        if(flag) {
            builder.append("line of AST is:");
            builder.append("\n");
            builder.append(String.valueOf(line)); //distance=1
            builder.append("\n");
        }
        return builder.toString();
    }
    
    public void testIssue32_5() {
        Option a;
        Option b;
        Option c;
        boolean isCNull = isNull(c); // distance=1
        boolean isBNull = isNull(b); // distance=1
        boolean isANull = isNull(a); // distance=1
    }
    
    public void testIssue32_6() {
        Option aOpt;
        Option bOpt;
        Option cOpt;
        isNull(cOpt); // distance = 1
        isNull(bOpt); // distance = 2
        isNull(aOpt); // distance = 3
    }
    
    public void testIssue32_7() {
        String line = "abc";
        writer.write(line);
        line.charAt(1);
        builder.append(line);
        test(line, line, line);
    }
    
    public void testIssue32_8(Writer w1, Writer w2, Writer w3) {
        String l1="1", l2="2", l3="3";
        w1.write(l3); //distance=1
        w2.write(l2); //distance=2
        w3.write(l1); //distance=3
    }
    
    public void testIssue32_9() {
        Options options = new Options();
        Option myOption = null;
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        System.out.println("message");
        myOption.setArgName("abc"); // distance=7
    }
    
    public void testIssue32_10() {
        Options options = new Options();
        Option myOption = null;
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        options.addBindFile(null);
        myOption.setArgName("q"); // distance=6
    }
    
    public int testIssue32_11(File toDir)
            throws IOException, FTPException,
            ParseException, InterruptedException
    {
        int count = 0;
        FTPFile[] files = client.dirDetails(".");

        log.info("Data archivation started");
        archiveOldData(archDir, files);
        log.info("Data archivation finished");

        if (files == null || files.length == 0) {
            warn("No files on a remote site");
        }
        else {
            log.debug("Files on remote site: " + files.length);

            for (FTPFile ftpFile : files) {
                if (!file.exists()) {
                    getFile(client, ftpFile, file);
                    file.setLastModified(ftpFile.lastModified().getTime());
                    count++;
                }
            }
        }

        client.quit();

        return count;
    }
    
    //////////////////////////////////////////////////
    // False positive. Will be fixed in future.
    //////////////////////////////////////////////////
    private TreeMapNode buildTree(List<Object[]> tree)
    {
        state.clear();
        revState.clear();
        TreeMapNode root = null;
        for (Object[] s : tree) {
            Integer id = (Integer) s[0];
            String label = (String) s[1];
            Integer parentId = (Integer) s[2]; ///!!!!!!!!
            Number weight = (Number) s[3];
            Number value = (Number) s[4];
            Integer childCount = (Integer) s[5];
            TreeMapNode node;
            if (childCount == 0) {
                node = new TreeMapNode(label,
                        weight != null ? weight.doubleValue() : 0.0,
                        new DefaultValue(value != null ? value.doubleValue()
                                : 0.0));
            }
            else {
                node = new TreeMapNode(label);
            }
            state.put(id, node);
            revState.put(node, id);
            if (parentId == null || parentId == -1) { ///!!!!!!!
                root = node;
            }
            else {
                state.get(parentId).add(node);
            }
        }
        return root;
    }
}
