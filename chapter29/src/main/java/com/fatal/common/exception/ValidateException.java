package com.fatal.common.exception;

import com.fatal.common.enums.ResponseEnum;

import javax.validation.ConstraintViolationException;

/**
 * 自定义校验异常，可以被本类异常处理器catch；优先本类的处理器，如果没有本类的，往上一层。
 *
 * @author Fatal
 * @date 2019/8/18 0018 11:00
 */
public class ValidateException extends ConstraintViolationException
{

    public ValidateException(ResponseEnum responseEnum)
    {
        super(responseEnum.getMessage(), null);
    }

}
