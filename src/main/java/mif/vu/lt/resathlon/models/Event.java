package mif.vu.lt.resathlon.models;

/*
 * Event - Sport competition
 */
public class Event implements Comparable<Event> {
	
	protected String NAME;
	protected double POINTS;
	protected double SCORE;
	protected Integer[] places;
	
	public Event(String name) {
		this.NAME = name;
	}
	
	public Event(String name, double points) { 
		this(name);
		this.POINTS = points;
	}
	
	public Event(String name, double points, double score) {
		this(name, points);
		this.SCORE = score;
	}
	
	public String getName() { 
		return this.NAME;
	}
	
	public double getPoints() {
		return this.POINTS;
	}
	
	public double getScore() { 
		return this.SCORE;
	}
	
	public void setName(String name) {
		this.NAME = name;
	}
	
	public void setPoints(double points) {
		this.POINTS = points;
	}
	
	public void setScore(double score) { 
		this.SCORE = score;
	}
	
	public void setPlaces(Integer[] places) {
		this.places = places;
	}
	
	public Integer[] getPlaces() {
		return this.places;
	}
	
	@Override
	public int compareTo(Event o) {
        if (this.SCORE > o.SCORE) {
            // Compared event score less then comparable event
            return -1;
        } else if (this.SCORE < o.SCORE) {
            // Compared event score greater than comparable Score
            return 1;
        }
		return 0;
	}
}
