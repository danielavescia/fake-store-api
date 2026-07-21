package dataprovider;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {

    @DataProvider(name = "invalidPayloads")
    public static Object[][] invalidPayloads(){
        return new Object[][]{
            { "Corpo JSON vazio" , "{}"},
            { "Sem o campo password", "{\"username\":\"johnd\"}"},
            { "Sem o campo username", "{\"password\":\"m38rmF$\"}"},
            { "Username vazio" ,"{\"username\":\"\",\"password\":\"m38rmF$\"}"},
            { "Password vazio","{\"username\":\"johnd\",\"password\":\"\"}"},
            { "Username nulo", "{\"username\":null,\"password\":\"m38rmF$\"}"},
            { "Password nulo","{\"username\":\"johnd\",\"password\":null}"},
            { "Password como array", "{\"username\":\"johnd\",\"password\":[\"m38rmF$\"]}"},
            { "Corpo completamente vazio" , ""},
            { "Corpo literal null", "null"},
            { "Corpo como array JSON" , "[]"},
            { "JSON malformado (vírgula sobrando)", "{\"username\":\"johnd\",\"password\":\"m38rmF$\",}"},
        };
    }

    @DataProvider(name = "invalidCredentials")
    public static Object[][] invalidCredentials(){
        return new Object[][]{
             {"Password incorreto","{\"username\":\"johnd\",\"password\": 123}"},
             {"Username incorreto","{\"username\":\"john\",\"password\":\"m38rmF$\"}"}
        };
    }
}