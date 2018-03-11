import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Wordladder {
	
	/*dictionary*/
	static HashSet<String> words =new HashSet<String>(); 
	/*top word and tail word*/
	static String word1,word2;
	
	
	
	
	
	/*
	 * function readdic()
	 * read the dictionary to the memory 
	 */
	public static void readdic() {
		File file = new File("src/EnglishWords.txt");
		try{
			FileReader dic = new FileReader(file);
			BufferedReader edic = new BufferedReader(dic);
				
			/*the read word*/
			String word = null;
				
			/*add words to the set*/
			while((word = edic.readLine())!=null) {
				words.add(word);
			}
			edic.close();
			dic.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
		
	
	
	
	
	
	
	
	
	/*
	 * function getWord()
	 * Get the top word and tail word of a WordLadder
	 */
	public static boolean getWord() {
		
		Scanner scanner = new Scanner(System.in);
		
		/*read the top word*/
		System.out.println("The top word:");
		word1=scanner.next();
		
		/*read the tail word*/
		System.out.println("\nThe tail word:");
		word2=scanner.next();
		
		scanner.close();
		
		int l1 = word1.length();
		int l2 = word2.length();
		
		/*
		 * check if two words are valid
		 * */
		
		/*the same length*/
		if(l1 != l2) {
			System.out.println("the length of two words is not the same.\n");
			return false;
		}
		
		/*not equal*/
		else if(word1==word2){
			System.out.println("the two words must be different.\n");
			return false;
		}
		
		/*the top word and the tail word is in the dictionary or not*/
		else {
			if(words.add(word1)) {
				System.out.println("the top word is not exist in the dictionary.\n");
				words.remove(word1);
				return false;
			}
			if(words.add(word2)) {
				System.out.println("the tail word is not exist in the dictionary.\n");
				words.remove(word2);
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	/*
	 * function bridge()
	 * find the ladder form word1 to word2
	 * */
	public static void bridge() {
		
		if(getWord()) {
			/*a clone of dictionary*/
			HashSet<String> cset = words;
			
			/*the length of word*/
			int len = word2.length();
			
			/*used to change word*/
			String list="abcdefghijklmnopqrstuvwxyz";
			
			Queue<Stack<String>> LadderQueue = new LinkedList<Stack<String>>();
			
			Stack<String> ladder = new Stack<String>();
			
			/*initialize*/
			ladder.push(word2);
			LadderQueue.add(ladder);
			
			
			while(!LadderQueue.isEmpty()) {
				
				ladder = LadderQueue.poll();
				
				String lastword = ladder.peek();
				
				for(int j=0;j<len;j++) {
					
					for(int i =0;i<26;i++) {
					
						if(list.substring(i, i+1) != lastword.substring(j, j+1)) {
							
							/*neb_word:the neighbor word*/
							String neb_word = lastword.substring(0, j)+list.substring(i, i+1)
							+lastword.substring(j+1);
							
							
							
							/*case:find the ladder*/
							if(neb_word.equals(word1)) {
								
								ladder.add(neb_word);
								/*print the ladder*/
								int ls = ladder.size();
								for(int k=0;k<ls;k++) {
									System.out.println(ladder.pop());
								}
								return;
									
							}
							
							
							
							/*case:neighbor word is invalid*/
							else if(cset.add(neb_word)) {
								words.remove(neb_word);
							}
							
							
							
							/*case:neighbor word is valid but not find the ladder*/
							else {
								Stack<String> nextladder =new Stack<String>();
								nextladder =(Stack<String>)ladder.clone();
								nextladder.push(neb_word);
								LadderQueue.add(nextladder);
								cset.remove(neb_word);
								
							}
						}
					}
				}
			}
			
			System.out.print("There is not ladder form "+word1+" to "+word2+".\n");
		}
	}
	
	public static void main(String[] args) {
		readdic();
		bridge();
	}

}
