package com.plugin;

/**
 * dongdong Created by 9:47 AM  2019/7/15
 */
public class Father {

    static {
        System.out.println(1);
    }
    public Father(){
        System.out.println(2);

    }
    public Father(int s){
        System.out.println(9);
    }
}
