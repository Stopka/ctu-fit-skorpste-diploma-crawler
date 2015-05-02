package cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner;

import cz.cvut.fit.skorpste.dip.crawler.Index;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.ICrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.*;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

/**
 * Crawls the solr index and searches for indexed documents
 * Created by stopka on 2.12.14.
 */
public class IndexCrawler extends AbstractCrawler<SolrDocument> {
    static Logger logger=Logger.getLogger(IndexCrawler.class);
    Index index;

    /**
     *
     * @param index index model
     * @param processor processor to process selected documents
     */
    public IndexCrawler(Index index,IProcessor<SolrDocument> processor){
        super(processor);
        this.index=index;
    }

    /**
     * Does the crawling
     * @throws CrawlerException General exception thrown any component in chain
     */
    public void processAll() throws CrawlerException{
        logger.debug("Iterating index");
        SolrDocumentList list = null;
        try {
            list = index.findAll();
            proceessList(list);
            for(int start=index.DEFAULT_ROWS;start<list.getNumFound();start+=index.DEFAULT_ROWS){
                list=index.findAll(start);
                proceessList(list);
            }
        } catch (SolrServerException e) {
            throw new CrawlerException("Retrieving documents from solr index failed", e);
        }
    }

    /**
     * Iterates the retrieved document list and does the processing
     * @param list list of sorl documents to iterate on
     */
    protected void proceessList(SolrDocumentList list){
        for(SolrDocument doc:list){
            processDocument(doc);
        }
    }

    /**
     * Processes the single document searched
     * Checks file against filter and if passed sends it to processor
     * @param doc SorlDocument to process
     * @throws CrawlerException General exception thrown by next processors in chain
     */
    protected void processDocument(SolrDocument doc) throws CrawlerException{
        processItem(doc);
    }
}


