package util;

/**
 * Created by icepoint on 8/29/15.
 */
public class TextUtil
{
    public static  boolean isEmpty(String str)
    {
        if(str == null || str.trim().length() == 0)
        {
            return true ;
        }
        return false ;
    }
}