/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package vuhid_tools.server;
import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Long Phan <long2@pdx.edu>
 * This class calculate the SHA1 hash.
 */
public class SHA1Calculator
{
	/**
	 * Convert from byte array to hex.
	 * 
	 * @param data		The (byte array) input.
	 * @return			Returns the hex string.
	 */
	private static String convertToHex(byte[] data)
	{
		StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++)
        {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                {
                    buf.append((char) ('0' + halfbyte));
                }
                else
                {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
	
	/**
	 * Generate a SHA1 hash from a text string.
	 * 
	 * @param text		The (String) input text.
	 * @return			Returns the SHA1 hash string.
	 */
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
	    return convertToHex(SHA1Bin(text));
    }
    
	/**
	 * Generate a SHA1 binary from a text string.
	 * 
	 * @param text		The (String) input text.
	 * @return			Returns the SHA1 hash binary.
	 */
    public static byte[] SHA1Bin(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return sha1hash;
    }
    
	/**
	 * Get the current time stamp as a string.
	 * 
	 * @return		Returns the current time stamp string.
	 */
    public static String getCurrentTimeStamp()
    {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Date now = new Date(); 
        String strDate = sdfDate.format(now); 
        return strDate; 
    }
}