package com.Utils;

import com.Vo.CsvVO;

import java.util.ArrayList;
import java.util.List;

/**
 * dongdong Created by 8:25 PM  2019/3/4
 */
public class DataSupply {

    public static List<CsvVO> tableSupply(List<CsvVO> list) {
        for (CsvVO csvVO : list) {
            List<String> contentlist = new ArrayList<>();
            int length = 0;
            for (String col : csvVO.getContent()) {
                if (!col.isEmpty()) {
//                    int colLength = col.toString().split(",").length;
                    int colLength = getCharNum(col,",");
                    if (colLength > length) {
                        length = colLength;
                    }
                }
            }
            csvVO.setColNum(length+1);
            for (String col : csvVO.getContent()) {
                int colLength = getCharNum(col,",");
                if (colLength < length) {
                    for (int i = 0; i < length - colLength; i++) {
                        col = col.concat(",");
                    }
                }
                contentlist.add(col);
            }
            csvVO.setContent(contentlist);
        }
        return list;
    }

    public static int getCharNum(String content,String achar){
        String newContent = content.replace(achar,"");
        int num = content.length()-newContent.length();
        return num;
    }
}