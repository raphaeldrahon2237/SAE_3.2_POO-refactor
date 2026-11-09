package towerdef.model.power;

import towerdef.model.Game;

import java.util.ArrayList;

public class CombinedPower implements Power {

    private final ArrayList<Power> powers;

    public CombinedPower() {
        powers = new ArrayList<>();
        powers.add(new HealingPower());
        powers.add(new DamagePower());
        powers.add(new SlowPower());
    }

    @Override
    public void activate(Game game) {
        for (Power power : powers) {
            power.activate(game);
        }
    }
}
