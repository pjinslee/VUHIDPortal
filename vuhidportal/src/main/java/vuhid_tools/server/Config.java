/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Authors: Long Phan, Robert Hickey, Peter Inslee
 * Date: 2012-08-15
 * @version 1.2
 * Comments:    Ver 1.0 2012-07-25 Long created "Config.java" for loading the database configuration
 *                                 settings from a file, using "PropertyLoader.java".
 *              Ver 1.0 2012-08-11 Peter created "Configuration.java" for storing hard-coded VUHID server
 *                                 configuration settings with some getter and setter methods.
 *              Ver 1.1 2012-08-12 Robert changed public class vars to private, and generated getter/setter
 *                                 methods for them in "Configuration.java".
 *              Ver 1.2 2012-08-15 Peter merged code from "Configuration.java" into "Config.java" so all
 *                                 settings are managed and loaded from files by a single class.
 *              Ver 1.3 2012-08-26 Long merged code from "Configuration.java" into "Config.java" (again).
 *              Ver 1.4 2012-08-30 Long Added configurations for server paths.
 */
package vuhid_tools.server;

import java.util.Properties;

import vuhid_tools.server.PropertyLoader;

/**
 * This class contains the global VUHID Portal configuration.<br>
 * It has static final fields initialized from configuration files.<br>
 * It's initialized at the very beginning of startup, and later the JIT compilation will optimize away
 * debug and unused code.
 */
public class Config extends PropertyLoader
{
	public static String	CONFIG_PATH	= "vuhid_tools/";
    public static final String  SERVER_CONFIGURATION_FILE = "config/server.properties";
    public static final String  VUHID_CONFIGURATION_FILE = "config/vuhid.properties";
    
    // GlassFish server path configuration
    public static String        SERVER_PATH;
    // Database server configuration
    public static String        DATABASE_DRIVER;
    public static String        DATABASE_URL;
    public static String        DATABASE_LOGIN;
    public static String        DATABASE_PASSWORD;
    public static int           DATABASE_MAX_CONNECTIONS;

    // VUHID server configuration
    public static String       vuhidServerHostName;
    public static String       alias;
    public static String       keystoreCPVAlgorithm;
    public static String       keyStoreFileName;
    public static String       keystoreFormat;
    public static String       keyStorePassword;
    public static String       trustStoreCPVAlgorithm;
    public static String       trustStoreFileName;
    public static String       trustStoreFormat;
    public static String       trustStorePassword;
    public static String       securityProtocol;
    public static String       fromHeaderValue;
    public static String       userAgentHeaderValue;

    // TODO: EMPI server configuration (This is just tentative placeholder stuff.)
    // private static String       empiPDSService;
    // private static String       empiPIXManagerService;

    public static void loadConfiguration()
    {
        Properties settings;
        
        // Load GlassFish server path configuration
        try {
            settings = PropertyLoader.loadProperties(CONFIG_PATH + SERVER_CONFIGURATION_FILE);

            SERVER_PATH = settings.getProperty("ServerPath", "applications/vuhidportal/");
        } catch (Exception e) {
            throw new Error("Failed to Load " + CONFIG_PATH + SERVER_CONFIGURATION_FILE + " File.");
        }
        
        
        // Load database server configuration
        try {
            settings = PropertyLoader.loadProperties(CONFIG_PATH + SERVER_CONFIGURATION_FILE);

            DATABASE_DRIVER = settings.getProperty("Driver", "com.mysql.jdbc.Driver");
            DATABASE_URL = settings.getProperty("URL", "jdbc:mysql://localhost/vuhid-portal");
            DATABASE_LOGIN = settings.getProperty("Login", "root");
            DATABASE_PASSWORD = settings.getProperty("Password", "");
            DATABASE_MAX_CONNECTIONS = Integer.parseInt(settings.getProperty("MaximumDbConnections", "10"));
        } catch (Exception e) {
            throw new Error("Failed to Load " + CONFIG_PATH + SERVER_CONFIGURATION_FILE + " File.");
        }

        // Load VUHID server configuration
        try {
            settings = PropertyLoader.loadProperties(CONFIG_PATH + VUHID_CONFIGURATION_FILE);

            vuhidServerHostName = settings.getProperty("vuhidServerHostName", "testid.vuhid.org");
            keyStoreFileName = settings.getProperty("keyStoreFileName", "Certificates/your_keystore.keystore");
            keyStorePassword = settings.getProperty("keyStorePassword", "your_keystore_password");
            keystoreFormat = settings.getProperty("keystoreFormat", "JKS");
            keystoreCPVAlgorithm = settings.getProperty("keystoreCPVAlgorithm", "SunX509");
            trustStoreFileName = settings.getProperty("trustStoreFileName", "Certificates/your_truststore.keystore");
            trustStorePassword = settings.getProperty("trustStorePassword", "your_truststore_password");
            trustStoreFormat = settings.getProperty("trustStoreFormat", "JKS");
            trustStoreCPVAlgorithm = settings.getProperty("trustStoreCPVAlgorithm", "SunX509");
            alias = settings.getProperty("alias", "your_alias");
            securityProtocol = settings.getProperty("securityProtocol", "TLS");
            fromHeaderValue = settings.getProperty("fromHeaderValue", "\"Your Name\" <your_username@your_email>");
            userAgentHeaderValue = settings.getProperty("userAgentHeaderValue", "VUHIDPortalPrototype");
        } catch (Exception e) {
            throw new Error("Failed to Load " + CONFIG_PATH + VUHID_CONFIGURATION_FILE + " File.");
        }

        // TODO: Load EMPI server configuration  (This is just tentative placeholder stuff.)
        /*try {
            settings = PropertyLoader.loadProperties(EMPI_CONFIGURATION_FILE);

            empiPDSService = settings.getProperty("empiPDSService", "http://(your EMPI IP address):8080/axis2/services/pds");
            empiPIXManagerService = settings.getProperty("empiPIXManagerService", "http://(your EMPI IP address):8080/axis2/services/pixmgr");
        } catch (Exception e) {
            throw new Error("Failed to Load " + EMPI_CONFIGURATION_FILE + " File.");
        }*/
    }

/*    
    public static void load() throws Exception
    {
        Util.printSection("Configuration");
        loadConfiguration();
        loadHexId();
        loadSubnets();
        loadTelnetConfig();
        loadIdFactoryConfig();
        loadDateTimeConfig();
        initDBProperties();

        L2Config.loadConfigs();

        registerConfig(new AllConfig());
    }

    private static final class AllConfig extends ConfigLoader
    {
        @Override
        protected String getName()
        {
            return "all";
        }
        
        private boolean _reloading = false;
        
        @Override
        protected void load() throws Exception
        {
            if (_reloading)
                return;
            
            _reloading = true;
            try
            {
                Config.load();
            }
            finally
            {
                _reloading = false;
            }
        }
    }

    // it has no instancies
    protected Config()
    {
    }
    
    public static void initDBProperties()
    {
        System.setProperty("teamc.db.driverclass", DATABASE_DRIVER);
        System.setProperty("teamc.db.urldb", DATABASE_URL);
        System.setProperty("teamc.db.user", DATABASE_LOGIN);
        System.setProperty("teamc.db.password", DATABASE_PASSWORD);
        System.setProperty("teamc.db.maximum.db.connection", Integer.toString(DATABASE_MAX_CONNECTIONS));
    }
}*/

    // ***************  GETTERS  **************************************************************************

    public static String getAlias() {
        return alias;
    }

    public static String getVuhidServerHostName() {
        return vuhidServerHostName;
    }

    public static String getKeystoreCPVAlgorithm() {
        return keystoreCPVAlgorithm;
    }

    public static String getKeyStoreFileName() {
        return keyStoreFileName;
    }

    public static String getKeystoreFormat() {
        return keystoreFormat;
    }

    public static String getKeyStorePassword() {
        return keyStorePassword;
    }

    public static String getTrustStoreCPVAlgorithm() {
        return trustStoreCPVAlgorithm;
    }

    public static String getTrustStoreFileName() {
        return trustStoreFileName;
    }

    public static String getTrustStoreFormat() {
        return trustStoreFormat;
    }

    public static String getTrustStorePassword() {
        return trustStorePassword;
    }

    public static String getSecurityProtocol() {
        return securityProtocol;
    }

    public static String getFromHeaderValue() {
        return fromHeaderValue;
    }

    public static String getUserAgentHeaderValue() {
        return userAgentHeaderValue;
    }

    // ***************  SETTERS  **************************************************************************

    public static boolean setAlias(String argAlias) {
        // TODO:  check for well formedness, maybe other validation?
        // if (alias is valid) {
            alias = argAlias;
            return true;
        // } else {
        //     return false;
        // }
    }

    public static boolean setVuhidServerHostName(String argVuhidServerHostName) {
        vuhidServerHostName = argVuhidServerHostName;
        return true;
    }

    public static boolean setKeystoreCPVAlgorithm(String argkeystoreCPVAlgorithm) {
        keystoreCPVAlgorithm = argkeystoreCPVAlgorithm;
        return true;
    }

    public static boolean setKeyStoreFileName(String argkeyStoreFileName) {
        keyStoreFileName = argkeyStoreFileName;
        return true;
    }

    public static boolean setKeystoreFormat(String argkeystoreFormat) {
        keystoreFormat = argkeystoreFormat;
        return true;
    }

    public static boolean setKeyStorePassword(String argkeyStorePassword) {
        keyStorePassword = argkeyStorePassword;
        return true;
    }

    public static boolean setTrustStoreCPVAlgorithm(String argtrustStoreCPVAlgorithm) {
        trustStoreCPVAlgorithm = argtrustStoreCPVAlgorithm;
        return true;
    }

    public static boolean setTrustStoreFileName(String argtrustStoreFileName) {
        trustStoreFileName = argtrustStoreFileName;
        return true;
    }

    public static boolean setTrustStoreFormat(String argtrustStoreFormat) {
        trustStoreFormat = argtrustStoreFormat;
        return true;
    }

    public static boolean setTrustStorePassword(String argtrustStorePassword) {
        trustStorePassword = argtrustStorePassword;
        return true;
    }

    public static boolean setSecurityProtocol(String argsecurityProtocol) {
        securityProtocol = argsecurityProtocol;
        return true;
    }

    public static boolean setFromHeaderValue(String argfromHeaderValue) {
        fromHeaderValue = argfromHeaderValue;
        return true;
    }

    public static boolean setUserAgentHeaderValue(String arguserAgentHeaderValue) {
        userAgentHeaderValue = arguserAgentHeaderValue;
        return true;
    }
}

