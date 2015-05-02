package cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Creates solr documents from plaintext files
 * Created by stopka on 2.12.14.
 */
public class TxtDocumentFactory extends AbstractDocumentFactory {
    String charset;

    /**
     * @param charset of text files
     */
    public TxtDocumentFactory(String charset){
        this.charset=charset;
    }

    /**
     * Parses plaintext file
     * @param file File to read content
     * @return parsed text
     * @throws CrawlerException Thrown on file reading errors
     */
    @Override
    protected String parseFile(File file) throws CrawlerException {
        try {
            byte[] encoded = Files.readAllBytes(file.toPath());
            return new String(encoded, charset);
        } catch (IOException e) {
            throw new CrawlerException("File can't be read.",e);
        }
    }
}
