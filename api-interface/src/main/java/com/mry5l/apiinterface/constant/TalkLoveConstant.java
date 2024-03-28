package com.mry5l.apiinterface.constant;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 土味情话常量类
 *
 * @author YJL
 * @version 1.0
 */
public class TalkLoveConstant{
    private static final Map<Integer,String> MAP = new ConcurrentHashMap<>();
    static {
        MAP.put(0,"请给我ROMANTIC的浪漫情话,最多30中文字数");
        MAP.put(1,"请给我富有诗意的情话,最多30中文字数");
        MAP.put(2,"请给我土味情话,最多30中文字数");
        MAP.put(3,"请给我流行的情话,最多30中文字数");
        MAP.put(4,"请给我富有森林意境的情话,最多30中文字数");
        MAP.put(5,"请给我高冷的情话,最多30中文字数");
        MAP.put(6,"请随机给我情话,最多30中文字数");
    }

    public static Map<Integer, String> getMAP() {
        return MAP;
    }
}
