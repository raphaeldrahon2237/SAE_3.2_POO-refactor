package towerdef.model.map;

public enum TileType {

    GRASS1(1, false),
    GRASS2(2, false),
    GRASS3(3, false),
    BASE(4, true),
    GRAY(5, false),

    ROADVERTICAL(6, true),
    ROADSIDE(7, true),
    ROADDOWNLEFT(8, true),
    ROADDOWNRIGHT(9, true),
    ROADUPLEFT(10, true),
    ROADUPRIGHT(11, true),

    CROSSROADDOWN(12, true),
    CROSSROADUP(13, true),
    CROSSROADFULL(14, true),

    MOUNTAIN1(15, false),
    MOUNTAIN2(16, false);

    public static final int TILESIZE = 32;

    private final int id;
    private final boolean walkable;

    TileType(int id, boolean walkable) {
        this.id = id;
        this.walkable = walkable;
    }

    public int getId() {
        return id;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public static TileType fromId(int id) {
        for (TileType tileType : values()) {
            if (tileType.id == id) {
                return tileType;
            }
        }
        return null;
    }
}