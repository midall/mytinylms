/*
 * SHATest.java
 * JUnit based test
 *
 * Created on August 24, 2003, 11:06 AM
 */

package gnu.java.security.provider;

import java.security.MessageDigest;
import junit.framework.*;
import java.util.*;
/**
 *
 * @author werni
 */
public class SHATest extends TestCase {
    
    public SHATest(java.lang.String testName) {
        super(testName);
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(SHATest.class);
        return suite;
    }
    
    /** Test of the SHA MessageDigest.
     *
     * Let the message be the ASCII binary-coded form of "abc", i.e.,
     * 01100001  01100010  01100011.
     * <p>
     * Message digest = A9993E36 4706816A BA3E2571 7850C26C 9CD0D89D
     *                  f53ebf2f8fc10d5f6f058cdf0f47f8d6ab71bab2
     * <p>
     * Example taken from Appendix A of [1].
     *
     * [1] Federal Information Processing Standards Publication 180-1.
     * Specifications for the Secure Hash Standard.  April 17, 1995.
     */
    public void testSampleMessageA() {
        System.out.println("testSampleMessageA");
        
        String message = "abc";
        byte[] expectedDigest = new byte[] {
            (byte) 0xA9, (byte) 0x99, (byte) 0x3E, (byte) 0x36,
            (byte) 0x47, (byte) 0x06, (byte) 0x81, (byte) 0x6A,
            (byte) 0xBA, (byte) 0x3E, (byte) 0x25, (byte) 0x71,
            (byte) 0x78, (byte) 0x50, (byte) 0xC2, (byte) 0x6C,
            (byte) 0x9C, (byte) 0xD0, (byte) 0xD8, (byte) 0x9D
        };
        
        SHA md = new SHA();
        for (int i=0; i < message.length(); i++) {
            md.update((byte) message.charAt(i));
        }
        byte[] digest = md.digest();
        
        if (! Arrays.equals(digest, expectedDigest)) {
            fail("Illegal digest:"+dump(digest));
        }
    }
    
    /** Test of the SHA MessageDigest.
     *
     * Let the message be the binary-coded form (cf. Appendix A) of the ASCII string
     *     "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq".
     * <p>
     * Message digest = 84983E44 1C3BD26E BAAE4AA1 F95129E5 E54670F1
     * <p>
     * Example taken from Appendix B of [1].
     *
     * [1] Federal Information Processing Standards Publication 180-1.
     * Specifications for the Secure Hash Standard.  April 17, 1995.
     */
    public void testSampleMessageB() {
        System.out.println("testSampleMessageB");
        
        String message = "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
        byte[] expectedDigest = new byte[] {
            (byte) 0x84, (byte) 0x98, (byte) 0x3E, (byte) 0x44,
            (byte) 0x1C, (byte) 0x3b, (byte) 0xD2, (byte) 0x6E,
            (byte) 0xBA, (byte) 0xAE, (byte) 0x4A, (byte) 0xA1,
            (byte) 0xF9, (byte) 0x51, (byte) 0x29, (byte) 0xE5,
            (byte) 0xE5, (byte) 0x46, (byte) 0x70, (byte) 0xF1
        };
        
        SHA md = new SHA();
        for (int i=0; i < message.length(); i++) {
            md.update((byte) message.charAt(i));
        }
        byte[] digest = md.digest();
        
        
        if (! Arrays.equals(digest, expectedDigest)) {
            fail("Illegal digest:"+dump(digest));
        }
    }
    

    /** Test of the SHA MessageDigest.
     *
     * Let the message be the binary-coded form of the ASCII string which
     * consists  of 1,000,000 repetitions of "a".  
     * <p>
     * Message digest = 34AA973C D4C4DAA4 F61EEB2B DBAD2731 6534016F
     * <p>
     * Example taken from Appendix C of [1].
     *
     * [1] Federal Information Processing Standards Publication 180-1.
     * Specifications for the Secure Hash Standard.  April 17, 1995.
     */
    public void testSampleMessageC() {
        System.out.println("testSampleMessageB");
        
        byte[] expectedDigest = new byte[] {
            (byte) 0x34, (byte) 0xAA, (byte) 0x97, (byte) 0x3C, 
            (byte) 0xD4, (byte) 0xC4, (byte) 0xDA, (byte) 0xA4, 
            (byte) 0xF6, (byte) 0x1E, (byte) 0xEB, (byte) 0x2B, 
            (byte) 0xDB, (byte) 0xAD, (byte) 0x27, (byte) 0x31, 
            (byte) 0x65, (byte) 0x34, (byte) 0x01, (byte) 0x6F
        };
        
        SHA md = new SHA();
        for (int i=0; i < 1000000; i++) {
            md.update((byte) 'a');
        }
        byte[] digest = md.digest();
        
        
        if (! Arrays.equals(digest, expectedDigest)) {
            fail("Illegal digest:"+dump(digest));
        }
    }
    
    private static String dump(byte[] a) {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i < a.length; i++) {
            if (i != 0 && i % 4 == 0) buf.append(' ');
            buf.append(Integer.toString(0xff & a[i], 16));
        }
        return buf.toString();
    }
    
    
}
