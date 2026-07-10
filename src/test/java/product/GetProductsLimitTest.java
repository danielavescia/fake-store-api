package product;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.List;

import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import dataprovider.ProductDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

/* Classe que testa o parâmetro limite com entradas válidas */
@Feature("Produtos")
@Story("Listar Quantidade Limite de Produtos Especificado na Requisição")
public class GetProductsLimitTest extends BaseTest {

    private final ProductClient productClient = new ProductClient();

    @Test(description = "[C05] - Deve retornar quantidade de produtos conforme o limite especificado", dataProvider = "validParamLimits", 
    dataProviderClass = ProductDataProvider.class)
    @Tag("C05")
    @Severity(SeverityLevel.MINOR)
    void shouldReturnProductsQuantityAccordingToLimit(String limit){

       Response response = productClient.getProductsWithLimit(limit);
       
       assertEquals(response.getStatusCode(), 200);

       List<Product> products = List.of(response.as(Product[].class));

       int expectedLimit = Integer.parseInt(limit);

       assertFalse(products.isEmpty());

       assertEquals(products.size(), expectedLimit, "Quantidade retornada(" + products.size() + ") não corresponde ao limite enviado (" + expectedLimit + ")");
    }
}