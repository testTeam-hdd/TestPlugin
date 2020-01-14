package com.plugin;

/**
 * dongdong Created by 9:47 AM  2019/7/15
 */
public class Son extends Father {
    static {
        System.out.println("a");
    }
    public Son(){
        System.out.println("b");
    }
    public Son(int a ){
        System.out.println(a);
    }

    public static void main(String[] args) {
        Father father = new Son();
        father = new Son();
        father = new Son(5);

    }
}
