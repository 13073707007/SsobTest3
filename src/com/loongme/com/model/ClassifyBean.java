package com.loongme.com.model;

import java.util.List;

public class ClassifyBean {
	/**
	 * servertype :
	 * [{"subName":[{"pid":1,"nikename":"蹇冪悊","name":"蹇冪悊鍖诲笀","id":7
	 * },{"pid":1,"nikename"
	 * :"鎵句笓瀹�","name":"涓撶鍖荤敓","id":8},{"pid":1,"nikename":
	 * "鍏荤敓","name":"鍏荤敓杈句汉"
	 * ,"id":9},{"pid":1,"nikename":"鍋ヨ韩","name":"鍋ヨ韩鏁欑粌","id"
	 * :10},{"pid":1,"nikename"
	 * :"鍑忚偉","name":"鍑忚偉婵\u20ac鍔卞笀","id":11},{"pid":1,"nikename"
	 * :null,"name":"瀹犵墿鍖荤敓"
	 * ,"id":12}],"pid":0,"nikename":null,"name":"鍋ュ悍","id":
	 * 1},{"subName":[{"pid"
	 * :2,"nikename":"寮\u20ac閿�","name":"寮\u20ac閿�","id":13}
	 * ,{"pid":2,"nikename":
	 * "瀹惰缁翠慨","name":"瀹惰缁翠慨","id":14},{"pid":2,"nikename"
	 * :"鎼","name":"鎼","id"
	 * :15},{"pid":2,"nikename":"绌鸿皟缁翠慨","name":"绌鸿皟缁翠慨",
	 * "id":16},{"pid":2,"nikename"
	 * :"瓒呭競鍒板","name":"鐢熸椿閰嶉\u20ac�","id":17},{"pid"
	 * :2,"nikename":"瀹跺眳鏀剁撼","name"
	 * :"瀹跺眳鏀剁撼","id":18},{"pid":2,"nikename":"淇濇磥娓呮礂"
	 * ,"name":"淇濇磥娓呮礂","id":19},{
	 * "pid":2,"nikename":"淇濆鏈堝珎","name":"淇濆鏈堝珎","id"
	 * :20},{"pid":2,"nikename":null
	 * ,"name":"澶у帹鍒板","id":21},{"pid":2,"nikename"
	 * :null,"name":"蹇\u20ac掍笂闂�","id"
	 * :22},{"pid":2,"nikename":null,"name":"绠￠亾鐤忛\u20ac�"
	 * ,"id":23}],"pid":0,"nikename"
	 * :null,"name":"涓婇棬","id":2},{"subName":[{"pid"
	 * :3,"nikename":null,"name":"瀹犵墿瀵勫吇"
	 * ,"id":24},{"pid":3,"nikename":null,"name"
	 * :"璺戣吙","id":25},{"pid":3,"nikename"
	 * :null,"name":"涓撹溅鎺ラ\u20ac�","id":26},{"pid"
	 * :3,"nikename":null,"name":"鍙告満"
	 * ,"id":27},{"pid":3,"nikename":null,"name":"鍑虹鎴垮瓙"
	 * ,"id":28},{"pid":3,"nikename"
	 * :null,"name":"鑷┚鎷艰溅","id":29}],"pid":0,"nikename"
	 * :null,"name":"鐢熸椿","id":
	 * 3},{"subName":[{"pid":4,"nikename":null,"name":"闄\u20ac涜"
	 * ,"id":30},{"pid"
	 * :4,"nikename":null,"name":"闄數褰�","id":31},{"pid":4,"nikename"
	 * :null,"name"
	 * :"闄父鎴�","id":32},{"pid":4,"nikename":null,"name":"闄亰澶�","id"
	 * :33},{"pid":
	 * 4,"nikename":null,"name":"闄梾娓�","id":34},{"pid":4,"nikename":
	 * null,"name":
	 * "闄涔�","id":35}],"pid":0,"nikename":null,"name":"闄帺闄箰","id"
	 * :4},{"subName"
	 * :[{"pid":5,"nikename":null,"name":"娓告垙楂樻墜","id":36},{"pid":5
	 * ,"nikename":null
	 * ,"name":"鑸炶箞杈句汉","id":37},{"pid":5,"nikename":null,"name":
	 * "涓撲笟椋欐瓕","id":38
	 * },{"pid":5,"nikename":null,"name":"鐟滀冀","id":39},{"pid":5,
	 * "nikename":null,
	 * "name":"璁捐","id":40},{"pid":5,"nikename":null,"name":"瀹舵暀"
	 * ,"id":41}],"pid"
	 * :0,"nikename":null,"name":"鏁欎綘涓\u20ac鎵�","id":5},{"subName"
	 * :[{"pid":6,"nikename"
	 * :null,"name":"绌挎惌鎺�","id":42},{"pid":6,"nikename":null
	 * ,"name":"缇庡鎺�","id"
	 * :43},{"pid":6,"nikename":null,"name":"缇庣敳","id":44},{"pid"
	 * :6,"nikename":null
	 * ,"name":"淇濆吇绉樼睄","id":45}],"pid":0,"nikename":null,"name"
	 * :"缇庝附鍔ㄤ汉","id":6}] status : 1
	 */

	private int status;
	/**
	 * subName :
	 * [{"pid":1,"nikename":"蹇冪悊","name":"蹇冪悊鍖诲笀","id":7},{"pid":1,"nikename"
	 * :"鎵句笓瀹�"
	 * ,"name":"涓撶鍖荤敓","id":8},{"pid":1,"nikename":"鍏荤敓","name":"鍏荤敓杈句汉"
	 * ,"id":9}
	 * ,{"pid":1,"nikename":"鍋ヨ韩","name":"鍋ヨ韩鏁欑粌","id":10},{"pid":1,"nikename"
	 * :"鍑忚偉","name":"鍑忚偉婵\u20ac鍔卞笀","id":11},{"pid":1,"nikename":null,"name":
	 * "瀹犵墿鍖荤敓","id":12}] pid : 0 nikename : null name : 鍋ュ悍 id : 1
	 */

	private List<ServertypeBean> servertype;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<ServertypeBean> getServertype() {
		return servertype;
	}

	public void setServertype(List<ServertypeBean> servertype) {
		this.servertype = servertype;
	}

	public static class ServertypeBean {
		private int pid;
		private Object nikename;
		private String name;
		private int id;
		/**
		 * pid : 1 nikename : 蹇冪悊 name : 蹇冪悊鍖诲笀 id : 7
		 */

		private List<SubNameBean> subName;

		public int getPid() {
			return pid;
		}

		public void setPid(int pid) {
			this.pid = pid;
		}

		public Object getNikename() {
			return nikename;
		}

		public void setNikename(Object nikename) {
			this.nikename = nikename;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public List<SubNameBean> getSubName() {
			return subName;
		}

		public void setSubName(List<SubNameBean> subName) {
			this.subName = subName;
		}

		public static class SubNameBean {
			private int pid;
			private String nikename;
			private String name;
			private int id;

			public int getPid() {
				return pid;
			}

			public void setPid(int pid) {
				this.pid = pid;
			}

			public String getNikename() {
				return nikename;
			}

			public void setNikename(String nikename) {
				this.nikename = nikename;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}
		}
	}

}
