package product;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import static org.testng.Assert.assertEquals;

@Feature("Produtos")
@Story("Listar todos os Produtos do catálago")
public class GetAllProductsTest extends BaseTest{
    
    private final ProductClient productClient = new ProductClient();

    private Response response;

    private List<Product> products;

    @BeforeClass(alwaysRun = true)
    @Step("Busca todos os produtos")
    public void fetchAllProducts(){
        response = productClient.getAllProducts();
        products = List.of(response.as(Product[].class));
    }

    @Test(description = "GET products deve retornar status code 200")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldReturnStatusCode200(){
       assertEquals(response.getStatusCode(),200, "Erro: Status code retornado foi: " + response.getStatusCode() + " e o esperado era 200.");
    }
}