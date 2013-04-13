package roman;

/**
 * <P>
 * Roman Numeral Conversion class
 * 
 * Convert an integer to Roman Numeral or take a Roman Numeral
 * string and convert it to an integer.
 * 
 * Symbol   Value
 * I        1
 * V        5
 * X        10
 * L        50
 * C        100
 * D        500
 * M        1,000
 * 
 * For the rules of Roman Numerals Please visit Wikipedia.
 * <a href="http://en.wikipedia.org/wiki/Roman_numerals">Roman Numerals</a>
 * 
 * </P>
 * 
 * @author Jake Eraklidis
 */

public final class Roman 
{
    protected int rNumber;
    protected static int MIN = 1;
    protected static int MAX = 3999;
    
    /**
     * Integer Constructor 
     * Check Min Max Bounds
     * @param number Input Integer
     * @throws NumberFormatException if input integer is not within MIN MAX range
     */
    public Roman(int number)
    {
        if(number < MIN || number > MAX )
        {
            throw new NumberFormatException();
        }
        
        rNumber = number;
    }
    
    /**
     * String Constructor 
     * Bounds Check for valid Roman Numeral characters inside string.
     * ALLOWS FOR NON STANDARD ROMAN NUMERAL STRINGS
     * Note: If you want standard Wikipedia defined Strings add <i>isValidRomanString()</i>
     *       to the constructor to make sure the String is in standard form.
     * @param str User inputed Numeral String
     * @throws NumberFormatException Not a legal Roman Numeral Character
     */
    public Roman(String str ) 
    {
        // Account for user input being slightly off
        String romanStr = str.toUpperCase();
        
        /* String Bounds checking
           If a character in the string is not valid IllegalArgumentException
           is thrown and caught and rethrown as an NumberFormatException
        */
        try
        {
            for(Character c : romanStr.toCharArray())
            {
                RomanNums.valueOf(c.toString());
            }
        }
        catch(IllegalArgumentException ex)
        {
            throw new NumberFormatException();
        }
        
        rNumber = convertToInt(romanStr);
    }
    
    protected int checkOverflow(int a, int b)
    {
        long result = ((long) a) + b;
        
        if(result > Integer.MAX_VALUE)
        {
            throw new NumberFormatException()
        }
        
        return result;
    }
    
    /**
     * Helper function to convert string to integer
     * @param romanString String of Roman Numerals
     * @return Integer converted from string 
     */
    protected int convertToInt(String romanString)
    {
        int total = 0;
        StringBuilder sb = new StringBuilder(romanString);
        romanString = sb.reverse().toString();
        
        RomanNums previous = RomanNums.I;
        RomanNums current;
        
        for(Character c : romanString.toCharArray())
        {
            current = RomanNums.valueOf( c.toString() );
            if( current.getValue() < previous.getValue())
            {
                total = total - current.getValue();
            }
            else
            {
                total = total + current.getValue();
            }
            
            previous = current;
            
        }
        
        return total;
    }
    
    public boolean isValidRomanString(String str)
    {
       int num = convertToInt(str);
       StringBuilder sb = new StringBuilder();
       int i = num;
        
       for(RomanNums rn : RomanNums.values())
       {
           while(i - rn.getValue() >= 0)
           {
               i = i - rn.getValue();
               sb.append(rn);
           }
       }
        
       return str.equals(sb.toString());
    }
    
    /**
     * Output String representation
     * @return String representation of Roman Numeral 
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int i = rNumber;
        
        for(RomanNums rn : RomanNums.values())
        {
            while(i - rn.getValue() >= 0)
            {
                i = i - rn.getValue();
                sb.append(rn);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Output integer representation
     * @return 
     */
    protected int toInt()
    {
        return rNumber;
    }
    
    /**
     * Enumerated type representing Roman Numerals to encapsulate bounds
     * All Numerals have stored values which can be retrieved with getValue()
     */
    public enum RomanNums 
    {
        M(1000),
        CM(900),
        D(500),
        CD(400),
        C(100),
        XC(90),
        L(50),
        XL(40),
        X(10),
        IX(9),
        V(5),
        IV(4),
        I(1);
        
        RomanNums(int num)
        {
            value = num;
        }
                
        private final int value;
        
        public int getValue()
        {
            return value;
        }
    }
    
    /**
     * Simple test routine
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        try
        {
         Roman test1 = new Roman("MDCCCCLXXXXVIIII");
         Roman test2 = new Roman("MCMXCIX");
         Roman test3 = new Roman("MIM");
         Roman test4 = new Roman("MMIV");
         
         Roman test5 = new Roman(1244);
         Roman test6 = new Roman(1999);
         Roman test7 = new Roman(2043);
         Roman test8 = new Roman(3147);
         
         System.out.println(test1.toInt());
         System.out.println(test2.toInt());
         System.out.println(test3.toInt());
         System.out.println(test4.toInt());
         System.out.println();
         System.out.println(test5.toString());
         System.out.println(test6.toString());
         System.out.println(test7.toString());
         System.out.println(test8.toString());
        }
        catch(Exception ex)
        {
            System.out.println("An exception has occured: " + ex.toString());
        }
        
    }
}
