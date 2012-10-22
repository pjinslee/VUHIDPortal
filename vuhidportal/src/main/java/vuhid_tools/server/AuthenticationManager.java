package vuhid_tools.server;

import javax.net.ssl.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Properties;


/**
 * Author: Robert Hickey
 * Date: 8/11/12
 * @version 1.1
 * Comments:    Ver 1.0 First version from Peter's work
 *                  1.1 Added static local Configuration var and set it to passed in Configuration config of constructor
 *                      Took out hard coded "SunX509" and called getter routine of Configuration
 *                      Took out hard coded "TLS" and called getter routine of Configuration
 *                      Took out hard coded "JKS" and called getter routine of Configuration
 *
 */
public class AuthenticationManager {
    //private static final boolean debug = true; //turns on printKeyStoreInfo routine below
    private static final boolean debug = false;

    private static KeyManager[] keyManagers;

    private static TrustManager[] trustManagers;
    
    AuthenticationManager() {}
    AuthenticationManager(String a) {
        try {
            //create key and trust managers
            createKeyManagers(Config.keyStoreFileName, Config.keyStorePassword, Config.alias);
            createTrustManagers(Config.trustStoreFileName, Config.trustStorePassword);

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            // create ssl context
            //SSLContext context = SSLContext.getInstance("TLS"); //RH: chg'd to below
            SSLContext context = SSLContext.getInstance(Config.securityProtocol);

            //init context with managers data
            context.init(keyManagers, trustManagers, null);
            SSLSocketFactory socketFactory = context.getSocketFactory();
            return socketFactory;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (KeyManagementException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createKeyManagers(String keyStoreFileName, String keyStorePassword, String alias)
            throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {

        //create Inputstream to keystore file
        java.io.InputStream inputStream = null;
        try{//attempt to load the key store as a regular file
            inputStream = new java.io.FileInputStream(keyStoreFileName);
        }
        catch (FileNotFoundException e){
            //keystore file not found, attempt to load a resource within the package
            System.out.println("Unable to load key store as file, attempting to load as resource");
            try{
                inputStream = AuthenticationManager.class.getClassLoader().getResourceAsStream(keyStoreFileName);
                if (inputStream == null) throw new Exception("no resource found");
            }
            catch (Exception er){
                System.out.println("Unable to load key store resource " + keyStoreFileName);
                System.exit(1);
            }
        }

        //create keystore object, load it with keystorefile data
        //KeyStore keyStore = KeyStore.getInstance("JKS"); // RH: chg'd to below
        KeyStore keyStore = KeyStore.getInstance(Config.trustStoreFormat);
        keyStore.load(inputStream, keyStorePassword == null ? null : keyStorePassword.toCharArray());

        //DEBUG information should be removed
        if (debug) {
            printKeystoreInfo(keyStore);
        }
        if (alias != null) {
            keyManagers = new KeyManager[] {new AuthenticationManager().new AliasKeyManager(keyStore, alias, keyStorePassword)};
        } else {
            //create keymanager factory and load the keystore object in it
            /* KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()); */
            //KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509"); // I changed this; RH: changed this to below
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(Config.trustStoreCPVAlgorithm); // I tried to change this; Wouldn't let me
            keyManagerFactory.init(keyStore, keyStorePassword == null ? null : keyStorePassword.toCharArray());
            keyManagers = keyManagerFactory.getKeyManagers();
        }
    }

    private static void createTrustManagers(String trustStoreFileName, String trustStorePassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        
        //create Inputstream to truststore file
        java.io.InputStream inputStream = null;
        try{//attempt to load the key store as a regular file
            inputStream = new java.io.FileInputStream(trustStoreFileName);
        }
        catch (FileNotFoundException e){
             System.out.println("Unable to load trust store as file, attempting to load as resource");
            //keystore file not found, attempt to load a resource within the package
            try{
                inputStream = AuthenticationManager.class.getClassLoader().getResourceAsStream(trustStoreFileName);
                if (inputStream == null) throw new Exception("no resource found");
            }
            catch (Exception er){
                System.out.println("Unable to load trust store file " + trustStoreFileName);
                System.exit(1);
            }
        }

        //create keystore object, load it with truststorefile data
        //KeyStore trustStore = KeyStore.getInstance("JKS"); // RH: chg'd to below
        KeyStore trustStore = KeyStore.getInstance(Config.trustStoreFormat);
        trustStore.load(inputStream, trustStorePassword == null ? null : trustStorePassword.toCharArray());
        //DEBUG information should be removed
        if (debug) {
            printKeystoreInfo(trustStore);
        }
        //create trustmanager factory and load the keystore object in it
        /* TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()); */
        //TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509"); RH: chg'd to below
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(Config.trustStoreCPVAlgorithm);
        trustManagerFactory.init(trustStore);
        trustManagers = trustManagerFactory.getTrustManagers();
    }

    private static void printKeystoreInfo(KeyStore keystore) throws KeyStoreException {
        System.out.println("Provider : " + keystore.getProvider().getName());
        System.out.println("Type : " + keystore.getType());
        System.out.println("Size : " + keystore.size());

        Enumeration en = keystore.aliases();
        while (en.hasMoreElements()) {
            System.out.println("Alias: " + en.nextElement());
        }
    }
    private class AliasKeyManager implements X509KeyManager {
        private KeyStore _ks;
        private String _alias;
        private String _password;

        public AliasKeyManager(KeyStore ks, String alias, String password) {
            _ks = ks;
            _alias = alias;
            _password = password;
        }

        public String chooseClientAlias(String[] str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public String chooseServerAlias(String str, Principal[] principal, Socket socket) {
            return _alias;
        }

        public X509Certificate[] getCertificateChain(String alias) {
            try {
                java.security.cert.Certificate[] certificates = this._ks.getCertificateChain(alias);
                if (certificates == null) {
                    throw new FileNotFoundException("no certificate found for alias:" + alias);
                }
                X509Certificate[] x509Certificates = new X509Certificate[certificates.length];
                System.arraycopy(certificates, 0, x509Certificates, 0, certificates.length);
                return x509Certificates;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] getClientAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }

        public PrivateKey getPrivateKey(String alias) {
            try {
                return (PrivateKey) _ks.getKey(alias, _password == null ? null : _password.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public String[] getServerAliases(String str, Principal[] principal) {
            return new String[] { _alias };
        }

    }
}
