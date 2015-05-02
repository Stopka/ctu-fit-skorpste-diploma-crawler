package cz.cvut.fit.skorpste.dip.crawler.crawler.cleaner.processor.filter;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.factory.IFactory;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Filters documents which files does not exist
 * Created by stopka on 3.12.14.
 */
public class FileExistsFilter<T> implements IFilter<T> {
    static Logger logger=Logger.getLogger(FileExistsFilter.class);
    IFactory<T,String> factory;

    /**
     * @param factory factory to retrieve file path from document
     */
    public FileExistsFilter(IFactory<T,String> factory){
        this.factory=factory;
    }

    /**
     *
     * @param item item to check if accepted
     * @return true if file exists in factory retrieved path
     */
    @Override
    public boolean isAccepted(T item) {
        File file=new File(factory.create(item));
        boolean result=file.exists();
        logger.debug("Checking file " + file.getAbsolutePath()+" ("+result+")");
        return result;
    }
}
