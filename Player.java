import java.util.*;

public abstract class Player{
    
    protected int priority; //sort based on this
    protected String name;
    protected ArrayList<Mark> marks; //marks will be in order of priority because they're placed in order of priority

    protected String actionText; //prompting text 

    public abstract int act(); //returns 1 if successful, 0 if successful, -1 if not supported
    // successful is the actual action was successful (not rb'd, etc)
    public abstract int act(Player other); //Println() any necessary info when leaving act() to act(Player) or recursing on act(Player)

    public int getPriority(){
	return priority;
    }

    public String getName(){
	return name;
    }

    public void addMark(Mark m){
	marks.add(m);
    }

    public ArrayList<Mark> getMarks(){
        return marks;
    }

    public void clearMarks(){
        marks = new ArrayList<Mark>();
    }

    public String getActionText(){
        return actionText;
    }

    public String toString(){
        return name;
    }

    public boolean isBlocked(){
    	for (Mark mark: marks){
	    if (mark.getType() == "block"){
		return true;
	    }
	}
	return false;
    }

}
