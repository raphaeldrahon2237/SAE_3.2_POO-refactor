
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import towerdef.model.Game;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Wolf;

import static org.junit.jupiter.api.Assertions.*;

public class gameJunit {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    public void canPlaceTowerTest() {
        // On vérifie qu'une tour peut être placée seulement sur une case valide.
        assertTrue(game.canPlaceTower(0, 0));
        assertFalse(game.canPlaceTower(-1, 0));
        assertFalse(game.canPlaceTower(1, 2));
        assertFalse(game.canPlaceTower(-10, 5));
    }

    @Test
    void placeTowerTest() {
        // On place une Archer Tower sur une case valide.
        boolean placed = game.placeTower(0, 0, 1);

        assertTrue(placed);
        assertEquals(880, game.getGold());
        assertEquals(1, game.getGameStats().getTowersPlaced());
        assertTrue(game.getTowerAt(0, 0) instanceof ArcherTower);
    }

    @Test
    void sellTowerTest() {
        game.placeTower(0, 0, 1);
        Tower tower = game.getTowerAt(0, 0);

        // On vend la tour placée.
        boolean sold = game.sellTower(tower);

        assertTrue(sold);
        assertEquals(940, game.getGold());
        assertNull(game.getTowerAt(0, 0));
    }

    @Test
    void removeDeadEntityTest() {
        Wolf wolf = new Wolf(0, 0);
        game.getEnemies().add(wolf);

        // On tue le loup avant d'appeler la suppression.
        wolf.takeDamage(999);
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size());
        assertEquals(1015, game.getGold());
        assertEquals(10, game.getScore());
        assertEquals(1, game.getGameStats().getEnemiesKilled());
    }
//    @Test
//    void questTest() {
//        ArcherTower archerTower = new ArcherTower(0, 0);
//
//        // On tue 10 loups avec une Archer Tower pour compléter la mini-quête.
//        for (int i = 0; i < 10; i++) {
//            Wolf wolf = new Wolf(0, 0);
//            wolf.setLastDamagingTower(archerTower);
//            wolf.takeDamage(999);
//
//            game.getEnemies().add(wolf);
//            game.removeDeadEntities();
//        }
//
//        assertEquals(1230, game.getGold());
//        assertEquals(150, game.getScore());
//        assertEquals(1, game.getMiniQuestManager().getCompletedQuestCount());
//        assertTrue(game.getMiniQuestManager().getQuests().get(0).isCompleted());
//    }
}