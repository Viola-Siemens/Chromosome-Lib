# 测试用例设计文档：Chromosome Lib v0.1.0

**版本：** 0.1.0+1.20.1

**日期：** 2026-04-23

**作者：** Liu Dongyu

**状态：**

- [x] 草案
- [ ] 评审中
- [ ] 已批准

**关联文档：**
- 产品需求文档：`docs/v1/PRD.md`
- 技术设计文档：`docs/v1/DR.md`

---

## 1. 测试范围

本文档覆盖 Chromosome Lib v0.1.0 的以下功能模块：

| 模块 | 优先级 | 测试类型 |
|------|--------|----------|
| BiologicalSex 枚举与序列化 | P0 | 单元测试 |
| SexDetermination 性别推导 | P0 | 单元测试 |
| SexHelper 工具类 | P0 | 单元测试 |
| 性染色体注册 API | P0 | 单元/集成测试 |
| 性别生成比例（50:50） | P0 | 集成测试（统计） |
| 禁止同性繁殖 | P0 | 集成测试 |
| Jade 模组集成 | P0 | 集成测试 |
| IHymenoptera 蜜蜂系统 | P1 | 集成测试 |
| 伴性遗传机制 | P1 | 集成测试（统计） |
| AfterSexDetermined 事件回调 | P1 | 集成测试 |
| BreedingValidator 自定义繁殖限制 | P1 | 集成测试 |
| 性别视觉表现（纹理替换） | P1 | 集成测试 |
| WTHIT 模组集成 | P1 | 集成测试 |
| 向后兼容性 | P1 | 回归测试 |

**不在本次范围内：**

- The One Probe 集成（P2）
- Y/W 染色体基因（P2）
- 性别行为差异（P2）
- 性别二态性 API（P2）

---

## 2. 测试环境

- **Java 版本：** Java 17 LTS
- **Minecraft 版本：** 1.20.1
- **Fabric Loader：** 0.16.10
- **Fabric API：** 0.92.1+1.20.1
- **Forge：** 47.2.0
- **测试框架：** JUnit 5（Fabric 端集成测试）
- **Jade 版本：** 与 Minecraft 1.20.1 兼容的最新版本（可选依赖）

**备注：** 集成测试位于 `fabric/src/test/` 目录下，使用 Fabric 的游戏测试框架。部分统计测试（性别比例验证）存在约 1.25% 的固有失败概率，失败后应重新运行一次。

---

## 3. 测试设计方法

- **等价类划分：** 对 XY / ZW / 无性染色体、MALE / FEMALE / ASEXUAL 分别设计用例；
- **边界值分析：** 对 LEFT/RIGHT 染色体副本数为 0、1、2+ 的边界场景分别设计用例；
- **统计验证：** 对 50:50 性别比例，使用 N=10000 次独立生成，95% 置信区间验证；
- **状态迁移：** 对注册 API 在 `freezeAndBuild()` 前后的读写状态分别设计用例；
- **负面测试：** 对非法参数、越权调用（如 frozen 后注册）设计异常用例。

---

## 4. 测试用例

### 4.1 模块：BiologicalSex 枚举与序列化

---

#### TC-001：BiologicalSex 序列化名称正确

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.1（性别基因存储），DR §2.1

**前置条件：** 无

**测试步骤：**

1. 调用 `BiologicalSex.MALE.getSerializedName()`；
2. 调用 `BiologicalSex.FEMALE.getSerializedName()`；
3. 调用 `BiologicalSex.ASEXUAL.getSerializedName()`。

**预期结果：**

| 枚举值       | 期望序列化名      |
|-----------|-------------|
| `MALE`    | `"male"`    |
| `FEMALE`  | `"female"`  |
| `ASEXUAL` | `"asexual"` |

---

#### TC-002：BiologicalSex CODEC 序列化与反序列化往返一致

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.1

**前置条件：** 无

**测试步骤：**

1. 对 `BiologicalSex.MALE`、`FEMALE`、`ASEXUAL` 各自通过 `BiologicalSex.CODEC` 序列化为字符串；
2. 再将字符串反序列化回 `BiologicalSex` 枚举值；
3. 验证反序列化结果与原始枚举值相等。

**预期结果：** 所有枚举值往返序列化后等于自身，无信息丢失。

---

#### TC-003：BiologicalSex fromValues 枚举完整性

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.1

**前置条件：** 无

**测试步骤：**

1. 调用 `BiologicalSex.values()` 获取所有枚举值；
2. 验证枚举值数量为 3（MALE、FEMALE、ASEXUAL）；
3. 验证 `StringRepresentable.fromEnum(BiologicalSex::values)` 不抛出异常。

**预期结果：** 枚举包含且仅包含 MALE、FEMALE、ASEXUAL 三个值。

---

### 4.2 模块：SexDetermination 性别推导

---

#### TC-010：XY 系统 - LEFT+LEFT 推导为 FEMALE

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.1（XY 性别决定系统），DR §2.2

**前置条件：** 无

**测试步骤：**

1. 调用 `SexDetermination.XY.resolve(left=2, right=0)`。

**预期结果：** 返回 `BiologicalSex.FEMALE`（XX 型为雌性）。

---

#### TC-011：XY 系统 - LEFT+RIGHT 推导为 MALE

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.1（XY 性别决定系统），DR §2.2

**前置条件：** 无

**测试步骤：**

1. 调用 `SexDetermination.XY.resolve(left=1, right=1)`。

**预期结果：** 返回 `BiologicalSex.MALE`（XY 型为雄性）。

---

#### TC-012：ZW 系统 - LEFT+LEFT 推导为 MALE

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.1（ZW 性别决定系统），DR §2.2

**前置条件：** 无

**测试步骤：**

1. 调用 `SexDetermination.ZW.resolve(left=2, right=0)`。

**预期结果：** 返回 `BiologicalSex.MALE`（ZZ 型为雄性）。

---

#### TC-013：ZW 系统 - LEFT+RIGHT 推导为 FEMALE

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.1（ZW 性别决定系统），DR §2.2

**前置条件：** 无

**测试步骤：**

1. 调用 `SexDetermination.ZW.resolve(left=1, right=1)`。

**预期结果：** 返回 `BiologicalSex.FEMALE`（ZW 型为雌性）。

---

#### TC-014：SexDetermination.resolve() 对 right=0 的边界处理

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.2

**前置条件：** 无

**测试步骤：**

1. 调用 `SexDetermination.XY.resolve(left=1, right=0)`；
2. 调用 `SexDetermination.ZW.resolve(left=1, right=0)`。

**预期结果：**

| 调用                 | 期望结果                                   |
|--------------------|----------------------------------------|
| `XY.resolve(1, 0)` | `FEMALE`（仅有 X 染色体，视为 XX 退化为 X0，逻辑同 XX） |
| `ZW.resolve(1, 0)` | `MALE`（仅有 Z 染色体，视为 ZZ 退化为 Z0，逻辑同 ZZ）   |

---

### 4.3 模块：SexHelper 工具类

---

#### TC-020：无性染色体实体查询性别返回 ASEXUAL

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.1，DR §2.3，DR §7.1

**前置条件：**
- 创建一个实体类型（如虚拟测试实体），该实体类型未注册任何性染色体。

**测试步骤：**

1. 调用 `SexHelper.getSex(entity)` 查询该实体的性别。

**预期结果：** 返回 `BiologicalSex.ASEXUAL`。

---

#### TC-021：XY 系统实体性别查询 - 雄性

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.1，DR §2.3

**前置条件：**
- 注册一条 XY 系统性染色体并关联至测试实体类型；
- 创建一个该实体，其性染色体实例为 LEFT+RIGHT（XY 型）。

**测试步骤：**

1. 调用 `SexHelper.getSex(entity)`。

**预期结果：** 返回 `BiologicalSex.MALE`。

---

#### TC-022：XY 系统实体性别查询 - 雌性

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.1，DR §2.3

**前置条件：**
- 注册一条 XY 系统性染色体并关联至测试实体类型；
- 创建一个该实体，其性染色体实例为 LEFT+LEFT（XX 型）。

**测试步骤：**

1. 调用 `SexHelper.getSex(entity)`。

**预期结果：** 返回 `BiologicalSex.FEMALE`。

---

#### TC-023：ZW 系统实体性别查询

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.1，DR §2.3

**前置条件：**
- 注册一条 ZW 系统性染色体并关联至测试实体类型；
- 分别创建 LEFT+LEFT（ZZ 型）和 LEFT+RIGHT（ZW 型）两个实体。

**测试步骤：**

1. 调用 `SexHelper.getSex(zzEntity)`；
2. 调用 `SexHelper.getSex(zwEntity)`。

**预期结果：**

| 实体   | 期望结果     |
|------|----------|
| ZZ 型 | `MALE`   |
| ZW 型 | `FEMALE` |

---

#### TC-024：IHymenoptera 实体性别查询优先于染色体推导

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.1（蜜蜂），DR §2.3、§2.4

**前置条件：**
- 创建一个实现了 `IHymenoptera` 接口的蜜蜂实体；
- 设置其存储性别为 `MALE`（单倍体雄蜂）；
- 蜜蜂无性染色体注册。

**测试步骤：**

1. 调用 `SexHelper.getSex(bee)`。

**预期结果：** 返回 `MALE`，不查染色体（即便查也应返回 ASEXUAL，但接口优先级更高）。

---

#### TC-025：isCompatibleBreedingPair - 异性配对兼容

**优先级：** P0

**测试类型：** 单元/集成测试

**关联需求：** PRD §4.4，DR §2.3

**前置条件：**
- 准备一个 XY 系统雄性实体（MALE）；
- 准备一个 XY 系统雌性实体（FEMALE）。

**测试步骤：**

1. 调用 `SexHelper.isCompatibleBreedingPair(maleEntity, femaleEntity)`。

**预期结果：** 返回 `true`。

---

#### TC-026：isCompatibleBreedingPair - 同性配对不兼容

**优先级：** P0

**测试类型：** 单元/集成测试

**关联需求：** PRD §4.4，DR §2.3

**前置条件：**
- 准备两个 XY 系统雄性实体（MALE）；
- 准备两个 XY 系统雌性实体（FEMALE）。

**测试步骤：**

1. 调用 `SexHelper.isCompatibleBreedingPair(male1, male2)`；
2. 调用 `SexHelper.isCompatibleBreedingPair(female1, female2)`。

**预期结果：**

| 调用   | 期望结果    |
|------|---------|
| 两个雄性 | `false` |
| 两个雌性 | `false` |

---

#### TC-027：isCompatibleBreedingPair - ASEXUAL 参与配对始终兼容

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** PRD §4.4，DR §2.3、§7.1

**前置条件：**
- 准备一个 ASEXUAL 实体（无性染色体）；
- 准备一个 MALE 实体；
- 准备另一个 ASEXUAL 实体。

**测试步骤：**

1. 调用 `SexHelper.isCompatibleBreedingPair(asexualEntity, maleEntity)`；
2. 调用 `SexHelper.isCompatibleBreedingPair(asexualEntity1, asexualEntity2)`。

**预期结果：** 两次调用均返回 `true`。

---

#### TC-028：isCompatibleBreedingPair 满足交换律

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.3

**前置条件：**
- 准备 MALE、FEMALE、ASEXUAL 三种实体各一个。

**测试步骤：**

1. 验证 `isCompatibleBreedingPair(A, B) == isCompatibleBreedingPair(B, A)` 对所有组合成立。

**预期结果：** 所有 6 种组合均满足交换律：
- MALE + FEMALE = true（双向）
- MALE + MALE = false（双向）
- FEMALE + FEMALE = false（双向）
- MALE + ASEXUAL = true（双向）
- FEMALE + ASEXUAL = true（双向）
- ASEXUAL + ASEXUAL = true（双向）

---

### 4.4 模块：性染色体注册 API

---

#### TC-030：注册性染色体和性别决定系统

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.6（性染色体注册 API），DR §2.5

**前置条件：**
- 在 `freezeAndBuild()` 之前。

**测试步骤：**

1. 调用 `RegistryRelations.registerSexChromosome(testChromosome, SexDetermination.XY)`；
2. 调用 `RegistryRelations.freezeAndBuild()`；
3. 调用 `RegistryRelations.getSexDetermination(testChromosome)`。

**预期结果：** 步骤 3 返回 `SexDetermination.XY`，不返回 `null`。

---

#### TC-031：关联实体类型与性染色体

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.6，DR §2.5

**前置条件：**
- 在 `freezeAndBuild()` 之前。

**测试步骤：**

1. 调用 `RegistryRelations.registerEntityTypeSexChromosome(EntityType.SHEEP, sheepXYChromosome)`；
2. 调用 `RegistryRelations.freezeAndBuild()`；
3. 调用 `RegistryRelations.getSexChromosome(EntityType.SHEEP)`。

**预期结果：** 步骤 3 返回 `sheepXYChromosome` 的 Holder，不返回 `null`。

---

#### TC-032：未注册实体类型查询性染色体返回 null

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.5、§7.1

**前置条件：**
- `freezeAndBuild()` 已调用；
- 测试实体类型（如 `EntityType.CREEPER`）未注册性染色体。

**测试步骤：**

1. 调用 `RegistryRelations.getSexChromosome(EntityType.CREEPER)`。

**预期结果：** 返回 `null`，不抛出异常。

---

#### TC-033：freezeAndBuild 后调用 registerSexChromosome 抛出异常

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.5、§7.2

**前置条件：**
- `freezeAndBuild()` 已调用。

**测试步骤：**

1. 尝试调用 `RegistryRelations.registerSexChromosome(testChromosome, SexDetermination.ZW)`。

**预期结果：** 抛出 `IllegalStateException`，注册失败。

---

#### TC-034：freezeAndBuild 后调用 registerEntityTypeSexChromosome 抛出异常

**优先级：** P0

**测试类型：** 单元测试

**关联需求：** DR §2.5、§7.2

**前置条件：**
- `freezeAndBuild()` 已调用。

**测试步骤：**

1. 尝试调用 `RegistryRelations.registerEntityTypeSexChromosome(EntityType.PIG, testChromosome)`。

**预期结果：** 抛出 `IllegalStateException`，注册失败。

---

### 4.5 模块：性别生成比例

---

#### TC-040：XY 系统自然生成性别比例接近 50:50

**优先级：** P0

**测试类型：** 集成测试（统计验证）

**关联需求：** PRD §4.1（性别生成 50:50），DR §2.6

**前置条件：**
- 已注册 XY 系统羊性染色体（`SHEEP_XY`）；
- 可批量生成羊实体（调用 `buildDefaultChromosomes()`）。

**测试步骤：**

1. 循环生成 10000 只羊实体；
2. 对每只羊调用 `SexHelper.getSex(sheep)` 统计雌雄数量；
3. 计算雌性比例 `p = female_count / 10000`；
4. 验证 `|p - 0.5| ≤ 0.0224`（95% 置信区间，阈值 = 1.96 × √(0.25/10000)）。

**预期结果：** 雌性比例在 47.76% 至 52.24% 之间。若本次失败，重新运行一次（固有失败概率约 5%）。

---

#### TC-041：ZW 系统自然生成性别比例接近 50:50

**优先级：** P0

**测试类型：** 集成测试（统计验证）

**关联需求：** PRD §4.1（ZW 性别决定系统），DR §2.6

**前置条件：**
- 已注册 ZW 系统鸡性染色体（`CHICKEN_ZW`）；
- 可批量生成鸡实体。

**测试步骤：**

1. 循环生成 10000 只鸡实体；
2. 对每只鸡调用 `SexHelper.getSex(chicken)` 统计雌雄数量；
3. 计算雄性比例 `p = male_count / 10000`；
4. 验证 `|p - 0.5| ≤ 0.0224`。

**预期结果：** 雄性比例在 47.76% 至 52.24% 之间。

---

#### TC-042：繁殖后代性别比例接近 50:50

**优先级：** P0

**测试类型：** 集成测试（统计验证）

**关联需求：** PRD §4.1（繁殖时性别生成 50:50），DR §2.6

**前置条件：**
- 准备一对可繁殖的雌雄羊（XY 系统）；
- 模拟繁殖事件触发 `buildDefaultChromosomes()`。

**测试步骤：**

1. 模拟 10000 次繁殖，每次由固定的雌雄亲本生成一只后代；
2. 对每只后代调用 `SexHelper.getSex(offspring)` 统计雌雄数量；
3. 计算雄性比例并验证在置信区间内。

**预期结果：** 后代雄性比例在 47.76% 至 52.24% 之间。

---

#### TC-043：性染色体实例组合正确性（XY）

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** DR §2.6

**前置条件：**
- 已注册 XY 性染色体。

**测试步骤：**

1. 多次调用 `buildDefaultChromosomes()` 生成羊的染色体列表；
2. 对每次结果，检查性染色体实例数量为 2；
3. 检查每次的组合类型为 LEFT+LEFT 或 LEFT+RIGHT，不存在 RIGHT+RIGHT 组合。

**预期结果：** 每次生成的性染色体组合只有 LEFT+LEFT 或 LEFT+RIGHT 两种形式，无 RIGHT+RIGHT。

---

### 4.6 模块：禁止同性繁殖

---

#### TC-050：同性繁殖（雄+雄）被阻止 - 不产生后代

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（性别验证），DR §3.2

**前置条件：**
- 创建两只雄性羊（XY 系统，MALE）；
- 两只羊均处于 InLove（求爱）状态。

**测试步骤：**

1. 让两只雄性羊相互执行 `BreedGoal` 的寻找配偶逻辑（`getFreePartner()`）；
2. 观察是否产生后代实体。

**预期结果：**
- `getFreePartner()` 返回 `null`（没有找到可配对的异性）；
- 不产生后代羊实体。

---

#### TC-051：同性繁殖（雌+雌）被阻止 - 不产生后代

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（性别验证），DR §3.2

**前置条件：**
- 创建两只雌性羊（XY 系统，FEMALE）；
- 两只羊均处于 InLove 状态。

**测试步骤：**

1. 让两只雌性羊执行 `BreedGoal` 的寻找配偶逻辑；
2. 观察是否产生后代实体。

**预期结果：**
- `getFreePartner()` 返回 `null`；
- 不产生后代羊实体。

---

#### TC-052：异性繁殖（雄+雌）正常进行

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4，DR §3.2

**前置条件：**
- 创建一只雄性羊（MALE）和一只雌性羊（FEMALE）；
- 两只羊均处于 InLove 状态，且满足原版繁殖条件（体力未耗尽，无繁殖冷却）。

**测试步骤：**

1. 让雄性羊执行 `BreedGoal` 的寻找配偶逻辑；
2. 触发繁殖事件；
3. 观察是否产生后代实体。

**预期结果：**
- `getFreePartner()` 返回雌性羊；
- 产生一只后代小羊。

---

#### TC-053：同性求爱时接受食物但不进入繁殖模式

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（交互反馈），DR §3.2

**前置条件：**
- 创建两只雄性羊；
- 玩家持有小麦（羊的繁殖食物）。

**测试步骤：**

1. 玩家对一只雄性羊使用小麦（右键）；
2. 观察羊的状态和粒子效果。

**预期结果：**
- 羊接受食物（食物数量减少）；
- 产生心形粒子效果（进入 InLove 状态）；
- 羊未进入繁殖模式（不产生后代）。

---

#### TC-054：同性求爱期间偶尔产生烟雾粒子

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（交互反馈），DR §3.2

**前置条件：**
- 创建两只雄性羊，均处于 InLove 状态；
- 无雌性羊在附近（半径 8 格内）。

**测试步骤：**

1. 让两只雄性羊维持 InLove 状态持续至少 20 个 tick；
2. 观察是否出现烟雾粒子（`ParticleTypes.SMOKE`）。

**预期结果：** 在多次 tick 中，应观察到至少一次烟雾粒子（约 1/20 概率/tick，观察 20 tick 期望出现 1 次）。

---

#### TC-055：求爱超时且仅有同性伙伴时触发成就

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（进度提示），DR §3.2

**前置条件：**
- 创建两只雄性羊，均处于 InLove 状态；
- 玩家位于附近（半径 16 格内）；
- 无雌性羊在附近。

**测试步骤：**

1. 等待两只雄性羊的 InLove 状态自然超时（loveTime 归零）；
2. 检查最近玩家是否触发了 `CLCriteriaTriggers.SAME_SEX_BREEDING_ATTEMPT` 进度触发器。

**预期结果：** 对应玩家的进度触发器被触发（"嘿，小查，那是公狗！"进度可解锁）。

---

#### TC-056：无异性伙伴时不触发成就

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（进度提示），DR §3.2

**前置条件：**
- 创建一只雄性羊，处于 InLove 状态；
- 无其他羊在附近。

**测试步骤：**

1. 等待该雄性羊的 InLove 状态超时；
2. 检查进度触发器是否被触发。

**预期结果：** 进度触发器**不**被触发（仅孤独的求爱，无同性伙伴在场）。

---

#### TC-057：有异性伙伴在场时不触发成就

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（进度提示），DR §3.2

**前置条件：**
- 创建一只雄性羊和一只雌性羊，均处于 InLove 状态；
- 无其他雄性羊在附近。

**测试步骤：**

1. 等待两只羊的 InLove 状态超时（若繁殖成功则更早退出）；
2. 检查进度触发器是否被触发。

**预期结果：** 进度触发器**不**被触发（有异性在场，不应触发同性成就）。

---

### 4.7 模块：特殊情况处理

---

#### TC-060：ASEXUAL 实体（无性染色体）之间可繁殖

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.4（兼容性），DR §7.1

**前置条件：**
- 准备两个无性染色体实体（如虚拟测试实体），均处于 InLove 状态。

**测试步骤：**

1. 调用 `SexHelper.isCompatibleBreedingPair(entity1, entity2)`；
2. 让两个实体执行 `BreedGoal` 逻辑。

**预期结果：**
- `isCompatibleBreedingPair` 返回 `true`；
- `BreedGoal` 不因性别过滤而阻止繁殖。

---

#### TC-061：骡子不受同性繁殖限制（骡子为不育生物）

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.4（特殊情况处理），DR §7.1

**前置条件：**
- 准备两只骡子（`EntityType.MULE`）。

**测试步骤：**

1. 调用 `SexHelper.getSex(mule1)` 和 `SexHelper.getSex(mule2)`；
2. 确认骡子的性别为 `ASEXUAL`（骡子倍性为 1，无配对性染色体）；
3. 验证 `SexHelper.isCompatibleBreedingPair(mule1, mule2)` 返回 `true`（但骡子仍因原版不育逻辑无法繁殖）。

**预期结果：** 骡子性别为 ASEXUAL，性别检查不阻止其尝试繁殖（原版不育逻辑兜底）。

---

#### TC-062：BreedingValidator 自定义限制生效

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.6（繁殖限制 API），DR §2.8

**前置条件：**
- 注册一个始终返回 `false` 的 `BreedingValidator`（禁止一切繁殖）。

**测试步骤：**

1. 创建一对雌雄羊（正常应可繁殖）；
2. 两只羊均处于 InLove 状态；
3. 执行 `BreedGoal` 逻辑。

**预期结果：** 不产生后代（`BreedingValidator` 禁止了繁殖）。

---

#### TC-063：BreedingValidator 不影响性别正常过滤

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.6，DR §2.8

**前置条件：**
- 注册一个始终返回 `true` 的 `BreedingValidator`（不添加额外限制）；
- 创建两只同性雄性羊。

**测试步骤：**

1. 两只雄性羊均处于 InLove 状态；
2. 执行 `BreedGoal` 逻辑。

**预期结果：** 不产生后代（性别过滤仍生效，BreedingValidator 不影响性别检查的执行顺序）。

---

### 4.8 模块：IHymenoptera 蜜蜂系统

---

#### TC-070：蜜蜂自然生成性别比例接近 50:50

**优先级：** P1

**测试类型：** 集成测试（统计验证）

**关联需求：** PRD §4.1（单倍体/双倍体系统），DR §3.3

**前置条件：**
- 已实现 `BeeEntityMixin` 和 `IHymenoptera` 接口。

**测试步骤：**

1. 批量生成 10000 只蜜蜂实体（调用 `finalizeSpawn()`）；
2. 对每只蜜蜂调用 `SexHelper.getSex(bee)` 统计 MALE/FEMALE 数量；
3. 计算雄性比例并验证在置信区间内。

**预期结果：** 雄性（单倍体）比例在 47.76% 至 52.24% 之间。

---

#### TC-071：单倍体雄蜂（MALE）的倍性为 1

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.1，DR §3.3

**前置条件：**
- 创建一只性别为 MALE 的蜜蜂（手动设置或通过 `finalizeSpawn()` 生成）。

**测试步骤：**

1. 调用 `((IChromosomeCarrier) bee).chromosomelib$getPloidy()` 获取倍性；
2. 调用 `buildDefaultChromosomes()` 构建染色体并统计每条常染色体的副本数。

**预期结果：**
- `getPloidy()` 返回 1；
- 所有常染色体各只有 1 个实例。

---

#### TC-072：双倍体雌蜂（FEMALE）的倍性为 2

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.1，DR §3.3

**前置条件：**
- 创建一只性别为 FEMALE 的蜜蜂。

**测试步骤：**

1. 调用 `((IChromosomeCarrier) bee).chromosomelib$getPloidy()` 获取倍性；
2. 调用 `buildDefaultChromosomes()` 构建染色体并统计每条常染色体的副本数。

**预期结果：**
- `getPloidy()` 返回 2；
- 所有常染色体各有 2 个实例。

---

#### TC-073：蜜蜂性别 NBT 持久化与恢复

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** DR §9.1、§9.2

**前置条件：**
- 创建一只性别为 MALE 的蜜蜂。

**测试步骤：**

1. 调用 `bee.addAdditionalSaveData(tag)` 将蜜蜂数据写入 NBT；
2. 验证 `tag.contains("chromosomelib_sex")` 为 true；
3. 验证 `tag.getString("chromosomelib_sex")` 等于 `"male"`；
4. 创建一只新蜜蜂并调用 `newBee.readAdditionalSaveData(tag)` 读取数据；
5. 调用 `SexHelper.getSex(newBee)` 查询性别。

**预期结果：** 新蜜蜂性别为 `MALE`，NBT 往返数据一致。

---

#### TC-074：蜜蜂性别 EntityDataAccessor 客户端同步

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** DR §3.3、§7.4

**前置条件：**
- 创建一只性别为 FEMALE 的蜜蜂；
- 模拟服务端到客户端的 EntityData 同步。

**测试步骤：**

1. 在服务端设置蜜蜂性别为 FEMALE（`chromosomelib$setHymenopteraSex(FEMALE)`）；
2. 触发 EntityData 同步；
3. 在客户端调用 `SexHelper.getSex(bee)`。

**预期结果：** 客户端蜜蜂性别为 `FEMALE`，与服务端一致。

---

#### TC-075：蜜蜂不受同性繁殖限制

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.4（特殊情况处理），PRD §8（蜜蜂）

**前置条件：**
- 创建一只雄蜂（MALE）和一只雌蜂（FEMALE，工蜂或蜂后）；
- 两只蜜蜂均处于可繁殖状态。

**测试步骤：**

1. 调用 `SexHelper.isCompatibleBreedingPair(drone, bee)` 验证配对兼容性。

**预期结果：** 返回 `true`（雄蜂为 MALE，雌蜂为 FEMALE，异性可配对）。

---

### 4.9 模块：伴性遗传机制

---

#### TC-080：X 连锁隐性基因 - 雄性（XY）单剂即表达

**优先级：** P1

**测试类型：** 集成测试（统计验证）

**关联需求：** PRD §4.5（X 连锁遗传），DR §3.4

**背景：** 位于 X 染色体上的隐性基因（如橘色毛色），雄性（XY）只有 X 染色体一份，只需一个隐性等位基因即可表达。

**前置条件：**
- 猫的橘色毛色基因（`OrangeAllele`）定义为 X 连锁隐性基因（`LeftGeneLocus`，隐性等位基因）；
- 准备纯合隐性雌猫（XX，两条 X 均携带隐性基因）和纯合显性雌猫（XX，两条 X 均携带显性基因）进行杂交繁殖，产生 F1 代。

**测试步骤：**

1. 让 F1 代雌猫（携带者，XX）与纯合显性雄猫（XY）繁殖，生成 1000 只后代；
2. 统计 F2 代中各性别的橘色表达频率；
3. 验证雄性后代中橘色频率约为 50%（XY 中 X 来自母亲，一半为 x）；
4. 验证雌性后代中橘色频率约为 0%（全为 XX 携带者或 XX 显性）。

**预期结果：** 雄性橘色频率在 45% ~ 55%，雌性橘色频率为 0%（允许统计误差）。

---

#### TC-081：Z 连锁隐性基因 - 雌性（ZW）单剂即表达

**优先级：** P1

**测试类型：** 集成测试（统计验证）

**关联需求：** PRD §4.5（Z 连锁遗传），DR §3.4

**背景：** 位于 Z 染色体上的隐性基因，雌性鸡（ZW）只有 Z 染色体一份，只需一个隐性等位基因即可表达。

**前置条件：**
- 鸡的特定羽毛颜色基因定义为 Z 连锁隐性基因（`LeftGeneLocus`）；
- 准备携带者雄鸡（ZZ，一条 Z 携带隐性基因）和正常雌鸡（ZW）繁殖，生成 1000 只后代。

**测试步骤：**

1. 统计后代雌性中的隐性性状表达频率；
2. 统计后代雄性中的隐性性状表达频率；
3. 验证雌性表达频率约为 50%（ZW 中 Z 一半来自携带者父亲）；
4. 验证雄性表达频率约为 0%（ZZ 均从父亲得到正常 Z，从母亲得到正常 Z）。

**预期结果：** 雌性表达频率在 45% ~ 55%，雄性表达频率为 0%（允许统计误差）。

---

#### TC-082：X 连锁基因不出现在 Y 染色体（RIGHT 类型）

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.5，DR §3.4

**前置条件：**
- 猫的橘色毛色基因为 `LeftGeneLocus`（仅在 LEFT 类型染色体上）；
- 创建多只雄猫（XY）。

**测试步骤：**

1. 对多只雄猫获取其所有染色体实例；
2. 找出性染色体的 RIGHT 实例（Y 染色体）；
3. 检查 RIGHT 实例上是否包含橘色毛色基因座（`LeftGeneLocus`）。

**预期结果：** Y 染色体（RIGHT 实例）上**不包含** `LeftGeneLocus` 类型的橘色基因座。

---

### 4.10 模块：AfterSexDetermined 事件回调

---

#### TC-090：自然生成实体时触发 AfterSexDetermined 事件

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.6（性别事件回调），DR §2.7

**前置条件：**
- 注册一个 `AfterSexDeterminedSolver`，记录收到的实体和性别。

**测试步骤：**

1. 生成一只羊（触发 `finalizeSpawn()`）；
2. 检查 `AfterSexDeterminedSolver` 是否被调用；
3. 检查回调参数中的实体是否为该羊；
4. 检查回调参数中的性别是否与 `SexHelper.getSex(sheep)` 一致。

**预期结果：**
- 事件被调用一次；
- 实体参数为刚生成的羊；
- 性别参数与实体实际性别一致。

---

#### TC-091：繁殖后代生成时触发 AfterSexDetermined 事件

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** PRD §4.6，DR §2.7

**前置条件：**
- 注册一个 `AfterSexDeterminedSolver`，记录收到的实体和性别；
- 准备一对雌雄羊可以繁殖。

**测试步骤：**

1. 触发繁殖事件，产生后代；
2. 检查 `AfterSexDeterminedSolver` 是否被调用；
3. 检查回调参数中的实体是否为后代；
4. 检查回调参数中的性别是否正确。

**预期结果：**
- 事件被调用一次（对后代）；
- 实体参数为后代羊；
- 性别参数与���代实际性别一致。

---

#### TC-092：AfterSexDetermined 回调在服务端触发

**优先级：** P1

**测试类型：** 集成测试

**关联需求：** DR §7.4（客户端/服务端隔离）

**前置条件：**
- 注册一个 `AfterSexDeterminedSolver`，记录被调用时的线程或逻辑端。

**测试步骤：**

1. 生成一只羊（服务端逻辑）；
2. 检查 `AfterSexDeterminedSolver` 的调用是否发生在服务端。

**预期结果：** 回调在服务端触发，不在客户端触发。

---

### 4.11 模块：Jade 模组集成

---

#### TC-100：Jade 插件注册成功

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.3（Jade 模组集成），DR §8

**前置条件：**
- Jade 模组已加载；
- Chromosome Lib 的 Jade 插件已注册（`CLWailaPlugin`/`CLJadePlugin`）。

**测试步骤：**

1. 检查 Jade 的插件注册表中是否包含 `CLWailaPlugin`；
2. 启动游戏，检查控制台无 Jade 插件加载错误。

**预期结果：** 插件注册成功，控制台无错误。

---

#### TC-101：Jade HUD 对雄性实体显示 "Male"

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.3，DR §8

**前置条件：**
- Jade 模组已加载；
- 准备一只雄性羊（MALE）；
- 玩家将鼠标悬停在该羊上。

**测试步骤：**

1. 检查 `CLEntitySexProvider.appendServerData()` 的输出数据；
2. 检查 `CLEntitySexProvider.appendTooltip()` 渲染的 HUD 文本。

**预期结果：**
- HUD 中出现性别信息文本（如"性别: 雄性"或"Sex: Male"）；
- 文本与实体实际性别（MALE）一致。

---

#### TC-102：Jade HUD 对雌性实体显示 "Female"

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.3

**前置条件：**
- Jade 模组已加载；
- 准备一只雌性羊（FEMALE）；
- 玩家将鼠标悬停在该羊上。

**测试步骤：**

1. 检查 Jade HUD 渲染的性别文本。

**预期结果：** HUD 中出现"性别: 雌性"或"Sex: Female"。

---

#### TC-103：Jade HUD 对 ASEXUAL 实体不显示性别

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.3，DR §7.1

**前置条件：**
- Jade 模组已加载；
- 准备一个无性染色体实体（ASEXUAL），如苦力怕。

**测试步骤：**

1. 将鼠标悬停在 ASEXUAL 实体上；
2. 检查 Jade HUD 中是否有性别信息显示。

**预期结果：** HUD 中**不**显示性别信息（ASEXUAL 实体无性别，不展示性别栏位）。

---

#### TC-104：Jade HUD 多语言支持（中英文）

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.3（可配置显示），DR 语言文件

**前置条件：**
- 分别以英文（en_us）和中文（zh_cn）语言环境启动游戏；
- Jade 模组已加载；
- 准备一只雄性实体。

**测试步骤：**

1. 英文环境：查看 HUD 性别文本；
2. 中文环境：查看 HUD 性别文本。

**预期结果：**
- 英文环境：显示英文性别名称；
- 中文环境：显示中文性别名称；
- 翻译 key 存在于 `en_us.json`、`zh_cn.json`、`zh_tw.json` 中。

---

### 4.12 模块：性别视觉表现

---

#### TC-110：公鸡使用雄性纹理

**优先级：** P1

**测试类型：** 集成测试（客户端）

**关联需求：** PRD §4.2（纹理差异），PRD §8（鸡 - ZW 系统）

**前置条件：**
- 已实现 `ChickenRendererMixin`；
- 游戏中存在一只雄性鸡（ZZ 型，MALE）；
- 雄性鸡纹理文件（如 `chicken_male.png`）已存在于资源包中。

**测试步骤：**

1. 在游戏中观察该雄性鸡的渲染效果；
2. 检查 `ChickenRendererMixin` 中 `getTextureLocation()` 的返回路径。

**预期结果：** 雄性鸡使用雄性专用纹理路径，与雌性鸡纹理不同。

---

#### TC-111：母鸡使用雌性纹理

**优先级：** P1

**测试类型：** 集成测试（客户端）

**关联需求：** PRD §4.2

**前置条件：**
- 已实现 `ChickenRendererMixin`；
- 游戏中存在一只雌性鸡（ZW 型，FEMALE）。

**测试步骤：**

1. 检查雌性鸡的渲染纹理路径。

**预期结果：** 雌性鸡使用雌性专用纹理路径（或原版鸡纹理，与雄性鸡不同）。

---

#### TC-112：性别纹理文件不存在时回退至原版纹理

**优先级：** P1

**测试类型：** 集成测试（客户端）

**关联需求：** PRD §4.2（资源包支持），DR §7.1

**前置条件：**
- 删除或不提供某种性别的专用纹理文件；
- 游戏中存在对应性别的实体。

**测试步骤：**

1. 查看游戏中该实体的渲染效果；
2. 检查控制台是否有纹理加载错误。

**预期结果：**
- 实体回退使用原版纹理，不显示为紫黑格（缺失纹理标志）；
- 控制台无异常报错（回退处理优雅）。

---

#### TC-113：资源包可覆盖性别纹理

**优先级：** P1

**测试类型：** 集成测试（客户端）

**关联需求：** PRD §4.2（资源包支持）

**前置条件：**
- 准备一个包含自定义雄性羊纹理的资源包；
- 游戏中存在一只雄性羊（MALE）。

**测试步骤：**

1. 加载该资源包；
2. 查看雄性羊的渲染效果。

**预期结果：** 雄性羊使用资源包中的自定义纹理，而非内置纹理。

---

### 4.13 模块：向后兼容性

---

#### TC-120：废弃方法 registerNecessaryChromosomeTypes 对非性染色体仍有效

**优先级：** P1

**测试类型：** 集成测试（回归）

**关联需求：** DR §6.2（向后兼容处理）

**前置条件：**
- 存在一条非性染色体（如普通毛色染色体）；
- 调用 `@Deprecated` 的 `registerNecessaryChromosomeTypes(woolColorChromosome, ChromosomeType.LEFT)`。

**测试步骤：**

1. 调用 `freezeAndBuild()` 并生成使用该染色体的实体；
2. 检查生成的染色体实例中，该普通染色体是否固定为 LEFT 类型。

**预期结果：** 非性染色体的类型固定为 LEFT，功能正常，无回归。

---

#### TC-121：v0.0.1 保存的存档数据兼容性

**优先级：** P1

**测试类型：** 集成测试（回归）

**关联需求：** PRD §9.1（技术挑战）

**前置条件：**
- 准备一个由 v0.0.1 版本生成的包含携带染色体数据的实体的存档。

**测试步骤：**

1. 在 v0.1.0 中加载该存档；
2. 检查加载后的实体是否丢失染色体数据；
3. 调用 `SexHelper.getSex(entity)` 验证性别推导正常（若实体未注册性染色体则为 ASEXUAL）。

**预期结果：**
- 存档加载无异常；
- 实体染色体数据完整保留；
- 性别查询不抛出异常。

---

#### TC-122：无 Jade 模组时 Jade 集成不影响游戏启动

**优先级：** P0

**测试类型：** 集成测试

**关联需求：** PRD §4.3，DR §7.2

**前置条件：**
- 不加载 Jade 模组。

**测试步骤：**

1. 启动游戏（Fabric 环境）；
2. 检查控制台是否有 Jade 相关的 `ClassNotFoundException` 或插件加载错误。

**预期结果：** 游戏正常启动，控制台无 Jade 相关异常（可选依赖处理正确）。

---

### 4.14 模块：BiologicalSexIconElement（Jade 图标元素）

---

#### TC-130：BiologicalSexIconElement 显示雄性图标

**优先级：** P0

**测试类型：** 集成测试（客户端）

**关联需求：** PRD §4.3（可配置显示），DR Jade 集成

**前置条件：**
- Jade 模组已加载；
- `BiologicalSexIconElement` 已注册；
- 准备一只雄性实体，玩家鼠标悬停。

**测试步骤：**

1. 检查 HUD 中 `BiologicalSexIconElement` 的渲染结果。

**预期结果：** 显示雄性图标（♂），颜色或样式与雌性图标明显区分。

---

#### TC-131：BiologicalSexIconElement 显示雌性图标

**优先级：** P0

**测试类型：** 集成测试（客户端）

**关联需求：** PRD §4.3

**前置条件：**
- 准备一只雌性实体，玩家鼠标悬停。

**测试步骤：**

1. 检查 HUD 中 `BiologicalSexIconElement` 的渲染结果。

**预期结果：** 显示雌性图标（♀）。

---

## 5. 测试用例汇总

| 编号     | 标题                                  | 模块               | 优先级 | 类型      |
|--------|-------------------------------------|------------------|-----|---------|
| TC-001 | BiologicalSex 序列化名称正确               | BiologicalSex    | P0  | 单元      |
| TC-002 | BiologicalSex CODEC 往返一致            | BiologicalSex    | P0  | 单元      |
| TC-003 | BiologicalSex fromValues 完整性        | BiologicalSex    | P0  | 单元      |
| TC-010 | XY 系统 LEFT+LEFT 推导为 FEMALE          | SexDetermination | P0  | 单元      |
| TC-011 | XY 系统 LEFT+RIGHT 推导为 MALE           | SexDetermination | P0  | 单元      |
| TC-012 | ZW 系统 LEFT+LEFT 推导为 MALE            | SexDetermination | P0  | 单元      |
| TC-013 | ZW 系统 LEFT+RIGHT 推导为 FEMALE         | SexDetermination | P0  | 单元      |
| TC-014 | SexDetermination.resolve() 边界处理     | SexDetermination | P0  | 单元      |
| TC-020 | 无性染色体实体查询返回 ASEXUAL                 | SexHelper        | P0  | 单元      |
| TC-021 | XY 系统实体性别查询 - 雄性                    | SexHelper        | P0  | 集成      |
| TC-022 | XY 系统实体性别查询 - 雌性                    | SexHelper        | P0  | 集成      |
| TC-023 | ZW 系统实体性别查询                         | SexHelper        | P0  | 集成      |
| TC-024 | IHymenoptera 优先于染色体推导               | SexHelper        | P1  | 集成      |
| TC-025 | isCompatibleBreedingPair 异性兼容       | SexHelper        | P0  | 单元/集成   |
| TC-026 | isCompatibleBreedingPair 同性不兼容      | SexHelper        | P0  | 单元/集成   |
| TC-027 | isCompatibleBreedingPair ASEXUAL 兼容 | SexHelper        | P0  | 单元      |
| TC-028 | isCompatibleBreedingPair 满足交换律      | SexHelper        | P0  | 单元      |
| TC-030 | 注册性染色体和性别决定系统                       | 注册 API           | P0  | 集成      |
| TC-031 | 关联实体类型与性染色体                         | 注册 API           | P0  | 集成      |
| TC-032 | 未注册实体类型查询返回 null                    | 注册 API           | P0  | 单元      |
| TC-033 | freeze 后 registerSexChromosome 抛异常  | 注册 API           | P0  | 单元      |
| TC-034 | freeze 后 registerEntityType 抛异常     | 注册 API           | P0  | 单元      |
| TC-040 | XY 系统自然生成比例 50:50                   | 性别生成             | P0  | 集成（统计）  |
| TC-041 | ZW 系统自然生成比例 50:50                   | 性别生成             | P0  | 集成（统计）  |
| TC-042 | 繁殖后代性别比例 50:50                      | 性别生成             | P0  | 集成（统计）  |
| TC-043 | 性染色体实例组合正确性                         | 性别生成             | P0  | 集成      |
| TC-050 | 雄+雄繁殖被阻止不产生后代                       | 禁止同性繁殖           | P0  | 集成      |
| TC-051 | 雌+雌繁殖被阻止不产生后代                       | 禁止同性繁殖           | P0  | 集成      |
| TC-052 | 雄+雌繁殖正常进行                           | 禁止同性繁殖           | P0  | 集成      |
| TC-053 | 同性求爱接受食物但不繁殖                        | 禁止同性繁殖           | P0  | 集成      |
| TC-054 | 同性求爱期间产生烟雾粒子                        | 禁止同性繁殖           | P0  | 集成      |
| TC-055 | 求爱超时仅同性触发成就                         | 禁止同性繁殖           | P0  | 集成      |
| TC-056 | 无同性伙伴时不触发成就                         | 禁止同性繁殖           | P0  | 集成      |
| TC-057 | 有异性在场时不触发成就                         | 禁止同性繁殖           | P0  | 集成      |
| TC-060 | ASEXUAL 实体之间可繁殖                     | 特殊情况             | P0  | 集成      |
| TC-061 | 骡子不受同性限制                            | 特殊情况             | P1  | 集成      |
| TC-062 | BreedingValidator 自定义限制生效           | 特殊情况             | P1  | 集成      |
| TC-063 | BreedingValidator 不影响性别过滤           | 特殊情况             | P1  | 集成      |
| TC-070 | 蜜蜂自然生成比例 50:50                      | IHymenoptera     | P1  | 集成（统计）  |
| TC-071 | 雄蜂倍性为 1                             | IHymenoptera     | P1  | 集成      |
| TC-072 | 雌蜂倍性为 2                             | IHymenoptera     | P1  | 集成      |
| TC-073 | 蜜蜂性别 NBT 持久化                        | IHymenoptera     | P1  | 集成      |
| TC-074 | 蜜蜂性别 EntityData 同步                  | IHymenoptera     | P1  | 集成      |
| TC-075 | 蜜蜂雄雌可繁殖                             | IHymenoptera     | P1  | 集成      |
| TC-080 | X 连锁隐性基因雄性单剂表达                      | 伴性遗传             | P1  | 集成（统计）  |
| TC-081 | Z 连锁隐性基因雌性单剂表达                      | 伴性遗传             | P1  | 集成（统计）  |
| TC-082 | X 连锁基因不出现在 Y 染色体                    | 伴性遗传             | P1  | 集成      |
| TC-090 | 自然生成触发 AfterSexDetermined 事件        | 事件回调             | P1  | 集成      |
| TC-091 | 繁殖后代触发 AfterSexDetermined 事件        | 事件回调             | P1  | 集成      |
| TC-092 | AfterSexDetermined 在服务端触发           | 事件回调             | P1  | 集成      |
| TC-100 | Jade 插件注册成功                         | Jade 集成          | P0  | 集成      |
| TC-101 | Jade HUD 显示雄性信息                     | Jade 集成          | P0  | 集成      |
| TC-102 | Jade HUD 显示雌性信息                     | Jade 集成          | P0  | 集成      |
| TC-103 | Jade HUD 不显示 ASEXUAL 性别             | Jade 集成          | P0  | 集成      |
| TC-104 | Jade HUD 多语言支持                      | Jade 集成          | P0  | 集成      |
| TC-110 | 公鸡使用雄性纹理                            | 视觉表现             | P1  | 集成（客户端） |
| TC-111 | 母鸡使用雌性纹理                            | 视觉表现             | P1  | 集成（客户端） |
| TC-112 | 性别纹理缺失时回退原版                         | 视觉表现             | P1  | 集成（客户端） |
| TC-113 | 资源包可覆盖性别纹理                          | 视觉表现             | P1  | 集成（客户端） |
| TC-120 | 废弃方法对非性染色体仍有效                       | 向后兼容             | P1  | 回归      |
| TC-121 | v0.0.1 存档数据兼容性                      | 向后兼容             | P1  | 回归      |
| TC-122 | 无 Jade 时不影响游戏启动                     | 向后兼容             | P0  | 集成      |
| TC-130 | Jade 图标显示雄性                         | Jade 图标元素        | P0  | 集成（客户端） |
| TC-131 | Jade 图标显示雌性                         | Jade 图标元素        | P0  | 集成（客户端） |

**合计：** 58 个测试用例

---

## 6. 测试数据说明

### 6.1 统计测试容差

统计测试（性别比例验证）采用以下参数：

- **样本量：** N = 10000
- **期望概率：** p = 0.5
- **置信度：** 95%（z = 1.96）
- **允许偏差：** ε = 1.96 × √(0.25 / 10000) ≈ 0.0098 × 2 ≈ 0.0196

即合格范围为 **48.04% ~ 51.96%**（约 ±2%）。

> **注意：** 上述置信度为 95%，每个统计测试用例有约 5% 的固有失败概率。若某统计用例失败，应重新运行一次再判定。

### 6.2 实体类型说明

| 实体         | 性别决定系统       | 性别判定规则                               | 优先级 |
|------------|--------------|--------------------------------------|-----|
| 羊（Sheep）   | XY           | LEFT+LEFT → FEMALE，LEFT+RIGHT → MALE | P0  |
| 鸡（Chicken） | ZW           | LEFT+LEFT → MALE，LEFT+RIGHT → FEMALE | P0  |
| 马（Horse）   | XY           | 同羊                                   | P0  |
| 牛（Cow）     | XY           | 同羊                                   | P0  |
| 猫（Cat）     | XY           | 同羊，橘色毛色为 X 连锁                        | P0  |
| 兔（Rabbit）  | XY           | 同羊                                   | P0  |
| 蜜蜂（Bee）    | IHymenoptera | 直接存储，不查染色体                           | P1  |
| 骡子（Mule）   | 无（倍性=1）      | 返回 ASEXUAL                           | N/A |