package cz.cvut.fit.skorpste.dip.crawler;


import org.apache.commons.configuration.*;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Factory creating a config model singleton
 * Created by stopka on 2.12.14.
 */
public class ConfigFactory {
    static Logger logger=Logger.getLogger(ConfigFactory.class);
    private static Configuration config;

    /**
     * Creates and cashes config model singleton
     * it uses several config properties sources
     * - file application.properties containing defaults
     * - system properties overriding default values
     * @return config model
     */
    public static Configuration getConfig(){
        if(config==null){
            config=createConfig();
        }
        return config;
    }

    /**
     * Creates config model
     * it uses several config properties sources
     * - file application.properties containing defaults
     * - system properties overriding default values
     * @return created config model
     */
    private static  Configuration createConfig(){
        logger.debug("Building config");
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        try {
            logger.debug("Loading config file");
            String path=config.getString("config");
            if(path==null){
                logger.debug("Using default config path");
                path="crawler.properties";
            }
            logger.debug("config="+path);
            config.addConfiguration(new PropertiesConfiguration(new File(path)));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        try {
            config.addConfiguration(new PropertiesConfiguration("application.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return config;
    }
}
