/**
 * Enum class Coin containing a range of coins and their associated money value.
 */
enum Coin implements Item {

        TWO_POUND(2),
        ONE_POUND(1),
        FIFTY_PENCE(0.5),
        TWENTY_PENCE(0.2),
        TEN_PENCE(0.1),
        FIVE_PENCE(0.05),
        TWO_PENCE(0.02),
        ONE_PENCE(0.01);
        private final double moneyValue;

        Coin(double moneyValue){
                this.moneyValue = moneyValue;
        }

        /**
         * @return The coin in a string format in lower case, with the _ replaced with a space.
         */
        @Override
        public String toString() {
                String string = super.toString();
                string = string.toLowerCase();
                string = string.replace('_', ' ');
                String outputString = string.substring(0, 1).toUpperCase() + string.substring(1);
                return outputString;
        }

        /**
         * @return The money value of the product.
         */
        public double getMoneyValue(){
        return moneyValue;
        }

}

