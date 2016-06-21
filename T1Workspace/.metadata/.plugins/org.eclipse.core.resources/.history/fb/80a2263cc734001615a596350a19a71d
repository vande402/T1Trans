package com.transcore.connexion.sample;

import java.rmi.RemoteException;

import cltool4j.GlobalConfigProperties;

/**
 * Demonstrates a simple user login.
 * 
 * For clarity, this sample code does minimal error handling. When developing a production application, we
 * strongly recommend implementing full error handling as demonstrated in {@link ErrorHandling}.
 * 
 */
public class Login extends BaseSampleClient {

    @Override
    public void run() throws RemoteException {
        final String loginId = GlobalConfigProperties.singleton().getProperty("loginId1");
        final String password = GlobalConfigProperties.singleton().getProperty("password1");

        System.out.println("Logging in with loginId " + loginId);
        login(loginId, password);
        System.out.println("Login Successful");
    }

    public static void main(final String[] args) {
        run(args);
    }
}
