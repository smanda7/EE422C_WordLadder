* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Siva Manda
 * sm48525
 * 16480
 * Clement Pardon
 * cp34735
 * 16460
 * Slip days used: <0>
 * Git URL: https://github.com/smanda7/EE422C_WordLadder
 * Fall 2016
 */


//package assignment3;
import java.util.*;
import java.io.*;

public class Main_ver101 {
	
	// static variables and constants only here.
	static Scanner kb;	// input Scanner for commands
	static PrintStream ps;	// output file
	static String[] dict;
	static List<Integer>[] adjList;
	static ArrayList<String> discovered = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();
		ps.println(getWordLadderBFS("START","SMART"));
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		Set<String> st = makeDictionary();
		dict = st.toArray(new String[st.size()]);
		int s = dict.length;
		adjList = new ArrayList[s];
		// TODO more code
		for (int i=0;i<s;i++){
			for (int j=i;j<s;j++){
				if (linked(dict[i],dict[j])){
					adjList[i].add(j);
					adjList[j].add(i);
				}
			}
		}
	}
	/**
	 * Simply returns if two words are different by only one letter
	 * @param w1 first word to compare
	 * @param w2 second word to compare
	 * @return boolean
	 */
	private static boolean linked(String w1, String w2){
		int i;
		for (i=0;i<w1.length();i++){
			if (w1.charAt(i) !=w2.charAt(i)){
				i++;
			}
		}
		return i==1;
	}
	
	/**
	 * @param keyboard Scanner connected to the input (System.in most of the time)
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String in = keyboard.nextLine();	
		String word1 = "";
		String word2 = "";
		boolean spacefound=false;
		for (int i=0;i<in.length();i++){
			if((in.charAt(i)!=' ')&&(in.charAt(i)!='\t')){
				if (!spacefound){
					word1 += in.charAt(i);
				}
				else{
					word2 += in.charAt(i);
				}
			}
			else{
				spacefound = true;
			}
		}
		ArrayList<String> output = new ArrayList<String>();
		output.add(word1); 
		output.add(word2);
		return output;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
	    	ArrayList<String> found = new ArrayList<String>();
	    	boolean done = false;
		//ladder.add(start); 
	    	
	    	/*
		* BFS starting at word start as source node
		* Build tree with dictionary words by changing one letter at a time
		* Adding children nodes
		* Mark nodes/words as discovered when traversing through tree
		* End word should be a leaf node that does not require more children
		* If reach a dead end, go back to last valid parent node and explore different path
		* Number of levels in tree to get to end word is the length of our word ladder
		* Use a queue to keep track of words and check them 
		* Ex: Queue<Stack> 
		*/
	    	
	    	//create stacks to push and pop words from
	    	//Stack bfsStack = new Stack();
	    	//Stack temp_stack = new Stack();
	    	int temp_node = 0;

	    	int start_node = 0;
	    	int end_node = 0;

	    	boolean node_visited[] = new boolean[dict.length];
	    	int prev_node[] = new int[dict.length];
	    
	    	// Create Queue to keep track of BFS tree and words 
	    	Queue bfsQueue = new LinkedList(); 
            
            // get the node number for start and end words
            for(int i = 0; i < dict.length; i++) {
            	 if (dict[i] == start) {
            	 	start_node = i;
            	 }

            	 if (dict[i] == end) {
            	 	end_node = i;
            	 }

                  node_visited[i] = false;
                  prev_node[i] = -1;
            }

	    	
	    	//bfsStack.push(start_node);
	    	bfsQueue.offer(start_node);
	    
	    
		// Set<String> dict = makeDictionary();
		// TODO more code
	    	
	    	//while Queue is not empty 
	    while (!done && !bfsQueue.isEmpty()) {
			
			//Remove the head of the queue
			//temp_stack = bfsQueue.poll();
			//temp_node =  (int) temp_stack.pop();
			temp_node = (int)bfsQueue.poll();

			if(temp_node == end_node) {
                 
                 done = true;
				// end node found. trace back and get the path
				discovered.add(end);

				int curNode = end_node;
				while(curNode != start_node) {
					curNode = prev_node[curNode];
					discovered.add(dict[curNode]);
				}
            
            //reverse the result list to have the start first
    		Collections.reverse(discovered);
    		return discovered;
			
			}

			/*
			IF head == value, return found.
			IF head has been visited: {
				discard the head.
				go back to start of while loop.
			}
			ELSE {
				mark head visited.
				FOR EACH neighbor of head
					IF neighbor has not been visited
						mark neighbor's parent to be head (if parent != null)
						add neighbor to queue.
			*/

			if(!node_visited[temp_node]) {

                 for(int j =  0; j < dict.length; j++)  {

                 	   if (j == temp_node) continue;

                 	   if( node_visited[j])  continue;   // this node is already visited

                       // check if j is a neighbor of temp_node
                 	   if(adjList[temp_node].contains(j)) {
                 	   	     //temp_stack.push(j);
	    	                 bfsQueue.offer(temp_node);
	    	                 prev_node[j] = temp_node;
                 	   }
                 }


			}

			
	    }  // end of while loop

		
	   // end not found
		return discovered;
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	

public static void printLadder(ArrayList<String> ladder, String start, String end) {
		int ladderLength = ladder.size();
		if (ladderLength < 2){
			System.out.println("no word ladder can be found between " + start +" and " + end + ".");
		}
		else{
			System.out.println("This is the word ladder : ");
			for (int i = 0; i<ladderLength;i++){
				System.out.println(ladder.get(i));
			}
		}
	}
	
}
