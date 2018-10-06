package com.cengel.starbucks.util;

import java.util.Random;

/**
 * @Title:
 * @Description:
 * @Author zhz
 * @Time 2018/9/12 - 15:28
 * @Version V1.0
 **/
public class RandomUtil {

	private static Random random = new Random();


	public static int [] getArr(int size,int topLen){
		int arr [] = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = getNum(topLen);
		}
		return arr;
	}


	//随机起个中文名
	public static String getCnName(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(CN.getOne());
		}
		return sb.toString();
	}

	public static int getNum(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(NUM.getOne());
		}
		return Integer.parseInt(sb.toString());
	}

	//数字和字母组合
	public static String  getEname(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			if (i % 2 == 1) {
				sb.append(NUM.getOne());
			} else sb.append(CN.getOne());
		}
		return sb.toString();
	}

	public static class CN {
		private static final int CN_START = Integer.parseInt("4E00", 16);
		private static final int CN_END   = Integer.parseInt("9FA5", 16);
		private static final int CN_LEN   = CN_END - CN_START + 1;
		public static Character getOne() {
			return (char) (random.nextInt(CN_LEN) + CN_START);
		}
	}

	public static class ENG {
		private static final int EN_B_START = 97;
		private static final int EN_B_END   = 122;
		private static final int EN_S_START = 65;
		private static final int EN_S_END   = 90;
		private static final int EN_LEN     = EN_S_END - EN_S_START + 1;

		public static Character getOne() {
			int r = random.nextInt(EN_LEN);
			if (r % 2 == 1) {
				return (char) (r + EN_B_START);
			} else {
				return (char) (r + EN_S_START);
			}
		}
	}

	public static class NUM {
		private static final int NUM_START = 48;
		private static final int NUM_END   = 57;
		private static final int NUM_LEN   = NUM_END - NUM_START + 1;

		public static Character getOne() {
			return (char) (random.nextInt(NUM_LEN) + NUM_START);
		}
	}
}
