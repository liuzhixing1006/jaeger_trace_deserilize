package com.huya.servicechain.utils.enums;


/**
 * Corp下级分配的子业务线BG枚举类
 *
 * @author by liuzhixing
 * @Classname BgType
 * @date 2019/6/14
 */
public enum BgType {
    /**
     * 国内HUYA WEB主站微服务
     */
    HUYA_WEB_SERVICE(CorpType.HUYA, "HUYA_WEB_SERVICE", 0),

    /**
     * 国内HUYA-TAF TAF服务调用链
     */
    HUYA_TAF(CorpType.HUYA, "HUYA_TAF", 1),

    /**
     * 海外YOME TAF服务调用链
     */
    YOME_TAF(CorpType.YOME, "YOME_TAF", 2),

    /**
     * 海外NIMO TAF服务调用链
     */
    NIMO_TAF(CorpType.NIMO, "NIMO_TAF",3);

    private CorpType corpType;

    private String name;

    private int type;

    BgType(CorpType corpType, String name, int type) {
        this.corpType = corpType;
        this.name = name;
        this.type = type;
    }

    public CorpType getCorpType() {return corpType;}

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
