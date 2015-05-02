package cz.cvut.fit.skorpste.dip.crawler.crawler.common;

/**
 * Exception thrown by all crawler components
 * Created by stopka on 3.12.14.
 */
public class CrawlerException extends RuntimeException {
    public CrawlerException(String message,Exception e){
        super(message,e);
    }

    public CrawlerException(String message){
        super(message);
    }

    public CrawlerException(Exception e){
        super(e);
    }
}
