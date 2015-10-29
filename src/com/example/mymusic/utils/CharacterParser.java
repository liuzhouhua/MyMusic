package com.example.mymusic.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


//汉子转拼音
public class CharacterParser {
	
	private StringBuilder buffer;

	// 将汉字转换为全拼  
    public static String getPingYin(String src) {  
  
        char[] t1 = null; 
        //转换为字符数组
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length];  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
        //设置小写
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
        //设置声调格式：无声调
        /*方法参数HanyuPinyinToneType有以下常量对象：
        HanyuPinyinToneType.WITH_TONE_NUMBER 用数字表示声调，例如：zhao4
        HanyuPinyinToneType.WITHOUT_TONE 无声调表示，例如：zhao
        HanyuPinyinToneType.WITH_TONE_MARK 用声调符号表示，例如：zhao*/
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        //设置特殊拼音ü的显示格式：
        /*方法参数HanyuPinyinVCharType有以下常量对象：
        HanyuPinyinVCharType.WITH_U_AND_COLON 以U和一个冒号表示该拼音， 
        HanyuPinyinVCharType.WITH_V 以V表示该字符， 
        HanyuPinyinVCharType.WITH_U_UNICODE  */
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断是否为汉字字符  
                if (java.lang.Character.toString(t1[i]).matches(  
                        "[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);  
                    t4 += t2[0];  
                } else  
                    t4 += java.lang.Character.toString(t1[i]);  
            }  
            return t4;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return t4;  
    }  
  
    // 返回中文每个字的首字母  
    public static String getPinYinHeadChar(String str) {  
  
        String convert = "";  
        for (int j = 0; j < str.length(); j++) {  
            char word = str.charAt(j);  
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);  
            if (pinyinArray != null) {  
                convert += pinyinArray[0].charAt(0);  
            } else {  
                convert += word;  
            }  
        }  
        return convert;  
    }  
    
    //返回第一个中文字的首字母
    public static String getPinYinFirstHeadChar(String str) {  
    	  
        String convert = "";  
        convert = getPinYinHeadChar(str);
        convert = convert.charAt(0)+"";
        return convert;
    }
  
    // 将字符串转移为ASCII码  
    public static String getCnASCII(String cnStr) {  
        StringBuffer strBuf = new StringBuffer();  
        byte[] bGBK = cnStr.getBytes();  
        for (int i = 0; i < bGBK.length; i++) {  
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));  
        }  
        return strBuf.toString();  
    }   
  
}
