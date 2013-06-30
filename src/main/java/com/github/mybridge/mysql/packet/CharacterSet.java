package com.github.mybridge.mysql.packet;

public final class CharacterSet {
	private String name;
	public static final CharacterSet big5_chinese_ci = new CharacterSet(
			"big5_chinese_ci");
	public static final CharacterSet latin2_czech_cs = new CharacterSet(
			"latin2_czech_cs");
	public static final CharacterSet dec8_swedish_ci = new CharacterSet(
			"dec8_swedish_ci");
	public static final CharacterSet cp850_general_ci = new CharacterSet(
			"cp850_general_ci");
	public static final CharacterSet latin1_german1_ci = new CharacterSet(
			"latin1_german1_ci");
	public static final CharacterSet hp8_english_ci = new CharacterSet(
			"hp8_english_ci");
	public static final CharacterSet koi8r_general_ci = new CharacterSet(
			"koi8r_general_ci");
	public static final CharacterSet latin1_swedish_ci = new CharacterSet(
			"latin1_swedish_ci");

	public static final CharacterSet latin2_general_ci = new CharacterSet(
			"latin2_general_ci");
	public static final CharacterSet swe7_swedish_ci = new CharacterSet(
			"swe7_swedish_ci");

	public CharacterSet(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
