package com.fatal.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 配置类
 *
 * @author: Fatal
 * @date: 2018/9/20 0020 16:16
 */
@Component
@ConfigurationProperties(prefix = "fatal")
@ToString
@Data
public class Fatal2
{

    private String name;

    private Integer age;

    /**
     * 测试对象、Map
     */
    private Fatal6 fatal6;

    private Map<String, Integer> map;

    /**
     * 测试集合、List，Set 数组数据封装
     */
    private String[] strings;

    private List<String> list;

    private Set<String> set;

}
