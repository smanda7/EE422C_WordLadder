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
		static ArrayList<String>  respath = new ArrayList<String>();
		static Stack<String> DFSStack = new Stack<String>();

		
		
		public static void main(String[] args) throws Exception {

			ArrayList<String> ladder = new ArrayList<String>();
			
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
			//ps.println(getWordLadderDFS("HEART","TEART"));
			
			ArrayList<String> userInput = parse(kb);
			
			if(!userInput.get(0).equals("//quit")){

			String start = userInput.get(0);
			String end = userInput.get(1);

	        start = start.toUpperCase();
	        end = end.toUpperCase();

			ladder = getWordLadderDFS(start, end);
			//ladder = getWordLadderBFS(start, end);

			//ps.println(DFSStack.size());
			//ps.println(dict.length);

	       printLadder(ladder);

			}
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
			for (int i=0;i<s;i++)
				adjList[i]= new ArrayList();
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
			int j=0;
			for (int i=0;i<w1.length();i++){
				if (w1.charAt(i) !=w2.charAt(i)){
					j++;
				}
			}
			return j==1;
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

			// Check if start and end are linked.
	        if(linked(start, end)) {
	        	discovered.add(start);
	        	discovered.add(end);
	        	return(discovered);
	        }
	        
	        try{
	        	DFSStack.clear();
	        	discovered.clear();
	        	recDFS(start, end);
	        }
			catch(StackOverflowError e){
				DFSStack.clear();
				discovered.clear();
				recDFS(end, start);
				Collections.reverse(DFSStack);
			}

			ArrayList<String> dfsArrayList = new ArrayList<String>();
			dfsArrayList.addAll(DFSStack);
			return dfsArrayList; 
		}

		public static void recDFS (String start,String end) {
			DFSStack.push(start);
			if (start.equals(end)){
				//do nothing
			}
			else if (DFSStack.empty()){
				//do nothing
			}
			else if (findNext(start).equals("")){
				discovered.add(DFSStack.pop());
				if (!DFSStack.empty())
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
			
		    	//ArrayList<String> found = new ArrayList<String>();
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
		    	
	         // Check if start and end are linked.
	         if(linked(start, end)) {
	        	respath.add(start);
	        	respath.add(end);
	        	return(respath);
	         }

		    	int temp_node = 0;

		    	int start_node = 0;
		    	int end_node = 0;

	            //System.out.println(" Dict length = " + dict.length);

		    	boolean node_visited[] = new boolean[dict.length];
		    	int prev_node[] = new int[dict.length];
		    
		    	// Create Queue to keep track of BFS tree and words 
		    	Queue bfsQueue = new LinkedList(); 
	            
	            // get the node number for start and end words
	            for(int i = 0; i < dict.length; i++) {

	                  node_visited[i] = false;
	                  prev_node[i] = -1;
	            }

	            start_node = findIndex(start);
	            end_node = findIndex(end);

	           // System.out.println(" Start_node = " + start_node);
	           // System.out.println(" end_node = " + end_node);

		    	
		    	//bfsStack.push(start_node);
		    	bfsQueue.offer(start_node);
		    
		    	
		    	//while Queue is not empty 
		    while (!done && !bfsQueue.isEmpty()) {
				
				//Remove the head of the queue
				
				temp_node = (int)bfsQueue.poll();

				// System.out.println(" temp_node = " + temp_node);

				if(temp_node == end_node) {
	                 
	                 done = true;
					// end node found. trace back and get the path
					respath.add(end);

					int curNode = end_node;
					while(curNode != start_node) {
						curNode = prev_node[curNode];
						respath.add(dict[curNode]);
					}
	            
	            //reverse the result list to have the start  first
	    		Collections.reverse(respath);
	    		return respath;
				
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

					node_visited[temp_node] = true;

	                 for(int j =  0; j < dict.length; j++)  {

	                 	   if (j == temp_node) continue;

	                 	   if( node_visited[j])  continue;   // this node is already visited

	                       // check if j is a neighbor of temp_node
	                 	   if(adjList[temp_node].contains(j)) {
	                 	   	     //temp_stack.push(j);
		    	                 bfsQueue.offer(j);
		    	                 prev_node[j] = temp_node;
	                 	   }
	                 }


				}

				
		    }  // end of while loop

			
		   // end not found
			return respath;
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
				//infile = new Scanner (new File("short_dict.txt"));
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
			int ladderLength = ladder.size();
			String start = ladder.get(0);
			String end = ladder.get(ladder.size() -1);
			if (ladderLength < 2){
				System.out.println("no word ladder can be found between " + start +" and " + end + ".");
			}
			else{
				System.out.println("a " + (ladderLength-2) + "-" + "rung word ladder exists between " + start.toLowerCase() + " and " + end.toLowerCase());
				for (String s:ladder){
					System.out.println(s.toLowerCase());
				}
			}
		}
}
