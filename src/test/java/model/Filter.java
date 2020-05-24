package model;

import java.util.List;

public class Filter {
    private String filterPrice;
    private List<String> priceValues;
    private String filterStock;
    private List<String> stockValues;
    private List<String> stockCaptions;
    private String filterBrand;
    private List<String> brandValues;
    private String filterMemory;
    private List<String> memoryValues;
    private List<String> memoryCaptions;

    public static Builder newEntity() {
        return new Filter().new Builder();
    }

    public String getFilterPrice() {
        return filterPrice;
    }

    public List<String> getPriceValues() {
        return priceValues;
    }

    public String getFilterStock() {
        return filterStock;
    }

    public List<String> getStockValues() {
        return stockValues;
    }

    public List<String> getStockCaptions() {
        return stockCaptions;
    }

    public String getFilterBrand() {
        return filterBrand;
    }

    public List<String> getBrandValues() {
        return brandValues;
    }

    public String getFilterMemory() {
        return filterMemory;
    }

    public List<String> getMemoryValues() {
        return memoryValues;
    }

    public List<String> getMemoryCaptions() {
        return memoryCaptions;
    }

    public class Builder {
        private Builder() {
        }

        public Builder withFilterPrice(String filterPrice) {Filter.this.filterPrice = filterPrice; return this;}
        public Builder withPriceValues(List<String> priceValues) {Filter.this.priceValues = priceValues;return this;}
        public Builder withFilterStock(String filterStock) { Filter.this.filterStock = filterStock; return this; }
        public Builder withStockValues(List<String> stockValues) { Filter.this.stockValues = stockValues; return this; }
        public Builder withStockCaptions(List<String> stockCaptions) { Filter.this.stockCaptions = stockCaptions; return this; }
        public Builder withFilterBrand(String filterBrand) { Filter.this.filterBrand = filterBrand; return this; }
        public Builder withBrandValues(List<String> brandValues) { Filter.this.brandValues = brandValues; return this; }
        public Builder withFilterMemory(String filterMemory) { Filter.this.filterMemory = filterMemory; return this; }
        public Builder withMemoryValues(List<String> memoryValues) { Filter.this.memoryValues = memoryValues; return this; }
        public Builder withMemoryCaptions(List<String> memoryCaptions) { Filter.this.memoryCaptions = memoryCaptions; return this; }
        public Filter build() {return Filter.this; }
    }
}

