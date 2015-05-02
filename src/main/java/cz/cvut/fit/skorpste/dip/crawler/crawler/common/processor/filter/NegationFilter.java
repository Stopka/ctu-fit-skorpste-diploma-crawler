package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter;

/**
 * Uses given filter and negates its output
 * Created by stopka on 3.4.15.
 */
public class NegationFilter<I> implements IFilter<I> {
    IFilter<I> filter;

    /**
     *
     * @param filter filter to negate
     */
    public NegationFilter(IFilter<I> filter){
        this.filter=filter;
    }


    @Override
    public boolean isAccepted(I item) {
        return !filter.isAccepted(item);
    }
}
