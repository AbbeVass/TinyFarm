package com.gdxproj.tinyfarm;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.badlogic.gdx.utils.Array;

public class Inventory {
    private HashMap<Item, Integer> _items;
    private Vector<InventorySubscriber> _subscribers;
    private int _selected;

    public Inventory() {
        _items = new HashMap<Item, Integer>();
        _subscribers = new Vector<InventorySubscriber>();
        _selected = 0;
    }

    /**
     * Adds a specified quantity of an item to the inventory.
     * @param item the item to add
     * @param addCount the amount to add
     */
    public void addItem(Item item, int addCount) {
        if(addCount <= 0) {
            return;
        }

        int newCountForItem = _items.getOrDefault(item, 0) + addCount;

        _items.put(item, newCountForItem);

        /*
         * send a notification to all subscribers that an item was added
         */
        for(InventorySubscriber subscriber : _subscribers) {
            subscriber.notifyAddItem(item, addCount);
        }
    }

    /**
     * Removes a specified quantity of an item from the inventory.
     * @param item the item to remove
     * @param removeCount the amount to remove
     */
    public void removeItem(Item item, int removeCount) {
        if(removeCount <= 0) {
            return;
        }

        int newCountForItem = _items.getOrDefault(item, 0) - removeCount;

        if(newCountForItem > 0) {
            _items.put(item, newCountForItem);
        } else {
            _items.remove(item);
        }

        /*
         * send a notification to all subscribers that an item was removed
         */
        for(InventorySubscriber subscriber : _subscribers) {
            subscriber.notifyRemovedItem(item, removeCount);
        }
    }

    /**
     * Returns the number of the specified item in the inventory.
     * @param item the item to check
     * @return the count of the item in the inventory
     */
    public int getItemCount(Item item) {
        int countForItem = _items.getOrDefault(item, 0);

        return countForItem;
    }

    /**
     * Get the first count of items in the inventory.
     * @param count the number of items to return
     * @return an array containing the items
     */
    public Array<Item> getFirstNItems(int count) {
        Set<Item> keys = _items.keySet();
        Array<Item> itemArray = new Array<Item>(count);
        itemArray.setSize(10);

        int i = 0;

        //copy items from the set to the array
        for(Item item : keys) {
            itemArray.set(i++, item);

            if(i >= count) {
                break;
            }
        }

        //fill any remaining slots with null
        for(; i < count; ++i) {
            itemArray.set(i, null);
        }

        return itemArray;
    }

    /**
     * Add a new subscriber to the list of subscribers.
     * @param subscriber the subscriber to add
     */
    public void addSubscriber(InventorySubscriber subscriber) {
        if(subscriber != null) {
            _subscribers.add(subscriber);
        }
    }

    /**
     * Set the selected item.
     * @param selection the item index to select.
     */
    public void setSelection(int index) {
        _selected = index;
    }

    /**
     * Get the selected item index.
     * @return the selected item index
     */
    public int getSelection() {
        return _selected;
    }
}
