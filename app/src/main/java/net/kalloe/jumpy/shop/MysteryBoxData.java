package net.kalloe.jumpy.shop;

/**
 * Created by Jamie on 16-7-2016.
 */
public class MysteryBoxData implements ShopData {

    private String name;
    private int price;

    public MysteryBoxData() {
        this.name = "Mystery Box";
        this.price = (int)(Math.random() * 10000);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void buy() {

    }

    @Override
    public void sell() {

    }
}
