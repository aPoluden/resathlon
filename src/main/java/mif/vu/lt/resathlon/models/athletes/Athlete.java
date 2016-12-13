package mif.vu.lt.resathlon.models.athletes;

import java.util.Set;

import mif.vu.lt.resathlon.models.Event;

public abstract class Athlete implements Comparable<Athlete> {
	
	protected String NAME_SURNAME; // TODO split name from surname
	protected double total_score;
	// Athlete participated events
	protected Set<Event> events;
	
	public Athlete(String[] dataset) {
		events = createEvents(dataset);
	}
	
	/*
	 * returns filled in Event set
	 */
	public abstract Set<Event> createEvents(String[] dataset);
	
	public Set<Event> getEvents() {
		return this.events;
	}
	
	public void setTotalScore(double score) {
		this.total_score = score;
	}
	
	public double getTotalScore() { 
		return this.total_score;
	}
		
	public String getName() {
		return this.NAME_SURNAME;
	}
	
	public abstract int compareTo(Athlete atl);
	
	
}
