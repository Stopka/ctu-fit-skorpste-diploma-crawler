package cz.cvut.fit.skorpste.dip.crawler.crawler.indexer.processor.factory;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Creates solr document using tika library
 * Created by stopka on 2.12.14.
 */
public class TikaDocumentFactory extends AbstractDocumentFactory {

    /**
     * Parses document text using tika
     * @param file File to read content
     * @return parsed text
     * @throws CrawlerException Thrown on file reading or parsing errors
     */
    @Override
    protected String parseFile(File file) throws CrawlerException {
        try {
            InputStream stream = new FileInputStream(file);
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            try {
                parser.parse(stream, handler, metadata);
                return handler.toString();
            } finally {
                stream.close();
            }
        }catch (Exception e){
            throw new CrawlerException("File parsing failed.",e);
        }
    }
}
