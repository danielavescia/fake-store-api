package product.put;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

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
@Story("Atualizar Produto - Validações de Limites e Segurança")
public class UpdateProductBoundaryAndSecurityTest extends BaseTest{

    private ProductClient productClient = new ProductClient();
    
    private Response response;


    @Test(description = "[C23] Deve retornar 400 e HTML de erro para JSON mal formado", dataProvider = "invalidJsonPayloads", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    @Severity(SeverityLevel.NORMAL)
    void shouldReturnBadRequestForInvalidJson(String scenario, String requestBody){
        response = productClient.updateProductRawBody(requestBody, "1");
        
        ApiAssertions.assertStatusCode(response, 400, scenario);
        assertTrue(response.getContentType().contains("text/html;"), "Erro cenário: " + scenario + "content-type deveria ser HTML, mas foi retornado: " + response.getContentType() );
    }

    @Test(description = "[C27] Deve rejeitar payload excessivamente grande com status code 413 ", dataProvider = "oversizedPayloads", dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    @Tag("security")
    @Severity(SeverityLevel.CRITICAL)
    void shouldReturnPayloadTooLarge(String scenario, Product requestBody){
        response = productClient.updateProduct(requestBody, "1");

        ApiAssertions.assertStatusCode(response, 413, scenario);
    }
}