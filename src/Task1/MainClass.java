//mohamed nasser esmail 20180241
// Mohamed Sobh Mohamed 20180226

package Task1;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class MainClass {
	
	
 final static  int  numberOfRegister=9;
 final static int loopsNumber=511; 
 
	
	
static int GetNewInput(int valuesOfGates[],Queue<Integer> state )
	{
		
		int sum=0;
		for (int i=valuesOfGates.length-1;i>=0;i--)
		{
			int x=state.peek();
			sum+=valuesOfGates[i]*x;
			state.poll();
		}
		return sum%2;
	}

static int [] generateKey(Queue<Integer> initialState,int []valuesOfGates)
{ 

	Queue<Integer> temp = new LinkedList<Integer>(); 

	int []key=new int [loopsNumber];
	for (int i=0;i<loopsNumber;i++)
	{
		key[i]=initialState.peek();
		for (int x:initialState)temp.add(x); // here we get a copy from initialState to be as a parameter in GetNewInput function
		int input=GetNewInput(valuesOfGates, temp);		
	    initialState.poll();
	    initialState.add(input);
		
		temp.clear();
		
	}

	return key;
	


}
		
static Queue<String> readFromFile(String path ) throws IOException
{

	Queue<String> data = new LinkedList<String>();
	File file = new File( path);
   
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st;
    while ((st = br.readLine()) != null)

    {
    	data.add(st);
    	
    
    }
    return data;
	}


static ArrayList<Integer> XOR(int []key,ArrayList<Integer>plantext,int warmup)
{
	ArrayList<Integer>cipherText = new ArrayList<Integer>();
	int []xorKey=new int[plantext.size()];
	int index=0;
	for (int i=warmup;i<plantext.size()+warmup;i++) //shifting by WarmUp
	{
		
		xorKey[index]=key[i];
		index++;
	}


	for (int i=0;i<plantext.size();i++) //here we do Xor with the planText 
	{
		int x=(xorKey[i]+plantext.get(i))%2;  //do XOR 
		
		cipherText.add(x);
	}
	
   return cipherText;
	
}

static ArrayList<Integer>  encryption(int []key,ArrayList<Integer>planText,int warmup)
{
	ArrayList<Integer> encryptoText=XOR(key,planText,warmup);
	return encryptoText;
	
	
}

static ArrayList<Integer> Decryption(int []key,ArrayList<Integer>cipherText,int warmup)
{
	
	ArrayList<Integer> originalText=XOR(key,cipherText,warmup);
	
	return originalText;

}


public static void main(String[] args) throws IOException {
	
	String path="input.txt";
	Queue<String>data=readFromFile(path);
	String initial=data.peek();data.poll();
	String Values=data.peek();data.poll();
	String warm=data.peek();data.poll();
	
	
     int [] valuesOfGates=new int [numberOfRegister] ;
	 Queue<Integer> initialState = new LinkedList<Integer>();
	 ArrayList<Integer> plantext = new ArrayList<Integer>();

	 int warmUp= Integer.parseInt(warm);
	 String plantext1=data.peek();data.poll(); 
	for (int i=initial.length()-1;i>=0;i--)//convert from String to integer   
	{
		if (initial.charAt(i)=='1')
		{
			initialState.add(1);
		}
		else if (initial.charAt(i)=='0')
		{
			initialState.add(0);
		}
		
	}
	for (int i=0;i<numberOfRegister;i++)
	{if (Values.charAt(i)=='1')  //convert from String to integer
	{		

		valuesOfGates[i]=1;
	}
	else if (Values.charAt(i)=='0')
	{
		valuesOfGates[i]=0;
	}
	}
	
	for (int i=0;i<plantext1.length();i++)
	{
		if (plantext1.charAt(i)=='1')
			plantext.add(1);
		else if  (plantext1.charAt(i)=='0')
			plantext.add(0);
			
	}
	
	
	
	int []key=generateKey( initialState,valuesOfGates);         //get key 
	
	ArrayList<Integer> ciphertext=encryption(key, plantext, warmUp); // use key to encrypt planText
	
	ArrayList<Integer>originalMassage=Decryption(key, ciphertext, warmUp); //use the same key to get the original message
	
	
	
    System.out.println ("key =");
	for (int k:key)
	System.out.print(k);
	
	System.out.println ("\n");
	
	System.out.println ("plantext");
	System.out.println (plantext);
	System.out.println ();
	
	System.out.println ("encryption text = ");
	System.out.println (ciphertext);
	System.out.println ();
	
	System.out.println ("deencryption text");
	System.out.println (originalMassage);
	
	

     
	

}
}


