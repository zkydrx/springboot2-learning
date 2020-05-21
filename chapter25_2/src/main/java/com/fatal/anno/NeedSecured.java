package com.fatal.anno;

import java.lang.annotation.*;

/**
 * 自定义注解（RUNTIME环境，类级别，支持继承）
 *
 * @author: Fatal
 * @date: 2018/11/10 0010 16:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)   // Class 级别
@Inherited // 表示 @NeedSecured 将会作用于修饰的 Class 的子类。即该注解可以继承
public @interface NeedSecured
{
}
