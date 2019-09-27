package com.huya.servicechain.utils.enums;


/**
 * 公司顶级业务Corp
 *
 * @author by liuzhixing
 * @Classname CorpType
 * @date 2019/6/14
 */
public enum CorpType {
    /**
     * 国内HUYA
     */
    HUYA("HUYA", 0),

    /**
     * 海外YOME
     */
    YOME("YOME", 1),

    /**
     * 海外NIMO
     */
    NIMO("NIMO", 2);

    private String name;

    private int type;

    CorpType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
