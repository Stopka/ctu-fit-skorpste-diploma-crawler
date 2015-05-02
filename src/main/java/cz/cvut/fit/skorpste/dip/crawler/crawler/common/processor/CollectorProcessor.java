package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.CrawlerException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Special type of processor
 * Makes colections of data to process an processes them in a batch
 * Created by stopka on 2.12.14.
 */
public class CollectorProcessor<T> extends AbstractPassThroughProcessor<T,List<T>> {
    static Logger logger=Logger.getLogger(CollectorProcessor.class);
    LinkedList<T> items =new LinkedList<T>();
    int maxSize=Integer.MAX_VALUE;

    /**
     *
     * @param processor Processor to process created batches
     */
    public CollectorProcessor(IProcessor<List<T>> processor){
        super(processor);
    }

    /**
     * Set maximal size of collection
     * -1=Integer.MAX_VALUE
     * @param maxSize max size of colection
     */
    public void setMaxSize(int maxSize) {
        if(maxSize<0){
            maxSize=Integer.MAX_VALUE;
        }
        this.maxSize = maxSize;
    }

    /**
     * Adds item to collection
     * if collection reaches the limit, it startes processing of collection
     * @param item item to add to collection
     * @throws CrawlerException when anything goes wrong in next processors in chain
     */
    protected void add(T item) throws CrawlerException {
        logger.debug("Collected " + item);
        items.add(item);
        if(items.size()>=maxSize){
            processCollection();;
        }
    }

    /**
     * Collects item to process
     * @param in item to collect
     * @throws CrawlerException  when anything goes wrong in next processors in chain
     */
    @Override
    public void process(T in) throws CrawlerException{
        add(in);
    }

    /**
     * Processes collection
     * Processing
     * @throws CrawlerException when anything goes wrong in next processors in chain
     */
    protected void processCollection() throws CrawlerException{
        logger.debug("Processing collection");
        if(items.isEmpty()){
            logger.debug("No items to process");
            return;
        }
        processItem(items);
        items=new LinkedList<>();
    }

    /**
     * Processes rest collection
     * Finishes processing
     * @throws CrawlerException  when anything goes wrong in next procesors in chain
     */
    public void finish() throws CrawlerException{
        logger.debug("Finishing collection processing");
        processCollection();
        super.finish();
    }
}
