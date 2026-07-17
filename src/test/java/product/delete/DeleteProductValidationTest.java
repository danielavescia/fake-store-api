package product.delete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Deve validar payloads")
public class DeleteProductValidationTest extends BaseTest{

    private static final ProductClient productClient = new ProductClient();

    private Response response;

    @Test(description ="[C39] - Deve retornar erro para Id's que não são Integers", dataProvider = "invalidIdType", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    @Severity(SeverityLevel.NORMAL)
    void shouldReturnErrorForInvalidId(String Scenario, Object invalidId){
        response = productClient.deleteProduct(invalidId.toString());
        ApiAssertions.assertStatusCode(response, 400);

        assertThat(response.jsonPath().getString("status"),
           is("error"));

        assertThat(response.jsonPath().getString("message"),
           equalTo("product id should be provided"));
    }

    @Test(description ="[C33] - Deve retornar erro para requisição sem Id no path param")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    void shouldReturnErrorForNoId(){
        response = productClient.deleteProduct("");

        ApiAssertions.assertStatusCode(response, 404);

        assertThat(response.getContentType(), is("text/html; charset=utf-8"));
    }
}