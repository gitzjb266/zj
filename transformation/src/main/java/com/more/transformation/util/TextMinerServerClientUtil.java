package com.more.transformation.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.more.transformation.exception.UdsDataserviceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * textMiner文本分析服务调用
 * 采用 restTemplate调用 http接口
 * @author skymeng
 *
 */
@Service
public class TextMinerServerClientUtil {
	
	private static Logger logger = LoggerFactory.getLogger(TextMinerServerClientUtil.class); // 日志记录
	
	@Value("${uds.text.miner.url:http://192.168.54.30:30005/api/v1/}")
	private String baseApiUrl;
	
	private String DATA_STR = "data";
	
//	@Autowired
//	private RestTemplate restTemplate;
	private RestTemplate restTemplate = new RestTemplate();
	
	/**
	 * 调用获取文章摘要接口
	 * @param text 文本内容
	 * @param size 获取摘要的个数
	 * @return JSONArray 摘要文本  如：
  [
    "随着VR技术的火热发展，互联网的大佬们都纷纷踏入了VR行业，创立了自己的产业生态链，扎克伯格说虚拟现实会成未示来的科技趋势，马化腾说颠覆微信的可能是VR，即使这样，还是有人不看好虚拟现实这个行业，例如，罗比.巴赫就表示对VR普及这方面不看好，而且这么说也不止他一个人。",
    "新年伊始，不少食品登上抽检“黑榜”。",
    "昨日本报从国家食品药品监督管理总局官网获悉，其公布的2015年第二期食品安全监督抽检信息显示，共抽检肉及肉制品样品2473批次，合格率为95%，55批次产品不合格；在被检测的食用油、油脂及制品中,21批次品牌产品被检出不合格，一线食用油品牌基本检查过关。"
  ]
	 */
	public JSONArray getInformationAbstract(String text,int size){
		
		JSONObject requestParamS = new JSONObject();
		requestParamS.put("size", size);
		requestParamS.put("text", text);
		
		ResponseEntity<JSONObject> responseEntity = null;
		try{
			responseEntity = restTemplate.postForEntity(baseApiUrl+"sentence_abstract", requestParamS, JSONObject.class);
		}catch (Exception e) {
			logger.error("获取文章摘要失败", e);
			throw new UdsDataserviceException("获取摘要失败");
		}
		
		if(responseEntity != null && responseEntity.getStatusCodeValue() == 200){
			JSONObject returnObject = responseEntity.getBody();
			JSONArray result = returnObject.getJSONArray(this.DATA_STR);
			return result;
		}else{
			logger.error("获取文章摘要失败");
			throw new UdsDataserviceException("获取文章摘要失败");
		}
	}
	
	/**
	 * 调用获取文章关键词
	 * @param text 文章文本
	 * @param top  获取前几个权重最高的关键词
	 * @return JSONArray 关键词  如：
	[	
	 {
      "flag": "v",
      "score": 1,
      "word": "采购"
    },
    {
      "flag": "n",
      "score": 0.5439709588491051,
      "word": "中心"
    },
    {
      "flag": "n",
      "score": 0.5137883985407397,
      "word": "项目"
    }
   ]
	 */
	public JSONArray getInformationKeyword(String text,int top){
		
		JSONObject requestParamS = new JSONObject();
		requestParamS.put("top", top);
		requestParamS.put("text", text);
		
		ResponseEntity<JSONObject> responseEntity = null;
		try{
			responseEntity = restTemplate.postForEntity(baseApiUrl+"sentence_keyword", requestParamS, JSONObject.class);
		}catch (Exception e) {
			logger.error("获取文章关键词失败", e);
			throw new UdsDataserviceException("获取文章关键词失败");
		}
		
		if(responseEntity != null && responseEntity.getStatusCodeValue() == 200){
			JSONObject returnObject = responseEntity.getBody();
			JSONArray result = returnObject.getJSONArray(this.DATA_STR);
			return result;
		}else{
			logger.error("获取文章关键词失败");
			throw new UdsDataserviceException("获取文章关键词失败");
		}
	}
	
	/**
	 * 调用命名实体识别接口
	 * @param text 需要分析的文本
	 * @return JSONObject命名实体识别后对象 如：
	 * {
    "Nh": [
      {
        "count": 5,
        "pos": "nh",
        "tag": "Nh",
        "word": "王秀军"
      },
      {
        "count": 1,
        "pos": "nh",
        "tag": "Nh",
        "word": "王秀军左一军委"
      },
      {
        "count": 1,
        "pos": "nh",
        "tag": "Nh",
        "word": "习"
      },
      {
        "count": 1,
        "pos": "nh",
        "tag": "Nh",
        "word": "田义祥"
      }
    ],
    "Ni": [
      {
        "count": 3,
        "pos": "j",
        "tag": "Ni",
        "word": "军委审计署"
      },
      {
        "count": 1,
        "pos": "n",
        "tag": "Ni",
        "word": "中央审计委员会"
      },
      {
        "count": 3,
        "pos": "ni",
        "tag": "Ni",
        "word": "中央军委"
      }
    ]
  }
	 */
	public JSONObject getNamedEntityRecognition(String text){
		
		JSONObject requestParamS = new JSONObject();
		requestParamS.put("text", text);
		
		ResponseEntity<JSONObject> responseEntity = null;
		try{
			responseEntity = restTemplate.postForEntity(baseApiUrl+"ner", requestParamS, JSONObject.class);
		}catch (Exception e) {
			logger.error("实体识别失败", e);
			throw new UdsDataserviceException("实体识别失败");
		}
		
		if(responseEntity != null && responseEntity.getStatusCodeValue() == 200){
			JSONObject returnObject = responseEntity.getBody();
			JSONObject result = returnObject.getJSONObject(this.DATA_STR);
			return result;
		}else{
			logger.error("实体识别失败");
			throw new UdsDataserviceException("实体识别失败");
		}
	
	}
	
	
}
