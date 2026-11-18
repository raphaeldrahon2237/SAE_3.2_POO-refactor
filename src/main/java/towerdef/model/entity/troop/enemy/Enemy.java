package towerdef.model.entity.troop.enemy;

import javafx.geometry.Point2D;
import towerdef.model.Game;
import towerdef.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Troop;
import towerdef.model.core.movement.AvoidTowersPenalty;
import towerdef.model.core.movement.TilePenaltyStrategy;

public abstract class Enemy extends Troop {

    private List<Point2D> path;
    private int currentTileIndex;
    private Point2D currentTarget;
    private int reward;
    private Tower lastDamagingTower;
    private TilePenaltyStrategy tilePenaltyStrategy;



    public Enemy(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y, int reward) {
        super(hp, damage, armor, attackSpeed, range, mvSpeed, x, y);
        this.path = new ArrayList<>();
        this.currentTileIndex = 0;
        this.currentTarget = null;
        this.reward = reward;
        this.lastDamagingTower = null;
        this.tilePenaltyStrategy = new AvoidTowersPenalty();
    }

    public TilePenaltyStrategy getTilePenaltyStrategy() {
        return tilePenaltyStrategy;
    }

    public void setTilePenaltyStrategy(TilePenaltyStrategy tilePenaltyStrategy) {
        this.tilePenaltyStrategy = tilePenaltyStrategy;
    }

    public void setPath(List<Point2D> path) {
        this.path = new ArrayList<>(path);
    }

    public void startMovement() {
        if (!path.isEmpty()) {
            Point2D start = path.get(0);

            setX(start.getX());
            setY(start.getY());

            currentTileIndex = 0;
            currentTarget = null;
        }
    }

    public void updateMovement(double deltaTime) {
        if (currentTarget == null) {
            currentTarget = getNextTarget();
        }

        if (currentTarget != null) {
           moveTowardsTarget(deltaTime);
        }
    }

    public Point2D predictPositionAfter(double duration) {
        Point2D prediction = new Point2D(getX(), getY());
        double remainingDistance = getMooveSpeed() * duration;

        int targetIndex = currentTileIndex;
        Point2D target = currentTarget;

        if (target == null) {
            targetIndex++;
            target = getTargetAtIndex(targetIndex);

        }
        while (target != null && remainingDistance > 0) {
            double distance = calculateDistanceBetween(prediction, target);

           if (remainingDistance >= distance) {
                prediction = target;
                remainingDistance -= distance;
                targetIndex++;
            } else {
              prediction = calculateNextPosition(prediction, target, remainingDistance);
                remainingDistance = 0;
            }
           target = getTargetAtIndex(targetIndex);
        }
        return prediction;
    }

    private Point2D getNextTarget() {
        currentTileIndex++;

        if (currentTileIndex < path.size()) {
            return path.get(currentTileIndex);
        }

        return null;
    }

    public abstract void attack(Game game);

    public abstract boolean isInCombat(Game game);



    protected boolean attackAlliedTroopInRange(Game game) {
        Troop targetTroop = game.findAlliedTroopInRange(getX(), getY(), getRange());
        return attackTarget(targetTroop, game);
    }

    protected boolean attackTarget(Entity target, Game game){
        boolean hasTarget = false;

        if (target != null) {
            hasTarget = true;

            if (canAttack()) {
                hitTarget(target, game);
                resetAttackTimer();
            }
        }

        return hasTarget;

    }

    protected abstract void hitTarget(Entity target, Game game);


    protected void attackBaseInRange(Game game) {
        if (game.isBaseInRange(this) && canAttack()) {
                game.damageBase(getDamage());
                resetAttackTimer();
        }
    }

    public int getReward() {
        return reward;
    }


    public void setLastDamagingTower(Tower lastDamagingTower) {
        if (isAlive()) {
            this.lastDamagingTower = lastDamagingTower;
        }
    }
    public Tower getLastDamagingTower() {
        return lastDamagingTower;
    }

    private void moveTowardsTarget(double deltaTime){
        Point2D position = new Point2D(getX(), getY());
        double distance = calculateDistanceBetween(position, currentTarget);
        double movementDistance = getMooveSpeed() * deltaTime;

        if(movementDistance >= distance) {
            setX(currentTarget.getX());
            setY(currentTarget.getY());
            currentTarget = null;
        } else {
            Point2D nextPosition = calculateNextPosition(position,currentTarget, movementDistance);
            setX(nextPosition.getX());
            setY(nextPosition.getY());
        }
    }


    private Point2D getTargetAtIndex(int index){
        if (index < path.size()) {
           return path.get(index);
        }
        return null;
    }

    private Point2D calculateNextPosition(Point2D position, Point2D target, double movementDistance){
        double directionX = calculateDirection(target.getX(), position.getX());
        double directionY = calculateDirection(target.getY(), position.getY());
        double distance = calculateDistance(directionX, directionY);

        double normalizedX = calculateNormalized(directionX, distance);
        double normalizedY = calculateNormalized(directionY, distance);

        return new Point2D( position.getX() + normalizedX * movementDistance,
                position.getY() + normalizedY * movementDistance);
    }

    private double calculateDirection(double target, double position){
        return target - position;
    }

    private double calculateDistance(double directionX, double directionY){
        return Math.sqrt(directionX * directionX + directionY * directionY);
    }

    private double calculateNormalized(double direction, double distance) {
        return direction / distance;
    }

    private double calculateDistanceBetween(Point2D position, Point2D target){
        double directionX = calculateDirection(target.getX(), position.getX());
        double directionY = calculateDirection(target.getY(), position.getY());
        return calculateDistance(directionX, directionY);

    }

}
