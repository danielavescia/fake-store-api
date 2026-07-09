package dataprovider;

import org.testng.annotations.DataProvider;

public class ProductDataProvider {

    @DataProvider(name = "validParamLimits" )
    public static Object[][] validParamLimits(){
        return new Object[][]{
            {1},
            {10},
            {19}
        };
    }

}
