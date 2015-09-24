package util;

/**
 * Created by icepoint on 8/29/15.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.TextUI;

import com.zhou.entity.LinkTypeData;
import com.zhou.entity.Rule;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author zhy
 *
 */
public class ExtractService
{
    /**
     * @param rule
     * @return
     */

    public static List<LinkTypeData> extract(Rule rule)
    {

        // 进行对rule的必要校验
        validateRule(rule);

        List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
        LinkTypeData data = null;
        try
        {
            /**
             * 解析rule
             */
            String url = rule.getUrl();
            String[] params = rule.getParams();
            String[] values = rule.getValues();
            String resultTagName = rule.getResultTagName();
            int type = rule.getType();
            int requestType = rule.getRequestMoethod();

            Connection conn = Jsoup.connect(url);
            // 设置查询参数

            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    conn.data(params[i], values[i]);
                }
            }

            // 设置请求类型
            Document doc = null;
            switch (requestType)
            {
                case Rule.GET:
                    doc = conn.timeout(100000).get();
                    break;
                case Rule.POST:
                    doc = conn.timeout(100000).post();
                    break;
            }

            //处理返回数据
            Elements results = new Elements();

            switch (type)
            {
                case Rule.CLASS:
                    results = doc.getElementsByClass("s-access-image cfMarker");
                    break;
                case Rule.ID:
                    Element result = doc.getElementById(resultTagName);
                    results.add(result);
                    break;
                case Rule.SELECTION:
                    results = doc.select("img");

                    break;
                default:
                    //当resultTagName为空时默认去body标签
                    if (TextUtil.isEmpty(resultTagName))
                    {
                        results = doc.getElementsByTag("img");

                    }
            }

            for (Element result : results)
            {
                Elements links = result.getElementsByTag("img");




                for (Element link : links)
                {
                    //必要的筛选
                    String linkHref = link.attr("src");
                    String linkText = link.text();


                    data = new LinkTypeData();
                    data.setLinkHref(linkHref);
                    data.setLinkText(linkText);

                    datas.add(data);
                }

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return datas;
    }
    public static List<LinkTypeData> extract2(Rule rule)
    {

        // 进行对rule的必要校验
        validateRule(rule);

        List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
        LinkTypeData data = null;
        try
        {
            /**
             * 解析rule
             */
            String url = rule.getUrl();
            String[] params = rule.getParams();
            String[] values = rule.getValues();
            String resultTagName = rule.getResultTagName();
            int type = rule.getType();
            int requestType = rule.getRequestMoethod();

            Connection conn = Jsoup.connect(url);
            // 设置查询参数

            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    conn.data(params[i], values[i]);
                }
            }

            // 设置请求类型
            Document doc = null;
            switch (requestType)
            {
                case Rule.GET:
                    doc = conn.timeout(100000).get();
                    break;
                case Rule.POST:
                    doc = conn.timeout(100000).post();
                    break;
            }

            //处理返回数据
            Elements results = new Elements();
            Elements results2=new Elements();
            switch (type)
            {
                case Rule.CLASS:
                    results = doc.getElementsByClass("s-access-image cfMarker");
                    break;
                case Rule.ID:
                    Element result = doc.getElementById(resultTagName);
                    results.add(result);
                    break;
                case Rule.SELECTION:

                    results2=doc.select("h2");
                    break;
                default:
                    //当resultTagName为空时默认去body标签
                    if (TextUtil.isEmpty(resultTagName))
                    {

                        results2 = doc.getElementsByTag("h2");
                    }
            }

            for (Element result : results2)
            {

                Elements links2 = result.getElementsByTag("h2");



                for (Element link : links2)
                {
                    //必要的筛选
                    String linkHref = link.attr("class");
                    String linkText = link.text();


                    data = new LinkTypeData();
                    data.setLinkHref(linkHref);
                    data.setLinkText(linkText);

                    datas.add(data);
                }

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return datas;
    }

    /**
     * 对传入的参数进行必要的校验
     */
    private static void validateRule(Rule rule)
    {
        String url = rule.getUrl();
        if (TextUtil.isEmpty(url))
        {
            throw new RuleException("url不能为空！");
        }
        if (!url.startsWith("http://"))
        {
            throw new RuleException("url的格式不正确！");
        }

        if (rule.getParams() != null && rule.getValues() != null)
        {
            if (rule.getParams().length != rule.getValues().length)
            {
                throw new RuleException("参数的键值对个数不匹配！");
            }
        }

    }


}