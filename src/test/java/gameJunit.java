
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

    //fini


    @Test
    void sellTowerSuccessTest() {
        // On place une tour sur une case valide.
        boolean placed = game.placeTower(0, 0, 1);

        assertTrue(placed, "La tour doit être placée sur la case valide (0, 0).");
        assertEquals(880, game.getGold(), "L'or doit diminuer après l'achat de la tour.");
        assertEquals(1, game.getGameStats().getTowersPlaced(), "Le nombre de tours placées doit augmenter.");
        assertEquals(0, game.getScore(), "Le score ne doit pas changer quand on place une tour.");

        Tower tower = game.getTowerAt(0, 0);

        assertTrue(tower != null, "La tour doit bien exister avant la vente.");

        // On vend la tour.
        boolean sold = game.sellTower(tower);

        assertTrue(sold, "La vente doit réussir.");
        assertEquals(940, game.getGold(), "L'or doit augmenter avec la valeur de revente de la tour.");
        assertEquals(0, game.getScore(), "Le score ne doit pas augmenter quand on vend une tour.");
        assertNull(game.getTowerAt(0, 0), "La tour ne doit plus être présente après la vente.");
    }

    @Test
    void sellTowerNullTest() {
        int goldBefore = game.getGold();
        int scoreBefore = game.getScore();
        int towersPlacedBefore = game.getGameStats().getTowersPlaced();

        // On essaie de vendre une tour inexistante.
        boolean sold = game.sellTower(null);

        assertFalse(sold, "La vente doit échouer si la tour est null.");
        assertEquals(goldBefore, game.getGold(), "L'or ne doit pas changer si la vente échoue.");
        assertEquals(scoreBefore, game.getScore(), "Le score ne doit pas changer si la vente échoue.");
        assertEquals(towersPlacedBefore, game.getGameStats().getTowersPlaced(), "Le nombre de tours placées ne doit pas changer.");
    }

    @Test
    void sellTowerAlreadySoldTest() {
        // On place une tour valide.
        boolean placed = game.placeTower(0, 0, 1);

        assertTrue(placed, "La tour doit être placée.");
        assertEquals(880, game.getGold(), "L'or doit diminuer après l'achat.");

        Tower tower = game.getTowerAt(0, 0);

        assertTrue(tower != null, "La tour doit exister avant la première vente.");

        // Première vente : elle doit fonctionner.
        boolean firstSell = game.sellTower(tower);

        assertTrue(firstSell, "La première vente doit réussir.");
        assertEquals(940, game.getGold(), "L'or doit augmenter après la première vente.");
        assertNull(game.getTowerAt(0, 0), "La tour doit être retirée après la première vente.");

        int goldBeforeSecondSell = game.getGold();
        int scoreBeforeSecondSell = game.getScore();

        // Deuxième vente de la même tour : elle doit échouer.
        boolean secondSell = game.sellTower(tower);

        assertFalse(secondSell, "On ne doit pas pouvoir vendre deux fois la même tour.");
        assertEquals(goldBeforeSecondSell, game.getGold(), "L'or ne doit pas augmenter si on revend une tour déjà vendue.");
        assertEquals(scoreBeforeSecondSell, game.getScore(), "Le score ne doit pas augmenter si la vente échoue.");
    }

    @Test
    void canPlaceTowerTest() {
        assertTrue(game.canPlaceTower(0, 0), "La case (0, 0) est dans la map et n'est pas un chemin.");

        assertFalse(game.canPlaceTower(-1, 0), "On ne peut pas placer une tour avec x négatif.");
        assertFalse(game.canPlaceTower(0, -1), "On ne peut pas placer une tour avec y négatif.");
        assertFalse(game.canPlaceTower(-10, -5), "On ne peut pas placer une tour avec deux coordonnées négatives.");

        assertFalse(game.canPlaceTower(40, 0), "x = 40 est en dehors de la map car la largeur va de 0 à 39.");
        assertFalse(game.canPlaceTower(0, 26), "y = 26 est en dehors de la map car la hauteur va de 0 à 25.");
        assertFalse(game.canPlaceTower(40, 26), "La case (40, 26) est totalement en dehors de la map.");

        assertFalse(game.canPlaceTower(1, 2), "La case (1, 2) est dans la map mais elle correspond à un chemin.");

        // On vérifie qu'on ne peut pas placer deux tours sur la même case.
        game.placeTower(0, 0, 1);
        assertFalse(game.canPlaceTower(0, 0), "On ne peut pas placer une deuxième tour sur une case déjà occupée.");
    }
    @Test
    void removeDeadEnemiesTest() {
        Wolf deadWolf1 = new Wolf(0, 0);
        Wolf aliveWolf = new Wolf(1, 0);
        Wolf deadWolf2 = new Wolf(2, 0);

        game.getEnemies().add(deadWolf1);
        game.getEnemies().add(aliveWolf);
        game.getEnemies().add(deadWolf2);

        // On tue deux loups et on laisse un loup vivant.
        deadWolf1.takeDamage(9999);
        deadWolf2.takeDamage(9999);

        game.removeDeadEntities();

        assertEquals(1, game.getEnemies().size(), "Seul le loup vivant doit rester.");
        assertTrue(game.getEnemies().contains(aliveWolf), "Le loup vivant doit rester dans la liste.");
        assertFalse(game.getEnemies().contains(deadWolf1), "Le premier loup mort doit être supprimé.");
        assertFalse(game.getEnemies().contains(deadWolf2), "Le deuxième loup mort doit être supprimé.");

        assertEquals(1030, game.getGold(), "Le joueur doit recevoir uniquement la récompense des deux loups morts.");
        assertEquals(20, game.getScore(), "Le score doit augmenter seulement pour les deux ennemis morts.");
    }

    @Test
    void removeDeadEntitiesTwiceDoesNotGiveRewardTwiceTest() {
        Wolf wolf = new Wolf(0, 0);
        game.getEnemies().add(wolf);

        // On tue le loup.
        wolf.takeDamage(9999);

        // Premier nettoyage : la récompense doit être donnée.
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "Le loup mort doit être supprimé.");
        assertEquals(1015, game.getGold(), "Le joueur doit recevoir la récompense une seule fois.");
        assertEquals(10, game.getScore(), "Le score doit augmenter une seule fois.");

        // Deuxième nettoyage : il ne doit rien se passer.
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "La liste doit rester vide.");
        assertEquals(1015, game.getGold(), "L'or ne doit pas augmenter une deuxième fois.");
        assertEquals(10, game.getScore(), "Le score ne doit pas augmenter une deuxième fois.");
    }
    @Test
    void removeDeadTowerDoesNotGiveRewardTest() {
        // On place une tour sur une case valide.
        boolean placed = game.placeTower(0, 0, 1);

        assertTrue(placed, "La tour doit être placée.");
        assertEquals(880, game.getGold(), "L'or doit diminuer après l'achat de la tour.");
        assertEquals(0, game.getScore(), "Le score ne doit pas changer après l'achat d'une tour.");

        Tower tower = game.getTowerAt(0, 0);

        assertTrue(tower != null, "La tour doit exister avant d'être tuée.");
        assertTrue(game.getAllies().contains(tower), "La tour doit aussi être présente dans la liste des alliés.");

        // On tue la tour sans la vendre.
        tower.takeDamage(9999);

        assertFalse(tower.isAlive(), "La tour doit être morte avant le nettoyage.");
        assertTrue(game.getAllies().contains(tower), "Avant removeDeadEntities, la tour morte est encore dans les alliés.");

        // On supprime les entités mortes.
        game.removeDeadEntities();

        assertNull(game.getTowerAt(0, 0), "La tour morte ne doit plus être récupérable sur la map.");
        assertFalse(game.getAllies().contains(tower), "La tour morte doit être supprimée de la liste des alliés.");

        assertEquals(880, game.getGold(), "Une tour morte ne doit pas donner d'argent.");
        assertEquals(0, game.getScore(), "Une tour morte ne doit pas augmenter le score.");
    }

    @Test
    void removeDeadEnemiesIncreaseStatsTest() {
        Wolf wolf1 = new Wolf(0, 0);
        Wolf wolf2 = new Wolf(1, 0);

        game.getEnemies().add(wolf1);
        game.getEnemies().add(wolf2);

        // On tue les deux loups.
        wolf1.takeDamage(9999);
        wolf2.takeDamage(9999);

        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "Les deux loups morts doivent être supprimés.");
        assertEquals(1030, game.getGold(), "Le joueur doit recevoir la récompense des deux loups.");
        assertEquals(20, game.getScore(), "Le score doit augmenter pour les deux ennemis morts.");
        assertEquals(2, game.getGameStats().getEnemiesKilled(), "Les statistiques doivent compter les deux ennemis tués.");
    }

    @Test
    void removeDeadEntitiesWithEmptyListsTest() {
        // On appelle removeDeadEntities alors qu'il n'y a aucun ennemi ni allié.
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "La liste des ennemis doit rester vide.");
        assertEquals(0, game.getAllies().size(), "La liste des alliés doit rester vide.");
        assertEquals(1000, game.getGold(), "L'or ne doit pas changer.");
        assertEquals(0, game.getScore(), "Le score ne doit pas changer.");
        assertEquals(0, game.getGameStats().getEnemiesKilled(), "Aucun ennemi tué ne doit être compté.");
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