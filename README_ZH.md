# Elear

### Ealer：亦乐
- 乐人之乐，人亦乐其乐；忧人之忧，人亦忧其忧。——白居易《辨兴亡之由策》

- 携带多标签图像分类模型的图像分享网站 UTF-8
- 用户可对上传的图像执行标签预测，发现潜在标签



### 如何配置

1. 按照根目录中ealer.sql新建MySQL数据库
2. 配置Prototype中resources下的jdbc.properties和log4j2.xml
3. 使用阿里云OSS新建bucket
4. 配置utils中的OssUtil
5. 选择需求的训练集训练预测模型后使用或使用已训练模型检验

