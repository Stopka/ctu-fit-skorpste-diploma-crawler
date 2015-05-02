package cz.cvut.fit.skorpste.dip.crawler.crawler.indexer;

import cz.cvut.fit.skorpste.dip.crawler.crawler.common.ICrawler;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.*;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter.IFilter;
import cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.IProcessor;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * Crawls given paths recursively,
 * searches for files,
 * selects files using given filter,
 * processes selected files using given processor
 * Created by stopka on 2.12.14.
 */
public class FileSystemCrawler extends AbstractCrawler<File> {
    static Logger logger=Logger.getLogger(FileSystemCrawler.class);

    List<String> paths;
    int depthLimit =Integer.MAX_VALUE;

    /**
     *
     * @param paths list of paths to crawl
     * @param processor processor component process selected files
     */
    public FileSystemCrawler(List<String> paths,IProcessor<File> processor){
        super(processor);
        this.paths=paths;
    }

    /**
     * Sets limit to depth of recursive crawling
     * @param depthLimit 1=crawling is not recursive; -1=no limits (Integer.MAX_VALUE is used)
     */
    public void setDepthLimit(int depthLimit) {
        if(depthLimit<0){
            depthLimit=Integer.MAX_VALUE;
        }
        this.depthLimit = depthLimit;
    }

    /**
     * Does the file search and sends it all to set processor
     */
    public void processAll(){
        logger.debug("Iterating filesystem");
        for(String path: paths){
            logger.info("Processing path " + path);
            processPath(path);
        }
    }

    /**
     * Does file search in given path recursively
     * starts depth counting
     * @param path path to file or direcotry to process
     */
    public void processPath(String path){
        File file=new File(path);
        if (!file.exists()) {
            logger.error("Path does not exist " + path);
            return;
        }
        processPath(file);
    }

    /**
     * Does file search in given file class recursively
     * starts depth counting
     * @param file general file object to process
     */
    public void processPath(File file){
        processPath(file,-1);
    }

    /**
     * Runs file and directory processing on given file
     * @param file general file object to process
     * @param depth holds actual direcotry depth
     */
    protected void processPath(File file,int depth){
        if(file.isDirectory()){
            processDirectory(file, depth + 1);
        }else{
            processFile(file);
        }
    }

    /**
     * Does the file and direcotry search in given directory
     * it monitors the depth and stops if reached
     * @param directory directory to iterate files in
     * @param depth actual depth in recursion
     */
    protected void processDirectory(File directory,int depth){
        if(depth>=this.depthLimit) {
            return;
        }
        logger.info("Searching directory " + directory.getAbsolutePath());
        for (File file : directory.listFiles()) {
            processPath(file,depth);
        }
    }

    /**
     * Checks file against filter and if passed sends it to processor
     * @param file processes the file
     */
    protected void processFile(File file){
        try {
            processItem(file);
        }catch (CrawlerException e){
            logger.warn(e.getMessage()+" ("+file.getAbsolutePath()+")");
            e.printStackTrace();
        }
    }
}


