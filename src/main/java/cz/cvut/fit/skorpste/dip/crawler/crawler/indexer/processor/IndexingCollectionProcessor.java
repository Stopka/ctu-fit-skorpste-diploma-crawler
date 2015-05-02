package cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor;

import cz.cvut.fit.skorpste.dip.crawler.Index;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;

import java.util.List;

/**
 * Processor which adds batches of documents to solr index
 * Created by stopka on 2.12.14.
 */
public class IndexingCollectionProcessor implements IProcessor<List<SolrInputDocument>> {
    Index index;
    static Logger logger=Logger.getLogger(IndexingCollectionProcessor.class);

    /**
     *
     * @param index index model
     */
    public IndexingCollectionProcessor(Index index){
        this.index=index;
    }

    /**
     * Adds all documents in list to index using single model query
     * @param docs list of sorl documents
     * @throws CrawlerException when document indexing failes
     */
    @Override
    public void process(List<SolrInputDocument> docs) throws CrawlerException{
        logger.debug("Adding collection to index");
        try {
            index.add(docs);
        } catch (Exception e) {
            throw new CrawlerException("Document indexing failed.",e);
        }
    }

    public void finish(){
        logger.debug("Finishing indexing collection processor");
    }
}
