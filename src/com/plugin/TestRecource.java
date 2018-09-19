package com.plugin;

/**
 * dongdong Created by 下午2:43  2018/9/19
 */
public class TestRecource {
    public static void main(String[] args){
        TestRecource.class.getResource("");
        String s1=TestRecource.class.getResource("").getPath();
        String s2=TestRecource.class.getResource("/").getPath();
        String s3=TestRecource.class.getResource("/icon/danger.png").getPath();
//        String s4=TestRecource.class.getResource("/resources/icon").getPath();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
//        System.out.println(s4);
    }
}
