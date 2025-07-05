// Modular Exponentiation using Fast Power (Binary Exponentiation)
public class ModularExponentiation {

    public static int modExp(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (int)((1L * result * base) % mod);
            }
            base = (int)((1L * base * base) % mod);
            exp = exp >> 1;
        }

        return result;
    }

    public static void main(String[] args) {
        int base = 2, exp = 10, mod = 1_000_000_007;
        System.out.println("Modular exponentiation result: " + modExp(base, exp, mod));
    }
}

/*
🧠 Logic:
- Exponentiation by squaring
- Reduce time from O(exp) to O(log exp)

⏱ Time Complexity: O(log exp)
🧠 Space Complexity: O(1)
*/
