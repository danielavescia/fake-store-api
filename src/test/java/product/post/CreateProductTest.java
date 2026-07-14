package product.post;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.db.client.product.ProductClient;
import com.db.model.Product;

import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

@Feature("Produtos")
@Story("Criar Produto Válido")
public class CreateProductTest extends BaseTest {

    private static final ProductClient productClient = new ProductClient();

    private Response response;

    private Product bodyRequest = Product.builder()
                    .title("Mens Casual Premium Slim Fit Jeans")
                    .price(Float.valueOf("99.99")) 
                    .description("Jeans 100% cotton")
                    .category("men's clothing")
                    .image("https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_t.png")
                    .build();

    private Product actualProduct;

    @BeforeClass
    public void createProduct(){
        response = productClient.createProduct(bodyRequest);
        actualProduct = response.as(Product.class);
    }

    @Test(description = "[C12] - Deve retornar produto criado com sucesso")
    @Tag("C12")
    @Severity(SeverityLevel.BLOCKER)
    void shouldCreateProductSuccessfully(){
        assertEquals(response.getStatusCode(), 201, "Erro: satatus code retornado: " + response.getStatusCode() + " e o esperado era 201.");
    }

    @Test(description = "[C12] - POST /products deve retornar Content-Type application/json")
    @Tag("C12")
    @Severity(SeverityLevel.MINOR)
    void shouldReturnJsonContentType(){
        assertTrue(response.getContentType().contains("application/json; charset=utf-8"),
         "Erro: Content-Type retornado: " + response.getContentType() + "e o esperado era application/json; charset=utf-8");
    }

    @Test(description = "[C12] - POST /products deve retornar produto criado")
    @Tag("C13")
    @Severity(SeverityLevel.CRITICAL)
    public void shoudlReturnExpectedProduct(){
        assertNotNull(actualProduct);
        assertThat(actualProduct, hasProperty("id"));
        assertEquals(actualProduct.getCategory(), bodyRequest.getCategory());
        assertEquals(actualProduct.getDescription(), bodyRequest.getDescription());
        assertEquals(actualProduct.getTitle(), bodyRequest.getTitle());
        assertEquals(actualProduct.getImage(), bodyRequest.getImage());
    }

    @Test(description = "[C13] - POST /products deve retornar response conforme schema")
    public void shouldReturnProductWithValidSchema(){
        assertThat(response.asString(), matchesJsonSchemaInClasspath("schemas/product-without-rating-schema.json"));
    }
}
