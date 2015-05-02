package cz.cvut.fit.skorpste.dip.crawler;

import org.apache.commons.configuration.Configuration;

/**
 * Created by stopka on 4.12.14.
 * Factory method for creating index interacting model
 */
public class IndexFactory {
    /**
     * Creates model using config params
     * config params: solr.host
     * @return Solrj based model for interacting with solr
     */
    public static Index create(){
        Configuration config=ConfigFactory.getConfig();
        return new Index(config.getString("solr.host"));
    }
}
