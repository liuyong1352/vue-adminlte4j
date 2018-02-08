依赖环境：node , webpack ； node安装 ， 自行搜索安装最新版本

#####前端基于webpack构建的 ， 所以需要先安装webpack
```
npm install --save-dev webpack
```
#####构建前端库文件
``` 
cd src\main\static

npm i 
npm start
```

运行test 下面ApplicationStarter
访问地址 http://localhost:8080/index.html

执行maven打包 得到jar即可


vue-adminlte-java 后台界面库 
提供统一的布局 ， ui控件
天然前后台分离 ， 通过rest-api提供数据， 控件与java类库模型进行绑定
开发时可配置的控件



###使用

[1] 引入webjar , Maven配置
```
<dependency>
    <groupId>com.vue.adminlte4j</groupId>
    <artifactId>vue-adminlte4j</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```
[2] 添加controller继承ApiAdminController

[3] 添加页面
```
<script src="/lib/lib.js"></script>
<body>
<v-app :data="data">
    
</v-app>
</body>
<script>app.main({})</script>
```
