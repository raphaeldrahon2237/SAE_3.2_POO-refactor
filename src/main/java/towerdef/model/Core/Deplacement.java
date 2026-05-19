package towerdef.model.Core;

import javafx.geometry.Point2D;
import towerdef.model.entity.Troop;

import java.util.ArrayList;
import java.util.List;

public class Deplacement {

    // La troupe que l'on veut déplacer
    private Troop troop;

    private List<Point2D> chemin;

    private double vitesse;

    private int indexCaseActuelle;

    private boolean enCours;

    public Deplacement(Troop troop, List<Point2D> chemin) {
        this(troop, chemin, 0.05);
    }

    public Deplacement(Troop troop, List<Point2D> chemin, double vitesse) {
        this.troop = troop;
        this.chemin = new ArrayList<>(chemin);
        this.vitesse = vitesse;
        this.indexCaseActuelle = 0;
        this.enCours = false;
    }

    public void start() {
        if (chemin.isEmpty()) {
            return;
        }

        // On place la troupe sur la première case du chemin
        Point2D depart = chemin.get(0);
        troop.setX(depart.getX());
        troop.setY(depart.getY());

        // La prochaine cible sera la deuxième case du chemin
        indexCaseActuelle = 1;

        enCours = true;
    }

    public void stop() {
        enCours = false;
    }

    public void update() {
        if (!enCours) {
            return;
        }

        if (indexCaseActuelle >= chemin.size()) {
            stop();
            return;
        }

        // On récupère la prochaine case cible
        Point2D cible = chemin.get(indexCaseActuelle);

        double xActuel = troop.getX();
        double yActuel = troop.getY();

        double xCible = cible.getX();
        double yCible = cible.getY();

        double dx = xCible - xActuel;
        double dy = yCible - yActuel;

        double distance = Math.sqrt(dx * dx + dy * dy);

        // Si la troupe est assez proche de la cible,on la place directement sur la case cible
        if (distance <= vitesse) {
            troop.setX(xCible);
            troop.setY(yCible);

            // Puis on passe à la case suivante
            indexCaseActuelle++;
            return;
        }

        // Normalisation du vecteur de déplacement
        double directionX = dx / distance;
        double directionY = dy / distance;

        troop.setX(xActuel + directionX * vitesse);
        troop.setY(yActuel + directionY * vitesse);
    }

    public boolean isEnCours() {
        return enCours;
    }
}