package towerdef.model.entity.impact;

public abstract class ImpactDecorator implements ImpactStrategy {

    protected ImpactStrategy impactStrategy;

    public ImpactDecorator(ImpactStrategy impactStrategy) {
        this.impactStrategy = impactStrategy;
    }
}
