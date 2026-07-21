package auth;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.db.client.auth.AuthClient;    
import com.db.model.User;

import assertions.ApiAssertions;
import base.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.Tag;
import io.restassured.response.Response;

/* Dados do usuário estão hardcode porque está presente na documentação da API */

@Feature("Usuários")
@Story("Login de Usuário Válido")
public class LoginTest extends BaseTest{

    private static final AuthClient authClient = new AuthClient();

    private Response response;

    private User user = new User("johnd", "m38rmF$");

    @BeforeClass
    public void createProduct(){
        response = authClient.login(user);
    }

    @Test(description = "Deve autenticar com sucesso e retornar status 201")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldAuthenticateSuccessfullyAndReturnCreated(){
        ApiAssertions.assertStatusCode(response, 201);
    }

    @Test(description = "Deve autenticar com sucesso e retornar JWT")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("smoke")
    void shouldAuthenticateSuccessfullyAndReturnJwt(){
        assertThat(response.jsonPath().getString("token"), is(notNullValue()));
    }
}