package fr.sgcib.katatennis.scoreboard.contexts.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import fr.sgcib.katatennis.scoreboard.pub.Players;

/**
 * Utility class to ease the management of listeners (maintain a list of listeners,
 * add some listeners...) and notify all them (using utility methods).
 * 
 * Each event has a corresponding notify* method.
 * @see #notifyDeuce()
 * @see #notifyGameVictory(Players)
 * @see #notifyMatchVictory(Players)
 * @see #notifySetVictory(Players)
 * @see #notifyTieBreak()
 */
public class TennisListenerManager {
	/**
	 * Objects to notify when events (such as victory) happen.
	 */
	private List<ITennisEventListener> listeners;
	
	public TennisListenerManager() {
		listeners = new ArrayList<>();
	}
	
	public void addAll(TennisListenerManager otherManager) {
		listeners.addAll(otherManager.listeners);
	}
	
	public void addAll(Collection<ITennisEventListener> otherListeners) {
		listeners.addAll(otherListeners);
	}
	
	public void add(ITennisEventListener listener) {
		listeners.add(listener);
	}
	
	public List<ITennisEventListener> getListeners() {
		return listeners;
	}
	
	public void forEachListener(Consumer<? super ITennisEventListener> action) {
		listeners.forEach(action);
	}
	
	public void notifyMatchVictory(Players winner) {
		listeners.forEach(listener -> listener.onMatchVictory(winner));
	}
	
	public void notifySetVictory(Players winner) {
		listeners.forEach(listener -> listener.onSetVictory(winner));
	}
	
	public void notifyGameVictory(Players winner) {
		listeners.forEach(listener -> listener.onGameVictory(winner));
	}
	
	public void notifyTieBreak() {
		listeners.forEach(listener -> listener.onTieBreak());
	}
	
	public void notifyDeuce() {
		listeners.forEach(listener -> listener.onDeuce());
	}
}
