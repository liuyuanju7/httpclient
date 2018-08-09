package com.liuyj.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;

/**
 * @author liuyuanju1
 * @date 2018/8/8
 * @description: 爬取网易云音乐 及  前三评论
 */
public class Music163 {

    public static void main(String[] args) throws IOException{
      //  getMusicFrom163();
    //    getwangyiyun();
    }

    @Test
    public  void getMusicFrom163() throws IOException{
        String url = "https://music.163.com/#/song?id=461080452";
        InputStream input = null;
        BufferedInputStream bis = null;
        FileOutputStream output = null;
        Document document;
        Elements elements;

        document = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36")
                .header("Cache-Control", "no-cache").timeout(2000000000).get();

        Document document1 = Jsoup.parse(document.text());

        Elements elements1 = document.select("em.f-ff2");

//        Element el = document.getElementById("g_iframe");
//
//        Document document1 = Jsoup.parseBodyFragment(el.text());

//        String iframe = el.attr("abs:src");

        Element body = document1.getElementById("auto-id-T2lWb3Rcz4O6ZtFe");
        //歌名
        Element song = document1.getElementsByClass("f-ff2").get(0);
        //歌手
        Element singer = document.select("div.hd").select("p.s-fc4").select("span").first();

        Element component = document.getElementsByClass("n-cmt").first();
        Elements element1 = component.getElementsByClass("cnt");

        System.out.println(song.text() + " -- " + singer.text());
        System.out.println(element1.get(0).text());
    }

    @Test
    public void getwangyiyun() throws IOException{
        String id = "461080452";
        Connection.Response execute = Jsoup.connect("http://music.163.com/m/song?id=" + id)
                .header("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36")
                .header("Cache-Control", "no-cache").timeout(2000000000)
//							.proxy(IpProxy.ipEntitys.get(i).getIp(),IpProxy.ipEntitys.get(i).getPort())
                .execute();
        String body = execute.body();
        // System.out.println(body);
        Document parse = execute.parse();

        //歌名
        Element song = parse.getElementsByClass("f-ff2").first();
        //歌手
        Element singer = parse.getElementsByClass("s-fc4").select("span").get(0);
        // 获取歌曲播放外链
        String outchain = "http://music.163.com/outchain/player?type=2&id=" + id + "&auto=1&height=66";
        System.out.println(song.text() + " " + singer.attr("title") + " " + outchain);

        //输出 html 文件
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("E:\\my_github\\httpclient\\http-client\\src\\main\\resources\\output\\musicList.html")));
        writer.write("");

        File input = new File(Music163.class.getClassLoader().getResource("template/music.html").getFile());
        Document template = Jsoup.parse(input,"utf-8");
        Element table = template.getElementById("music");
        Element tempTr = table.getElementById("template");
        tempTr.attr("id",id);
        tempTr.getElementsByClass("song").select("span").first().text(song.text() + "--" + singer.attr("title"));
        tempTr.getElementsByClass("outchain").select("iframe").attr("src",outchain);
        tempTr.removeClass("hide");
        table.append(tempTr.html());
        writer.write(template.html());

        writer.close();
    }
}
