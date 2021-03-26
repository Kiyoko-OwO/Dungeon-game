package unsw.dungeon.goal;

import unsw.dungeon.Dungeon;

public class ExitGoal extends Goal {

    public ExitGoal(String name, Dungeon dungeon) {
        super(name, dungeon);
    }    

    @Override
    public boolean checkGoal() {
        if (super.getDungeon().getPlayer().getExit() == 1) {
            super.goalFinished();
        } else {
            super.setGoal();
        }

        return super.getGoal();
    }
}