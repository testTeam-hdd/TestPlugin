package com.Utils;


import com.miz.testframework.vo.XlsRowVO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangyt on 2017/7/28.
 */
public class CSVParseUtil {

    private String fileName = null;
    private BufferedReader br = null;
//    private List<String> list = new ArrayList<String>();
    private int rowNum =0;
    private int colNum =0;

//    public CSVParseUtil(String fileName) throws Exception {
//        InputStream is = Thread.currentThread().getContextClassLoader()
//                .getResourceAsStream(fileName);
//        InputStreamReader isr = new InputStreamReader(is,"UTF-8");
//        br = new BufferedReader(isr);
//        String stemp;
//        while ((stemp = br.readLine()) != null) {
//            list.add(stemp);
//        }
//        this.colNum =getColNum();
//        this.rowNum  = getRowNum();
//    }

//    public List getList() {
//        return list;
//    }
    /**
     * 获取行数
     * @return
     */
    public int getRowNum(List<String> list) {
        return list.size();
    }
    /**
     * 获取列数
     * @return
     */
    public int getColNum(List<String> list) {
        if (!list.toString().equals("[]")) {
            if (list.get(0).toString().contains(",")) {// csv为逗号分隔文件
                return list.get(0).toString().split(",").length;
            } else if (list.get(0).toString().trim().length() != 0) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    /**
     * 获取某个单元格
     * @param row
     * @param col
     * @return
     */
    public String getString(List<String> list,int row, int col) {
        String temp = null;
        int colnum = this.getColNum(list);
        if (colnum > 1) {
            temp = list.get(row).toString().split(",")[col];
        } else if(colnum == 1){
            temp = list.get(row).toString();
        } else {
            temp = null;
        }
        return temp;
    }

    public void CsvClose()throws Exception{
        this.br.close();
    }

    public  List<XlsRowVO>  parseCSV(List<String> list){
        List<String> titleList = new ArrayList<>();
        for(int j=0;j<colNum;j++){
            titleList.add(getString(list,0, j));
        }
        List<XlsRowVO> rowVOList = new ArrayList<XlsRowVO>();
        for(int i=1;i<rowNum;i++){
            XlsRowVO xlsRowVO = new XlsRowVO();
            Map<String, Object > map = new HashMap<String ,Object >();
            xlsRowVO.setMapData(map);
            for(int j=0;j<colNum;j++){
                map.put(titleList.get(j),getString(list,i, j));
            }
            rowVOList.add(xlsRowVO);
        }
        return rowVOList;
    }
    public static void main(String[] args)throws Exception {
        com.miz.testframework.util.CSVParseUtil util = new com.miz.testframework.util.CSVParseUtil("testers/normal/chargeAccountFee/response.csv");
        List<XlsRowVO> list = util.parseCSV();

    }


}
