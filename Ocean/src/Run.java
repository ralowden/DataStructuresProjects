
/* Run is a class whose instances are the objects of DListNodes in a Run Length Encoding. Each 
 * Run object knows its type (either EMPTY, SHARK, or FISH), its hunger (empty and fish objects have a hunger of -1) 
 * and how many of each type it holds. 
 * 
 * NOTE: "hunger" is the shark's hunger level as based on Ocean's definition (new Shark's hunger = 0; after 1 time-step, 
 * hunger = 1, etc. If hunger = starveTime + 1, Shark dies. 
 */

public class Run {

	protected int type, size, hunger; 

	
	/*Constructor for a run containing sharks
	 * 
	 */
	public Run(int t, int s, int h) {
		this.type = t;
		this.size = s; 
		this.hunger = h;
		
	}
	/* Constructor for a run containing fish or nothing
	 * Hunger will equal -1 to be consistent with Ocean.sharkFeeding()
	 */
	
	public Run(int t, int s) {
		this(t, s, -1);
	}
	
	public int getType() {
		return type;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getHunger() {
		return hunger;
	}
	
	public void increaseSize() {
		this.size++;
	}
	
	public void decreaseSize() {
		this.size--;
	}
	
	public void setSize(int x) {
		this.size = x;
	}
	
	public String toString() {
		String t = "";
		if(type == Ocean.EMPTY) t = ".";
		if(type == Ocean.SHARK) { 
			t = "S" + hunger + ",";
		}
		if(type == Ocean.FISH) t = "F";
		return t + size;
	}
	
	public static void main(String[] args) {
		Run empty = new Run(Ocean.EMPTY, 4);
		Run fish = new Run(Ocean.FISH, 10);
		Run shark = new Run(Ocean.SHARK, 2, 5);
		System.out.println(empty.toString());
		System.out.println(fish.toString());
		System.out.println(shark.toString());
	}

}