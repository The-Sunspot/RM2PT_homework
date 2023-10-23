SY2321101 曹胜华

## 系统介绍

选取了一个折扣商城系统的建模设计。同普通的商城区别是1）折扣商城用户需要主动申请准入资质并由管理员审核。因此User泛化出了QualifiedUser这一Actor。2）商品限时销售且严格限量，因此需要Admin提前规定每次促销活动的活动费率，覆盖商品名单，库存等。

## 建模结果

![image-20231023231434965](https://raw.githubusercontent.com/The-Sunspot/IMAGE/main/image-20231023231434965.png)

![image-20231023211630101](https://raw.githubusercontent.com/The-Sunspot/IMAGE/main/image-20231023211630101.png)



![img](https://raw.githubusercontent.com/The-Sunspot/IMAGE/main/8Z9U3207%25IQK%5B7%7B%7EDEA%60W6M.png)



**## 模型规模**

用例：Actor 3，用例9

顺序图：顺序图7，系统操作总数13，合约12

类图：类9

自然语言建模：最外层uesr story 9，Flow中的User和System 10+