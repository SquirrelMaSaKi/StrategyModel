package com.rj.utils;

import org.apache.lucene.analysis.Analyzer;

import java.io.Reader;

/**
 * 分词器
 * 因为Analyzer的createComponents方法API改变了需要重新实现分析器
 */
public class IKAnalyzer4Lucene7 extends Analyzer {
    private boolean useSmart = false;

    public IKAnalyzer4Lucene7() {
        this(false);
    }

    public IKAnalyzer4Lucene7(boolean useSmart) {
        super();
        this.useSmart = useSmart;
    }

    public boolean isUseSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }


    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        //传入一个IKTokenizer4Lucene7类，真正实现分词
        IKTokenizer4Lucene7 ik = new IKTokenizer4Lucene7(reader, this.useSmart);
        return new TokenStreamComponents(ik);
    }
}
