package kraynov.n.financialaccountingsystembackend.model;

public class CurrencyDTO {
    private final String id;
    private final String shortName;
    private final String fullName;
    private final String symbol;
    private final short isoCode;

    public CurrencyDTO(Builder builder) {
        this.id = builder.getId();
        this.shortName = builder.getShortName();
        this.fullName = builder.getFullName();
        this.symbol = builder.getSymbol();
        this.isoCode = builder.getIsoCode();
    }

    public String getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSymbol() {
        return symbol;
    }

    public short getIsoCode() {
        return isoCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String shortName;
        private String fullName;
        private String symbol;
        private short isoCode;

        public String getId() {
            return id;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public String getShortName() {
            return shortName;
        }

        public Builder setShortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public String getFullName() {
            return fullName;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public String getSymbol() {
            return symbol;
        }

        public Builder setSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public short getIsoCode() {
            return isoCode;
        }

        public Builder setIsoCode(short isoCode) {
            this.isoCode = isoCode;
            return this;
        }

        public CurrencyDTO build() {
            return new CurrencyDTO(this);
        }

    }
}
