package by.vbalanse.facade.utils;

import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * @author mikolasusla@gmail.com
 */
public class StringUtils {

    private static Logger logger = Logger.getLogger(StringUtils.class);

    public static String md5(String source) {
        String md5String = source;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format
            StringBuilder sb = new StringBuilder();
            for (byte data : byteData) {
                sb.append(Integer.toString((data & 0xff) + 0x100, 16).substring(1));
            }
            md5String = sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return md5String;
    }
}
