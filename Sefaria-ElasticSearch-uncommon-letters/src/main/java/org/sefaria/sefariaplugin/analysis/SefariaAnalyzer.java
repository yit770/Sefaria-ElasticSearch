package org.sefaria.sefariaplugin.analysis;

import  org.apache.lucene.analysis.Analyzer;
import  org.apache.lucene.analysis.TokenStream;
import  org.apache.lucene.analysis.Tokenizer;
import  org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.pattern.PatternReplaceFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.util.regex.Pattern;

/**
 * Created by nss on 2/15/17.
 */
public class SefariaAnalyzer extends Analyzer {


    @Override
    protected TokenStreamComponents createComponents(String field) {
        Pattern nikPat = Pattern.compile("[\u0591-\u05BD\u05BF\u05C1\u05C2\u05C4\u05C5\u05C7]");

        Tokenizer tokenizer = new StandardTokenizer();
        TokenStream filter = new PatternReplaceFilter(tokenizer,nikPat,"",true);
        filter = new ASCIIFoldingFilter(filter);
        filter = new LowerCaseFilter(filter);
        filter = new InfreqLetterTokenFilter(filter);
        filter = new LengthFilter(filter, 2, 20);
        return new TokenStreamComponents(tokenizer, filter);
    }
}
