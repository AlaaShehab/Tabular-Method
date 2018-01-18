/**
 *
 */

/**
 * @author Personal
 *
 */
public class onesBits {

	public Integer count(Integer u) {
	Integer uCount = 0;

	uCount = u - ((u >> 1) & 033333333333) - ((u >> 2) & 011111111111);
	return ((uCount + (uCount >> 3)) & 030707070707) % 63;
	}

	public Integer anotherMethod(Integer u) {
			Integer uCount=u;
		       do
		       {
		             u=u>>1;
		             uCount -= u;
		       }
		       while(u != 0);
		       return uCount;
	}
}
