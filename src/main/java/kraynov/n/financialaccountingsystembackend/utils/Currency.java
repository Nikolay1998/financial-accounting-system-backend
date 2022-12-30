package kraynov.n.financialaccountingsystembackend.utils;

public enum Currency {
    DOLLAR_USA(1, "Доллар США"),
    RUBLE_RU(2, "Рубль"),
    ETHEREUM(3, "Эфир");

    private int id;
    private String name;

    Currency(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
