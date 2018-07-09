# 贡献 Apache Tomcat

首先，感谢您的贡献！ 我希望这对你来说是一个愉快的第一次体验，并且你会继续贡献。

请访问我们的 [加入页面](http://tomcat.apache.org/getinvolved.html)
了解更多关于如何贡献的信息。

## 行为守则

该项目和参与其中的每个人都受Apache软件基金会[行为准则](http://www.apache.org/foundation/policies/conduct.html) 的管辖。 
通过参与，您需要遵守此代码(code)。如果您发现不可接受的行为，请访问
[报告指南页面](http://www.apache.org/foundation/policies/conduct.html#reporting-guidelines)，
并按照说明进行操作。

## 我可以如何贡献？

我们收到的大部分贡献都是代码贡献，但您也可以贡献给文档，wiki等，或只是报告可供我们修复的坚实(solid)的缺陷。

### 报告 bug

请查阅我们的[指南](http://tomcat.apache.org/bugreport.html)，了解如何提交错误报告。 
此页面还包含指向其他资源的链接以帮助您。

### 你的第一个代码贡献

### 难以决定如何贡献？

不确定从哪里开始贡献Tomcat？ 你可以先看一下标有“beginner”的问题(issues)，连接就在下面。
请注意，初学者关键字对于项目来说是最新的，所以如果在filter中没有任何问题的解答，
请随意在[dev list](http://tomcat.apache.org/lists.html#tomcat-dev)上提问。

* [Beginner issues](https://bz.apache.org/bugzilla/buglist.cgi?bug_status=NEW&bug_status=ASSIGNED&bug_status=REOPENED&bug_status=NEEDINFO&keywords=Beginner&keywords_type=allwords&list_id=160824&product=Tomcat%207&product=Tomcat%208&product=Tomcat%209&query_format=advanced) -
issues应该只需要几行代码，和一两个测试就可以解决.

这个列表展示了所有标记了‘Beginner’的bug，并且当前支持所有的Tomcat版本（7，8，9）。

如果比起java你更喜欢C，那么你可以查看Bugzilla中的tomcat-native和Tomcat Connectors 产品。

### 如何提供您的第一个补丁(Patch)

兴奋吗？ 本节将指导您为项目的提交者(committers)提供一个补丁(patch)以供评审和接受(acceptance)。

##### 选择你的提交方式

您可以通过以下方式之一提供补丁（按优先顺序）：

* 将补丁附件到Bugzilla问题下面
* Github Pull 请求
> **注意：** Github是Tomcat存储在SVN存储库中的镜像，因此无法完全合并。 
你的贡献将被转换成SVN补丁，并提交你的名字作为荣誉。
* 用电子邮件将补丁发送到开发者列表。 这不是首选，但是如果补丁与bug没有关系。
或者您希望开发人员检查，则可能需要发送电子邮件。

##### 获取源代码

你已经选择了要提交补丁的方式，现在需要获取源代码。

###### 从 Source Distribution 下载

如果你想提交一个补丁（就像你在SVN上做的那样），这个方法有效，
但使用sources distribution和VCS的区别在于您必须使用diff手动生成补丁文件。
如果这是您想要的，您可以从[下载页面](https://tomcat.apache.org/download-90.cgi)的“Source Code Distributions”部分下载源代码。

###### SVN

如果您选择将补丁附加到Bugzilla问题下面（或通过电子邮件发送），那么您需要签出SVN版本库里面的代码。
有关新提交者如何执行此操作的说明，请参见[此处](http://www.apache.org/dev/contributors.html#svnbasics)。
但是，为了快速迭代，短版本如下。 请注意，SVN存储库的根目录是[tomcat / trunk](http://svn.apache.org/repos/asf/tomcat/trunk),
但您也可以克隆特定版本，例如
[tc8.5.x](http://svn.apache.org/repos/asf/tomcat/tc8.5.x/trunk/) 或者其他tag (
[TOMCAT_8_5_15](http://svn.apache.org/repos/asf/tomcat/tc8.5.x/tags/TOMCAT_8_5_15/)).

```
$ svn co http://svn.apache.org/repos/asf/tomcat/trunk/
```

##### Github

对于Github来说，它几乎是一样的。 选择你想要的主要版本（现在他们在不同的存储库中），
fork存储库，然后克隆你fork的仓库来完成这项工作。

```
$ git clone https://github.com/$USERNAME/tomcat.git
```

#### 提交你的补丁！

在您选择完提交方法，检索源代码并修复问题之后，就可以提交你的完成的工作了。 
此时，只需按照您之前选择的提交方法进行操作即可。

* Bugzilla附件 - 将SVN补丁附加到Bugzilla问题下面  
* 在您的本地分支中解决问题并推送到您的存储库副本后，打开Github PR进行审核(review)。  
* 电子邮件 - 再次提醒，不是首选，但您可以发送电子邮件到开发人员列表，附带补丁以供审核(review)。

#### 等待反馈(feedback)

这可能需要一段时间用来提交审查。 在此期间请耐心等待，因为所有提交者都是项目的志愿者。
如果自您提交以来已经过了很长时间（例如几个月），
请随时更新您的BZ、PR或通过电子邮件向开发者列表发送消息以解决您的问题。
有时事情会在所有工作中丢失，我们需要有人提醒 :smile:

## 代码风格指南

Apache Tomcat有非常松散的编码约定，但以下指南将非常有用：

* 使用空格进行缩进，而不是制表符
* Java源代码每行不超过100个字符，文档源代码每行不超过80个字符（.txt，.xml）
* Java源代码：{ 在行尾，4个空格缩进
* XML源代码：2个空格缩进

## 我们遗漏了什么吗?

你看完这份指南，发现它遗漏了什么吗？或者您对某个特定步骤感到困惑？
如果是这样，请告诉我们！ 或者更好的办法是，提交PR来解决问题 :wink:
