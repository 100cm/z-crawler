package util;


import com.zhou.entity.LinkTypeData;
import com.zhou.entity.Papers;
import com.zhou.entity.Rule;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by icepoint on 8/29/15.
 */
public class Test {

    public  static void main (String args[]){




        Test tes=new Test();
        System.out.print(tes.search().size());
        for (int i=0;i<tes.search().size();i++){
            tes.running(tes.search().get(i).getName(),tes.search().get(i).getId());
        }



}

    public void running(String bookname,int id){

        Rule rule = new Rule("http://www.amazon.cn/s/ref=nb_sb_noss_1?__mk_zh_CN=",
                new String[] {"url","field-keywords" }, new String[] { "search-alias=stripbooks",bookname},
                "img",Rule.SELECTION, Rule.GET);
        Rule rule2 = new Rule("http://www.amazon.cn/s/ref=nb_sb_noss_1?__mk_zh_CN=",
                new String[] {"url","field-keywords" }, new String[] { "search-alias=stripbooks",bookname},
                "h2",Rule.SELECTION, Rule.GET);
        List<LinkTypeData> extracts2 = ExtractService.extract2(rule2);
        List<LinkTypeData> extracts = ExtractService.extract(rule);


        try {

            if (extracts.size() != 0) {

                if (extracts2.size() != 0) {


                    for (int i=3;i<=5;i++){
                        if (bookname.split(" ")[0].equals(extracts2.get(i-2).getLinkText().split(" ")[0])) {
                            downloadFromUrl(extracts.get(i).getLinkHref(), "/Users/icepoint/Desktop/papers/", id);
                            System.out.println("success!");
                        }

                }

                } else {

                    running(bookname, id);

                }


            }
        }
        catch(Exception e){
           System.out.print("error");

        }


    }



    public static String downloadFromUrl(String url,String dir,int id) {

        try {
            URL httpurl = new URL(url);
            String fileName = getFileNameFromUrl(url);
            Test test=new Test();
            String jpg=test.getExtensionName(fileName);


            if (jpg.equals("jpg")) {
                fileName = id + "." + jpg;
                File f = new File(dir + fileName);
                FileUtils.copyURLToFile(httpurl, f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Fault!";
        }
        return "Successful!";
    }

    public static String getFileNameFromUrl(String url){
        String name = new Long(System.currentTimeMillis()).toString() + ".X";
        int index = url.lastIndexOf("/");
        if(index > 0){
            name = url.substring(index + 1);
            if(name.trim().length()>0){
                return name;
            }
        }
        return name;
    }

    public List<Papers> search(){

        List<Papers> p=new ArrayList<>();
        Statement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/em_development",
                    "kevin", "");
            stmt = c.createStatement();
            String sql = " select * from papers " ;
            java.sql.ResultSet rs=stmt.executeQuery(sql);
            int count=1;
            while (rs.next()){
               Papers papers=new Papers();
                papers.setName(rs.getString("title"));
                papers.setId(rs.getInt("id"));
                p.add(papers);

            }
            System.out.print(count);


            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return p;



    }
    public String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


}
