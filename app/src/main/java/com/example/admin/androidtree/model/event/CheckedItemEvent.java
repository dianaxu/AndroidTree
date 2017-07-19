package com.example.admin.androidtree.model.event;

import com.example.admin.androidtree.activity.fragment.factory.IBuilderFactory;

/**
 * @author Diana
 * @date 2017/7/11
 */

public class CheckedItemEvent {
    private int checkItemIndex;
    private IBuilderFactory factory;

    public CheckedItemEvent(int checkItemIndex, IBuilderFactory factory) {
        this.checkItemIndex = checkItemIndex;
        this.factory = factory;
    }

    public int getCheckItemIndex() {
        return checkItemIndex;
    }

    public IBuilderFactory getBuilderFactory() {
        return factory;
    }
}
