package towerdef.model.entity.troop.enemy;

import javafx.geometry.Point2D;
import towerdef.model.Game;
import towerdef.model.Utils;
import towerdef.model.entity.AttackType;
import towerdef.model.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import towerdef.model.entity.tower.Tower;
import towerdef.model.entity.troop.Troop;

public abstract class Enemy extends Troop {

    private List<Point2D> path;
    private int currentTileIndex;
    private Point2D currentTarget;
    private int reward;
    private Tower lastDamagingTower;

    public Enemy(int hp, int damage, int armor, int attackSpeed, int range, int mvSpeed, int x, int y, int reward) {
        super(hp, damage, armor, attackSpeed, range, mvSpeed, x, y);
        this.path = new ArrayList<>();
        this.currentTileIndex = 0;
        this.currentTarget = null;
        this.reward = reward;
        this.lastDamagingTower = null;
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
            double currentX = getX();
            double currentY = getY();

            double targetX = currentTarget.getX();
            double targetY = currentTarget.getY();

            double directionX = targetX - currentX;
            double directionY = targetY - currentY;

            double distance = Math.sqrt(directionX * directionX + directionY * directionY);

            if (distance == 0) {
                currentTarget = null;
            } else {
                double movementDistance = getMooveSpeed() * deltaTime;

                if (movementDistance >= distance) {
                    setX(targetX);
                    setY(targetY);
                    currentTarget = null;
                } else {
                    double normalizedX = directionX / distance;
                    double normalizedY = directionY / distance;

                    setX(currentX + normalizedX * movementDistance);
                    setY(currentY + normalizedY * movementDistance);
                }
            }
        }
    }

    public Point2D predictPositionAfter(double duration) {
        double predictionX = getX();
        double predictionY = getY();
        double remainingDistance = getMooveSpeed() * duration;

        int targetIndex = currentTileIndex;
        Point2D target = currentTarget;

        if (target == null) {
            targetIndex++;

            if (targetIndex < path.size()) {
                target = path.get(targetIndex);
            }
        }

        while (target != null && remainingDistance > 0) {
            double directionX = target.getX() - predictionX;
            double directionY = target.getY() - predictionY;
            double distance = Math.sqrt(directionX * directionX + directionY * directionY);

            if (distance == 0) {
                targetIndex++;
            } else if (remainingDistance >= distance) {
                predictionX = target.getX();
                predictionY = target.getY();
                remainingDistance -= distance;
                targetIndex++;
            } else {
                double normalizedX = directionX / distance;
                double normalizedY = directionY / distance;

                predictionX += normalizedX * remainingDistance;
                predictionY += normalizedY * remainingDistance;
                remainingDistance = 0;
            }

            if (targetIndex < path.size()) {
                target = path.get(targetIndex);
            } else {
                target = null;
            }
        }

        return new Point2D(predictionX, predictionY);
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
        boolean hasTarget = false;
        Troop targetTroop = game.findAlliedTroopInRange(getX(), getY(), getRange());

        if (targetTroop != null) {
            hasTarget = true;

            if (canAttack()) {
                targetTroop.takeDamage(getDamage());
                incrementAttackCounter();
                resetAttackTimer();
            }
        }

        return hasTarget;
    }

    protected boolean shootProjectileAtAlliedTroopInRange(Game game) {
        boolean hasTarget = false;
        Troop targetTroop = game.findAlliedTroopInRange(getX(), getY(), getRange());

        if (targetTroop != null) {
            hasTarget = true;

            if (canAttack()) {
                shootProjectileAtTarget(targetTroop, game, getAttackType());
                resetAttackTimer();
            }
        }

        return hasTarget;
    }

    protected void attackBaseInRange(Game game) {
        if (game.isBaseInRange(this)) {
            if (canAttack()) {
                game.damageBase(getDamage());
                resetAttackTimer();
            }
        }
    }

    protected void shootProjectileAtTarget(Entity target, Game game, AttackType projectileType) {
        Point2D targetTile = Utils.toTilePosition(target.getX(), target.getY());
        incrementAttackCounter();
        game.createProjectile(this, target, targetTile, projectileType);
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
}
