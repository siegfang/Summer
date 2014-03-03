package summer.util;

import java.util.List;

/** 
 * @author siegfang
 */
public interface ITokenizer {

    /**
     * 中文分句和分词接口
     */
    public List<String> tokenize(String input);

}
