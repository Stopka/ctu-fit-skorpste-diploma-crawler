package cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;

/**
 * General interface of factory which creates solr document from file
 * Created by stopka on 2.12.14.
 */
public interface IDocumentFactory extends IFactory<File,SolrInputDocument> {
}
