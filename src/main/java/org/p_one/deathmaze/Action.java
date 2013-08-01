package org.p_one.deathmaze;

public interface Action {
	public boolean execute(Game game);
	public String getDescription();
}
