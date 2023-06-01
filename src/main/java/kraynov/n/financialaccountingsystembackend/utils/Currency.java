package kraynov.n.financialaccountingsystembackend.utils;

public enum Currency {
    DOLLAR_USA(1, "Доллар США", "USD"),
    RUBLE_RU(2, "Рубль", "RUB"),
    ETHEREUM(3, "Эфир", "ETH");

    private final int id;
    private final String name;
    private final String shortName;

    Currency(int id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }
}
