package auth;

import org.testng.annotations.Test;

import com.db.client.auth.AuthClient;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.LoginDataProvider;
import io.restassured.response.Response;

public class LoginInvalidCredentials extends BaseTest{
    AuthClient authClient = new AuthClient();

    @Test(description = "Deve rejeitar payloads malforamados sem autenticar",
        dataProvider = "invalidCredentials", dataProviderClass= LoginDataProvider.class)
    void shouldReturnUnauthorized(String scenario, String payload){
        
        Response response = authClient.login(payload);
        ApiAssertions.assertStatusCode(response, 401, scenario);
    }
}
