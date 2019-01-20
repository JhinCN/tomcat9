<%--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
request.setAttribute("year", sdf.format(new java.util.Date()));
request.setAttribute("tomcatUrl", "http://tomcat.apache.org/");
request.setAttribute("tomcatDocUrl", "/docs/");
request.setAttribute("tomcatExamplesUrl", "/examples/");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title><%=request.getServletContext().getServerInfo() %></title>
        <link href="favicon.ico" rel="icon" type="image/x-icon" />
        <link href="favicon.ico" rel="shortcut icon" type="image/x-icon" />
        <link href="tomcat.css" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <div id="wrapper">
            <div id="navigation" class="curved container">
                <span id="nav-home"><a href="${tomcatUrl}">主页</a></span>
                <span id="nav-hosts"><a href="${tomcatDocUrl}">文档</a></span>
                <span id="nav-config"><a href="${tomcatDocUrl}config/">配置</a></span>
                <span id="nav-examples"><a href="${tomcatExamplesUrl}">示例</a></span>
                <span id="nav-wiki"><a href="http://wiki.apache.org/tomcat/FrontPage">Wiki</a></span>
                <span id="nav-lists"><a href="${tomcatUrl}lists.html">邮件列表</a></span>
                <span id="nav-help"><a href="${tomcatUrl}findhelp.html">寻求帮助</a></span>
                <br class="separator" />
            </div>
            <div id="asf-box">
                <h1>${pageContext.servletContext.serverInfo}</h1>
            </div>
            <div id="upper" class="curved container">
                <div id="congrats" class="curved container">
                    <h2>如果你看到这个页面，你已经成功安装了Tomcat。恭喜！</h2>
                </div>
                <div id="notice">
                    <img src="tomcat.png" alt="[tomcat logo]" />
                    <div id="tasks">
                        <h3>推荐阅读：</h3>
                        <h4><a href="${tomcatDocUrl}security-howto.html">如何考虑安全因素</a></h4>
                        <h4><a href="${tomcatDocUrl}manager-howto.html">如何管理应用程序</a></h4>
                        <h4><a href="${tomcatDocUrl}cluster-howto.html">如何管理集群与会话(session)复制</a></h4>
                    </div>
                </div>
                <div id="actions">
                    <div class="button">
                        <a class="container shadow" href="/manager/status"><span>服务状态</span></a>
                    </div>
                    <div class="button">
                        <a class="container shadow" href="/manager/html"><span>应用管理</span></a>
                    </div>
                    <div class="button">
                        <a class="container shadow" href="/host-manager/html"><span>主机管理</span></a>
                    </div>
                </div>
                <!--
                <br class="separator" />
                -->
                <br class="separator" />
            </div>
            <div id="middle" class="curved container">
                <h3>开发者快速入门</h3>
                <div class="col25">
                    <div class="container">
                        <p><a href="${tomcatDocUrl}setup.html">Tomcat安装</a></p>
                        <p><a href="${tomcatDocUrl}appdev/">第一个Web应用程序</a></p>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="${tomcatDocUrl}realm-howto.html">Realms &amp; AAA</a></p>
                        <p><a href="${tomcatDocUrl}jndi-datasource-examples-howto.html">JDBC 数据源</a></p>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="${tomcatExamplesUrl}">示例</a></p>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="http://wiki.apache.org/tomcat/Specifications">Servlet 规范</a></p>
                        <p><a href="http://wiki.apache.org/tomcat/TomcatVersions">Tomcat 版本</a></p>
                    </div>
                </div>
                <br class="separator" />
            </div>
            <div id="lower">
                <div id="low-manage" class="">
                    <div class="curved container">
                        <h3>管理 Tomcat</h3>
                        <p>为了安全，访问<a href="/manager/html">Web应用程序管理页面</a>限制。
                        用户自定义如下:</p>
                        <pre>$CATALINA_HOME/conf/tomcat-users.xml</pre>
                        <p>在 Tomcat @VERSION_MAJOR_MINOR@ 中，分配不同用户对管理器应用程序的访问权限。
                            &nbsp; <a href="${tomcatDocUrl}manager-howto.html">了解更多...</a></p>
                        <br />
                        <h4><a href="${tomcatDocUrl}RELEASE-NOTES.txt">发行说明</a></h4>
                        <h4><a href="${tomcatDocUrl}changelog.html">更新日志</a></h4>
                        <h4><a href="${tomcatUrl}migration.html">迁移指南</a></h4>
                        <h4><a href="${tomcatUrl}security.html">安全通告</a></h4>
                    </div>
                </div>
                <div id="low-docs" class="">
                    <div class="curved container">
                        <h3>文档</h3>
                        <h4><a href="${tomcatDocUrl}">Tomcat @VERSION_MAJOR_MINOR@ 文档</a></h4>
                        <h4><a href="${tomcatDocUrl}config/">Tomcat @VERSION_MAJOR_MINOR@ 配置</a></h4>
                        <h4><a href="http://wiki.apache.org/tomcat/FrontPage">Tomcat Wiki</a></h4>
                        <p>在以下位置查找其他重要配置</p>
                        <pre>$CATALINA_HOME/RUNNING.txt</pre>
                        <p>开发者可能对以下内容感兴趣：</p>
                        <ul>
                            <li><a href="http://tomcat.apache.org/bugreport.html">Tomcat @VERSION_MAJOR_MINOR@ bug 数据库</a></li>
                            <li><a href="${tomcatDocUrl}api/index.html">Tomcat @VERSION_MAJOR_MINOR@ JavaDocs</a></li>
                            <li><a href="http://svn.apache.org/repos/asf/tomcat/tc@VERSION_MAJOR_MINOR@.x/">Tomcat @VERSION_MAJOR_MINOR@ SVN 仓库</a></li>
                        </ul>
                    </div>
                </div>
                <div id="low-help" class="">
                    <div class="curved container">
                        <h3>获得帮助</h3>
                        <h4><a href="${tomcatUrl}faq/">常问问题</a> 和 <a href="${tomcatUrl}lists.html">邮件列表</a></h4>
                        <p>以下邮件列表可用：</p>
                        <ul>
                            <li id="list-announce"><strong><a href="${tomcatUrl}lists.html#tomcat-announce">tomcat-announce</a><br />
                                重要公告，发布，安全漏洞通知（低等级）。</strong>
                            </li>
                            <li><a href="${tomcatUrl}lists.html#tomcat-users">tomcat-users</a><br />
                                用户支持和讨论
                            </li>
                            <li><a href="${tomcatUrl}lists.html#taglibs-user">taglibs-user</a><br />
                                <a href="${tomcatUrl}taglibs/">Apache Taglibs</a>的用户支持和讨论
                            </li>
                            <li><a href="${tomcatUrl}lists.html#tomcat-dev">tomcat-dev</a><br />
                                开发人员邮件列表，包括提交消息
                            </li>
                        </ul>
                    </div>
                </div>
                <br class="separator" />
            </div>
            <div id="footer" class="curved container">
                <div class="col20">
                    <div class="container">
                        <h4>其它下载</h4>
                        <ul>
                            <li><a href="${tomcatUrl}download-connectors.cgi">Tomcat Connectors</a></li>
                            <li><a href="${tomcatUrl}download-native.cgi">Tomcat Native</a></li>
                            <li><a href="${tomcatUrl}taglibs/">Taglibs</a></li>
                            <li><a href="${tomcatDocUrl}deployer-howto.html">Deployer</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>其它文档</h4>
                        <ul>
                            <li><a href="${tomcatUrl}connectors-doc/">Tomcat Connectors</a></li>
                            <li><a href="${tomcatUrl}connectors-doc/">mod_jk 文档</a></li>
                            <li><a href="${tomcatUrl}native-doc/">Tomcat Native</a></li>
                            <li><a href="${tomcatDocUrl}deployer-howto.html">Deployer</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>参与其中</h4>
                        <ul>
                            <li><a href="${tomcatUrl}getinvolved.html">概要</a></li>
                            <li><a href="${tomcatUrl}svn.html">SVN 仓库</a></li>
                            <li><a href="${tomcatUrl}lists.html">邮件列表</a></li>
                            <li><a href="http://wiki.apache.org/tomcat/FrontPage">Wiki</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>杂项</h4>
                        <ul>
                            <li><a href="${tomcatUrl}contact.html">联系</a></li>
                            <li><a href="${tomcatUrl}legal.html">legal</a></li>
                            <li><a href="http://www.apache.org/foundation/sponsorship.html">赞助</a></li>
                            <li><a href="http://www.apache.org/foundation/thanks.html">致谢</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col20">
                    <div class="container">
                        <h4>Apache软件基金会</h4>
                        <ul>
                            <li><a href="${tomcatUrl}whoweare.html">我们是谁</a></li>
                            <li><a href="${tomcatUrl}heritage.html">Heritage</a></li>
                            <li><a href="http://www.apache.org">Apache 主页</a></li>
                            <li><a href="${tomcatUrl}resources.html">资源</a></li>
                        </ul>
                    </div>
                </div>
                <br class="separator" />
            </div>
            <p class="copyright">Copyright &copy;1999-${year} Apache 软件基金会。 版权所有</p>
        </div>
    </body>

</html>
