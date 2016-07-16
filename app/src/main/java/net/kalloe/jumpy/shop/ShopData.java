package net.kalloe.jumpy.shop;

/**
 * Created by Jamie on 16-7-2016.
 */
public interface ShopData {
    String getName();
    void setName(String name);
    int getPrice();
    void setPrice(int price);
    void buy();
    void sell();
}
