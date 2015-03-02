/* (c) Adriacom Software d.o.o.
 * 22211 Vodice, Croatia
 * Created by Z.Rosko (zrosko@yahoo.com)
 * Date 2007.05.23 
 * Time: 15:33:47
 */
package hr.as2.inf.common.format;


public class AS2CheckDigit {

    public int checkDigitModu1011(String value) {
        // allowable characters within identifier
        String validChars = "0123456789";

        // remove leading or trailing whitespace, convert to uppercase
        value = value.trim().toUpperCase();

        // this will be a running total
        int sum = 0;
        // loop through digits from right to left
        for (int i = 0; i < value.length(); i++) {

          //set ch to "current" character to be processed
          char ch = value.charAt(value.length() - i - 1);

          // throw exception for invalid characters
          if (validChars.indexOf(ch) == -1)
              return 0;

          // our "digit" is calculated using ASCII value - 48
          int digit = (int)ch - 48;

          // weight will be the current digit's contribution to
          // the running total
          int weight;
          if (i % 2 == 0) {

            // for alternating digits starting with the rightmost, we
            // use our formula this is the same as multiplying x 2 and
            // adding digits together for values 0 to 9.  Using the 
            // following formula allows us to gracefully calculate a
            // weight for non-numeric "digits" as well (from their 
            // ASCII value - 48).
            weight = (2 * digit) - (int) (digit / 5) * 9;

          } else {

            // even-positioned digits just contribute their ascii
            // value minus 48
            weight = digit;

          }

          // keep a running total of weights
          sum += weight;

        }

        // avoid sum less than 10 (if characters below "0" allowed,
        // this could happen)
        sum = Math.abs(sum) + 10;

        // check digit is amount needed to reach next number
        // divisible by ten
        return (10 - (sum % 10)) % 10;
        /*
        char ch = value.charAt(0);
        int pos_1 = Integer.valueOf(String.valueOf(ch)).intValue();
        pos_1 = pos_1 + 10;
        Double pos_11 = new Double(pos_1/10); 
        //pos_11.*/
        //return 0;
    }
    public int checkDigitModu10(String idWithoutCheckdigit) {

        // allowable characters within identifier
        String validChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZ_";

        // remove leading or trailing whitespace, convert to uppercase
        idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();

        // this will be a running total
        int sum = 0;

        // loop through digits from right to left
        for (int i = 0; i < idWithoutCheckdigit.length(); i++) {

          //set ch to "current" character to be processed
          char ch = idWithoutCheckdigit
                    .charAt(idWithoutCheckdigit.length() - i - 1);

          // throw exception for invalid characters
          if (validChars.indexOf(ch) == -1)
              return 0;//TODO
            //throw new Exception("\"" + ch + "\" is an invalid character");

          // our "digit" is calculated using ASCII value - 48
          int digit = (int)ch - 48;

          // weight will be the current digit's contribution to
          // the running total
          int weight;
          if (i % 2 == 0) {

            // for alternating digits starting with the rightmost, we
            // use our formula this is the same as multiplying x 2 and
            // adding digits together for values 0 to 9.  Using the 
            // following formula allows us to gracefully calculate a
            // weight for non-numeric "digits" as well (from their 
            // ASCII value - 48).
            weight = (2 * digit) - (int) (digit / 5) * 9;

          } else {

            // even-positioned digits just contribute their ascii
            // value minus 48
            weight = digit;

          }

          // keep a running total of weights
          sum += weight;

        }

        // avoid sum less than 10 (if characters below "0" allowed,
        // this could happen)
        sum = Math.abs(sum) + 10;

        // check digit is amount needed to reach next number
        // divisible by ten
        return (10 - (sum % 10)) % 10;

      }
}
