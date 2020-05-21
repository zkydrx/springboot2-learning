package com.fatal.vo;

import com.fatal.dto.ShopCartItemDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车skuVO，去掉了重复的属性
 *
 * @author Fatal
 * @date 2019/8/15 0015 17:45
 */
@Data
@Accessors(chain = true)
public class ShopCartItemVO
{

    private Long id;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 商品名称（冗余字段）
     */
    private String goodsName;

    /**
     * sku单价（单位：分）
     */
    private Long price;

    /**
     * sku库存
     */
    private Integer stock;

    /**
     * sku图片
     */
    private String picture;

    /**
     * 规格（规格本来应该是张表的，这里为了方便，就直接 String）
     */
    private String properties;

    /**
     * 单种sku允许添加到购物车的最大数额
     */
    private Integer max;

    /**
     * sku状态：-1 下架; 0 删除; 1 在架
     */
    private Integer status;

    private static ShopCartItemVO of(ShopCartItemDTO shopCartItemDTO)
    {
        ShopCartItemVO shopCartItemVO = new ShopCartItemVO();
        BeanUtils.copyProperties(shopCartItemDTO, shopCartItemVO);
        return shopCartItemVO;
    }

    public static List<ShopCartItemVO> of(List<ShopCartItemDTO> shopCartItemDTOs)
    {
        return shopCartItemDTOs.stream().map(ShopCartItemVO::of).collect(Collectors.toList());
    }

}
