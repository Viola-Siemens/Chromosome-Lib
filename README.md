# Chromosome Lib

[![CurseForge](https://img.shields.io/curseforge/dt/chromosomelib?logo=curseforge&label=CurseForge)](https://www.curseforge.com/minecraft/mc-mods/chromosome-lib)
[![Modrinth](https://img.shields.io/modrinth/dt/chromosome-lib?logo=modrinth&label=Modrinth)](https://modrinth.com/mod/chromosome-lib)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-brightgreen)](https://minecraft.net)
[![Forge](https://img.shields.io/badge/Forge-47.2.0-red)](https://minecraftforge.net)
[![Fabric](https://img.shields.io/badge/Fabric-0.16.10-blue)](https://fabricmc.net)

A Minecraft library mod that introduces a complete chromosome and gene system, enabling realistic genetic inheritance for any entity. Designed for both mod developers who want to add deep genetic mechanics to their mobs, and players who enjoy scientific breeding gameplay.

## Downloads

| Platform   | Link |
|------------|------|
| CurseForge | [chromosome-lib](https://www.curseforge.com/minecraft/mc-mods/chromosome-lib) |
| Modrinth   | [chromosome-lib](https://modrinth.com/mod/chromosome-lib) |

## Features

### Realistic Genetics Simulation

- **Mendelian Inheritance** — Follows the Law of Segregation and Law of Independent Assortment. Offspring ratios match classic 3:1 and 9:3:3:1 distributions.
- **Dominant & Recessive Genes** — Dominant genes express with a single copy; recessive genes require both copies to be expressed.
- **Gene Mutation** — Low-probability mutations during breeding or natural spawning produce rare new traits.
- **Chromosomal Crossover** — Simulates meiotic recombination to increase genetic diversity among siblings.
- **Epistasis** — One gene can suppress the expression of another, enabling complex multi-gene trait control.

### Built-in Vanilla Mob Support

Chromosome Lib ships with genetic data for **20 vanilla breedable mobs**, each using real-world chromosome counts:

| Entity      | Chromosomes (pairs) | Sex System | Notes |
|-------------|---------------------|------------|-------|
| Axolotl     | 14                  | XY         | 5 color variants |
| Bee         | 16                  | Haplodiploid | Workers haploid, queens diploid |
| Cat         | 19                  | XY         | Multiple coat and eye colors |
| Chicken     | 39                  | ZW         | Hen ZW, Rooster ZZ |
| Cow         | 30                  | XY         | Includes Mooshroom |
| Donkey      | 31                  | XY         | Can breed with horses to produce mules |
| Fox         | 17                  | XY         | Red and snow fox variants |
| Frog        | 13                  | XY         | 3 temperature-based variants |
| Goat        | 30                  | XY         | Screaming goat support |
| Horse       | 32                  | XY         | Can breed with donkeys to produce mules |
| Mule        | 31.5                | XY         | Sterile horse-donkey hybrid |
| Ocelot      | 18                  | XY         | Shares genetic traits with cats |
| Panda       | 21                  | XY         | Multiple personality types |
| Parrot      | 40                  | ZW         | 5 color variants |
| Pig         | 19                  | XY         | Includes variant forms |
| Polar Bear  | 37                  | XY         |       |
| Rabbit      | 22                  | XY         | Multiple coat colors and the Killer Bunny |
| Sheep       | 27                  | XY         | All 16 wool colors |
| Turtle      | 28                  | XY         |       |
| Frog        | 13                  | XY         | 3 biome-based variants |

> Chromosome counts and sex determination systems are based on real-world biology, simplified where needed for game balance.

### Mod Developer API

- **Chromosome Registration API** — Register chromosomes (autosomes and sex chromosomes) for any entity type with a simple API.
- **Gene & Trait Registration API** — Define gene loci on chromosomes and map genes to traits.
- **Trait Application Events** — React when traits are resolved to modify entity attributes, appearance, or AI behavior.
- **Full Javadoc** — Every public API is documented.
- **Reference Implementations** — All 20 vanilla mobs serve as complete usage examples.
- **Cross-loader Support** — Targets both Forge and Fabric from a single codebase.
- **Debug Tooling** — `CLLogger` utility and an in-game command for querying an entity's genotype.

## Architecture

The project uses a three-module MultiLoader architecture:

```
Chromosome-Lib/
├── common/    # Platform-agnostic core: chromosomes, genes, traits, events, API
├── fabric/    # Fabric compatibility layer and integration tests
└── forge/     # Forge compatibility layer
```

Common code never imports Forge or Fabric APIs directly. Platform-specific behavior is accessed through the `PlatformHelper` service abstraction.

## Using as a Dependency

Chromosome Lib is published to a GitHub Pages Maven repository. Add the following to your `build.gradle` to declare it as a dependency:

```groovy
repositories {
    maven {
        name = "Viola-Siemens Maven"
        url = "https://viola-siemens.github.io/pages/maven/"
    }
}
```

Then declare the dependency for your loader:

**Forge**
```groovy
dependencies {
    implementation fg.deobf("com.hexagram2021.chromosomelib:chromosomelib-forge:${chromosomelib_version}")
}
```

**Fabric / Quilt (Loom)**
```groovy
dependencies {
    modImplementation "com.hexagram2021.chromosomelib:chromosomelib-fabric:${chromosomelib_version}"
}
```

Set the version in your `gradle.properties`:

```properties
chromosomelib_version=0.0.1+1.20.1
```

> Latest releases are listed on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/chromosome-lib) and [Modrinth](https://modrinth.com/mod/chromosome-lib).

> Example: [Inheritable Coat Color](https://github.com/Viola-Siemens/Inheritable-Coat-Color/blob/dev/MultiLoader-1.20.1/common/build.gradle#L17-L31)

## Requirements

| Dependency    | Version         |
|---------------|-----------------|
| Minecraft      | 1.20 – 1.20.1   |
| Java           | 17+             |
| Forge          | 47.2.0+         |
| Fabric Loader  | 0.16.10+        |
| Fabric API     | 0.92.1+1.20.1   |

Chromosome Lib is a **library mod**. It does not add gameplay content on its own; it provides the foundation for other mods to build upon.

## Building from Source

```bash
# Build all modules
./gradlew build

# Build a specific loader
./gradlew :fabric:build
./gradlew :forge:build

# Run tests
./gradlew test

# Run the Fabric client
./gradlew :fabric:runClient
```

> Note: Integration tests use probabilistic models to verify Mendelian ratios. Due to statistical variance, tests have approximately a 1.25% chance of failing even when the implementation is correct. Re-running once is usually sufficient.

## Roadmap

Planned features for upcoming versions:

- **v0.1.0** — Sex determination system (XY/ZW), sex-linked inheritance, visual sexual dimorphism, same-sex breeding restrictions, Jade/WTHIT integration
- **Future** — Datapack-defined genes and traits, KubeJS scripting support, additional Minecraft version support (1.20.x, 1.21.x), polyploidy, chromosomal aberrations

## License

[MIT License](LICENSE) — Copyright (c) Liu Dongyu

## Links

- [GitHub Repository](https://github.com/Viola-Siemens/Chromosome-Lib)
- [Issue Tracker](https://github.com/Viola-Siemens/Chromosome-Lib/issues)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/chromosome-lib)
- [Modrinth](https://modrinth.com/mod/chromosome-lib)