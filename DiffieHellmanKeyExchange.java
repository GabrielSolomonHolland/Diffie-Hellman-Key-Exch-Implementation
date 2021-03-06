//Gabriel Solomon Holland

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Random ;

public class DiffieHellmanKeyExchange {


/*

Instructions:

Choose a prime number p and a base g that is a primitive root modulo p (see below).
These numbers could be determined by shouting across a room for all we care
Choose numbers:

Calculate:
Alice chooses a secret integer a (probably randomly), computes A=(g^a)%p, and sends A to Bob
Bob chooses a secret integer b (probably randomly), computes B=(g^b)%p, and sends B to Alice

Verify:
Alice computes key=(B^a)%p
Bob computes key=(A^b)%p

Write a program that simulates the Diffie-Hellman KEX protocol described above. 
Your program's output should verify that Alice's and Bob's keys match, and output all relevant variables.
*/

    public static void main(String[]args)
    {
        Scanner scan = new Scanner(System.in); //make scanner
        Random rand = new Random() ; //make RNG

        System.out.print("Enter a prime number (p): ") ;
        int p = scan.nextInt() ;
        
        //Make sure they enter a prime number and reject 0 because it makes the code no fun
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

        //seed value for more rng
        int gTemp = rand.nextInt(999999) ;

        //Prime factors of the randomized value
        HashSet<BigInteger> gHS = (primeFactors(BigInteger.valueOf(gTemp))) ;

        //make it an array so we can yoink out just one of the factors to use
        Object[] gAL = gHS.toArray() ;

        //Take one of those objects and then take the smallest prim root of it. Split into multiple parts for clarity. 
        BigInteger primeNum = (BigInteger)(gAL[rand.nextInt(gAL.length)]) ;
        BigInteger primRoot = smallestPrimitiveRoot(primeNum) ;
        BigInteger g = primRoot ;
        System.out.println("\ng = " + g) ;
        

        //Take a and b input, if they enter 0 use rng.
        System.out.print("\nEnter your \"a\" secret value (Enter 0 to randomize): ");
        int a = scan.nextInt() ;
        System.out.print("\nEnter your \"b\" secret value (Enter 0 to randomize): ");
        int b = scan.nextInt() ;


        //If they want rng
        if(a==0)
        {
            a = rand.nextInt(100000) ; //Change 100000 value to edit bounds
            System.out.println("\n(Secret) a = " + a) ;
        }
        if(b==0)
        {
            b = rand.nextInt(100000) ;
            System.out.println("(Secret) b = " + b) ;
        }
        scan.close() ; //Close scanner


        //Repeat computing instructions
        //Alice chooses a secret integer a (probably randomly), computes A=(g^a)%p, and sends A to Bob
        //Bob chooses a secret integer b (probably randomly), computes B=(g^b)%p, and sends B to Alice



        //Make a section to hardcode values for quick testing
        //a = 13 ;
        //b = 15 ;
        //p = 23 ;
        //g = BigInteger.valueOf(5) ;


        //System.out.println("Hardcoded values for testing: ") ;
        System.out.println("\np = " + p + "\ng = " + g) ;

        //Make variables bigIntegers for calculations
        BigInteger aCalcs = BigInteger.valueOf(a) ;
        BigInteger bCalcs = BigInteger.valueOf(b) ;
        BigInteger pCalcs = BigInteger.valueOf(p) ;

        BigInteger A = g.modPow(aCalcs, pCalcs) ;
        BigInteger B = g.modPow(bCalcs, pCalcs) ;

        System.out.println("\nAlice calulated " + A + " for A") ;
        System.out.println("Bob calculated " + B + " for B") ;



        //Repeat Verification instructions
        //Alice computes key=(B^a)%p
        //Bob computes key=(A^b)%p

        BigInteger verB = B.modPow(aCalcs, pCalcs) ; //Alice is verifying Bob's thing
        BigInteger verA = A.modPow(bCalcs, pCalcs) ; //Bob is verifying Alice's thing

        System.out.println("\nB verified is " + verB) ;
        System.out.println("A verified is " + verA) ;


        if(verB == verA) //If they are they same we're good!
        {
            System.out.println("\n" + verA + " = " + verB + " so we're good!") ;
        }

}

//Import code from Dr. Eloe for prime factors and smallest prim roots
public static HashSet<BigInteger> primeFactors(BigInteger composite)
    {
        HashSet<BigInteger> pFactors = new HashSet<>();
        int twopows = composite.getLowestSetBit();
        if (twopows > 0)
        {
            pFactors.add(BigInteger.TWO);
            composite = composite.shiftRight(twopows); // Shifting right is equivalent to a division by 2
            // this is also fine; BigInts are immutable so we're not breaking anything
        }
        BigInteger currdivisor = BigInteger.valueOf(3);
        while (!(composite.compareTo(currdivisor) <= 0))
        {
            while (composite.mod(currdivisor).equals(BigInteger.ZERO))
            {
                pFactors.add(currdivisor);
                composite = composite.divide(currdivisor);
            }
            currdivisor = currdivisor.add(BigInteger.TWO); // this will sometimes not be prime, but the while above
            // ensures we're OK!
        }
        // if composite is actually not composite, and not two...
        if (composite.compareTo(BigInteger.TWO) > 0)
            pFactors.add(composite);
        return pFactors;
    }
    public static BigInteger smallestPrimitiveRoot(BigInteger n)
    {
        // Thanks to https://en.wikipedia.org/wiki/Primitive_root_modulo_n#Finding_primitive_roots
        // Wiki is our best friend
        // precondition: n is prime
        assert n.isProbablePrime(7);
        BigInteger phi_n = n.subtract(BigInteger.ONE);
        HashSet<BigInteger> factors = primeFactors(phi_n);
        BigInteger g = BigInteger.TWO;

        while (g.compareTo(phi_n) <= 0)
        {
            boolean is_congruent_1 = false;
            for (BigInteger factor: factors)
            {
                if (g.modPow(phi_n.divide(factor), n).equals(BigInteger.ONE))
                    is_congruent_1 = true;
            }
            if (!is_congruent_1)
                return g;
            g = g.add(BigInteger.ONE);
        }
        return BigInteger.valueOf(-1);
    }

    
        //Make checker so entered number is prime
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

}


//Gabriel Solomon Holland


/*
Write your code in one single source file (if you use Java) or use the Python gmpy notebooks and print to a .pdf.
In either comments at the bottom of your .java file or a Markdown cell in the Python Notebook put the output of 
at least 3 runs with different values of p of your program (using next_prime with random starting points works 
quite well for determining this, or just hard-code different values).  Additionally, answer the following questions
in comments at the bottom of your code and the :

1. How did you determine the secret integers for both Alice and Bob?  Were there limitations or bounds on how you chose the secret integers?

2. What values could an eavesdropper determine by listening on the insecure channel?
is this enough to recreate the entire secret key?


Submit your java file or a printed pdf of your python notebook with the above specified information
 (output of at least 3 runs, answers to the questions


 Run#1: 
        Enter a prime number (p): 334
        334 is not prime
        Please enter a prime number: 337
        337 is prime

        g = 2

        Enter your "a" secret value (Enter 0 to randomize): 0

        Enter your "b" secret value (Enter 0 to randomize): 0

        (Secret) a = 21349
        (Secret) b = 91232

        Alice calulated 104 for A
        Bob calculated 256 for B

        B verified is 169
        A verified is 169
        They equal each other

Run#2
        Enter a prime number (p): 767
        767 is not prime
        Please enter a prime number: 897
        897 is not prime
        Please enter a prime number: 567
        567 is not prime
        Please enter a prime number: 113
        113 is prime

        g = 2

        Enter your "a" secret value (Enter 0 to randomize): 0

        Enter your "b" secret value (Enter 0 to randomize): 0

        (Secret) a = 45112
        (Secret) b = 29179

        Alice calulated 16 for A
        Bob calculated 8 for B

        B verified is 28
        A verified is 28
        They equal each other
Run#3
    Enter a prime number (p): 9873
    9873 is not prime
    Please enter a prime number: 5677
    5677 is not prime
    Please enter a prime number: 99763
    99763 is not prime
    Please enter a prime number: 9011
    9011 is prime

    g = -1

    Enter your "a" secret value (Enter 0 to randomize): 0

    Enter your "b" secret value (Enter 0 to randomize): 0

    (Secret) a = 22123
    (Secret) b = 64669

    Alice calulated 9010 for A
    Bob calculated 9010 for B

    B verified is 9010
    A verified is 9010 
    They equal each other

Run#4 Because Run 3 gave me a g value of -1
    Enter a prime number (p): 9241
    9241 is prime

    g = 2

    Enter your "a" secret value (Enter 0 to randomize): 0

    Enter your "b" secret value (Enter 0 to randomize): 0

    (Secret) a = 4621
    (Secret) b = 7800

    Alice calulated 2 for A
    Bob calculated 7025 for B

    B verified is 7025
    A verified is 7025
    They equal each other


Question 1: How did you determine the secret integers for both Alice and Bob?
Were there limitations or bounds on how you chose the secret integers?

    I let the user either enter a number or pick a random number under 100,000. I used 100k because I wanted a reasonably large number
    but not something rediculus, I also left room to make it bigger or smaller if I wanted different results

Question 2: What values could an eavesdropper determine by listening on the insecure channel? Is this enough to recreate the entire secret key?

    The information publicly available: p, g. If they continued listening they could get A and B (A,B =/= a,b. Lowercase are secret).
    They could not get the secret a or b value which is the message they are trying to steal. Even with the A, B, p, g they can't
    reverse engineer the original a/b value out because there are multiple values that could fit, as is the nature of modulus. When we are
    using numbers under 100k they could maybe guess it but if a and b were much larger (int representations of strings) then there is
    no realistic way of getting the original value. 
*/


//Gabriel Solomon Holland just throwing my name everywhere since this file is LONG

