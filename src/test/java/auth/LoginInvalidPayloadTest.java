package auth;

import org.testng.annotations.Test;

import com.db.client.auth.AuthClient;

import assertions.ApiAssertions;
import base.BaseTest;
import dataprovider.LoginDataProvider;
import io.restassured.response.Response;

public class LoginInvalidPayloadTest extends BaseTest{

    AuthClient authClient = new AuthClient();

    @Test(description = "Deve rejeitar payloads malforamados sem autenticar",
        dataProvider = "invalidPayloads", dataProviderClass= LoginDataProvider.class)
    void shouldRejectInvalidPayload(String scenario, String payload){
        
        Response response = authClient.login(payload);
        ApiAssertions.assertStatusCode(response, 400, scenario);
    }
}