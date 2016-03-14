package net.kalloe.jumpy.scene;

import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.util.adt.color.Color;

/**
 * Created by Jamie on 14-3-2016.
 */
public class MenuSceneTextItemDecorator extends ColorMenuItemDecorator {

    //Variables
    private TextMenuItem textMenuItem;

    public MenuSceneTextItemDecorator(TextMenuItem textMenuItem, Color pSelectedColor, Color pUnselectedColor) {
        super(textMenuItem, pSelectedColor, pUnselectedColor);

        this.textMenuItem = textMenuItem;
    }

    /**
     * Sets the text to the Text Menu Item.
     * @param text char sequence text.
     */
    public void setText(CharSequence text) {
        this.textMenuItem.setText(text);
    }
}
