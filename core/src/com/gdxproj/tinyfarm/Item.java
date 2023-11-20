package com.gdxproj.tinyfarm;

public abstract class Item {
    /**
     * Get the item's nickname. This should return a String that UNIQUELY identifies
     * the item.
     * @return the item's unique identifier string
     */
    abstract String getNickname();

    /**
     * Get the item's name that will be shown to the player.
     * @return the item's name
     */
    abstract String getName();

    /**
     * Get the item's description that will be shown to the player.
     * @return the item's description
     */
    abstract String getDescription();

    /**
     * Get the item's appearance code. Index in the items.png spritesheet.
     * @return the item's appearance code
     */
    abstract int getAppearance();

    /**
     * Called when the item is used on a specific tile in the world.
     * @param tileX the X-coordinate of the tile
     * @param tileY the Y-coordinate of the tile
     * @return whether the item was consumed (true = remove one from the inventory)
     */
    abstract boolean useOnTile(int tileX, int tileY);

    @Override
    public int hashCode() {
        return getNickname().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        /*
         * return false if the other object is null or is not a subclass of Item
         */
        if(obj == null || !(obj instanceof Item)) {
            return false;
        }

        Item oitem = (Item)obj;

        /*
         * Compare nicknames
         */
        return getNickname().equals(oitem.getNickname());
    }
}