package pzemtsov;

public class Hasher {
    public int hashCode(long key) {
	return (int) (key % 946840871);
    }
}
// 32423809332432432432423 -> 12121