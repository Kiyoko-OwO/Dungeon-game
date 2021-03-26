package unsw.dungeon.goal;


import unsw.dungeon.entity.*;
import unsw.dungeon.Dungeon;

public class SwitchGoal extends Goal {

    public SwitchGoal(String name, Dungeon dungeon) {
        super(name, dungeon);
    }

    @Override
    public boolean checkGoal() {
        for (Entity entity : super.getDungeon().getEntity()) {
            if (entity instanceof Switch) {
                if (entity.getCovered() != 1) {
                    return super.getGoal();
                }
            }
        }
        System.out.println("x");
        super.goalFinished();
        return super.getGoal();
    }
}