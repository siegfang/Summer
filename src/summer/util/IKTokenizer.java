package summer.util;

import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by siegfang on 14-03-06.
 * IK包装的一个中文分词器。
 */
public class IKTokenizer implements ITokenizer {

    private HashSet<String> userStopwords = new HashSet<String>();

    public IKTokenizer(List<String> userLibrary, List<String> userStopwordList){

        Dictionary.initial(DefaultConfig.getInstance());
        if (userLibrary != null){
            Dictionary.getSingleton().addWords(userLibrary);
        }
        if (userStopwordList != null){
            userStopwords.addAll(userStopwordList);
        }

    }

    public IKTokenizer(){

        this(null, null);

    }

    @Override
    public String[] tokenize(String input) {

        if (input == null){
            return new String[0];
        }

        IKSegmenter ikSeg = new IKSegmenter(new StringReader(input), true);

        String[] tokenArray = null;
        LinkedList<String> tokenList = new LinkedList<String>();
        try {
            for (Lexeme lexeme = ikSeg.next(); lexeme != null; lexeme = ikSeg.next()){
                if (userStopwords.contains(lexeme.getLexemeText())){
                    continue;
                }
                tokenList.add(lexeme.getLexemeText());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
        tokenArray = new String[tokenList.size()];
        int tokenIndex = 0;
        for (String token : tokenList){
            tokenArray[tokenIndex] = token;
            tokenIndex ++;
        }

        return tokenArray;
    }
}
