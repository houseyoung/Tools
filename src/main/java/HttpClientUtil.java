import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ywzhang
 */
public class HttpClientUtil {

    /**
     * 发送HttpGet请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        String strResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(response.getEntity());
            }
            else {
//                logger.warn("get请求提交失败:{}, 返回结果为:{}", url, response.toString());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
//            logger.error("get请求提交失败:{}", url, e);
        }
        return strResult;
    }

    /**
     * 发送HttpPost请求，参数为map
     *
     * @param url
     * @param paramMap
     * @return
     */
    public static String sendPost(String url, Map<String, String> paramMap) {
        String str = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost(url);
            if (null != paramMap && paramMap.size() > 0) {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8);
                httpPost.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(httpPost);

            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                    url = URLDecoder.decode(url, "UTF-8");
                    str = EntityUtils.toString(result.getEntity());
                }
                catch (Exception e) {
                    e.printStackTrace();
//                    logger.warn("post请求提交失败:{}", url, e);
                }
            }
            else {
//                logger.warn("post请求提交失败:{}, 返回结果为:{}", url, result.toString());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
//            logger.error("post请求提交失败:{}", url, e);
        }
        return str;
    }

    /**
     * 发送不带参数的HttpPost请求
     *
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        return sendPost(url, null);
    }

}