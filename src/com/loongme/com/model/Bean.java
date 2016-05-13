package com.loongme.com.model;

import java.io.Serializable;

public class Bean implements Serializable {
	@Override
	public String toString() {
		return super.toString();
	}

	public String rc;
	public String operation;
	public String service;
	public String text;
	public Semantic semantic;

	public class Answer {
		public String text;
		public String type;

	}

	public class Semantic {
		public Slots slots;
	}

	public class Slots {
		public String code;
		public Datetime datetime;
		public String name;
		public String content;
	}

	public class Datetime {
		public String date;
		public String type;
		public String time;
		public String timeOrig;
	}
}
