package com.exception;

/**
 * dongdong Created by 上午10:54  2018/9/19
 */
public enum PluginErrorMsg {
    CLASS_NOT_FIND("所查询的类不存在"),
    PSICLASS_NOT_FIND("当前环境中未找到该测试类，请检查环境配置是否正确"),
    TEST_CLASS_NOT_FIND("测试类输入错误，请检查后重新输入"),
    TEST_METHOD_NOT_FIND("测试方法输入错误，请检查后重新输入"),
    TEST_CLASS_IS_EMPTY("测试类不能为空"),
    TEST_METHOD_IS_EMPTY("测试方法不能为空"),
    TEST_DESCRIPTION_IS_EMPTY("脚本描述不能为空"),
    TEST_AUTHOR_IS_EMPTY("作者不能为空"),
    TEST_CLASS_ERROR("当前类不是测试类"),
    DBENTITY_CLASS_NOT_FIND("DBInsert对象错误，请输入该数据表对应的实体类"),
    DBCHECKBO_CLASS_NOT_FIND("DBCheck对象错误，请输入该数据表对应的BO对象");

    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    PluginErrorMsg(String msg){
        this.msg = msg;
    }
}
