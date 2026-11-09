package towerdef.model.power;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import towerdef.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PowerManager {

    private final Game game;
    private final Random random;
    private final ArrayList<Power> powers;
    private final HashMap<Power, IntegerProperty> quantities;

    public PowerManager(Game game, Random random, ArrayList<Power> powers) {
        this.game = game;
        this.random = random;
        this.powers = powers;
        quantities = new HashMap<>();

        for (Power power : powers) {
            quantities.put(power, new SimpleIntegerProperty(0));
        }
    }

    public Power gainRandomPower() {
        Power power = powers.get(random.nextInt(powers.size()));
        IntegerProperty quantity = quantityProperty(power);
        quantity.set(quantity.get() + 1);
        return power;
    }

    public IntegerProperty quantityProperty(Power power) {
        return quantities.get(power);
    }

    public boolean usePower(Power power) {
        if (game.isGameOver() || !quantities.containsKey(power)) {
            return false;
        }

        IntegerProperty quantity = quantityProperty(power);
        if (quantity.get() <= 0) {
            return false;
        }

        quantity.set(quantity.get() - 1);
        power.activate(game);
        return true;
    }
}
