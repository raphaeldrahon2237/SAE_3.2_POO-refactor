
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import towerdef.model.Game;
import towerdef.model.entity.tower.ArcherTower;
import towerdef.model.entity.tower.Mage;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Archer;
import towerdef.model.entity.troop.Wolf;
import towerdef.model.GameEntities;
import towerdef.model.Progression;
import towerdef.model.TowerManager;
import towerdef.model.entity.Entity;
import towerdef.model.entity.tower.Barrack;
import towerdef.model.entity.troop.Recruit;
import towerdef.model.map.MapGrid;

import static org.junit.jupiter.api.Assertions.*;

public class gameJunit {
    private static final int BARRACK_ID = 3;
    private static final int BARRACK_X = 7;
    private static final int BARRACK_Y = 0;

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }


    private TowerManager createTowerManagerForRespawnTest(GameEntities gameEntities) {
        MapGrid mapGrid = new MapGrid();
        Progression progression = new Progression("test");

        return new TowerManager(mapGrid, gameEntities, progression);
    }

    private Barrack placeBarrackForRespawnTest(TowerManager towerManager, GameEntities gameEntities) {
        // On place une Barrack sur une case valide.
        boolean placed = towerManager.placeTower(BARRACK_X, BARRACK_Y, BARRACK_ID);

        assertTrue(placed, "La Barrack doit être placée.");
        assertEquals(1, gameEntities.getTowers().size(), "Une seule tour doit être placée.");
        assertTrue(gameEntities.getTowers().get(0) instanceof Barrack, "La tour placée doit être une Barrack.");

        return (Barrack) gameEntities.getTowers().get(0);
    }

    private Recruit findRecruit(GameEntities gameEntities) {
        for (Entity ally : gameEntities.getAllies()) {
            if (ally instanceof Recruit) {
                return (Recruit) ally;
            }
        }

        return null;
    }

    private int countRecruits(GameEntities gameEntities) {
        int count = 0;

        for (Entity ally : gameEntities.getAllies()) {
            if (ally instanceof Recruit) {
                count++;
            }
        }

        return count;
    }

    private Wolf createDeadWolfKilledBy(Tower tower) {
        Wolf wolf = new Wolf(0, 0);

        // On indique quelle tour a fait le dernier dégât avant de tuer le loup.
        wolf.setLastDamagingTower(tower);
        wolf.takeDamage(9999);

        return wolf;
    }

    private Archer createDeadArcherKilledBy(Tower tower) {
        Archer archer = new Archer(0, 0);

        // On indique quelle tour a fait le dernier dégât avant de tuer l'archer.
        archer.setLastDamagingTower(tower);
        archer.takeDamage(9999);

        return archer;
    }


    @Test
        // Vérifie qu'une tour peut être vendue et que l'or est bien remboursé.
    void sellTowerSuccessTest() {
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
        // Vérifie que la vente échoue lorsqu'on essaie de vendre une tour inexistante.
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

    // Vérifie qu'une même tour ne peut pas être vendue deux fois.
    @Test
    void sellTowerAlreadySoldTest() {
        boolean placed = game.placeTower(0, 0, 1);

        assertTrue(placed, "La tour doit être placée.");
        assertEquals(880, game.getGold(), "L'or doit diminuer après l'achat.");

        Tower tower = game.getTowerAt(0, 0);

        assertTrue(tower != null, "La tour doit exister avant la première vente.");

        boolean firstSell = game.sellTower(tower);

        assertTrue(firstSell, "La première vente doit réussir.");
        assertEquals(940, game.getGold(), "L'or doit augmenter après la première vente.");
        assertNull(game.getTowerAt(0, 0), "La tour doit être retirée après la première vente.");

        int goldBeforeSecondSell = game.getGold();
        int scoreBeforeSecondSell = game.getScore();

        boolean secondSell = game.sellTower(tower);

        assertFalse(secondSell, "On ne doit pas pouvoir vendre deux fois la même tour.");
        assertEquals(goldBeforeSecondSell, game.getGold(), "L'or ne doit pas augmenter si on revend une tour déjà vendue.");
        assertEquals(scoreBeforeSecondSell, game.getScore(), "Le score ne doit pas augmenter si la vente échoue.");
    }

    // Vérifie les différentes conditions nécessaires pour placer une tour.
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
    // Vérifie que les ennemis morts sont supprimés et que les ennemis vivants restent.
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

    // Vérifie qu'un ennemi mort ne donne pas deux fois sa récompense.
    @Test
    void removeDeadEnemyTwiceTest() {
        Wolf wolf = new Wolf(0, 0);
        game.getEnemies().add(wolf);

        wolf.takeDamage(9999);

        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "Le loup mort doit être supprimé.");
        assertEquals(1015, game.getGold(), "Le joueur doit recevoir la récompense une seule fois.");
        assertEquals(10, game.getScore(), "Le score doit augmenter une seule fois.");

        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "La liste doit rester vide.");
        assertEquals(1015, game.getGold(), "L'or ne doit pas augmenter une deuxième fois.");
        assertEquals(10, game.getScore(), "Le score ne doit pas augmenter une deuxième fois.");
    }

    // Vérifie qu'une tour morte est supprimée sans donner d'or ni de score.
    @Test
    void removeDeadTowerTest() {
        boolean placed = game.placeTower(0, 0, 1);

        assertTrue(placed, "La tour doit être placée.");
        assertEquals(880, game.getGold(), "L'or doit diminuer après l'achat de la tour.");
        assertEquals(0, game.getScore(), "Le score ne doit pas changer après l'achat d'une tour.");

        Tower tower = game.getTowerAt(0, 0);

        assertTrue(tower != null, "La tour doit exister avant d'être tuée.");
        assertTrue(game.getAllies().contains(tower), "La tour doit aussi être présente dans la liste des alliés.");

        tower.takeDamage(9999);

        assertFalse(tower.isAlive(), "La tour doit être morte avant le nettoyage.");
        assertTrue(game.getAllies().contains(tower), "Avant removeDeadEntities, la tour morte est encore dans les alliés.");

        game.removeDeadEntities();

        assertNull(game.getTowerAt(0, 0), "La tour morte ne doit plus être récupérable sur la map.");
        assertFalse(game.getAllies().contains(tower), "La tour morte doit être supprimée de la liste des alliés.");

        assertEquals(880, game.getGold(), "Une tour morte ne doit pas donner d'argent.");
        assertEquals(0, game.getScore(), "Une tour morte ne doit pas augmenter le score.");
    }

    // Vérifie que le nombre d'ennemis tués augmente dans les statistiques.
    @Test
    void enemyKillStatsTest() {
        Wolf wolf1 = new Wolf(0, 0);
        Wolf wolf2 = new Wolf(1, 0);

        game.getEnemies().add(wolf1);
        game.getEnemies().add(wolf2);

        wolf1.takeDamage(9999);
        wolf2.takeDamage(9999);

        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "Les deux loups morts doivent être supprimés.");
        assertEquals(1030, game.getGold(), "Le joueur doit recevoir la récompense des deux loups.");
        assertEquals(20, game.getScore(), "Le score doit augmenter pour les deux ennemis morts.");
        assertEquals(2, game.getGameStats().getEnemiesKilled(), "Les statistiques doivent compter les deux ennemis tués.");
    }
    //Vérifie que la suppression des entités mortes fonctionne avec des listes vides.
    @Test
    void removeDeadEntitiesEmptyTest() {
        // On appelle removeDeadEntities alors qu'il n'y a aucun ennemi ni allié.
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "La liste des ennemis doit rester vide.");
        assertEquals(0, game.getAllies().size(), "La liste des alliés doit rester vide.");
        assertEquals(1000, game.getGold(), "L'or ne doit pas changer.");
        assertEquals(0, game.getScore(), "Le score ne doit pas changer.");
        assertEquals(0, game.getGameStats().getEnemiesKilled(), "Aucun ennemi tué ne doit être compté.");
    }

    // Vérifie que la quête est terminée après exactement dix loups tués.
    @Test
    void  completeQuestWithTenKillsTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);

        for (int i = 0; i < 10; i++) {
            Wolf wolf = createDeadWolfKilledBy(archerTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les loups morts doivent être supprimés.");
        assertEquals(1230, game.getGold(), "Le joueur doit recevoir les récompenses des loups et le bonus de quête.");
        assertEquals(150, game.getScore(), "Le score doit recevoir les kills et le bonus de quête.");
        assertEquals(1, game.getMiniQuestManager().getCompletedQuestCount(), "Une seule mini-quête doit être complétée.");
        assertTrue(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête des 10 loups doit être terminée.");
        assertEquals(10, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression doit être bloquée à 10.");
    }
    //Vérifie que la quête n'est pas terminée avec seulement neuf loups tués.
    @Test
    void questNotCompleteWithNineKillsTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);

        // On tue seulement 9 loups alors que la quête en demande 10.
        for (int i = 0; i < 9; i++) {
            Wolf wolf = createDeadWolfKilledBy(archerTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les loups morts doivent être supprimés.");
        assertEquals(1135, game.getGold(), "Le joueur doit recevoir seulement les récompenses des 9 loups.");
        assertEquals(90, game.getScore(), "Le score doit augmenter seulement avec les 9 kills.");
        assertEquals(0, game.getMiniQuestManager().getCompletedQuestCount(), "La mini-quête ne doit pas être complétée.");
        assertFalse(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête ne doit pas être terminée avec seulement 9 kills.");
        assertEquals(9, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression doit être à 9.");
    }

    // Vérifie que la récompense de la quête n'est donnée qu'une seule fois.
    @Test
    void questRewardGivenOnceTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);

        for (int i = 0; i < 15; i++) {
            Wolf wolf = createDeadWolfKilledBy(archerTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les loups morts doivent être supprimés.");
        assertEquals(1305, game.getGold(), "Le bonus de mini-quête ne doit être donné qu'une seule fois.");
        assertEquals(200, game.getScore(), "Le bonus de score ne doit être donné qu'une seule fois.");
        assertEquals(1, game.getMiniQuestManager().getCompletedQuestCount(), "La quête ne doit être comptée qu'une seule fois.");
        assertTrue(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête doit rester terminée.");
        assertEquals(10, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression ne doit pas dépasser 10.");
    }
    //Vérifie que la quête ne progresse pas lorsque les loups sont tués par la mauvaise tour.
    @Test
    void wrongTowerQuestTest() {
        Mage mageTower = new Mage(0, 0);

        // On tue 10 loups, mais avec une Mage Tower au lieu d'une Archer Tower.
        for (int i = 0; i < 10; i++) {
            Wolf wolf = createDeadWolfKilledBy(mageTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les loups morts doivent être supprimés.");
        assertEquals(1150, game.getGold(), "Le joueur doit recevoir seulement les récompenses des loups.");
        assertEquals(100, game.getScore(), "Le score doit augmenter seulement avec les kills.");
        assertEquals(0, game.getMiniQuestManager().getCompletedQuestCount(), "Aucune mini-quête ne doit être complétée.");
        assertFalse(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête Archer Tower ne doit pas être terminée.");
        assertEquals(0, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression ne doit pas augmenter avec la mauvaise tour.");
    }
    // Vérifie que la quête ne progresse pas lorsque le mauvais type d'ennemi est tué. @Test
    @Test
    void wrongEnemyQuestTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);

        // On tue 10 archers avec une Archer Tower, mais la quête demande des loups.
        for (int i = 0; i < 10; i++) {
            Archer archer = createDeadArcherKilledBy(archerTower);

            game.getEnemies().add(archer);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les archers morts doivent être supprimés.");
        assertEquals(1300, game.getGold(), "Le joueur doit recevoir seulement les récompenses des archers.");
        assertEquals(100, game.getScore(), "Le score doit augmenter seulement avec les kills.");
        assertEquals(0, game.getMiniQuestManager().getCompletedQuestCount(), "Aucune mini-quête ne doit être complétée.");
        assertFalse(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête des loups ne doit pas être terminée.");
        assertEquals(0, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression de la quête des loups ne doit pas augmenter.");
    }
    // Vérifie que la quête ne progresse pas si aucune tour responsable n'est enregistrée.
    @Test
    void killWithoutTowerQuestTest() {
        // On tue 10 loups sans indiquer quelle tour les a tués.
        for (int i = 0; i < 10; i++) {
            Wolf wolf = new Wolf(0, 0);
            wolf.takeDamage(9999);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les loups morts doivent être supprimés.");
        assertEquals(1150, game.getGold(), "Le joueur doit recevoir seulement les récompenses des loups.");
        assertEquals(100, game.getScore(), "Le score doit augmenter seulement avec les kills.");
        assertEquals(0, game.getMiniQuestManager().getCompletedQuestCount(), "Aucune mini-quête ne doit être complétée sans tour responsable.");
        assertFalse(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête ne doit pas être terminée sans lastDamagingTower.");
        assertEquals(0, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression ne doit pas augmenter sans tour responsable.");
    }

    // Vérifie qu'un même ennemi mort ne peut pas être compté plusieurs fois dans la quête. @Test
    @Test
    void sameEnemyCountedOnceTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);
        Wolf wolf = createDeadWolfKilledBy(archerTower);

        game.getEnemies().add(wolf);

        // Premier nettoyage : le loup doit être compté une seule fois.
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "Le loup mort doit être supprimé.");
        assertEquals(1015, game.getGold(), "Le joueur doit recevoir la récompense du loup une seule fois.");
        assertEquals(10, game.getScore(), "Le score doit augmenter une seule fois.");
        assertEquals(1, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La quête doit progresser de 1.");

        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "La liste doit rester vide.");
        assertEquals(1015, game.getGold(), "L'or ne doit pas augmenter une deuxième fois.");
        assertEquals(10, game.getScore(), "Le score ne doit pas augmenter une deuxième fois.");
        assertEquals(1, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La quête ne doit pas progresser une deuxième fois.");
        assertEquals(0, game.getMiniQuestManager().getCompletedQuestCount(), "La quête ne doit pas être complétée avec un seul loup.");
    }


    // Vérifie qu'un même ennemi mort ne peut pas être compté plusieurs fois dans la quête.
    @Test
    void completeQuestWithSeveralDeathsTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);

        // On ajoute 10 loups morts avant d'appeler removeDeadEntities.
        for (int i = 0; i < 10; i++) {
            Wolf wolf = createDeadWolfKilledBy(archerTower);
            game.getEnemies().add(wolf);
        }

        // Un seul nettoyage doit traiter les 10 morts.
        game.removeDeadEntities();

        assertEquals(0, game.getEnemies().size(), "Les 10 loups morts doivent être supprimés en une seule fois.");
        assertEquals(1230, game.getGold(), "Le joueur doit recevoir les récompenses des 10 loups et le bonus de quête.");
        assertEquals(150, game.getScore(), "Le score doit recevoir les 10 kills et le bonus de quête.");
        assertEquals(1, game.getMiniQuestManager().getCompletedQuestCount(), "La mini-quête doit être complétée.");
        assertTrue(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête doit être terminée.");
        assertEquals(10, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression doit atteindre 10.");
    }
    // Vérifie que seuls les ennemis tués avec la bonne tour font progresser la quête.
    @Test
    void mixedTowerKillsQuestTest() {
        ArcherTower archerTower = new ArcherTower(0, 0);
        Mage mageTower = new Mage(0, 0);

        // On tue 5 loups avec la bonne tour.
        for (int i = 0; i < 5; i++) {
            Wolf wolf = createDeadWolfKilledBy(archerTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        // On tue 5 loups avec la mauvaise tour.
        for (int i = 0; i < 5; i++) {
            Wolf wolf = createDeadWolfKilledBy(mageTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(1150, game.getGold(), "Le joueur doit recevoir les récompenses des 10 loups.");
        assertEquals(100, game.getScore(), "Le score doit augmenter avec les 10 kills.");
        assertEquals(0, game.getMiniQuestManager().getCompletedQuestCount(), "La quête ne doit pas être complétée avec seulement 5 bons kills.");
        assertFalse(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête ne doit pas être terminée.");
        assertEquals(5, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "Seuls les 5 kills de l'Archer Tower doivent compter.");

        // On ajoute encore 5 loups tués avec la bonne tour.
        for (int i = 0; i < 5; i++) {
            Wolf wolf = createDeadWolfKilledBy(archerTower);

            game.getEnemies().add(wolf);
            game.removeDeadEntities();
        }

        assertEquals(0, game.getEnemies().size(), "Tous les loups morts doivent être supprimés.");
        assertEquals(1305, game.getGold(), "Le bonus doit être donné après les 10 bons kills.");
        assertEquals(200, game.getScore(), "Le score doit inclure les kills et le bonus de quête.");
        assertEquals(1, game.getMiniQuestManager().getCompletedQuestCount(), "Une seule quête doit être complétée.");
        assertTrue(game.getMiniQuestManager().getQuests().get(0).isCompleted(), "La quête doit être terminée après 10 bons kills.");
        assertEquals(10, game.getMiniQuestManager().getQuests().get(0).getCurrentAmount(), "La progression doit être à 10.");
    }

    //Vérifie qu'une Recruit est créée au moment où une Barrack est placée.
    @Test
    void barrackCreatesRecruitTest() {
        GameEntities gameEntities = new GameEntities();
        TowerManager towerManager = createTowerManagerForRespawnTest(gameEntities);

        // On place une Barrack niveau 1.
        Barrack barrack = placeBarrackForRespawnTest(towerManager, gameEntities);

        Recruit recruit = findRecruit(gameEntities);

        assertTrue(recruit != null, "Une Recruit doit être créée quand la Barrack est placée.");
        assertEquals(1, countRecruits(gameEntities), "Il doit y avoir une seule Recruit.");
        assertTrue(barrack.hasRecruitedTroop(), "La Barrack doit garder l'id de sa Recruit.");
        assertEquals(recruit.getId(), barrack.getRecruitedTroopId(), "L'id stocké doit être celui de la Recruit.");
    }
    // Vérifie qu'une Recruit morte ne réapparaît pas pendant la même vague.
    @Test
    void recruitDoesNotRespawnSameWaveTest() {
        GameEntities gameEntities = new GameEntities();
        TowerManager towerManager = createTowerManagerForRespawnTest(gameEntities);

        Barrack barrack = placeBarrackForRespawnTest(towerManager, gameEntities);
        Recruit recruit = findRecruit(gameEntities);

        assertTrue(recruit != null, "La Recruit doit exister avant le test.");

        // On tue la Recruit.
        recruit.takeDamage(9999);
        gameEntities.removeDeadEntities();

        // La Barrack remarque que sa Recruit est morte à la vague 0.
        towerManager.updateBarrackRespawns(0);

        assertEquals(0, countRecruits(gameEntities), "La Recruit morte doit être supprimée.");
        assertNull(findRecruit(gameEntities), "Il ne doit plus y avoir de Recruit.");
        assertFalse(barrack.hasRecruitedTroop(), "La Barrack ne doit plus avoir de Recruit vivante.");
        assertEquals(0, barrack.getRecruitedTroopDeathWave(), "La vague de mort doit être enregistrée.");

        // Toujours vague 0 : elle ne doit pas encore respawn.
        towerManager.updateBarrackRespawns(0);

        assertEquals(0, countRecruits(gameEntities), "La Recruit ne doit pas respawn dans la même vague.");
        assertNull(findRecruit(gameEntities), "Aucune nouvelle Recruit ne doit être créée dans la même vague.");
        assertFalse(barrack.hasRecruitedTroop(), "La Barrack ne doit toujours pas avoir de Recruit.");
    }
    // Vérifie qu'une Recruit morte réapparaît à la vague suivante.
    @Test
    void recruitRespawnsNextWaveTest() {
        GameEntities gameEntities = new GameEntities();
        TowerManager towerManager = createTowerManagerForRespawnTest(gameEntities);

        Barrack barrack = placeBarrackForRespawnTest(towerManager, gameEntities);
        Recruit firstRecruit = findRecruit(gameEntities);

        assertTrue(firstRecruit != null, "La première Recruit doit exister.");
        int firstRecruitId = firstRecruit.getId();

        // On tue la première Recruit.
        firstRecruit.takeDamage(9999);
        gameEntities.removeDeadEntities();

        // La Barrack enregistre la mort à la vague 0.
        towerManager.updateBarrackRespawns(0);

        assertEquals(0, countRecruits(gameEntities), "Il ne doit plus y avoir de Recruit après sa mort.");
        assertFalse(barrack.hasRecruitedTroop(), "La Barrack ne doit plus avoir de Recruit vivante.");

        // À la vague 1, la Recruit doit respawn.
        towerManager.updateBarrackRespawns(1);

        Recruit newRecruit = findRecruit(gameEntities);

        assertTrue(newRecruit != null, "Une nouvelle Recruit doit respawn après 1 vague.");
        assertEquals(1, countRecruits(gameEntities), "Il doit y avoir exactement une Recruit après le respawn.");
        assertTrue(barrack.hasRecruitedTroop(), "La Barrack doit stocker la nouvelle Recruit.");
        assertFalse(newRecruit.getId() == firstRecruitId, "La nouvelle Recruit doit avoir un autre id.");
        assertEquals(newRecruit.getId(), barrack.getRecruitedTroopId(), "La Barrack doit stocker l'id de la nouvelle Recruit.");
        assertEquals(-1, barrack.getRecruitedTroopDeathWave(), "La vague de mort doit être réinitialisée après le respawn.");
    }
    // Vérifie que la Barrack ne crée pas une nouvelle Recruit si la précédente est encore vivante.
    @Test
    void noRespawnWhileRecruitAliveTest() {
        GameEntities gameEntities = new GameEntities();
        TowerManager towerManager = createTowerManagerForRespawnTest(gameEntities);

        Barrack barrack = placeBarrackForRespawnTest(towerManager, gameEntities);
        Recruit recruit = findRecruit(gameEntities);

        assertTrue(recruit != null, "La Recruit doit exister.");
        assertEquals(1, countRecruits(gameEntities), "Il doit y avoir une seule Recruit au départ.");

        // Même après plusieurs vagues, si la Recruit est vivante, la Barrack ne doit pas en créer une autre.
        towerManager.updateBarrackRespawns(1);
        towerManager.updateBarrackRespawns(2);
        towerManager.updateBarrackRespawns(3);

        assertEquals(1, countRecruits(gameEntities), "La Barrack ne doit pas créer plusieurs Recruits si l'ancienne est vivante.");
        assertTrue(barrack.hasRecruitedTroop(), "La Barrack doit toujours avoir sa Recruit.");
        assertEquals(recruit.getId(), barrack.getRecruitedTroopId(), "L'id de la Recruit ne doit pas changer.");
    }

}