package unsw.dungeon.goal;

import unsw.dungeon.entity.*;
import unsw.dungeon.Dungeon;

public class TreasureGoal extends Goal {
    private Dungeon dungeon;

    public TreasureGoal(String name, Dungeon dungeon) {
        super(name, dungeon);
        this.dungeon = dungeon;
    }

    @Override
    public boolean checkGoal() {
        for (Entity entity : this.dungeon.getEntity()) {
            if (entity instanceof Treasure) {
                return super.getGoal();
            }
        }
        super.goalFinished();
        return super.getGoal();
    }
}