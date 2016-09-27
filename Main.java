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
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
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
		
		// TODO methods to read in words, output ladder
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		String [] array1; 
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
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
				if (!spacefound)
					word1 += in.charAt(i);
				else
					word2 += in.charAt(i);
			}else
				spacefound = true;
		}
		ArrayList<String> output = new ArrayList<String>();
		output.add(word1); output.add(word2);
		return output;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
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
		for (int i = 0; i<ladder.size;i++)
			System.out.println(ladder.get(i));
	}
	// TODO
	// Other private static methods here
}
