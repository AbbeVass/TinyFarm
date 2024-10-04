package com.gdxproj.tinyfarm;

import java.util.HashMap;

/**
 * Singleton class.
 * Use ItemManager.getInstance() to acquire the global item manager.
 */
public class ItemManager {
    private static ItemManager __instance;

    /**
     * This array contains all valid items in the game.
     */
    private static final Item[] __items = {
        new WheatSeedItem(),
        new WheatItem(),
        new CarrotSeedItem(),
        new CarrotItem(),
        new OnionSeedItem(),
        new OnionItem(),
        new CabbageSeedItem(),
        new CabbageItem(),
        new PumpkinSeedItem(),
        new PumpkinItem(),
        new TomatoSeedItem(),
        new TomatoItem()
    };

    public HashMap<String, Item> _itemMap;

    /**
     * Get the Item object given the item's nickname
     * @param nickname the nickname of the item
     * @return the Item object
     * @throws FarmGameException if the nickname does not represent a valid item, an exception may be thrown
     */
    public Item getItemFromNickname(String nickname) throws FarmGameException {
        Item item = _itemMap.get(nickname);

        if(item == null) {
            throw new FarmGameException("Item \'" + nickname + "\' does not exist");
        }

        return item;
    }

    private ItemManager() {
        _itemMap = new HashMap<String, Item>();

        addAllItems();
    }

    /**
     * Get the item manager's singleton instance.
     * @return the singleton instance
     */
    public static ItemManager getInstance() {
        if(__instance == null) {
            __instance = new ItemManager();
        }

        return __instance;
    }

    private void addAllItems() {
        for(Item item : __items) {
            _itemMap.put(item.getNickname(), item);
        }
    }
}
