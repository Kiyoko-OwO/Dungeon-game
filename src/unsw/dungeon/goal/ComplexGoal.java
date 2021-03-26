package unsw.dungeon.goal;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.Dungeon;

/**
 * Complexgoal 
 */
public class ComplexGoal extends Goal {
    private List<Goal> goals;
    private boolean allNeedFinish;

    public ComplexGoal(String name, Dungeon dungeon, boolean allNeedFinish) {
        super(name, dungeon);
        this.goals = new ArrayList<>();
        this.allNeedFinish = allNeedFinish;
    }

    public void addGoal(Goal... allgoals) {
        for (Goal goal : allgoals) {
            this.goals.add(goal);
        }
    }

    @Override
    public boolean checkGoal() {
        for (Goal goal : this.goals) {
            if (this.allNeedFinish) {
                if (!goal.checkGoal()) {
                    return super.getGoal();
                } 
            } else {
                if (goal.checkGoal()) {
                    super.goalFinished();
                    return super.getGoal();
                }
            }
        }
        if (this.allNeedFinish) {
            super.goalFinished();
            return super.getGoal();
        } else {
            return super.getGoal();
        }
    }

}
