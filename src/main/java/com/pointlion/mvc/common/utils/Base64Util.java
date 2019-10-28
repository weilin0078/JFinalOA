package com.pointlion.mvc.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	public static void main(String a[]) {
		// String aaaa = GetImageStr();
		GenerateImage(
				"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",
				"d://test", "aaaa.png");
	}

	// 图片转化成base64字符串
	public static String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "d://test.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static boolean GenerateImage(String imgStr, String filePath, String filename) { // 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(filePath + "/" + filename);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}