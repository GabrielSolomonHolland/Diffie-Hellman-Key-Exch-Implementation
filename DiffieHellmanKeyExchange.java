import java.math.BigInteger;
//import java.util.HashSet;
import java.util.Scanner;
import java.util.Random ;

//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
public class DiffieHellmanKeyExchange {


/*
Choose a prime number p and a base g that is a primitive root modulo p (see below).

Choose numbers:
Prime number p = 97
Prim root g = 17

Calculate:
These numbers could be determined by shouting across a room for all we care
Alice chooses a secret integer a (probably randomly), computes A=(g^a)%p, and sends A to Bob
Bob chooses a secret integer b (probably randomly), computes B=(g^b)%p, and sends B to Alice

Verify:
Alice computes key=(B^a)%p
Bob computes key=(A^b)%p

Write a program that simulates the Diffie-Hellman KEX protocol described above. 
Your program's output should verify that Alice's and Bob's keys match, and output all relevant variables.

Finding g requires some math, and is not necessarily trivial.  I've put together the following code that may help you in your implementation
*/

    public static boolean isPrime(int p)
    {
        for (int i = 2; i <= p/2; ++i) 
        {
        // condition for nonprime number
            if (p % i == 0) 
            {
                return false;
            }
        }
        return true;
    }

    public static void main(String[]args)
    {
        Scanner scan = new Scanner(System.in); //make scanner
        Random rand = new Random() ; //make RNG

        System.out.print("Enter a prime number (p): ") ;
        int p = scan.nextInt() ;


        //Give a constant for this, keep code for custom number commented out
        BigInteger g = BigInteger.valueOf(17) ;
        
        //System.out.print("Enter a primitive root: ") ;
        //int g = scan.nextInt() ;
        
    
        //Make sure they enter a prime number
        while(!isPrime(p) || p == 0)
        {

            System.out.print(p);
            System.out.println(" is not prime") ;
            System.out.print("Please enter a prime number: ") ;
            p = scan.nextInt();
        }

        System.out.println(p + " is prime") ;
        

        //Take a and b input, if they enter 0 use rng from earlier.
        System.out.print("Enter your \"a\" secret value (Enter 0 to randomize): ");
        int a = scan.nextInt() ;
        System.out.print("\nEnter your \"b\" secret value (Enter 0 to randomize): ");
        int b = scan.nextInt() ;

        if(a==0)
        {
            a = rand.nextInt(1000) ; //Change 1000 value to edit bounds
            System.out.println("a = " + a) ;
        }
        if(b==0)
        {
            b = rand.nextInt(1000) ;
            System.out.println("b = " + b) ;
        }
        scan.close() ; //Close scanner


        //Repeat computing instructions
        //Alice chooses a secret integer a (probably randomly), computes A=(g^a)%p, and sends A to Bob
        //Bob chooses a secret integer b (probably randomly), computes B=(g^b)%p, and sends B to Alice


        //Make A or B a big integer value of the long value of the double value of g^(a or b) % p
        BigInteger aCalcs = BigInteger.valueOf(a) ;
        BigInteger bCalcs = BigInteger.valueOf(b) ;
        BigInteger pCalcs = BigInteger.valueOf(p) ;

        BigInteger A = g.modPow(aCalcs, pCalcs) ;
        BigInteger B = g.modPow(bCalcs, pCalcs) ;

        System.out.println(A) ;
        System.out.println(B) ;
}
}

