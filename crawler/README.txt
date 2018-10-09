类Credit2是用来爬取淘大象网站中旺旺号查询功能

首先需要根据URL https://www.taodaxiang.com/credit2 获取PHPSESSID
然后根据PHPSESSID和参数account进行post请求https://www.taodaxiang.com/credit2/index/get

https://www.taodaxiang.com/credit2需要进行js解析，所以需要模拟浏览器  
使用selenium+phantomjs 进行模拟无界面浏览器

每次只能生成一个phantomjs 进程，使用完毕需要关闭，多个phantomjs 进程会出现问题，无法爬取 
原因：可能引起反爬虫，IP禁用
