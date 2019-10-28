package com.pointlion.plugin.mail.mockhttp;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by farmer on 16/8/31.
 *
 * @author 1256810099@qq.com
 */
public class MockPrintWriter extends PrintWriter {
	
	private String content = null;
	
	public MockPrintWriter(Writer out) {
		super(out);
	}
	
	@Override
	public void close() {
		content = out.toString();
		super.close();
	}
	
	@Override
	public String toString(){
		try {
			close();
		} catch (Exception e) {
			//修复Beetl没关闭流导致失败的BUG
		}
		return content;
	}
}
