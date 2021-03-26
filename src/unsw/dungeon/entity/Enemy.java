package unsw.dungeon.entity;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.attackmode.*;
import unsw.dungeon.interact.Moving;
import unsw.dungeon.dijkstrapath.*;

public class Enemy extends Entity implements Moving{
	private Dungeon dungeon;
	ArrayList<Location> path;

    public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		Dijkstra findPath = new Dijkstra(this.dungeon);
		this.path = findPath.getshortestPath(x, y, dungeon.getPlayer().getX(), dungeon.getPlayer().getY());
    }

	public void autoMove() {
		int enemyX = super.getX();
		int enemyY = super.getY();
		Location next = getnextStep(enemyX, enemyY, this.path);
		if (next == null)	return;

		int nextX = next.getX();
		int nextY = next.getY();
		
		if (nextX > enemyX) {
			moveRight();
		} else if (nextX < enemyX) {
			moveLeft();
		} else if (nextY > enemyY) {
			moveDown();
		} else if (nextY < enemyY) {
			moveUp();
		}
		for (Entity e : this.dungeon.getEntity()) {
			if (e instanceof BlackHole) {
				if (nextX == e.getX() && nextY == e.getY()) {
					this.dungeon.getEntity().remove(this);
				}
			}
		}
	}

	public void moveAway(int playerX, int playerY, int enemyX, int enemyY) {
		Dijkstra findPath = new Dijkstra(this.dungeon);
		this.path = findPath.getFurthestPath(playerX, playerY, enemyX, enemyY);
		
	}

	public void moveTo(int playerX, int playerY, int enemyX, int enemyY) {
		Dijkstra findPath = new Dijkstra(this.dungeon);
		this.path = findPath.getshortestPath(enemyX, enemyY, playerX, playerY);
		
	}

	private Location getnextStep(int x, int y, ArrayList<Location> path) {
		for (Location lo : path) {
			Location prev = lo.getprev();
			if (lo.getX() == x && lo.getY() == y)	continue;
			if (prev.getX() == x && prev.getY() == y) {
				return lo;
			}
		}
		return null;
	}

    @Override
	public void moveUp() {
		this.y().set(this.getY()-1);
	}

	@Override
	public void moveDown() {
		this.y().set(this.getY()+1);
	}

	@Override
	public void moveLeft() {
		this.x().set(this.getX()-1);
	}

	@Override
	public void moveRight() {
		this.x().set(this.getX()+1);
	}

    @Override
	public void attack(Player player, int x, int y) {
		int playerX = player.getX();
		int playerY = player.getY();
		int enemyX = this.getX();
		int enemyY = this.getY();
		// if the player and the enemy in the same square or face to face, call attack
		if ( ((playerX == enemyX + 1) && (playerY == enemyY)) ||
				((playerX == enemyX - 1) && (playerY == enemyY)) ||	
				((playerX == enemyX) && (playerY == enemyY + 1)) ||
				((playerX == enemyX) && (playerY == enemyY - 1) ||
				((playerX == enemyX) && (playerY == enemyY))) ) {
			player.getAttackmode().attack(player.getEntityList(), player.getX(), player.getY());
            return;
		}

		if (player.getAttackmode() instanceof Invicible) {
			this.moveAway(playerX, playerY, enemyX, enemyY);
		} else {
			this.moveTo(playerX, playerY, enemyX, enemyY);
		}
	}


	@Override
	public void update(Player player) {
		this.attack(player, this.getX(), this.getY());
	}
}