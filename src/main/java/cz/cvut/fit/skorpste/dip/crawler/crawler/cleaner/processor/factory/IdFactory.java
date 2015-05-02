package cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import org.apache.solr.common.SolrDocument;

/**
 * Gets id filed from solr document
 * Created by stopka on 2.12.14.
 */
public class IdFactory implements IFactory<SolrDocument,String> {

    public String create(SolrDocument doc) {
        return (String)doc.getFieldValue("id");
    }
}
