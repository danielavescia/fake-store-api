package product.put;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Atualizar Produto - Payload Parcial ou Incompleto")
public class UpdateProductPartialOrInvalidPayloadTest extends BaseTest{

    private final ProductClient productClient = new ProductClient();
    
    private Response response;

    private SoftAssert softAssert = new SoftAssert();

    @Test(description = "[C21] Deve atualizar produto com payload parcial ou vazio",  dataProvider = "invalidOrPartialPayloads", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    public void shouldUpdateProductWithPartialPayload(String scenario, Product body){
        
        response = productClient.updateProduct(body, "1");

        Product actualProduct = response.as(Product.class);
        
        ApiAssertions.assertStatusCode(response, 200, scenario);
        ApiAssertions.softAssertNotNull(softAssert, "id", actualProduct.getId());
        softAssert.assertAll();
    }
}