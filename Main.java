/* WORD LADDER Main.java
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


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static Scanner kb;	// input Scanner for commands
	static PrintStream ps;	// output file
	static String[] dict;
	static List<Integer>[] adjList;
	static ArrayList<String> discovered = new ArrayList<String>();
	static Stack<String> DFSStack = new Stack<String>();
	
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
	}
	
	/*	public static void dispAdj (){
		for (int i=0;i<adjList.length;i++){
		ps.print(dict[i] + " : ");
		List<Integer> l= adjList[i];
			if (l!=null){
				for(int j:l){
					ps.print(dict[j]+"  ");
				}
				ps.println();
			}
		}
		ps.println(adjList.length);
	}
	*/
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		Set<String> st = makeDictionary();
		dict = st.toArray(new String[st.size()]);
		int s = dict.length;
		adjList = new ArrayList[s];
		for (int i=0;i<s;i++){
			adjList[i]= new ArrayList<Integer>();
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
		DFSStack.clear();
		discovered.clear();
		recDFS(start, end);
		
		return DFSStack.toArray().reverse(); // replace this line later with real return
	}
	
	public static void recDFS (String start,String end) {
		DFSStack.push(start);
		if (start.equals(end)){
			DFSStack.push(end);
		}
		else if (DFSStack.empty()){
			//do nothing
		}
		else if (deadEnd(start)){
			discovered.add(DFSStack.pop());
			recDFS(DFSStack.pop(), end);
		}
		else{
			discovered.add(start);
			recDFS(findNext(start), end);
		}	
	}
	
	public static String findNext(String start){
		int i =findIndex(start);
		String out = "";
		if (adjList[i]!=null)
			for (int j:adjList[i]){
				if (!discovered.contains(dict[j])){
					out=dict[j];
					break;
				}
			}
		return out;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
	    	ArrayList<String> found = new ArrayList<String>();
	    	boolean done = false;
	    	int endIndex = findIndex(end);
	    	int startIndex = findIndex(start);
	    	
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
	    	Stack<Integer> bfsStack = new Stack<Integer>();
	    	Stack<Integer> tempStack = new Stack<Integer>();
	    
	    	// Create Queue to keep track of BFS tree and words 
	    	Queue<Stack> bfsQueue = new LinkedList<Stack>; 
	    	bfsStack.push(start);
	    	bfsQueue.offer(bfsStack);
	    	
	    	//while Queue is not empty 
	    	while (!done && !bfsQueue.isEmpty()){
			
			//Remove the head of the queue
			temp_stack = bfsQueue.poll();
			
			int head = tempStack.peek();
	    		if ( head == endIndex) 
	    			foundIndex.addAll(tempStack);
	    		else if (discovered.contains(head)) {
	    			tempStack.pop();
	    		}
	    		else {
	    			discovered.add(head);
	    			List<Integer> currentList = adjList[head];
	    			if (currentList!=null)
	    				for (int i:currentList)					//FOR EACH neighbor of head
	    					if (!discovered.contains(i))	{									//IF neighbor has not been visited
	    						tempStack.push(i);								//mark neighbor's parent to be head (if parent != null)
	    						bfsQueue.offer(tempStack);												//add neighbor to queue.
	    						tempStack.pop();
	    					}
	    		}
	    	}
	    	return toStringList(foundIndex);	
	}
   	    	
    	public static int findIndex(String word){
    		int result = -1;
    		for (int i=0; i< dict.length;i++){
    			if (dict[i].equals(word))
    				result=i;
    		}
    		return result;
    	}
    
    	public static ArrayList<String> toStringList (List<Integer> index){
    		ArrayList<String> out = new ArrayList<String>();
    		for (int i:index){
    			ps.println(i);
    			out.add(dict[i]);
    		}
    		return out;
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
	
	public static void printLadder(ArrayList<String> ladder) {
		System.out.println("This is the word ladder : ");
		for (int i = 0; i<ladder.size();i++)
			System.out.println(ladder.get(i));
	}
	// TODO
	// Other private static methods here
}
