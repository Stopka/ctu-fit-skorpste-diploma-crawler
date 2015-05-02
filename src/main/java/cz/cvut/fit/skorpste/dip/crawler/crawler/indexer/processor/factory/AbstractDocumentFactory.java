package cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * General implementation of factory which creates solr document from file
 * Created by stopka on 2.12.14.
 */
public abstract class AbstractDocumentFactory implements IDocumentFactory {
    private String category;

    /**
     *
     * @param category category field
     */
    public AbstractDocumentFactory(String category){
        this.category=category;
    }

    /**
     * category field="default"
     */
    public AbstractDocumentFactory(){
        this("default");
    }

    /**
     * Creates solr document
     * it sets fields:
     * id=canonical file path
     * name=filename
     * edited=last modified date
     * cat=category set in constructor
     * text=depends on parseFile(file) implementation
     * @param file File object to read file data
     * @return solr index document
     * @throws CrawlerException General exception thrown on any error
     */
    public SolrInputDocument create(File file) throws CrawlerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField( "id", getId(file));
        doc.addField( "name", getName(file));
        doc.addField( "edited", getEdited(file) );
        doc.addField( "cat", getCat(file) );
        doc.addField( "text", parseFile(file) );
        return doc;
    }

    /**
     * prepares document field
     * @param file File object to read file data
     * @return canonical file path
     */
    protected String getId(File file){
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            return file.getAbsolutePath();
        }
    }

    /**
     * prepares document field
     * @param file File object to read file data
     * @return file name
     */
    protected String getName(File file){
        return file.getName();
    }

    /**
     * prepares document field
     * @param file File object to read file data
     * @return last modified date
     */
    protected Date getEdited(File file){
        return new Date(file.lastModified());
    }

    /**
     * prepares document field
     * @param file File object to read file data
     * @return category given in contructor
     */
    protected String getCat(File file){
        return category;
    }

    /**
     * prepares document field
     * @param file File object to read file content
     * @return prepared text field
     */
    abstract protected Object parseFile(File file) throws CrawlerException;
}
