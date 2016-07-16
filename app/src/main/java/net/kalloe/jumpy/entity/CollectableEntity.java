package net.kalloe.jumpy.entity;

import net.kalloe.jumpy.shop.ShopData;

/**
 * Created by Jamie on 16-7-2016.
 */
public interface CollectableEntity {

    ShopData getShopData();
    void setShopData(ShopData data);

    void obtain(Player player);
}
