package Lab4;

import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;

/**
 * Huffman Encoder class to compress text files
 *	Designed so that one instance of the class can compress/decompress one file
 *	This is because the JFileChooser method we are given is very finicky and
 *	does not like to be called twice during runtime
 * @author Andy Werchniak and Rob Sayegh
 */
public class HuffmanEncoder{
	//instance variables
	private String pathName, compressedPathName;
	private Map<Character, String> codeMap;
	private BinaryTree<CharFreq> tree;
	
	/**
	 * Method to decompress the compressed file and print it to a new file of the same name with _decompressed
	 * @throws IOException
	 */
	public void decompress() throws IOException{
		int bit;
		//create buffered bit reader to read the file
		BufferedBitReader bitOutputFile = new BufferedBitReader(this.compressedPathName);
		
		//create buffered writer to write decompressed code
		String decompressedPathName = appendName(pathName, false);
		BufferedWriter outputFile =  new BufferedWriter(new FileWriter(decompressedPathName));
		BinaryTree<CharFreq> current = this.tree;
		
		//read bit input and fill a string with 0's and 1's
		try{
			bit = bitOutputFile.readBit();

			if(tree!=null)
				if(this.tree.height()==0)
					outputFile.write(tree.getValue().getChar());
			else{
				while(bit!=-1){
					if (bit == 0)
						current = current.getLeft();
					else
						current = current.getRight();
					
					if (current.isLeaf()) {
						outputFile.write(current.getValue().getChar());
						current = tree;
					}
					bit = bitOutputFile.readBit();
				}
			}
		}
		catch(IOException e){
			System.err.println("Unhandled IOException: "+ e);
		}
		finally{
			bitOutputFile.close();
			outputFile.close();
		}

		System.out.println("File " + compressedPathName +" successfully decompressed and saved as " + decompressedPathName);
		
	}
	
	/**
	 * Prompts the user for an input file and saves a compressed version of it, adding _compressed to its name
	 * @throws IOException
	 */
	public void compress() throws IOException{
		int i;
		
		//fill map of characters and their codes
		makeTreeAndMap();

		//if we are compressing a second file, we need its path
		if(pathName ==null)
			this.pathName = getFilePath();
			
		//Create a buffered reader to read the file
		BufferedReader inputFile = new BufferedReader(new FileReader(this.pathName));
		
		//Create a buffered writer to write new file
		this.compressedPathName = appendName(pathName, true);
		BufferedBitWriter bitOutputFile = new BufferedBitWriter(this.compressedPathName);
		
		try {
			if(this.tree!=null){
				int cInt = inputFile.read();
					while (cInt != -1){
						Character character = new Character((char) cInt);
						String s = codeMap.get(character);
					
						for(i=0;i<s.length();i++){
							if(s.charAt(i) == '0')
								bitOutputFile.writeBit(0);
							else if(s.charAt(i) == '1')
								bitOutputFile.writeBit(1);
							else System.err.println("Something went wrong");
						}
				
						cInt = inputFile.read(); // read the next character's integer representation
					}
		  }
		}
		finally {
		  inputFile.close();
		  bitOutputFile.close();
		}
		
		System.out.println("File " + pathName +" successfully compressed and saved as " + compressedPathName);
		
	}
	
	/**
	 * Method to append "_compressed" or "_decompressed" to a file path while keeping its extension
	 * NOTE: Strings may not include a '.' unless it indicates the beginning of its extension
	 * @param name String to which we add the suffix
	 * @param compressed whether to add "_compressed" or "_decompressed"
	 * @return the appended String
	 */
	private static String appendName (String name, Boolean compressed){
		String newName = "";
		String character;
		int i;
		//iterate through the string until we reach a period, which we'll assume is the beginning of the extension
		for(i = 0; name.charAt(i)!='.' && i<name.length()-1;i++){
			character = "" + name.charAt(i);
			newName = newName.concat(character);
		}
		
		//if we reached the end with no period, there's no extension
		if(i==name.length()-1){
			System.err.println("No file extension");
			return null;
		}
		
		//append to the end of it
		if(compressed == true)
			newName = newName.concat("_compressed.txt");
		else newName = newName.concat("_decompressed.txt");
		
		//return the appended String
		return newName;
		
	}
	
	/**
	 * This method creates the Huffman Binary Tree and also creates the map of characters
	 * 	to their String codes
	 */
	private void makeTreeAndMap() throws IOException{
		//fill a map of characters and their frequencies
		Map<Character, Integer> charCodes = mapCharFreqs();
		if (charCodes.size() != 0){
			//fill a min priority queue with nodes of the CharFreq objects
			PriorityQueue<BinaryTree<CharFreq>> queue = fillQueue(charCodes);
		
			//create a single tree out of the priority queue
			fillTree(queue);
			
			if(charCodes.size()!=0)
				//Create the code map for compressing
				treeToMap(this.tree);
			else this.tree = null;
		}
	}
	
	/**
	 * Private helper method to convert the Huffman binary tree into a map of character codes
	 */
	private void treeToMap(BinaryTree<CharFreq> tree){
		Map<Character,String> codeMap = new HashMap<Character,String>();
		
		if(tree!= null)
			//pass in an empty string to start the path
			mapHelper(tree, codeMap, "");
		this.codeMap = codeMap;
	}
	
	/**
	 * Private recursive helper method for treeToMap
	 */
	private void mapHelper(BinaryTree<CharFreq> node, Map<Character, String> codeMap, String path){
		//if we've reached a leaf, our path is done
		if(node.isLeaf()){
				codeMap.put(node.getValue().getChar(), path);
		}else{
			//otherwise, if we go left we add a 0 to the path, and if we go right we add a 1
			mapHelper(node.getLeft(), codeMap, path + "0");
			mapHelper(node.getRight(), codeMap, path + "1");
		}
	}
	
	/**
	 * Helper method to create a single tree based on a priority queue of nodes
	 */
	private void fillTree(PriorityQueue<BinaryTree<CharFreq>> queue){
		BinaryTree<CharFreq> node = new BinaryTree<CharFreq>(null);		//initialize to avoid annoying error
		BinaryTree<CharFreq> a,b;
		
		while(queue.peek()!= null){
			a=queue.poll();
			if(queue.peek()!=null){		//second check-> the last time through there will only be 1 node in the queue
				b=queue.poll();
				//calling second constructor. Data=CharFreq object with null character, freq that's the sum of children
				//left child = CharFreq object a, right child = CharFreq object b
				node = new BinaryTree<CharFreq>(new CharFreq(null,a.getValue().getFreq()+b.getValue().getFreq()),a,b);
				//add new node to the queue
				queue.add(node);
			} else node = a;
		}
		
		this.tree = node;
	}
	
	/**
	 * Method to fill a priority queue based on a map of characters and their frequencies
	 * @return priority queue of CharFreq nodes
	 */
	private PriorityQueue<BinaryTree<CharFreq>> fillQueue(Map<Character,Integer> charCodes){
		Character temp;
		BinaryTree<CharFreq> node;
		//create a charFreq object for each key in the map
		//create a singleton tree for each object and fill a min priority queue
		PriorityQueue<BinaryTree<CharFreq>> queue = new PriorityQueue<BinaryTree<CharFreq>>(1, new TreeComparator());
		Set<Character> charKeys = charCodes.keySet();
		Iterator<Character> iter = charKeys.iterator();
			
		while(iter.hasNext()){
			temp = iter.next();
			node = new BinaryTree<CharFreq>(new CharFreq(temp, charCodes.get(temp)));
			queue.add(node);
		}

		return queue;
	}
	
	/**
	 * method to create a map of characters and their frequencies
	 * @return a HashMap of characters and the frequencies they appeared at
	 */
	private Map<Character, Integer> mapCharFreqs() throws IOException{
		int cInt;
		char thisChar;
		this.pathName = getFilePath();
		BufferedReader inputFile = new BufferedReader(new FileReader(this.pathName));
		Map<Character,Integer> returnMap = new HashMap<Character,Integer>();
		
		//scan the file, and fill a map of characters and frequencies
		try {
			cInt = inputFile.read();
			 while (cInt != -1){
				thisChar = (char)cInt;
				
				if(returnMap.containsKey(thisChar))
					returnMap.put(new Character(thisChar), new Integer(returnMap.get(thisChar)+1));
				else returnMap.put(new Character(thisChar), new Integer(1));
				
				cInt = inputFile.read(); // read the next character's integer representation
		  }
			
		  return returnMap;
		}
		finally {
		  inputFile.close();
		}
	}
	
	/**
   * Puts up a fileChooser and gets the path name for the file to be opened.
   * Returns an empty string if the user clicks Cancel.
   * @return path name of the file chosen
   */
 public static String getFilePath() {
   // Create a file chooser.
   JFileChooser fc = new JFileChooser();
    
   int returnVal = fc.showOpenDialog(null);
   if (returnVal == JFileChooser.APPROVE_OPTION) {
     File file = fc.getSelectedFile();
     String pathName = file.getAbsolutePath();
     return pathName;
   }
   else
     return "";
  }
 	
 	/**
 	 * Private class to store a character and its frequency
   * 	for use as a node in the binary tree
 	 * @author andywerchniak
 	 */
 	private class CharFreq{
 		private Character c;	//use wrapper class so we can set it to null sometimes
 		private int freq;
 		
		public CharFreq(Character c, Integer freq){
 			this.c=c;
 			this.freq=freq;
 		}
 		
		public char getChar(){
 			return c;
 		}
 		
		public int getFreq(){
 			return freq;
 		}
 		
 		@SuppressWarnings("unused")
		public void setChar(char c){
 			this.c=c;
 		}
 		
 		@SuppressWarnings("unused")
		public void setFreq(int freq){
 			this.freq = freq;
 		}
 		
 		// "//" indicates the character value is null
 		public String toString(){
 			if(c==null)
 				return "" + "//" + freq;
 			else return ""+c+freq;
 		}
 	}
 	
 	/**
 	 * Private helper class for the min priority queue
 	 * @author andywerchniak
 	 */
 	private class TreeComparator implements Comparator<BinaryTree<CharFreq>>{
 		
 		public int compare(BinaryTree<CharFreq> a, BinaryTree<CharFreq> b){
 			//negative if a has smaller, 0 if same, positive if b has smaller
 			return a.getValue().getFreq()-b.getValue().getFreq();
 		}
 	}
 	
 	/**
 	 * main method to test
 	 */
 	public static void main(String[] args){
//		HuffmanEncoder test = new HuffmanEncoder();
		
		//test mapCharFreqs() code
//		Map<Character, Integer> charCodes = test.mapCharFreqs();
//		Set<Character> keys = charCodes.keySet();
//		Iterator<Character> iter = keys.iterator();
//		char temp1;
//		
//		while(iter.hasNext()){
//			temp1 = iter.next();
//			System.out.println("Character: " + temp1 + "\n\tFrequency: " + charCodes.get(temp1));
//		}
		
		//test fillQueue(Map<Character,Integer>) code
//		PriorityQueue<BinaryTree<CharFreq>> queue = test.fillQueue(charCodes);
//		CharFreq temp2;
//		while(queue.peek()!= null){
//		temp2 = queue.poll().getValue();
//		System.out.println("Character: " + temp2.getChar() + "\n\tFrequency: " + temp2.getFreq());
//	}
		
		//test fillTree(PriorityQueue<BinaryTree<CharFreq>>)
//		BinaryTree<CharFreq> tree = test.fillTree(queue);
//		System.out.print(tree);
		
		//test treeToMap
//		Map<Character, String> codeMap = test.treeToMap(tree);
//	Set<Character> keys = codeMap.keySet();
//	Iterator<Character> iter2 = keys.iterator();
//	char temp3;
//	
//	while(iter2.hasNext()){
//		temp3 = iter2.next();
//		System.out.println("Character: " + temp3 + "\n\tCode: " + codeMap.get(temp3));
//	}
		
		//test createCodeMap()
//		Map<Character, String> codeMap = test.createCodeMap();
//		Set<Character> keys = codeMap.keySet();
//		Iterator<Character> iter = keys.iterator();
//		char temp;
//
//		while(iter.hasNext()){
//			temp = iter.next();
//			System.out.println("Character: " + temp + "\n\tCode: " + codeMap.get(temp));
//		}
		
		//test appendName helper method
//		System.out.println(appendName("WarAndPeace.txt", true));
//		
		//test program with test input
//		test.compress();
//		test.decompress();
		
//		HuffmanEncoder mobyDick = new HuffmanEncoder();
//		mobyDick.compress();
//		mobyDick.decompress();
		
//		HuffmanEncoder USConstitution = new HuffmanEncoder();
//		USConstitution.compress();
//		USConstitution.decompress();
		
		HuffmanEncoder huff = new HuffmanEncoder();
		try{
			huff.compress();
		}
		catch (IOException e){
			System.err.println("Unhandled IOException: "+ e);
		}
		
		try{
			huff.decompress();
		}
		catch(IOException ex){
			System.err.println("Unhandled IOException: " +ex);
		}
	}
}