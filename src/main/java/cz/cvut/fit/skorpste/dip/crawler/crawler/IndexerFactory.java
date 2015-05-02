package cz.cvut.fit.skorpste.dip.crawler.crawler;

import cz.cvut.fit.skorpste.dip.crawler.Index;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.ICrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.FileSystemCrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory.TikaDocumentFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory.TxtDocumentFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.IndexingCollectionProcessor;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.util.List;

/**
 * Factory of index component chain
 * Created by stopka on 3.12.14.
 */
public class IndexerFactory extends CrawlerFactory<File, SolrInputDocument>{
    static Logger logger=Logger.getLogger(IndexerFactory.class);

    public IndexerFactory(Index index){
        super(index);
    }

    /**
     * Creates index component chain depending on configuration
     * @return complete file crawler to index files on filesystem
     */
    public ICrawler get(){
        logger.debug("Building index chain");
        return getCrawler();
    }

    /**
     * Creates crawler
     * - chooses configured crawler indexer.crawler=file_system|none
     * @return configured crawler
     */
    protected ICrawler getCrawler(){
        logger.debug("Creating crawler");
        String type=getConfig().getString("indexer.crawler");
        if(type!=null && type.equals("file_system")) {
            logger.debug("crawler=file_system");
            return getFileSystemCrawler();
        }
        logger.debug("crawler=none");
        return getNoneCrawler();
    }

    /**
     * Creates file system crawler
     * - sets paths to crawl config indexer.crawler.file_system.paths=?
     * - can limit recursive file search by config indexer.crawler.file_system.depth_limit
     * @return file system crawler
     */
    protected ICrawler getFileSystemCrawler(){
        logger.debug("Creating File system crawler");
        List<String> paths=(List<String>)getConfig().getList("indexer.crawler.file_system.paths");
        logger.debug("paths="+paths.toString());
        FileSystemCrawler crawler= new FileSystemCrawler(paths,getFactoryProcessor());
        if(getConfig().containsKey("indexer.crawler.file_system.depth_limit")) {
            int val=getConfig().getInt("indexer.crawler.file_system.depth_limit");
            logger.debug("depth_limit="+val);
            crawler.setDepthLimit(val);
        }
        return crawler;
    }

    /**
     * Creates document factory
     * - chooses configured factory indexer.factory=txt|tika
     * @return configured document factory processor
     */
    protected IFactory<File,SolrInputDocument> getFactory(){
        logger.debug("Creating document factory");
        String type=getConfig().getString("indexer.factory");
        if(type!=null && type.equals("tika")){
            logger.debug("factory=tika");
            return getTikaFactory();
        }
        logger.debug("factory=txt");
        return getTxtFactory();
    }

    /**
     * Creates collection processor
     * @return collector
     */
    @Override
    protected IProcessor<List<SolrInputDocument>> getProcessor() {
        Index index=getIndex();
        logger.debug("Creating indexer processor");
        return new IndexingCollectionProcessor(index);
    }

    /**
     * Creates text document factory
     * - sets charset using config indexer.factory.txt.charset=?
     * @return factory to read plain texts
     */
    protected IFactory getTxtFactory(){
        logger.debug("Creating txt document factory");
        String val=getConfig().getString("indexer.factory.txt.charset");
        logger.debug("charset="+val);
        return new TxtDocumentFactory(val);
    }

    /**
     * Creates tika document factory
     * @return factory to read rich docs
     */
    protected IFactory getTikaFactory(){
        logger.debug("Creating tika document factory");
        return new TikaDocumentFactory();
    }
}
