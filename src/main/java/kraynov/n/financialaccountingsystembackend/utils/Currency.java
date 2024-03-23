package kraynov.n.financialaccountingsystembackend.utils;

public enum Currency {
    DOLLAR_USA(1, "Доллар США", "USD", "$"),
    RUBLE_RU(2, "Рубль", "RUB", "₽"),
    ETHEREUM(3, "Эфир", "ETH", "Ξ");

    private final int id;
    private final String name;
    private final String shortName;
    private final String symbol;

    Currency(int id, String name, String shortName, String symbol) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Currency define(int id) {
        for (Currency currency : values()) {
            if (currency.getId() == id) {
                return currency;
            }
        }
        throw new IllegalStateException("Cannot find currency with id = " + id);
    }
}
