import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Interface to provide the method round to the program.
 */
public interface Rounding {

     /**
      * This method is used to round the inputted double to two decimal places.
      *
      * @param x The value to be rounded.
      * @return Returns the inputted value rounded to two decimal places.
      */
     static double round(double x) {
          int places = 2;
          BigDecimal bd = BigDecimal.valueOf(x);
          bd = bd.setScale(places, RoundingMode.HALF_UP);
          return bd.doubleValue();
     }
}
