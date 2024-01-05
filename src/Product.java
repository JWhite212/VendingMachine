/**
 * Enum product containing a range of products and their associated price
 * and code.
 */
enum Product implements Item {

    COKE("0001",2.00),
    SPRITE("0002",2.00),
    WATER("0003",1.50),
    LEMONADE("0004",1.75),
    CRISPS("1001",1.75),
    PEANUTS("1002",1.75),
    CHOCOLATE("1003",1.75),
    CANDY("1004",1.75);

    private final String code;
    private final double price;

    Product(String code, Double price){
        this.code = code;
        this.price = price;
    }

    /**
     * @return The code of the product.
     */
    public String getCode(){
        return code;
    }

    /**
     * @return The product in a string format in lower case, and the _ replaced with a space.
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
     * @param code The code of the product.
     * @return The price of the specified product based on the code.
     */
    public static double getPrice(String code) {
        switch (code) {
            case "0001":
            case "1004":
            case "0002":
                return 2.00;
            case "0003":
            case "1002":
            case "1001":
                return 1.50;
            case "0004":
                return 1.75;
            case "1003":
                return 2.50;
            default:
                return 0;
        }
    }

    /**
     * @param code The code of the product.
     * @return The product associated with the inputted code.
     */
    public static Product getProducts(String code) {
        switch(code) {
            case "0001":
                return COKE;
            case "0002":
                return SPRITE;
            case "0003":
                return WATER;
            case "0004":
                return LEMONADE;
            case "1001":
                return CRISPS;
            case "1002":
                return PEANUTS;
            case "1003":
                return CHOCOLATE;
            case "1004":
                return CANDY;
            default:
                return null;
        }
    }
}

