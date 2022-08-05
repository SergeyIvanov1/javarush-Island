package ru.javarush.sergeyivanov.island.ContentOfIsland;

import ru.javarush.sergeyivanov.island.ContentOfIsland.Field.Location;

public abstract class Nature {
    Location location;
    public static final int ALL_OBJECT = 0;

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
