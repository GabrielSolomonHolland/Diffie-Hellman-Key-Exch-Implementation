import java.math.BigInteger;
import java.util.HashSet;
public class PrimitiveRoots {

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

    public static void main(String [] args)
    {
        // "verified" with some known values from https://en.wikipedia.org/wiki/Primitive_root_modulo_n#Table_of_primitive_roots
        System.out.println(smallestPrimitiveRoot(BigInteger.valueOf(71)));
    }
}
