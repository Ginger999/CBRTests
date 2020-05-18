import com.tngtech.java.junit.dataprovider.DataProvider;
import java.util.Arrays;
import java.util.Collections;

public class DataProviders {
    @DataProvider
    public static Object[][] test01() {
        return new Object[][] { { Filter.newEntity().withFilterPrice("Цена").withPriceValues(Collections.singletonList("10001"))
                .withFilterBrand("Производитель").withBrandValues(Collections.singletonList("xiaomi"))
                .withFilterMemory("Объем встроенной памяти").withMemoryValues(Arrays.asList("32tl", "32tg"))
                .withMemoryCaptions(Arrays.asList("64 ГБ", "128 ГБ")).build() },

        };
    }
    @DataProvider
    public static Object[][] test02() {
        return new Object[][] {
                { Filter.newEntity()
                        .withFilterPrice("Цена")
                        .withPriceValues(Collections.singletonList("10001"))
                        .build() },

        };
    }

    @DataProvider
    public static Object[][] test03() {
        return new Object[][] {
                { Filter.newEntity()
                        .withFilterPrice("Цена")
                        .withPriceValues(Collections.singletonList("40001"))
                        .withFilterBrand("Производитель")
                        .withBrandValues(Collections.singletonList("apple"))
                        .build() },
        };
    }

}
