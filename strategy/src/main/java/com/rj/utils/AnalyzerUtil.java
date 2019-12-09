package com.rj.utils;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * IKAnalyzer4Lucence中文分词器，根据细分粒度对内容进行分词
 * 这里是一个针对这个分词器的工具类，目的是将这个内容的分词结果封装为一个List集合
 */
public class AnalyzerUtil {
    public static List doToken(TokenStream tokenStream) throws IOException {
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        ArrayList<String> list = new ArrayList<>();
        while (tokenStream.incrementToken()) {
            System.out.println(attribute.toString()+"|"); //遍历分词的结果，然后输出结果
            list.add(attribute.toString());
        }
        System.out.println();
        tokenStream.end();
        tokenStream.close();
        return list;
    }
}
