package product.post;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Criar Produtos")
public class CreateProductPartialOrInvalidPayloadTest extends BaseTest{
    
    private final ProductClient productClient = new ProductClient();

    private Response response;

    @Test(description = "Deve criar produto com payload parcial ou vazio",  dataProvider = "invalidOrPartialPayloads", dataProviderClass = ProductDataProvider.class)
    public void shouldCreateProductWithPartialPayload(String scenario, Product body){
        
        response = productClient.createProduct(body);

        Product actualProduct = response.as(Product.class);

        assertEquals(response.getStatusCode(), 201, "Erro: satatus code retornado: " + response.getStatusCode() + " e o esperado era 201.");
        assertNotNull(actualProduct.getId(), "Erro: id é nulo para o cenário: " + scenario);
    }
}
