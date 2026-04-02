# 产品需求文档：Chromosome Lib

**版本：** 0.1.0+1.20.1

**日期：** 2026-03-30

**作者：** Liu Dongyu

**状态：**

- [x] 草案
- [x] 评审中
- [x] 已批准

**问题追踪：** [https://github.com/Viola-Siemens/Chromosome-Lib/issues](https://github.com/Viola-Siemens/Chromosome-Lib/issues)

---

## 1. 描述与愿景

本文档定义了 **Chromosome Lib v0.1.0** 的产品需求，该版本在 v0.0.1 的基础上，新增完整的性别表现系统和禁止同性繁殖机制，进一步增强遗传系统的科学性和真实性喵~

### 1.1 问题陈述

尽管 v0.0.1 已经实现了基础的染色体和基因系统，但仍存在以下关键问题喵~：

1. **性别不可见**：虽然实现了遗传学机制，但生物的性别在游戏中完全不可见，玩家无法直观感知生物的性别信息喵~
2. **繁殖逻辑不真实**：任意两只同种生物都可以繁殖，不符合真实生物学规律，也与竞品 MCA Reborn 的问题（支持同性繁殖）雷同喵~
3. **性染色体系统未激活**：虽然已在代码中定义了性染色体（XY/ZW），但性别决定机制未完全实现，伴性遗传功能无法发挥作用喵~
4. **缺乏第三方模组集成**：无法与玩家常用的信息展示模组（如 Jade、WTHIT）集成，导致性别信息难以获取喵~

### 1.2 愿景陈述

通过实现完整的性别表现系统和科学的繁殖限制机制，让 Minecraft 生物的遗传学模拟更加真实和完整，为玩家提供更具深度的育种体验，同时为模组开发者提供全面的性别相关 API 喵~

### 1.3 目标与成功指标

- **功能完整性**：实现所有 P0 和 P1 功能，通过所有集成测试用例喵~
- **视觉表现**：至少为 6 种典型生物（鸡、马、牛、猫、羊、兔）实现性别二态性的视觉差异喵~
- **玩家反馈**：在测试版发布后，收集玩家反馈，好评率达到 85% 以上喵~
- **开发者采用**：为模组开发者提供性别相关 API，至少有 1 个第三方模组在 v0.1.0 发布后的 3 个月内集成该功能喵~
- **兼容性**：与 Jade、WTHIT 两大信息展示模组完全兼容，支持无缝显示性别信息喵~

## 2. 用户画像与故事

### 2.1 角色 1：模组开发者张明

- **开发风格**：追求科学性和真实性，希望为自定义生物添加完整的性别系统喵~
- **目标**：为自定义生物实现性别二态性（雌雄外观差异）和性别限制的繁殖机制喵~
- **痛点**：现有系统缺乏性别表现功能，无法实现性别可视化，也无法限制同性繁殖喵~
- **需求场景**：
	- 为自定义生物注册性染色体（XY 或 ZW 系统）喵~
	- 根据性别基因为生物应用不同的模型和纹理喵~
	- 实现伴性遗传特征（如色盲基因位于 X 染色体）喵~

### 2.2 角色 2：整合包制作者李华

- **使用风格**：通过资源包和少量配置调整游戏内容喵~
- **目标**：为原版生物添加性别视觉差异，让玩家能够一眼识别生物性别喵~
- **痛点**：即使模组支持性别系统，也无法通过简单的资源包调整实现性别视觉差异喵~
- **需求场景**：
	- 通过替换纹理文件为雄性和雌性生物设置不同的外观喵~
	- 调整特定性别的生物模型大小（如雄性体型更大）喵~

### 2.3 角色 3：育种玩家王芳

- **游戏风格**：喜欢通过育种培育理想性状的生物，追求真实的育种体验喵~
- **目标**：通过识别生物性别进行选择性繁殖，培育出符合预期的后代喵~
- **痛点**：无法直观识别生物性别，导致育种策略难以实施；同性生物也能繁殖，破坏了真实感喵~
- **需求场景**：
	- 通过外观差异快速识别生物性别（如公鸡有鸡冠，母鸡无鸡冠）喵~
	- 使用 Jade 模组悬停查看生物的详细性别信息喵~
	- 尝试同性繁殖时，生物拒绝进入繁殖模式，让玩家意识到需要一雄一雌喵~

### 2.4 角色 4：科普内容创作者赵刚

- **创作风格**：制作 Minecraft 遗传学科普视频，向玩家讲解孟德尔遗传定律喵~
- **目标**：使用 Chromosome Lib 演示真实的遗传学现象，如伴性遗传、性别决定机制喵~
- **痛点**：缺乏性别可视化，难以向观众展示性别相关的遗传学概念喵~
- **需求场景**：
	- 演示 XY 性别决定系统：父亲提供 X 或 Y，母亲提供 X 喵~
	- 演示伴性遗传：色盲基因位于 X 染色体，雄性更易患病喵~
	- 使用 Jade 模组展示生物的性染色体组成（XX、XY、ZZ、ZW）喵~

### 2.5 用户故事

- **作为**张明，**我希望**通过 API 为自定义生物注册性别系统**以便**实现雌雄异形和性别限制的繁殖机制喵~
- **作为**张明，**我希望**系统支持伴性遗传**以便**模拟 X 染色体上的基因遗传规律喵~
- **作为**李华，**我希望**通过资源包为不同性别的生物设置不同纹理**以便**玩家能够直观识别性别喵~
- **作为**王芳，**我希望**通过生物的外观差异快速识别性别**以便**实施科学的育种策略喵~
- **作为**王芳，**我希望**同性生物无法繁殖**以便**获得更真实的游戏体验喵~
- **作为**赵刚，**我希望**通过 Jade 模组查看生物的性染色体信息**以便**向观众展示性别决定机制喵~

## 3. 竞品分析与模组特色

<table>
  <tr>
    <th>项目名称</th>
    <th>性别表现</th>
    <th>繁殖限制</th>
    <th>科学准确性</th>
    <th>如何差异化（我们的特色）</th>
  </tr>
  <tr>
    <td>原版 Minecraft</td>
    <td>无性别概念</td>
    <td>任意两只同种生物可繁殖</td>
    <td>低</td>
    <td>我们实现了完整的性别表现系统，包括性染色体、性别二态性和科学的繁殖限制喵~</td>
  </tr>
  <tr>
    <td>凡家物语重生（MCAR）</td>
    <td>村民有性别标识</td>
    <td>支持同性繁殖（不科学）</td>
    <td>低</td>
    <td>我们基于真实遗传学机制实现性别系统，严格禁止同性繁殖，符合生物学规律喵~</td>
  </tr>
  <tr>
    <td>Alex's Mobs</td>
    <td>部分生物有性别差异</td>
    <td>部分生物限制同性繁殖</td>
    <td>中</td>
    <td>我们提供通用的性别系统库，支持任何生物，而非仅限于特定模组的生物喵~</td>
  </tr>
  <tr>
    <td>Genetics Reborn</td>
    <td>无性别表现</td>
    <td>无繁殖限制</td>
    <td>中（基因系统）</td>
    <td>我们在基因系统的基础上增加了性别表现和繁殖限制，更加全面喵~</td>
  </tr>
</table>

## 4. 功能点与产品架构图

### 4.1 性别决定系统

- **描述**：基于性染色体（XY 或 ZW）实现科学的性别决定机制，让每只生物在出生时都拥有明确的性别喵~
- **功能**：
	- **XY 性别决定系统**：适用于哺乳动物（猫、马、牛、羊、兔、狐狸、熊猫等），雄性 XY，雌性 XX，后代性别由父亲的精子决定（50% X，50% Y）喵~
	- **ZW 性别决定系统**：适用于鸟类（鸡、鹦鹉），雄性 ZZ，雌性 ZW，后代性别由母亲的卵细胞决定（50% Z，50% W）喵~
	- **单倍体/双倍体系统**：适用于蜜蜂，雄蜂为单倍体（未受精卵发育），蜂后和工蜂为双倍体（受精卵发育），工蜂无性别喵~
	- **性别基因**：性别信息存储在染色体实例中，通过性染色体类型（X/Y 或 Z/W）确定，可通过 API 查询喵~
	- **性别生成**：繁殖时，父母双方各提供一条性染色体，遵循孟德尔遗传定律（50:50 比例）；自然生成的生物性别随机（50:50）喵~

### 4.2 性别视觉表现

- **描述**：通过模型和纹理差异让玩家能够直观识别生物的性别，实现性别二态性喵~
- **功能**：
	- **纹理差异**：为不同性别的生物加载不同的纹理文件，如公鸡使用鲜艳的红色鸡冠纹理，母鸡使用暗淡的灰色纹理喵~
	- **模型差异**：为特定生物调整模型（如公鸡的鸡冠更高，雄性马的体型更大），通过几何模型体现性别差异喵~
	- **资源包支持**：支持通过资源包替换性别纹理和模型，让整合包制作者可以自定义性别外观喵~
	- **优先实现的生物**：
		- **鸡**：公鸡有高大的红色鸡冠和鲜艳的羽毛，母鸡鸡冠较小且颜色暗淡喵~
		- **马**：雄性体型更大，颈部肌肉更发达喵~
		- **牛**：公牛体型更大，角更粗壮喵~
		- **猫**：某些品种的猫有性别相关的毛色差异（如橘猫通常为雄性）喵~
		- **羊**：公羊有更大的角喵~
		- **兔**：雄性体型稍大喵~

### 4.3 第三方模组集成

- **描述**：与玩家常用的信息展示模组集成，让性别信息可以通过 HUD 直观展示喵~
- **功能**：
	- **Jade 模组集成**：当玩家将鼠标悬停在生物上时，Jade 的 HUD 中显示生物的性别信息（如"性别: 雄性"或"性别: 雌性"）喵~
	- **WTHIT 模组集成**：与 WTHIT（What The Hell Is That）模组集成，提供类似的 HUD 显示功能喵~
	- **The One Probe 集成**：支持 The One Probe 模组，让玩家通过探测器查看性别信息喵~
	- **可配置显示**：提供配置选项，让玩家选择是否在 HUD 中显示性别信息，以及显示格式（文字、图标等）喵~
	- **性染色体信息**：高级模式下，HUD 还可以显示生物的性染色体组成（如 XX、XY、ZZ、ZW），便于科普和教学喵~

### 4.4 禁止同性繁殖

- **描述**：基于性别系统实现科学的繁殖限制，只有一雄一雌才能繁殖，提高游戏的真实性喵~
- **功能**：
	- **性别验证**：在繁殖触发前，验证两只生物的性别，只有雌雄配对才能进入繁殖模式喵~
	- **交互反馈**：当玩家喂食了两只同性生物，且没有异性生物进入求偶状态时，生物会接受繁殖食物（心形粒子效果），并偶尔产生黑色粒子效果（与狼驯化失败粒子相同），不会进入繁殖模式，也不会产生后代喵~
    - **进度提示**：当一个生物进入求偶状态，并在状态结束前检测到**周围有同样进入求偶状态的同性生物**，但**没有**检测到任何进入求偶状态的**异性生物**，那么在求偶状态超时结束时，触发玩家【嘿，小查，那是公狗！】（致敬 GTA 5 富兰克林台词）进度的触发器喵~
	- **无额外提示**：不显示聊天消息或其他明确提示，让玩家通过观察生物行为自行发现问题，更符合真实情况喵~
	- **特殊情况处理**：
		- **蜜蜂**：现实中一个蜂巢中的雌性蜜蜂只有一个蜂后和多个工蜂，而工蜂不育，但为了简化问题，不会限制蜂巢雌雄比例，且任何雌蜂都可以与雄蜂繁殖喵~
		- **骡子**：骡子是马和驴的杂交后代，本身不育，不受性别限制影响喵~
	- **兼容性**：与原版繁殖机制完全兼容，不影响其他模组的自定义繁殖逻辑喵~

### 4.5 伴性遗传机制

- **描述**：实现位于性染色体上的基因的特殊遗传规律，丰富遗传学模拟的深度喵~
- **功能**：
	- **X 连锁遗传**：位于 X 染色体上的基因，雄性（XY）只需一个隐性等位基因即可表达，雌性（XX）需要两个隐性等位基因才能表达喵~
	- **Z 连锁遗传**：位于 Z 染色体上的基因，雌性（ZW）只需一个隐性等位基因即可表达，雄性（ZZ）需要两个隐性等位基因才能表达喵~
	- **Y 染色体基因**：位于 Y 染色体上的基因仅在雄性中表达，且只能从父亲传给儿子喵~
	- **W 染色体基因**：位于 W 染色体上的基因仅在雌性中表达，且只能从母亲传给女儿喵~
	- **应用示例**：
		- **猫的橘色毛色**：位于 X 染色体上，雄性橘猫比雌性橘猫更常见（符合现实）喵~
		- **鸡的羽毛颜色**：位于 Z 染色体上，某些羽毛颜色基因在雌性中更易表达喵~

### 4.6 开发者 API 扩展

- **描述**：为模组开发者提供性别相关的 API，让其他模组可以轻松集成性别系统喵~
- **功能**：
	- **性别查询 API**：提供方法查询任意实体的性别（雄性、雌性、无性别）喵~
	- **性染色体注册 API**：为自定义生物注册性染色体类型（XY、ZW、单倍体/双倍体）喵~
	- **性别事件回调**：在性别确定后触发事件，让开发者可以根据性别调整生物的属性、外观、AI 行为喵~
	- **繁殖限制 API**：提供方法自定义繁殖限制逻辑，支持特殊生物的非常规繁殖规则喵~
	- **性别二态性 API**：提供方法根据性别动态切换纹理和模型，简化性别视觉差异的实现喵~

## 5. 功能优先级

<table>
  <tr>
    <th>模块</th>
    <th>子模块</th>
    <th>优先级</th>
    <th>状态</th>
  </tr>
  <tr>
    <td rowspan="5"><b>性别决定系统</b></td>
    <td>XY 性别决定系统（哺乳动物）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>ZW 性别决定系统（鸟类）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>性别基因存储和查询 API</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>繁殖时性别生成（50:50 比例）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>单倍体/双倍体系统（蜜蜂）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td rowspan="4"><b>性别视觉表现</b></td>
    <td>纹理差异（6 种典型生物：鸡、马、牛、猫、羊、兔）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>模型差异（体型、鸡冠等）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>资源包支持（自定义性别纹理）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>扩展到其他生物（14 种剩余生物）</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td rowspan="5"><b>第三方模组集成</b></td>
    <td>Jade 模组集成（HUD 显示性别）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>WTHIT 模组集成</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>The One Probe 集成</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>可配置 HUD 显示选项</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>性染色体信息显示（高级模式）</td>
    <td>P3</td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td rowspan="4"><b>禁止同性繁殖</b></td>
    <td>繁殖前性别验证</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>交互反馈（接受食物但不繁殖）</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>特殊情况处理（蜜蜂、骡子等）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>兼容性测试（与其他模组的繁殖逻辑兼容）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td rowspan="4"><b>伴性遗传机制</b></td>
    <td>X 连锁遗传（如猫的橘色毛色）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>Z 连锁遗传（如鸡的羽毛颜色）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>Y 染色体基因（仅雄性表达）</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>W 染色体基因（仅雌性表达）</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td rowspan="5"><b>开发者 API 扩展</b></td>
    <td>性别查询 API</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>性染色体注册 API</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>性别事件回调</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>繁殖限制 API（自定义繁殖规则）</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
    <td><input type="checkbox"/></td>
  </tr>
  <tr>
    <td>性别二态性 API（动态纹理/模型切换）</td>
    <td><span style="color:deepskyblue">P2</span></td>
    <td><input type="checkbox"/></td>
  </tr>
</table>

## 6. 未来路线图

### 6.1 完善性别系统

- [ ] 添加性别相关的行为差异（如雄性更具攻击性） - <span style="color:deepskyblue">P2</span>
- [ ] 支持雌雄同体生物（如蜗牛、蚯蚓） - P3

### 6.2 增强第三方集成

- [ ] 集成更多信息展示模组（如 Hwyla、Waila） - <span style="color:deepskyblue">P2</span>
- [ ] 提供性别图标资源包（让 HUD 显示♂♀图标） - P3
- [ ] 支持通过聊天栏查询生物性别（命令行工具） - P3

### 6.3 高级遗传学特性

- [ ] 支持性别转换机制（如青蛙、鱼类在特定条件下可变性） - P3
- [ ] 支持多性别系统（如三性、多性） - <span style="color:gray;font-weight:50">P4</span>
- [ ] 支持性别决定的温度依赖（如海龟、鳄鱼） - <span style="color:gray;font-weight:50">P4</span>

### 6.4 文档与社区

- [ ] 编写性别系统开发者文档和教程 - <span style="color:red;font-weight:900">P0</span>
- [ ] 制作性别系统科普视频（面向玩家） - <span style="color:orchid;font-weight:600">P1</span>
- [ ] 完善 Wiki 文档，添加性别系统章节 - <span style="color:orchid;font-weight:600">P1</span>
- [ ] 收集玩家反馈，优化性别视觉表现 - <span style="color:orchid;font-weight:600">P1</span>

## 7. 术语表

| 术语 | 英文 | 解释 |
|------|------|------|
| 性别 | Sex | 生物的生理性别，由性染色体决定喵~ |
| 性染色体 | Sex Chromosome | 决定性别的染色体，如 X/Y 或 Z/W 喵~ |
| XY 系统 | XY System | 雄性为 XY，雌性为 XX 的性别决定系统，常见于哺乳动物喵~ |
| ZW 系统 | ZW System | 雄性为 ZZ，雌性为 ZW 的性别决定系统，常见于鸟类喵~ |
| 性别二态性 | Sexual Dimorphism | 同种生物的雌雄个体在外观、体型、行为等方面的差异喵~ |
| 伴性遗传 | Sex-linked Inheritance | 位于性染色体上的基因的遗传规律，与性别相关喵~ |
| X 连锁遗传 | X-linked Inheritance | 位于 X 染色体上的基因的遗传，雄性更易表达隐性性状喵~ |
| Z 连锁遗传 | Z-linked Inheritance | 位于 Z 染色体上的基因的遗传，雌性更易表达隐性性状喵~ |
| 单倍体 | Haploid | 只含有一套染色体的生物体，如雄蜂喵~ |
| 双倍体 | Diploid | 含有两套染色体的生物体，如蜂后和工蜂喵~ |
| 雌雄异形 | Sexual Dimorphism | 同种生物的雌雄个体在形态上的差异喵~ |
| 同性繁殖 | Same-Sex Breeding | 两只相同性别的生物尝试繁殖，不符合生物学规律喵~ |

## 8. 附录：性别系统覆盖的原版生物

<table>
  <tr>
    <th>实体类型</th>
    <th>英文名</th>
    <th>性别系统</th>
    <th>性别二态性</th>
    <th>优先级</th>
  </tr>
  <tr>
    <td>鸡</td>
    <td>Chicken</td>
    <td>ZW</td>
    <td>公鸡有高大的红色鸡冠，母鸡鸡冠较小且暗淡喵~</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
  </tr>
  <tr>
    <td>马</td>
    <td>Horse</td>
    <td>XY</td>
    <td>雄性体型更大，颈部肌肉更发达喵~</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
  </tr>
  <tr>
    <td>牛</td>
    <td>Cow</td>
    <td>XY</td>
    <td>公牛体型更大，角更粗壮喵~</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
  </tr>
  <tr>
    <td>猫</td>
    <td>Cat</td>
    <td>XY</td>
    <td>橘色毛色为 X 连锁，雄性橘猫比雌性常见喵~</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
  </tr>
  <tr>
    <td>羊</td>
    <td>Sheep</td>
    <td>XY</td>
    <td>公羊有更大的角喵~</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
  </tr>
  <tr>
    <td>兔</td>
    <td>Rabbit</td>
    <td>XY</td>
    <td>雄性体型稍大喵~</td>
    <td><span style="color:red;font-weight:900">P0</span></td>
  </tr>
  <tr>
    <td>蜜蜂</td>
    <td>Bee</td>
    <td>单倍体/双倍体</td>
    <td>工蜂无性别，雄蜂和蜂后有性别喵~</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
  </tr>
  <tr>
    <td>鹦鹉</td>
    <td>Parrot</td>
    <td>ZW</td>
    <td>某些羽毛颜色为 Z 连锁喵~</td>
    <td><span style="color:orchid;font-weight:600">P1</span></td>
  </tr>
  <tr>
    <td>狐狸</td>
    <td>Fox</td>
    <td>XY</td>
    <td>雄性体型稍大喵~</td>
    <td><span style="color:deepskyblue">P2</span></td>
  </tr>
  <tr>
    <td>猪</td>
    <td>Pig</td>
    <td>XY</td>
    <td>公猪体型更大喵~</td>
    <td><span style="color:deepskyblue">P2</span></td>
  </tr>
  <tr>
    <td>驴</td>
    <td>Donkey</td>
    <td>XY</td>
    <td>雄性体型稍大喵~</td>
    <td><span style="color:deepskyblue">P2</span></td>
  </tr>
  <tr>
    <td>青蛙</td>
    <td>Frog</td>
    <td>XY</td>
    <td>雄性有更大的声囊喵~</td>
    <td><span style="color:deepskyblue">P2</span></td>
  </tr>
  <tr>
    <td>山羊</td>
    <td>Goat</td>
    <td>XY</td>
    <td>公羊有更大的角喵~</td>
    <td><span style="color:deepskyblue">P2</span></td>
  </tr>
  <tr>
    <td>熊猫</td>
    <td>Panda</td>
    <td>XY</td>
    <td>性别差异不明显喵~</td>
    <td>P3</td>
  </tr>
  <tr>
    <td>豹猫</td>
    <td>Ocelot</td>
    <td>XY</td>
    <td>性别差异不明显喵~</td>
    <td>P3</td>
  </tr>
  <tr>
    <td>北极熊</td>
    <td>Polar Bear</td>
    <td>XY</td>
    <td>雄性体型更大喵~</td>
    <td>P3</td>
  </tr>
  <tr>
    <td>海龟</td>
    <td>Turtle</td>
    <td>XY（温度依赖）</td>
    <td>性别差异不明显，未来可支持温度决定性别喵~</td>
    <td>P3</td>
  </tr>
  <tr>
    <td>美西螈</td>
    <td>Axolotl</td>
    <td>XY</td>
    <td>性别差异不明显喵~</td>
    <td>P3</td>
  </tr>
  <tr>
    <td>骡子</td>
    <td>Mule</td>
    <td>XY（不育）</td>
    <td>不参与繁殖，性别差异不明显喵~</td>
    <td>N/A</td>
  </tr>
</table>

**注**：
- 性别二态性的实现程度取决于优先级，P0 生物将优先获得完整的视觉差异喵~
- 部分生物（如熊猫、豹猫）的性别差异在现实中不明显，将在后续版本中考虑更细微的视觉或行为差异喵~
- 骡子作为不育的杂交后代，不参与繁殖系统喵~

---

## 9. 风险与挑战

### 9.1 技术挑战

- **模型和纹理资源量**：为每种生物实现性别二态性需要大量的美术资源（双倍的纹理和模型），可能增加模组包大小和开发成本喵~
	- **缓解措施**：优先实现 P0 生物，P2 和 P3 生物可在后续版本中逐步完善；支持资源包，让社区贡献性别纹理喵~
- **第三方模组兼容性**：其他生物模组可能有自己的繁殖逻辑，需要确保性别系统不与其冲突喵~
	- **缓解措施**：通过 Mixin 优先级和事件系统确保兼容性；提供 API 让其他模组自定义繁殖规则喵~
- **性能开销**：性别信息的存储和查询可能增加内存和 CPU 开销喵~
	- **缓解措施**：性别信息作为染色体数据的一部分存储，不额外增加数据结构；使用缓存优化查询性能喵~

### 9.2 用户体验挑战

- **玩家困惑**：禁止同性繁殖后，玩家可能不理解为什么两只生物无法繁殖喵~
	- **缓解措施**：通过 Jade 等 HUD 模组清晰展示性别信息；在 Wiki 和教程中说明繁殖规则喵~
- **性别识别困难**：部分生物的性别二态性不明显，玩家可能难以直观识别喵~
	- **缓解措施**：为这些生物提供 Jade HUD 显示支持，让玩家通过悬停查看性别喵~

### 9.3 社区接受度风险

- **真实性 vs 游戏性**：部分玩家可能认为性别限制繁殖过于严格，影响游戏体验喵~
	- **缓解措施**：提供配置选项，让玩家选择是否启用性别限制繁殖；在整合包中，整合包作者可根据受众调整配置喵~

---

## 10. 成功标准

### 10.1 功能完成度

- [ ] 所有 P0 功能实现并通过测试喵~
- [ ] 至少 80% 的 P1 功能实现并通过测试喵~
- [ ] 6 种典型生物的性别视觉差异完成并经过玩家反馈验证喵~

### 10.2 质量标准

- [ ] 所有集成测试用例通过（包括性别生成比例、繁殖限制等）喵~
- [ ] Jade 和 WTHIT 集成测试通过，HUD 正确显示性别信息喵~
- [ ] 与至少 5 个常见生物模组的兼容性测试通过喵~

### 10.3 用户满意度

- [ ] 测试版发布后，收集至少 50 份玩家反馈，好评率达到 85% 以上喵~
- [ ] 在 CurseForge 和 Modrinth 上的评分达到 4.5/5.0 以上喵~
- [ ] 至少 1 个知名 YouTuber 或 Bilibili UP 主制作性别系统相关的科普或教程视频喵~

### 10.4 社区影响

- [ ] 发布 2 个月内，至少有 1 个第三方模组集成 Chromosome Lib 的性别系统 API 喵~
- [ ] 发布 3 个月内，在 MCMOD 中文模组网站上，性别系统相关的讨论帖获得至少 10 个回复喵~
- [ ] 性别系统开发者文档被至少 5 个模组开发者引用或参考喵~