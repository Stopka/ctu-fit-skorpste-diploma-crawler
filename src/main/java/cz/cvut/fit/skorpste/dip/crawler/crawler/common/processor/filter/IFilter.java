package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter;

/**
 * Filter component to do a selections of item
 * Created by stopka on 2.12.14.
 */
public interface IFilter<T> {
    /**
     * Whether this item passed this filter
     * @param item item to check
     * @return true if accepted, valid, passed...
     */
    public boolean isAccepted(T item);
}
