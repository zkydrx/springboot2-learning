package com.fatal.aspect;

import com.fatal.dao.ActionDao;
import com.fatal.entity.Action;
import com.fatal.entity.ChangeItem;
import com.fatal.enums.ActionType;
import com.fatal.utils.DifferentUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * @author: Fatal
 * @date: 2018/11/15 0015 14:20
 */
@Aspect
@Component
public class ActionAspect
{

    /**
     * 实体id
     */
    private final String ID = "id";
    /**
     * 保存、更新的方法名
     */
    private final String SAVE = "save";
    /**
     * 删除的方法名
     */
    private final String DELETE = "deleteById";
    /**
     * 操作者
     */
    private final String ADMIN = "admin";

    @Autowired
    private ActionDao dao;

    @Pointcut("execution(public * com.fatal.dao.ProductDao.save*(..))")
    public void save()
    {
    }

    @Pointcut("execution(public * com.fatal.dao.ProductDao.delete*(..))")
    public void delete()
    {
    }

    /**
     * 1、判断操作的类型 -- 增加、删除或者更新（增加和更新通过id区分）
     * 2、获取ChangeItem
     *
     * @return
     */
    @Around("save() || delete()")
    public Object addOperateLog(ProceedingJoinPoint joinPoint) throws Throwable
    {

        Action action = new Action();
        ActionType actionType = null;
        Object oldObj = null;
        // 获得方法名
        String method = joinPoint.getSignature().getName();
        // 获取切点参数。update -> Product, delete -> id
        Object joinPointArg = joinPoint.getArgs()[0];

        Long id = null;
        if (method.contains(DELETE))
        {
            id = (Long) joinPointArg;
        }
        else
        {
            // 先判断有没有id
            Object property = PropertyUtils.getProperty(joinPointArg, ID);
            if (!ObjectUtils.isEmpty(property))
            {
                id = Long.valueOf(property.toString());
            }
        }

        if (ObjectUtils.isEmpty(id))
        {
            // 新增
            actionType = ActionType.INSERT;
            action.setObjectClass(joinPointArg.getClass().getName());
        }
        else
        {
            if (method.contains(SAVE))
            {
                // 更新
                actionType = ActionType.UPDATE;
            }
            else if (method.contains(DELETE))
            {
                // 删除
                actionType = ActionType.DELETE;
            }
            // 通过切点的父类反射获得 findById()。然后拿id通过反射去数据库查原始数据
            oldObj = DifferentUtil.getObjectById(joinPoint.getTarget(), id);
            action.setObjectClass(oldObj.getClass().getName());
            action.setObjectId(id);
        }

        // ======      实际方法的调用 begin      ======
        Object result = joinPoint.proceed(joinPoint.getArgs());
        // =========           end          =========

        if (ObjectUtils.isEmpty(id))
        {
            // 新增后的实体中是存在id的
            Long newId = Long.valueOf(PropertyUtils.getProperty(result, ID).toString());
            action.setObjectId(newId);
            List<ChangeItem> changeItems = DifferentUtil.getInsertChangeItems(joinPointArg);
            action.getChanges().addAll(changeItems);
        }
        else
        {
            if (SAVE.equals(method))
            {
                // 更新
                List<ChangeItem> changeItems = DifferentUtil.getChangeItems(oldObj, joinPointArg);
                action.getChanges().addAll(changeItems);
            }
            else if (DELETE.equals(method))
            {
                // 删除
                ChangeItem changeItem = DifferentUtil.getDeleteChangeItem(oldObj);
                action.getChanges().add(changeItem);
            }
        }
        action.setOperateTime(new Date());
        action.setOperator(ADMIN);
        action.setActionType(actionType);
        // 保存操作数据
        dao.save(action);
        return result;
    }

}

