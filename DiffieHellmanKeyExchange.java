import java.math.BigInteger;
//import java.util.HashSet;
import java.util.Scanner;
import java.util.Random ;

//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
public class DiffieHellmanKeyExchange {


/*
Choose a prime number p and a base g that is a primitive root modulo p (see below).
These numbers could be determined by shouting across a room for all we care
Choose numbers:
Prime number p = 97
Prim root g = 17

Calculate:
Alice chooses a secret integer a (probably randomly), computes A=(g^a)%p, and sends A to Bob
Bob chooses a secret integer b (probably randomly), computes B=(g^b)%p, and sends B to Alice

Verify:
Alice computes key=(B^a)%p
Bob computes key=(A^b)%p

Write a program that simulates the Diffie-Hellman KEX protocol described above. 
Your program's output should verify that Alice's and Bob's keys match, and output all relevant variables.
*/

    //Make sure entered number is prime
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
        
        //Make sure they enter a prime number and reject 0
        while(!isPrime(p) || p == 0)
        {

            System.out.print(p);
            System.out.println(" is not prime") ;
            System.out.print("Please enter a prime number: ") ;
            p = scan.nextInt();
        }

        System.out.println(p + " is prime") ;


        //Comment out constant for this, RNG a g value
        //BigInteger g = BigInteger.valueOf(17) ;
        int gTemp = rand.nextInt(9999) ;
        BigInteger g = PrimitiveRoots.smallestPrimitiveRoot(BigInteger.valueOf(gTemp)) ;
        System.out.println("\nSecret) g = " + g) ;
        

        //Take a and b input, if they enter 0 use rng from earlier.
        System.out.print("\nEnter your \"a\" secret value (Enter 0 to randomize): ");
        int a = scan.nextInt() ;
        System.out.print("\nEnter your \"b\" secret value (Enter 0 to randomize): ");
        int b = scan.nextInt() ;

        if(a==0)
        {
            a = rand.nextInt(1000) ; //Change 1000 value to edit bounds
            System.out.println("\n(Secret) a = " + a) ;
        }
        if(b==0)
        {
            b = rand.nextInt(1000) ;
            System.out.println("\n(Secret) b = " + b) ;
        }
        scan.close() ; //Close scanner


        //Repeat computing instructions
        //Alice chooses a secret integer a (probably randomly), computes A=(g^a)%p, and sends A to Bob
        //Bob chooses a secret integer b (probably randomly), computes B=(g^b)%p, and sends B to Alice


        //Make variables bigIntegers for calculations
        BigInteger aCalcs = BigInteger.valueOf(a) ;
        BigInteger bCalcs = BigInteger.valueOf(b) ;
        BigInteger pCalcs = BigInteger.valueOf(p) ;

        BigInteger A = g.modPow(aCalcs, pCalcs) ;
        BigInteger B = g.modPow(bCalcs, pCalcs) ;

        System.out.println("\n\nAlice: *Shouting* Hey I calculated " + A + " for A") ;
        System.out.println("Bob: *Shouting* Yo I calculated " + B + " for B") ;



        //Repeat Verification instructions
        //Alice computes key=(B^a)%p
        //Bob computes key=(A^b)%p

        BigInteger verB = B.modPow(aCalcs, pCalcs) ; //Alice is verifying Bob's thing
        BigInteger verA = A.modPow(bCalcs, pCalcs) ; //Bob is verifying Alice's thing

        System.out.println("\n\nAlice: B verified is " + verB) ;
        System.out.println("Bob: A verified is " + verA) ;


        if(verB == verA) //If they are they same we're good!
        {
            System.out.println("\n" + verA + " = " + verB + " so we're good!") ;
        }

}
}

