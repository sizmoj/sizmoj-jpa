package org.springside.examples.quickstart.data;

import org.springside.examples.quickstart.entity.Tag;
import org.springside.modules.test.data.RandomData;

public class TagData {
	public static Tag RandomTag() {
		Tag tag = new Tag();
		tag.setName(RandomName());
		return tag;
	}

	private static String RandomName() {
		return RandomData.randomName("tag");
	}
}
