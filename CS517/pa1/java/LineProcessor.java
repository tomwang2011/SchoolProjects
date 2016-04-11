/*
 * Copyright 2016: smanna@cpp.edu
 * Please do not change any public method's header.
 * Feel free to include your own methods/variables as required.
 */
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineProcessor {

	public LineProcessor() {
		// Constructor
		// TODO(student): Feel free to add your initialization code, if any.
	}

	private HashSet<String> findEmails(String line) {
		HashSet<String> set = new HashSet<>();

		Pattern p = Pattern.compile(
			"([a-z]\\w+(_|-|\\.|\\sdot\\s){0,1}\\w*(\\s*&#x40;\\s*|\\s*@\\s*|(\\s+\\(*(at)\\)*\\s+))((\\w+(\\.|\\sdot\\s))+\\w+|(\\w+\\s)+\\b(edu)|(org)|(io)|(com)|(net)\\b))");

		line = line.toLowerCase();

		Matcher m = p.matcher(line);

		HashSet<String> uneditSet = new HashSet<>();

		while (m.find()) {
			uneditSet.add(m.group(1));
		}

		for (String s : uneditSet) {
			s = s.replaceAll(
				"\\s*&#x40;\\s*|\\s*@\\s*|(\\s+\\(*(at)\\)*\\s+)", "@");

			s = s.replaceAll("\\sdot\\s|\\s", ".");

			s = s.trim();

			set.add(s);
		}

		return set;
	}

	private HashSet<String> findPhoneNumbers(final String line) {
		HashSet<String> set = new HashSet<>();

		Pattern p = Pattern.compile(
			"((?:\\D|^){1}(\\(*\\d\\d\\d\\)*\\D\\d\\d\\d\\D\\d\\d\\d\\d)(?:\\D|$){1})");

		Matcher m = p.matcher(line);

		HashSet<String> uneditSet = new HashSet<>();

		while (m.find()) {
			uneditSet.add(m.group(2));
		}

		for (String s : uneditSet) {
			StringBuilder sb = new StringBuilder();

			for (char c : s.toCharArray()) {
				if (Character.isDigit(c)) {
					sb.append(c);
				}
				if (sb.length() == 3 || sb.length() == 7) {
					sb.append('-');
				}
			}

			set.add(sb.toString());
		}
		return set;
	}

	public HashSet<String> processLine(String line) {
		// You should not be modifying this method.
		HashSet<String> email = findEmails(line);
		HashSet<String> phone = findPhoneNumbers(line);
		HashSet<String> email_n_phones = new HashSet<String>();
		for (String e : email) {
			email_n_phones.add("e\t" + e);
		}
		for (String p : phone) {
			email_n_phones.add("p\t" + p);
		}
		return email_n_phones;
	}
}
