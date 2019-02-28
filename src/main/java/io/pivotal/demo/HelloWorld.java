package io.pivotal.demo;

import com.cavium.cfm2.LoginManager;
import com.cavium.provider.CaviumProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
public class HelloWorld {

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping(value = "/hsm")
    public String hsm() {

        String HSM_USER = "pivotal_user";
        String HSM_PASSWORD = "pivotal";
        String HSM_PARTITION = "hsm-4mmk3q3dp3a"; //

        loginWithExplicitCredentials(HSM_USER, HSM_PASSWORD, HSM_PARTITION);
        System.out.println("Connecting to " + true);
        return "Hello World";
    }

    /**
     * The explicit login method allows users to pass credentials to the Cluster manually. If you obtain credentials
     * from a provider during runtime, this method allows you to login.
     * @param user Name of CU user in HSM
     * @param pass Password for CU user.
     * @param partition HSM ID
     */
    public static void loginWithExplicitCredentials(String user, String pass, String partition) {

        try {
            Security.addProvider(new CaviumProvider());
            LoginManager lm = LoginManager.getInstance();
            lm.login(partition, user, pass);
            System.out.printf("\nLogin successful!\n\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
