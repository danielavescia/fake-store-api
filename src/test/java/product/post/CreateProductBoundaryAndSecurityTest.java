package product.post;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

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
@Story("Criar Produto - Validações de Limites e Segurança")
public class CreateProductBoundaryAndSecurityTest extends BaseTest{

    private ProductClient productClient = new ProductClient();
    
    private Response response;

    @Test(description = "[C15] Deve retornar 400 e HTML de erro para JSON mal formado", dataProvider = "invalidJsonPayloads", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    void shouldReturnBadRequestForInvalidJson(String scenario, String requestBody){
        response = productClient.createProductRawBody(requestBody);
        
        ApiAssertions.assertStatusCode(response, 400, scenario);
        assertTrue(response.getContentType().contains("text/html;"), "Erro cenário: " + scenario + "content-type deveria ser HTML, mas foi retornado: " + response.getContentType() );
    }

    @Test(description = "[C18] Deve rejeitar payload excessivamente grande com status code 413 ", dataProvider = "oversizedPayloads", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    @Tag("security")
    void shouldReturnPayloadTooLarge(String scenario, Product requestBody){
        response = productClient.createProduct(requestBody);

        ApiAssertions.assertStatusCode(response, 413, scenario);
    }
}