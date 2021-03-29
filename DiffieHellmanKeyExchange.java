import java.math.BigInteger;
import java.util.HashSet;
import java.util.Scanner;

import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
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
        for (int i = 2; i <= p / 2; ++i) 
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
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter a prime number (p): ") ;
        int p = scan.nextInt() ;
    
        if(isPrime(p))
        {
            System.out.print(p);
            System.out.println(" Is Prime") ;
        }
        else
        {
            System.out.print(p);
            System.out.println(" Is not Prime") ;
        }

    }
}

