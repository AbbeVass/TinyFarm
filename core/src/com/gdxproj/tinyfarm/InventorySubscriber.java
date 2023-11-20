package com.gdxproj.tinyfarm;

/**
 * Classes implementing this interface may subscribe to Inventory
 * and receive add/remove item notifications.
 */
public interface InventorySubscriber {
    public void notifyAddItem(Item item, int count);
    public void notifyRemovedItem(Item item, int count);
}
