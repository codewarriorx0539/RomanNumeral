package com.ffic;

import static java.lang.Thread.sleep;

/**
 *
 * <p>
 * Roman Numeral Conversion Class. All rights reserved. </br>
 *
 * <h3>Project:</h3> Roman Numeral Project
 *
 * <h3>Abstract:</h3>
 *
 * Roman Numeral Conversion - Convert an integer to a Roman Numeral string or convert a Roman Numeral</br>
 * string to an integer. The internal stored representation of is an integer called romanNumber.</br>
 * </br>
 * </p>
 * <p>
 * Roman Numeral Symbol = Integer Value
 * <ul>
 *  <li>I = 1</li>
 *  <li>V = 5</li>
 *  <li>X = 10</li>
 *  <li>L = 50</li>
 *  <li>C = 100</li>
 *  <li>D = 500</li>
 *  <li>M = 1,000</li>
 * </ul>
 * </br>
 * For the rules of Roman Numerals Please visit Wikipedia:
 * <a href="http://en.wikipedia.org/wiki/Roman_numerals">Roman Numerals</a>
 * </p>
 *
 * <p>
 * Tracebility:
 * <ul>
 *  <li>Mapping: <a href="mapping.html">Mapping</a></li>
 *  <li>Requirements: <a href="requirements.html">Reqs</a></li>
 *  <li>Non Functional Requirements: <a href="nfrs.html">NFRs</a></li>
 * </ul>
 * </p>
 *
 * @author Tech Lead: <a href="mailto:jake.eraklidis@gmail.com">Jacob Eraklidis</a>
 *
 * @author Contracting Tech Lead: <a href="mailto:jake.eraklidis@gmail.com">Jacob Eraklidis</a>
 *
 * @author Contracting Manager: <a href="mailto:jake.eraklidis@gmail.com">Jacob Eraklidis</a>
 *
 * @author Defect Manager: <a href="mailto:jake.eraklidis@gmail.com">Jacob Eraklidis</a>
 *
 */
public final class Roman
{
    // The numeric representation of the Roman Numeral
    private int romanNumber;

    // The acceptable range of values to convert
    private static int MIN = 1;
    private static int MAX = 3999;

    /**
     * An enumerated type representing Roman Numerals. All Numerals have stored values which
     * can be retrieved with getValue().
     *
     * <ul>
     *	 	<li>M  = 1000</li>
     *   	<li>CM = 900</li>
     *   	<li>D  = 500</li>
     *   	<li>CD = 400</li>
     *   	<li>C  = 100</li>
     *   	<li>XC = 90</li>
     *   	<li>L  = 50</li>
     *   	<li>XL = 40</li>
     *   	<li>X  = 10</li>
     *   	<li>IX = 9</li>
     *   	<li>V  = 5</li>
     *   	<li>IV = 4</li>
     *   	<li>I  = 1</li>
     * </ul>
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
     * Default Constructor
     * Do not allow access to the default constructor.
     */
    private Roman()
    {
    }

    /**
     * Integer Constructor
     * Check Min Max Bounds
     *
     * @param number Input integer to convert
     * @throws NumberFormatException if input integer is not within MIN MAX range
     */
    public Roman(int number)
    {
        if(number < MIN || number > MAX )
        {
            throw new NumberFormatException();
        }

        romanNumber = number;
    }

    /**
     * String Constructor
     * Bounds Check for valid Roman Numeral characters inside the string then convert to an Integer.
     * This Constructor allows for non standard roman numeral strings.
     * Note: If you want standard Wikipedia defined Strings add <i>isValidRomanString()</i>
     *       to the constructor to make sure the String is in standard form.
     *
     * @param str User inputed Roman Numeral String
     * @throws NumberFormatException Not a legal Roman Numeral Character
     */
    public Roman(String str)
    {
        // Account for user input being slightly off
        String romanStr = str.toUpperCase();

        /**
         *  String Bounds checking
         *  If a character in the string is not valid IllegalArgumentException
         *  is thrown and caught and rethrown as an NumberFormatException
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

        romanNumber = convertToInt(romanStr);
    }

    /**
     * This internal method will add two intergers together and detect if overflow will occur by
     * summing the values. If overflow occured this method will throw an exception.
     *
     * @param leftOperand The left operand
     * @param rightOperand The right operand
     * @throws NumberFormatException Overflow detected
     */
    private int checkOverflow(int leftOperand, int rightOperand)
    {
        long result = ((long) leftOperand) + rightOperand;

        if(result > Integer.MAX_VALUE)
        {
            throw new NumberFormatException();
        }

        return (int) result;
    }

    /**
     * Helper function to convert a string to an integer.
     *
     * @param romanString String of Roman Numerals
     * @return Integer converted from the Roman Numeral string
     */
    private int convertToInt(String romanString)
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

    /**
     * Validation of string data input by the user
     *
     * @param str String of Roman Numerals
     * @return Boolean value returns true if the string contains only valid Roman Numeral characters within the RomanNums enum.
     */
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
     * Returns the Roman Numeral String representation
     *
     * @return String representation of Roman Numeral
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int i = romanNumber;

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
     * Output integer Roman Numeral representation
     *
     * @return Integer representation
     */
    public int toInt()
    {
        return romanNumber;
    }

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

            test1.toInt();
            test2.toInt();
            test3.toInt();
            test4.toInt();

            test5.toString();
            test6.toString();
            test7.toString();
            test8.toString();
        
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

