package dataprovider;

import java.util.Comparator;

import org.testng.annotations.DataProvider;

public class ProductDataProvider {

    @DataProvider(name = "validParamLimits")
    public static Object[][] validParamLimits(){
        return new Object[][]{
            {"1"},
            {"10"},
            {"19"}
        };
    }

    @DataProvider(name = "sortOrders")
    public static Object[][] sortOrders(){
        return new Object[][]{
            {"asc", Comparator.<Integer>naturalOrder(), "crescente"},
            {"desc", Comparator.<Integer>reverseOrder(), "decrescente"}
        };
    }

    @DataProvider(name = "invalidParamsLimit")
    public static Object[][] invalidParamsLimit(){
        return new Object[][]{
            {"0", 20},
            {"-1", 19},
            {"abc", 20}
        };
    }

    @DataProvider(name = "invalidId")
    public static Object [][] invalidId(){
        return new Object[][]{
            {"abc"},
            {"-1"},
            {"%2"}
        };
    }

    @DataProvider(name = "InvalidRangeId")
    public static Object [][] InvalidRangeId(){
        return new Object[][]{
            {"2147483648"},
            {"99999999999"},
            {"-2147483649"}
        };
    }   
}
