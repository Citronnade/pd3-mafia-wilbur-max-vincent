import java.util.*;

public class Hooker extends Player{ 

    //should we use static initialization blocks for this kind of stuff?
    public Hooker(String name){
	this.name = name;
	priority = 4;
	marks = new ArrayList<Mark>();
    actionText = "Please choose someone to block: ";
    }

    public int act(){
	return 0;
    }

    public int act(Player other){
	if (isBlocked()){
	    return -1;
	}
	block(other);
	return -1;
    }
    public int block(Player other){
	other.addMark(new Mark(this, "block"));
	return 1;
    }


}
