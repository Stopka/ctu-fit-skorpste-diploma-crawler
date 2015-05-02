package cz.cvut.fit.skorpste.dip.crawler.crawler.common.processor.filter;

/**
 * Created by stopka on 3.12.14.
 */
public class TrueFilter<I> implements IFilter<I> {

    @Override
    public boolean isAccepted(I item) {
        return true;
    }
}
