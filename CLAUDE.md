# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Chromosome Lib 是一个为 Minecraft 添加染色体和基因系统的跨版本、跨加载器库模组。

**重要特性：**
- **多加载器架构**：支持 Forge 和 Fabric 两个主流模组加载器
- **跨版本兼容**：当前支持 Minecraft 1.20.x 系列，未来将支持更多版本
- **模块化设计**：核心逻辑与平台实现完全分离

## 构建与测试命令

### 构建项目

```bash
# 构建所有模块
./gradlew build

# 构建特定加载器
./gradlew :fabric:build
./gradlew :forge:build

# 仅构建 common 模块
./gradlew :common:build

# 清理构建产物
./gradlew clean
```

### 运行测试

```bash
# 运行所有测试
./gradlew test

# 仅运行 Fabric 模块测试
./gradlew :fabric:test

# 运行单个测试类
./gradlew :fabric:test --tests "com.hexagram2021.chromosomelib.test.fabric.SheepTest"

# 运行单个测试方法
./gradlew :fabric:test --tests "com.hexagram2021.chromosomelib.test.fabric.SheepTest.testSheepBreedSegregationRatio1"
```

**测试注意事项：**
- 集成测试位于 `fabric/src/test/` 目录下
- 测试使用概率模型验证遗传规律（如孟德尔分离比 3:1）
- **即使代码完全正确，测试仍有约 1.25% 的概率失败**（统计学置信区间导致）
- 如果测试失败，重新运行一次通常会通过
- 未来计划实现真正的单元测试以替代当前的概率测试

### 运行游戏客户端/服务器

```bash
# Fabric 客户端
./gradlew :fabric:runClient

# Fabric 服务器
./gradlew :fabric:runServer

# Forge 客户端
./gradlew :forge:runClient

# Forge 服务器
./gradlew :forge:runServer

# Forge 数据生成
./gradlew :forge:runData
```

## 架构设计

### 多加载器架构（MultiLoader）

项目采用三层模块结构，实现"一次编写，多处运行"：

```
Chromosome-Lib/
├── common/          # 核心功能模块（平台无关）
├── fabric/          # Fabric 平台兼容层
└── forge/           # Forge 平台兼容层
```

### 模块职责划分

#### Common 模块（`common/`）

**职责：**
- 实现所有核心业务逻辑（染色体、基因、性状系统）
- 定义平台无关的事件接口
- 提供公共 API 供其他模组使用
- 包含 Mixin 修改原版行为（平台通用部分）

**关键包结构：**
- `common.chromosome` - 染色体系统核心
- `common.gene` - 基因定义
- `common.gene_locus` - 基因座位
- `common.trait` - 性状类型
- `common.util` - 工具类（繁殖算法、日志等）
- `event/` - 平台无关事件定义
- `platform.services/` - 平台抽象接口（SPI）

**开发规则：**
- Common 代码不得依赖任何特定加载器 API
- 不得直接访问 Fabric API 或 Forge API
- 需要平台特定功能时，通过 `platform.services` 接口抽象
- Common 模块作为依赖被 Fabric 和 Forge 模块引用

#### Fabric 模块（`fabric/`）

**职责：**
- 实现 Common 模块的平台接口（`FabricPlatformHelper`）
- 桥接 Common 事件到 Fabric 事件系统
- Fabric 特定的 Mixin（如果需要）
- 集成测试实现

**关键文件：**
- `ChromosomeLibFabric.java` - Fabric 模组入口
- `FabricPlatformHelper.java` - 平台接口实现
- `event/CLFabricEvents.java` - Fabric 事件桥接
- `test/` - JUnit 测试套件

#### Forge 模块（`forge/`）

**职责：**
- 实现 Common 模块的平台接口（`ForgePlatformHelper`）
- 桥接 Common 事件到 Forge 事件系统（`SolveAfterAssigningTraitEvent`, `SolveUnpairedChromosomesEvent`）
- Forge 特定的 Mixin（如果需要）

**关键文件：**
- `ChromosomeLibForge.java` - Forge 模组入口
- `ForgePlatformHelper.java` - 平台接口实现
- `event/` - Forge 平台事件封装

### BuildSrc 自定义插件

项目使用自定义 Gradle 插件管理多加载器构建：

- `multiloader-common.gradle` - Common 模块的通用配置
- `multiloader-loader.gradle` - Fabric/Forge 模块的通用配置

## 代码规范

### Javadoc 和注释语言

**重要：所有 Javadoc 和代码注释必须使用英文编写。**

示例：
```java
/**
 * Represents a chromosome instance in an entity. <br/>
 * Each chromosome contains multiple gene loci and determines specific traits.
 * 
 * @author liudongyu
 */
public class ChromosomeInstance {
    /**
     * Performs genetic recombination during breeding.
     *
     * @param parent1 The first parent's chromosome
     * @param parent2 The second parent's chromosome
     * @param random Random source for genetic crossover
     * @return The offspring's recombined chromosome
     */
    public static ChromosomeInstance recombine(
        ChromosomeInstance parent1,
        ChromosomeInstance parent2,
        RandomSource random
    ) {
        // Select genes from parents using Mendelian inheritance rules
        // ...
    }
}
```

### 其他规范

参考全局 CLAUDE.md 中的规范：
- 使用 Tab 缩进（不使用空格）
- K&R 大括号风格
- 每行代码不超过 150 字符
- Public 类和方法必须有 Javadoc
- 方法体内适当添加注释说明关键逻辑

## 依赖关系

- **Java**: 17 LTS
- **Minecraft**: 1.20.1
- **Fabric Loader**: 0.16.10
- **Fabric API**: 0.92.1+1.20.1
- **Forge**: 47.2.0
- **Parchment Mappings**: 2023.09.03（用于更好的反混淆名称）
- **Mixin**: 0.8.5
- **MixinExtras**: 0.4.1

## 常见开发任务

### 添加新的染色体/基因

1. 在 `common/src/.../BuiltInChromosomes.java` 中注册新染色体
2. 在适当的包中定义基因和性状
3. 确保拓扑排序正确（基因依赖关系）
4. 在两个平台模块中都测试

### 添加平台特定功能

1. 在 `common/src/.../platform/services/` 中定义接口
2. 在 `fabric/` 和 `forge/` 中分别实现
3. 通过 `PlatformHelper` 获取实现

### 修改 Mixin

- 平台通用的 Mixin 放在 `common/src/.../mixin/`
- 平台特定的 Mixin 放在对应加载器模块的 `mixin/` 包中
- 修改后需要在两个平台上都测试

## 调试技巧

- 使用 IDEA 直接运行 Gradle 任务中的 `runClient`/`runServer` 配置
- 日志工具：使用 `CLLogger` 类（位于 `common/util/`）
- Mixin 调试：添加 `-Dmixin.debug.verbose=true` 到 VM 参数
- 测试失败时：查看控制台输出的基因型分布，验证是否接近理论值

## 版本兼容性说明

- 当前版本支持 Minecraft 1.20 - 1.20.1（`[1.20, 1.20.2)`）
- 添加新版本支持时，需要同时更新 Common、Fabric 和 Forge 三个模块
- 跨版本开发应优先考虑使用稳定的 Minecraft API（如 Registry、Entity 等）

## 相关文档

- 架构图：`docs/Application Architecture Diagram.png`
- 实体关系图：`docs/ER.png`
- 染色体/基因概念图：`docs/Chromosome.png`、`docs/Gene.png`、`docs/Gene Locus.png`