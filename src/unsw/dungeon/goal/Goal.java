package unsw.dungeon.goal;

import unsw.dungeon.entity.*;
import unsw.dungeon.Dungeon;

public class Goal implements EntityObserver{
    private String name;
    private boolean finished;
    private Dungeon dungeon;


    public Goal(String name, Dungeon dungeon) {
        this.name = name;
        this.finished = false;
        this.dungeon = dungeon;
        this.dungeon.setGoal(this);
    }

    public String getName() {
        return this.name;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }
    
    public boolean checkGoal() {
        return this.finished;
    }
    
    public void goalFinished() {
        this.finished = true;
    }

    public boolean getGoal() {
        return this.finished;
    }
    public void setGoal() {
        this.finished = false;
    }


    @Override
    public void update(Player player) {
        if (checkGoal()) {
            System.out.println("Congratulations!! You WIN the game");
        }
    }
}