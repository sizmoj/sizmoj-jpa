package org.springside.examples.quickstart.data;

import org.springside.examples.quickstart.entity.Term;
import org.springside.modules.test.data.RandomData;

public class TermData {
	public static Term RandomTerm() {
		Term term = new Term();
		term.setName(randomName());
		return term;
	}

	private static String randomName() {
		return RandomData.randomName("name");
	}
}
