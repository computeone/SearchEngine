package com.search.data;

/*
 * 
 */
public class Token {
	private String text;
	private long id;
	private int[] code;;
	//token的最大长度
	private final int Token_Length = 1 << 20;
	/*
	 * text即文本，id即field的ID，offset即token在field中的位置
	 */
	//id是field的id号，offset是在当前文档中token在field中的偏移量
	public Token(String text, long id, int offset) throws TokenIDOverException {
		if (offset < Token_Length) {
			this.text = text;
			this.id = id + offset;
			this.set_unicode(Unicode(this.text));
		} else {
			throw new TokenIDOverException();
		}
	}

	private int[] Unicode(String str) {
		int[] unicode = new int[str.length()];
		char[] c = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {
			unicode[i] = Character.codePointAt(c, i);
		}
		return unicode;
	}

	public void set_unicode(int[] code) {
		this.code = code;
	}

	public int[] get_unicode() {
		return code;
	}

	public String getTerm() {
		return text;
	}

	public long getID() {
		return id;
	}
}
