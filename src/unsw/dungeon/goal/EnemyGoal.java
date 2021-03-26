package unsw.dungeon.goal;

import unsw.dungeon.entity.*;
import unsw.dungeon.Dungeon;

public class EnemyGoal extends Goal {

    public EnemyGoal(String name, Dungeon dungeon) {
        super(name, dungeon);
    }

    @Override
    public boolean checkGoal() {
        for (Entity entity : super.getDungeon().getEntity()) {
            if (entity instanceof Enemy) {
                return super.getGoal();
            }
        }
        super.goalFinished();
        return super.getGoal();
    }
}