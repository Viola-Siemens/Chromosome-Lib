# 产品需求文档：Chromosome Lib

**版本：** 0.0.1+1.20.1

**日期：** 2026-03-30

**作者：** Liu Dongyu

**状态：**

- [x] 草案
- [x] 评审中
- [x] 已批准

**问题追踪：** [https://github.com/Viola-Siemens/Chromosome-Lib/issues](https://github.com/Viola-Siemens/Chromosome-Lib/issues)

---

## 1. 描述与愿景

本文档定义了 **Chromosome Lib** 的产品需求，该模组旨在为 Minecraft 添加完整的染色体和基因系统，为模组开发者提供真实的遗传学机制，让生物繁殖和变异更具科学性和可玩性喵~

### 1.1 问题陈述

当前 Minecraft 的生物繁殖机制过于简化，仅支持简单的性状随机继承（如颜色、标记等），缺乏真实的遗传学基础喵~

模组开发者无法为自己的生物添加复杂的遗传特性，如显性/隐性基因、性状关联、基因突变、染色体交叉互换等现实生物学现象喵~

### 1.2 愿景陈述

打造一个科学、完整、易扩展的染色体和基因系统库，让 Minecraft 模组开发者能够轻松为任何生物添加真实的遗传学机制，实现孟德尔遗传定律、基因突变、染色体重组等生物学概念，丰富游戏的生物系统深度喵~

### 1.3 目标与成功指标

- **开发者采用率**：首月在 CurseForge 和 Modrinth 获得 1,000 次下载喵~
- **社区认可**：在 MCMOD 中文模组网站上获得 5 枚以上红票喵~
- **生态建设**：首年内至少有 3 个其他模组使用 Chromosome Lib 作为必要或可选前置依赖喵~
- **文档完整性**：提供完整的开发者文档和 API 示例，让模组开发者能在 2 小时内上手喵~

## 2. 用户画像与故事

### 2.1 角色 1：模组开发者张明

- **开发风格**：喜欢为自己的生物模组添加深度机制，追求真实性和科学性喵~
- **目标**：为自定义生物添加染色体和基因系统，实现复杂的遗传特性喵~
- **痛点**：原版繁殖机制过于简单，自己实现遗传学系统工作量巨大且容易出错喵~

### 2.2 角色 2：内容包制作者李华

- **使用风格**：通过数据包和资源包定制游戏内容，不编写代码喵~
- **目标**：调整原版生物的遗传特性，如增加稀有毛色的突变概率喵~
- **痛点**：无法通过数据包调整生物的遗传参数，必须依赖模组开发者喵~

### 2.3 角色 3：玩家王芳

- **游戏风格**：喜欢生物繁殖和育种，追求培育出稀有性状的生物喵~
- **目标**：通过选择性繁殖培育出理想性状的动物（如特定颜色、高属性）喵~
- **痛点**：原版繁殖机制过于随机，无法通过科学的育种策略达成目标喵~

### 2.4 用户故事

- **作为**张明，**我希望**通过简单的 API 为我的自定义生物注册染色体和基因**以便**快速实现遗传机制而不需要从头编写整个系统喵~
- **作为**张明，**我希望**系统支持复杂的基因关系（如显隐性、共显性、多基因控制）**以便**模拟真实的遗传学现象喵~
- **作为**李华，**我希望**通过配置文件调整基因突变概率和初始基因频率**以便**在不修改代码的情况下平衡游戏体验喵~
- **作为**王芳，**我希望**繁殖系统遵循孟德尔遗传定律**以便**通过选择性育种策略培育出目标性状喵~
- **作为**王芳，**我希望**低概率出现基因突变**以便**获得稀有的新性状并增加育种的趣味性喵~

## 3. 竞品分析与模组特色

<table>
  <tr>
    <th>项目名称</th>
    <th>优势</th>
    <th>劣势</th>
    <th>开源</th>
    <th>兼容 1.20+</th>
    <th>如何差异化（我们的特色）</th>
  </tr>
  <tr>
    <td>原版 Minecraft</td>
    <td>简单易懂的繁殖机制</td>
    <td>性状继承完全随机，缺乏遗传学深度</td>
    <td>×</td>
    <td>√</td>
    <td>我们实现了完整的染色体和基因系统，支持孟德尔遗传定律、基因突变、染色体交叉互换等真实遗传学机制喵~</td>
  </tr>
  <tr>
    <td>凡家物语重生（MCAR）</td>
    <td>丰富的特征和性格预设</td>
    <td>仅针对村民进行设计，缺少可扩展性；不符合真实情况，没有将特征抽象成基因，甚至支持同性繁殖</td>
    <td>√</td>
    <td>√</td>
    <td>我们提供底层库，实现了真实的遗传学机制，能够让任何模组都能使用染色体系统，且不仅限于特定生物喵~</td>
  </tr>
  <tr>
    <td>自定义实现</td>
    <td>功能可控，算法实现高度自由</td>
    <td>开发成本高，容易出现兼容性问题，难以维护</td>
    <td>N/A</td>
    <td>N/A</td>
    <td>我们提供开箱即用的解决方案，支持跨加载器（Forge/Fabric），节省开发者时间喵~</td>
  </tr>
</table>

## 4. 功能点与产品架构图

![Chromosome Lib 产品架构图](./Architecture.png)

### 4.1 原版生物遗传支持

- **描述**：为 Minecraft 原版的 20 种可繁殖生物添加完整的染色体和基因系统，让玩家可以通过科学育种培育理想性状的生物喵~
- **功能**：
	- **生物覆盖**：支持美西螈、蜜蜂、猫、鸡、牛、驴、狐狸、青蛙、山羊、马、骡子、豹猫、熊猫、鹦鹉、猪、北极熊、兔子、绵羊、海龟共 20 种生物喵~
	- **真实染色体**：每种生物拥有符合现实的染色体数量（如人类 23 对，鸡 39 对）喵~
    - **性染色体系统**：对于有性染色体系统（XY 或 ZW）的生物，进行合理的性别生成，以及实现性染色体上基因的伴性遗传喵~
	- **性状表现**：基因决定生物的外观性状（如毛色、眼色、斑纹）和行为特征（如熊猫性格、山羊尖叫）喵~

### 4.2 真实遗传学模拟

- **描述**：实现科学的遗传学机制，让生物繁殖遵循真实的生物学规律，而不是简单的随机继承喵~
- **功能**：
	- **孟德尔遗传定律**：严格遵循分离定律和自由组合定律，繁殖结果符合经典的 3:1 或 9:3:3:1 分离比喵~
	- **显性隐性关系**：支持显性基因（一个拷贝即表达）和隐性基因（需要两个拷贝才表达）喵~
	- **基因突变**：繁殖和自然生成时有低概率发生基因突变，产生稀有的新性状，增加育种趣味性喵~
	- **染色体交叉互换**：模拟减数分裂过程中的染色体交换，增加遗传多样性，让同一对父母的后代不完全相同喵~
	- **性状关联**：支持上位效应（一个基因抑制另一个基因的表达）和复杂的多基因控制性状喵~
	- **性别决定**：支持 XY 系统（如猫、马）和 ZW 系统（如鸡、鹦鹉），性别影响某些性状的遗传喵~

### 4.3 模组开发者 API

- **描述**：为模组开发者提供完整的 API 和工具，让其他模组可以轻松为自定义生物添加染色体和基因系统喵~
- **功能**：
	- **染色体注册 API**：简单的 API 为任何实体类型注册染色体，支持常染色体和性染色体喵~
	- **基因和性状注册 API**：为染色体上的座位注册基因，定义基因与性状的映射关系喵~
	- **性状应用事件**：提供事件回调，在性状确定后修改生物的属性、外观、AI 行为等喵~
	- **完整文档**：所有公共 API 都提供详细的 Javadoc 文档，方便开发者理解和使用喵~
	- **示例代码**：内置 20 种原版生物的完整实现作为参考，开发者可以直接复制和修改喵~
	- **跨平台支持**：同时支持 Forge 和 Fabric 两大主流加载器，一次开发，多平台运行喵~
	- **调试工具**：提供日志工具和测试框架，帮助开发者验证遗传机制的正确性喵~

### 4.4 可配置的遗传参数

- **描述**：支持通过配置文件调整遗传参数，让内容包制作者可以在不修改代码的情况下调整游戏体验喵~
- **功能**：
	- **数据包支持**：支持通过数据包定义新的基因和性状，无需编写代码喵~
    - **KubeJS 支持**：支持通过 KubeJS 使用 JavaScript 低代码脚本定义新的基因和性状，方便整合包作者进行调整喵~

## 5. 功能优先级

<table>
  <tr>
    <th>模块</th>
    <th>子模块</th>
    <th>优先级</th>
    <th>状态</th>
  </tr>
  <tr>
    <td rowspan="4"><b>原版生物遗传支持</b></td>
    <td>基础生物支持（猫、狐狸、兔子、羊等常见生物）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>扩展生物支持（美西螈、熊猫、鹦鹉等特殊生物）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>性染色体系统（XY/ZW 性别决定）</td>
    <td>P3</td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>性状表现（毛色、眼色、行为等）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td rowspan="6"><b>真实遗传学模拟</b></td>
    <td>孟德尔遗传定律（分离定律和自由组合定律）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>显性隐性关系</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>基因突变</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>染色体交叉互换</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>性状关联和上位效应</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>性别决定机制</td>
    <td>P3</td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td rowspan="7"><b>模组开发者 API</b></td>
    <td>染色体注册 API</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>基因和性状注册 API</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>性状应用事件回调</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>完整 Javadoc 文档</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>示例代码（20 种原版生物）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>跨平台支持（Forge + Fabric）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td>调试工具和测试框架</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox" checked/></td>
  </tr>
  <tr>
    <td rowspan="2"><b>可配置的遗传参数</b></td>
    <td>数据包定义基因和性状</td>
    <td>P3</td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>KubeJS 定义基因和性状</td>
    <td>P3</td>
    <td><input type="checkbox"/></td>
  </tr>
</table>

## 6. 未来路线图

### 6.1 完善核心功能

- [ ] 完善开发者文档和教程 - <span style="color:red;font-weight:900">P0</span>
- [x] 添加更多内置生物支持 - <span style="color:red;font-weight:900">P0</span>

### 6.2 增强可扩展性

- [ ] 支持数据包定义基因和性状 - <span style="color:deepskyblue">P2</span>
- [ ] 支持 KubeJS 定义基因和性状 - P3
- [x] 添加命令行工具查看生物基因型 - <span style="color:orchid;font-weight:600">P1</span>
- [ ] 支持基因编辑物品（创造模式工具） - P3
- [ ] 添加基因图谱 GUI（调试界面） - P3

### 6.3 高级遗传学特性

- [ ] 支持多倍体（四倍体、六倍体等） - <span style="color:gray;font-weight:50">P4</span>
- [ ] 支持染色体畸变（缺失、重复、倒位、易位） - <span style="color:gray;font-weight:50">P4</span>
- [ ] 支持表观遗传学（基因甲基化、组蛋白修饰） - <span style="color:gray;font-weight:50">P4</span>
- [ ] 支持线粒体 DNA 母系遗传 - <span style="color:gray;font-weight:50">P4</span>

### 6.4 社区支持

- [ ] 完整的单元测试覆盖 - <span style="color:orchid;font-weight:600">P1</span>
- [ ] 性能优化和内存占用优化 - <span style="color:deepskyblue">P2</span>
- [ ] 完善的 Wiki 文档 - <span style="color:orchid;font-weight:600">P1</span>
- [ ] 支持更多 Minecraft 版本（1.20.x、1.21.x、26.x） - <span style="color:orchid;font-weight:600">P1</span>

## 7. 术语表

| 术语 | 英文 | 解释 |
|------|------|------|
| 染色体 | Chromosome | 基因的载体，成对存在于生物体内喵~ |
| 基因座位 | Gene Locus | 基因在染色体上的位置喵~ |
| 基因/等位基因 | Gene/Allele | 遗传信息的基本单位，决定性状喵~ |
| 性状 | Trait | 基因的表型表现，如毛色、体型等喵~ |
| 基因型 | Genotype | 生物体的基因组成喵~ |
| 表型 | Phenotype | 生物体外在表现的性状喵~ |
| 显性基因 | Dominant Gene | 只需一个拷贝就能表达的基因喵~ |
| 隐性基因 | Recessive Gene | 需要两个拷贝才能表达的基因喵~ |
| 同源染色体 | Homologous Chromosomes | 成对存在的相同类型染色体喵~ |
| 性染色体 | Sex Chromosome | 决定性别的染色体（如 X/Y）喵~ |
| 常染色体 | Autosome | 非性染色体喵~ |
| 交叉互换 | Crossing Over | 减数分裂时染色体间交换基因片段喵~ |
| 基因突变 | Gene Mutation | 基因序列发生改变，产生新等位基因喵~ |
| 孟德尔遗传定律 | Mendel's Laws | 分离定律和自由组合定律喵~ |
| 上位效应 | Epistasis | 一个基因抑制另一个基因的表达喵~ |

## 8. 附录：已支持的原版生物

| 实体类型 | 英文名 | 染色体数量 | 性染色体系统 | 备注 |
|---------|--------|-----------|-------------|------|
| 美西螈 | Axolotl | 14 | XY | 支持 5 种颜色变体喵~ |
| 蜜蜂 | Bee | 16 | 单倍体/双倍体 | 工蜂为单倍体，蜂后为双倍体喵~ |
| 猫 | Cat | 19 | XY | 支持多种毛色和眼色喵~ |
| 鸡 | Chicken | 39 | ZW | 母鸡 ZW，公鸡 ZZ 喵~ |
| 牛 | Cow | 30 | XY | 支持普通牛和哞菇喵~ |
| 驴 | Donkey | 31 | XY | 可与马杂交产生骡子喵~ |
| 狐狸 | Fox | 17 | XY | 支持红狐和雪狐喵~ |
| 青蛙 | Frog | 13 | XY | 支持 3 种温度变体喵~ |
| 山羊 | Goat | 30 | XY | 支持尖叫山羊喵~ |
| 马 | Horse | 32 | XY | 可与驴杂交产生骡子喵~ |
| 人类 | Human | 23 | XY | 扩展性实体（需其他模组）喵~ |
| 骡子 | Mule | 31.5 | XY | 马驴杂交，不育喵~ |
| 豹猫 | Ocelot | 18 | XY | 与猫有相似基因喵~ |
| 熊猫 | Panda | 21 | XY | 支持多种性格类型喵~ |
| 鹦鹉 | Parrot | 40 | ZW | 支持 5 种颜色变体喵~ |
| 猪 | Pig | 19 | XY | 普通猪和变异猪喵~ |
| 北极熊 | Polar Bear | 37 | XY | 大型陆生动物喵~ |
| 兔子 | Rabbit | 22 | XY | 支持多种毛色和杀手兔喵~ |
| 绵羊 | Sheep | 27 | XY | 支持 16 种颜色喵~ |
| 海龟 | Turtle | 28 | XY | 海洋爬行动物喵~ |

**注**：染色体数量和性染色体系统参考现实生物学数据，但为了游戏平衡和可玩性做了适当简化喵~