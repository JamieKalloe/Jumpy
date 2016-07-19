package net.kalloe.jumpy.shop;

/**
 * Created by Jamie on 19-7-2016.
 */
public class GoldData implements ShopData{

    private String name;
    private int price;

    /**
     * Creates a new instance of the Life (PowerUp) shop data.
     */
    public GoldData() {
        this.name = "Gold";
        this.price = (int)(Math.random() * 100000);
    }

    /**
     * Returns the String name of the PowerUp.
     * @return String name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Sets the String name of the PowerUp.
     * @param name String name.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the int price of the PowerUp.
     * @return int price.
     */
    @Override
    public int getPrice() {
        return this.price;
    }

    /**
     * Sets the int price of the PowerUp.
     * @param price int price.
     */
    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void buy() {
        //save bought item into shared pref
    }

    @Override
    public void sell() {
        //remove sold item from shared pref
    }
}
