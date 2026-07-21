package product.get.getAll;

import java.util.List;

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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

/** 
 * Testes para avaliar o comportamento da API para valores inválidos do query param 'limit'
 * A API não realiza validação de tipo nesses casos
 * limit=0 - retorna status code 200 e array com 20 produtos 
 * limit=-1 - retorna status code 200 e array com 19 produtos
 * limit=abc - retorna status code 200 e array com 20 produtos
 * logo, os testes só capturam o comportamento observado já que não existe nada documentado
**/
@Feature("Produtos")
@Story("Listar Quantidade Limite de Produtos Especificado na Requisição")
public class GetProductsLimitEdgeCasesTest extends BaseTest{

    private final ProductClient productClient = new ProductClient();

    @Test(description = "[C05] - Comportamento atual: API não valida limit inválido", dataProvider = "invalidParamsLimit", 
    dataProviderClass = ProductDataProvider.class)
    @Tag("regression")
    @Severity(SeverityLevel.MINOR)
    void shouldReturnBadRequestWhenInvalidQueryParams(String limit, int expectedCount){

       Response response = productClient.getProductsWithLimit(limit);
       
       ApiAssertions.assertStatusCode(response, 200);

       List<Product> products = List.of(response.as(Product[].class));

       assertFalse(products.isEmpty());

       assertEquals(products.size(), expectedCount, "Quantidade retornada(" + products.size() + ") não corresponde ao esperado (" + expectedCount + ")");
    }
}