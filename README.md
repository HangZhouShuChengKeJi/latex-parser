# latex-parser
latex 解析器。

# demo
```java
// 创建解析器
TexParser texParser = new TexParser(plainText);
// 解析
Atom atom = texParser.parse();
System.out.printf("%s : %s \n", i, plainText, JSON.toJSONString(atom));
```