package com.Utils;

import com.Vo.CsvVO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * dongdong Created by 5:24 PM  2019/3/6
 */
public class FileUtil {

    /**
     * 读取一个路径下所有文件的文件内容
     */
    public static List<CsvVO> getFileContent(String path) {
        List<CsvVO> csvList = new ArrayList<>();
        CSVParseUtil csvParseUtil = new CSVParseUtil();
        File file = new File(path);
        File[] files = file.listFiles();
        for (File content : files) {
            List<String> list = new ArrayList<>();
            CsvVO csvVO = new CsvVO();
            BufferedReader br;
            int rowNum;
            int colNum;

            try {
                InputStream is = new FileInputStream(content);
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
                String stemp;
                while ((stemp = br.readLine()) != null) {
                    list.add(stemp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            colNum = csvParseUtil.getColNum(list);
            rowNum = csvParseUtil.getRowNum(list);
            csvVO.setRowNum(rowNum);
            csvVO.setColNum(colNum);
            csvVO.setCsvName(content.getName());
            csvVO.setContent(list);
            csvList.add(csvVO);
        }
        List<CsvVO> list = DataSupply.tableSupply(csvList);
        return list;
    }


    /**
     * 读取某个文件的文件内容
     * @param path 文件的全路径
     */
    public static CsvVO getOneFileContent(String path,String filename) {
        CSVParseUtil csvParseUtil = new CSVParseUtil();
        File file = new File(path);
        List<String> list = new ArrayList<>();
        CsvVO csvVO = new CsvVO();
        BufferedReader br;
        int rowNum;
        int colNum;

        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String stemp;
            while ((stemp = br.readLine()) != null) {
                list.add(stemp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        colNum = csvParseUtil.getColNum(list);
        rowNum = csvParseUtil.getRowNum(list);
        csvVO.setRowNum(rowNum);
        csvVO.setColNum(colNum);
        csvVO.setCsvName(filename);
        csvVO.setContent(list);
        return csvVO;
    }
}
