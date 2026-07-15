package product.post;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("")
public class CreateProductBoundaryAndSecurityTest extends BaseTest{

    private ProductClient productClient = new ProductClient();
    
    private Response response;

    @Test(description = "Deve retornar 400 e HTML de erro para JSON mal formado", dataProvider = "invalidJsonPayloads", dataProviderClass = ProductDataProvider.class)
    void shouldReturnBadRequestForInvalidJson(String scenario, String requestBody){
        response = productClient.createProductRawBody(requestBody);
        
        assertEquals(response.getStatusCode(),400, "Erro no cenário: " + scenario + "status code retornado: " + response.getStatusCode() + ", mas o esperado era 400.");
        assertTrue(response.getContentType().contains("text/html;"), "Erro cenário: " + scenario + "content-type deveria ser HTML, mas foi retornado: " + response.getContentType() );
    }

    @Test(description = "Deve rejeitar payload excessivamente grande com status code 413 ", dataProvider = "oversizedPayloads", dataProviderClass = ProductDataProvider.class)
    void shouldReturnPayloadTooLarge(String scenario, Product requestBody){
        response = productClient.createProduct(requestBody);

        assertEquals(response.getStatusCode(), 413,
        "Erro no cenário '" + scenario + "': API retornou erro de servidor (" + response.getStatusCode() + ") - possível processamento inseguro do payload.");

    }
}
