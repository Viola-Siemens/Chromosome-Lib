# Chromosome-Lib 项目上下文

## 项目概述

**Chromosome-Lib** 是一个为 Minecraft 添加染色体和基因系统的跨版本、跨加载器库模组喵~

- **项目类型**：Minecraft 模组库（Library Mod）
- **架构模式**：多加载器架构（MultiLoader）
- **支持平台**：Fabric 和 Forge
- **许可证**：LGPL-2.1

### 核心功能

- 染色体系统（Chromosome System）
- 基因座位系统（Gene Locus System）
- 性状系统（Trait System）
- 遗传算法（繁殖、基因重组、显隐性关系）
- 自定义注册表（Registry）

## 项目结构

```
Chromosome-Lib/
├── common/          # 核心功能模块（平台无关）
│   └── src/main/
│       ├── java/com/hexagram2021/chromosomelib/
│       │   ├── common/          # 核心业务逻辑
│       │   │   ├── chromosome/  # 染色体定义
│       │   │   ├── gene/        # 基因定义
│       │   │   ├── gene_locus/  # 基因座位
│       │   │   ├── trait/       # 性状系统
│       │   │   ├── entity/      # 实体接口
│       │   │   ├── util/        # 工具类
│       │   │   └── command/     # 命令系统
│       │   ├── event/           # 事件定义
│       │   ├── mixin/           # 通用 Mixin
│       │   ├── platform/        # 平台抽象接口（SPI）
│       │   └── registry/        # 注册表系统
│       └── resources/
│           ├── chromosomelib.accesswidener
│           └── chromosomelib.mixins.json
├── fabric/          # Fabric 平台兼容层
│   └── src/
│       ├── main/    # Fabric 实现
│       └── test/    # JUnit 集成测试
├── forge/           # Forge 平台兼容层
│   └── src/main/    # Forge 实现
├── buildSrc/        # 自定义 Gradle 插件
└── docs/            # 设计文档和架构图
```

## 构建与运行

### 环境要求

- **JDK**: 17 LTS
- **Gradle**: 项目自带 Gradle Wrapper

### 构建命令

```bash
# 构建所有模块
./gradlew build

# 构建特定模块
./gradlew :common:build
./gradlew :fabric:build
./gradlew :forge:build

# 清理构建产物
./gradlew clean
```

### 运行测试

```bash
# 运行所有测试
./gradlew test

# 运行特定模块测试
./gradlew :fabric:test
./gradlew :forge:test

# 运行单个测试类
./gradlew :fabric:test --tests "com.hexagram2021.chromosomelib.test.fabric.SheepTest"

# 运行单个测试方法
./gradlew :fabric:test --tests "com.hexagram2021.chromosomelib.test.fabric.SheepTest.testSheepBreedSegregationRatio1"
```

**测试注意事项**：
- 集成测试使用概率模型验证遗传规律（如孟德尔分离比 3:1）
- 即使代码正确，测试仍有约 1.25% 概率因统计波动失败
- 测试失败时重新运行通常可通过

### 运行游戏

```bash
# Fabric 客户端/服务器
./gradlew :fabric:runClient
./gradlew :fabric:runServer

# Forge 客户端/服务器
./gradlew :forge:runClient
./gradlew :forge:runServer

# Forge 数据生成
./gradlew :forge:runData
```

## 架构设计

### 模块职责

| 模块 | 职责 |
|------|------|
| **common** | 核心业务逻辑、平台无关代码、通用 Mixin、事件定义、平台抽象接口 |
| **fabric** | Fabric 平台接口实现、事件桥接、集成测试 |
| **forge** | Forge 平台接口实现、事件桥接、数据生成 |

### 关键设计原则

1. **Common 模块不得依赖任何特定加载器 API**（Fabric API / Forge API）
2. **平台特定功能通过 SPI 接口抽象**（`platform.services.IPlatformHelper`）
3. **Common 模块作为依赖被 Fabric 和 Forge 引用**

### 核心类说明

| 类 | 说明 |
|----|------|
| `Chromosome` | 染色体定义，包含基因座位映射 |
| `ChromosomeInstance` | 实体中的染色体实例（包含等位基因） |
| `Gene` | 基因定义，支持显隐性关系图 |
| `GeneLocus` | 基因座位，定义基因在染色体上的位置 |
| `Trait` / `TraitType` | 性状系统，基因表达的结果 |
| `Breeders` | 繁殖算法工具类 |

## 代码规范

### 通用规范

- **缩进**：使用 Tab，不使用空格
- **大括号**：K&R 风格（起始大括号不换行）
- **行宽**：每行不超过 150 字符
- **方法长度**：每个方法不超过 300 行

### Javadoc 规范

**重要：所有 Javadoc 和代码注释使用英文编写**喵~

```java
/**
 * Represents a chromosome in the genetic system.
 *
 * @author liudongyu
 */
public class Chromosome {
    /**
     * Gets the index of this chromosome.
     *
     * @return The chromosome index
     */
    public int index() {
        return this.index;
    }
}
```

### 命名约定

- **类名**：大驼峰式（`ChromosomeInstance`）
- **方法名**：小驼峰式（`getGeneLoci`）
- **变量名**：小驼峰式（`geneLocus`）
- **常量名**：全大写加下划线（`CHROMOSOMES_TAG`）
- **包名**：全小写（`com.hexagram2021.chromosomelib.common.chromosome`）

## 依赖信息

| 依赖 | 版本 |
|------|------|
| Minecraft | 1.20.1 |
| Fabric Loader | 0.16.10 |
| Fabric API | 0.92.1+1.20.1 |
| Forge | 47.2.0 |
| Parchment Mappings | 2023.09.03 |
| Mixin | 0.8.5 |
| MixinExtras | 0.4.1 |
| JetBrains Annotations | 25.0.0 |

## 配置说明

### gradle.properties 关键配置

```properties
# 项目版本
version=0.0.1+1.20.1
group=com.hexagram2021.chromosomelib

# Minecraft 版本
minecraft_version=1.20.1
minecraft_version_range=[1.20, 1.20.2)

# 模组信息
mod_name=Chromosome Lib
mod_author=Liu Dongyu
mod_id=chromosomelib
```

## 开发指南

### 添加新的染色体/基因

1. 在 `BuiltInChromosomes.java` 中注册内置染色体
2. 定义基因和性状类
3. 确保基因拓扑排序正确（处理显隐性依赖）
4. 在 Fabric 和 Forge 两个平台上测试

### 添加平台特定功能

1. 在 `common/src/.../platform/services/` 中定义 SPI 接口
2. 在 `fabric/` 和 `forge/` 中分别实现接口
3. 通过 `Services.PLATFORM_HELPER` 获取实现

### 修改 Mixin

- 通用 Mixin 放在 `common/src/.../mixin/`
- 平台特定 Mixin 放在对应加载器模块中
- 修改后需在两个平台上测试

## 调试技巧

- **日志工具**：使用 `CLLogger` 类
- **Mixin 调试**：添加 VM 参数 `-Dmixin.debug.verbose=true`
- **IDEA 运行**：直接运行 Gradle 任务中的 `runClient`/`runServer` 配置

## 相关文档

- **架构图**：`docs/Application Architecture Diagram.png`
- **实体关系图**：`docs/ER.png`
- **概念图**：`docs/Chromosome.png`、`docs/Gene.png`、`docs/Gene Locus.png`
- **需求文档**：`docs/v0/`、`docs/v1/`

## 常见问题

**Q: 测试偶尔失败怎么办？**
A: 由于测试使用概率模型，约 1.25% 的失败率是正常的。重新运行测试通常可通过。

**Q: 如何查看构建产物？**
A: 构建产物位于各模块的 `build/libs/` 目录下。

**Q: 如何添加新的 Minecraft 版本支持？**
A: 需要更新 `gradle.properties` 中的版本配置，并可能需要调整 Common、Fabric、Forge 三个模块的代码以适配新版 API。
