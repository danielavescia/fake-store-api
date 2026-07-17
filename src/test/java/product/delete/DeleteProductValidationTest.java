package product.delete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.db.client.product.ProductClient;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Deve validar payloads")
public class DeleteProductValidationTest extends BaseTest{

    private static final ProductClient productClient = new ProductClient();

    private Response response;

    @Test(description ="Deve retornar erro para Id's que não são Integers", dataProvider = "invalidIdType", dataProviderClass = ProductDataProvider.class)
    void shouldReturnErrorForInvalidId(String Scenario, Object invalidId){
        response = productClient.deleteProduct(invalidId.toString());
        ApiAssertions.assertStatusCode(response, 400);

        assertThat(response.jsonPath().getString("status"),
           is("error"));

        assertThat(response.jsonPath().getString("message"),
           equalTo("product id should be provided"));
    }
}