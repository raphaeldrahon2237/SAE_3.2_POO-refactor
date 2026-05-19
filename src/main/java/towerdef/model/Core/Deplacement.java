package towerdef.model.Core;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.geometry.Point2D;

import javafx.util.Duration;

import towerdef.model.entity.Troop;

import java.util.ArrayList;
import java.util.List;

public class Deplacement {

    private Troop troop;

    // Chaque Point2D représente une case avec x et y
    private List<Point2D> chemin;

    private double dureeParCase;

    private Timeline timeline;

    private int indexCaseActuelle;

    // Si on ne précise pas la durée, elle sera de 0.35 seconde par case
    public Deplacement(Troop troop, List<Point2D> chemin) {
        this(troop, chemin, 0.35);
    }

    public Deplacement(Troop troop, List<Point2D> chemin, double dureeParCase) {
        this.troop = troop;

        this.chemin = new ArrayList<>(chemin);

        this.dureeParCase = dureeParCase;
    }

    public void start() {

        if (chemin.isEmpty()) {
            return;
        }

        stop();

        // On récupère la première case du chemin
        Point2D depart = chemin.get(0);

        // On place la troupe au point de départ
        troop.setX(depart.getX());
        troop.setY(depart.getY());

        // On indique que la troupe est actuellement sur la première case
        indexCaseActuelle = 0;

        deplacerVersProchaineCase();
    }

    public void stop() {

        if (timeline != null) {
            timeline.stop();
        }
    }

    private void deplacerVersProchaineCase() {

        // On passe à la case suivante
        indexCaseActuelle++;

        // Si l'index dépasse la taille du chemin cela veut dire que la troupe est arrivée à la fin
        if (indexCaseActuelle >= chemin.size()) {
            return;
        }

        // On récupère la case cible
        Point2D cible = chemin.get(indexCaseActuelle);

        // On crée une animation qui va modifier progressivement x et y
        timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(dureeParCase),

                        // On anime la propriété x de la troupe jusqu'au x de la cible
                        new KeyValue(
                                troop.xProperty(),
                                cible.getX(),
                                Interpolator.LINEAR
                        ),

                        // pareil pour Y
                        new KeyValue(
                                troop.yProperty(),
                                cible.getY(),
                                Interpolator.LINEAR
                        )
                )
        );

        // on relance automatiquement le déplacement vers la prochaine case
        timeline.setOnFinished(event -> deplacerVersProchaineCase());

        timeline.play();
    }
}