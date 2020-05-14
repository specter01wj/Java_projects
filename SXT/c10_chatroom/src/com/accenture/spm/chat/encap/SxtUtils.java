package com.accenture.spm.chat.encap;

import java.io.Closeable;

public class SxtUtils {

	public static void close(Closeable... targets ) {
		for(Closeable target:targets) {
			try {
				if(null!=target) {
					target.close();
				}
			}catch(Exception e) {
				
			}
		}
	}
	
}
