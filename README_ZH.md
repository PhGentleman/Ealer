# Ealer

### Ealer：亦乐-乐人之乐，人亦乐其乐；忧人之忧，人亦忧其忧。
- 携带多标签图像分类模型的图像分享网站
- 用户可对上传的图像执行标签预测，发现潜在标签
- 只是个人的学习作品



### 如何配置

1. 按照根目录中ealer.sql新建MySQL数据库
2. 配置Prototype中resources下的jdbc.properties和log4j2.xml
3. 使用阿里云OSS新建bucket
4. 配置utils中的OssUtil
5. 根据需要改变预测数据集或模型时需要自行寻求方案
6. Spring和Flask需要同时启动

Python3.8
