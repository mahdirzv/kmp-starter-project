# KMP Base Scaffold

A minimal Kotlin Multiplatform starter for Android, iOS, and Desktop (JVM).

The scaffold is intentionally small, feature-first, and easy to extend with optional packs. The live app modules stay clean, while reusable pack modules live under `kmp/` with proper KMP source-set roots so they remain usable in the IDE.

---

## Quick start

```bash
./gradlew --no-daemon build
```

## Targets

### Android

```bash
./gradlew :composeApp:assembleDebug
```

### Desktop (JVM)

```bash
./gradlew :composeApp:run
```

### iOS

Open `iosApp` in Xcode and run it there.

## Commands

| Command | Description |
|---|---|
| `./gradlew --no-daemon build` | Full repo verification build |
| `./gradlew :shared:build` | Compile the shared module |
| `./gradlew :composeApp:build` | Build the Compose Multiplatform app |
| `./gradlew :composeApp:assembleDebug` | Build the Android debug APK |
| `./gradlew :composeApp:run` | Run the Desktop app |

## Project structure

```text
shared/                     # Shared app logic, root wiring, feature components, DI
composeApp/                 # Compose Multiplatform UI module
  src/commonMain/...        # App + feature UIs + shared theme tokens
  src/androidMain/...       # Android entry points
  src/jvmMain/...           # Desktop entry point
iosApp/                     # Native iOS app (SwiftUI entry point)
kmp/                        # Optional KMP pack modules with real source-set roots
gradle/libs.versions.toml   # Dependency versions (single source of truth)
```

### Live app layout

```text
composeApp/src/commonMain/kotlin/.../
  home/ui/HomeScreen.kt
  settings/ui/SettingsScreen.kt
  root/ui/RootContent.kt
  ui/theme/*

shared/src/commonMain/kotlin/.../
  home/HomeComponent.kt
  settings/SettingsComponent.kt
  root/RootComponent.kt
  di/Modules.kt
```

### Optional pack modules

These are real Gradle/KMP pack modules so Android Studio imports `kmp/auth`, `kmp/room_data`, and `kmp/ui_theme` directly as Kotlin/Compose source modules instead of nested reference trees.
Included Gradle module ids:
- `:kmp:auth`
- `:kmp:room_data`
- `:kmp:ui_theme`

> **Important:** these packs are **reference modules, not wired dependencies**. `composeApp`/`shared` do not import from them today — each pack is jvm-only and the app targets android + ios + jvm. Treat them as implementation blueprints: skeleton + DI + domain interfaces you copy or wire in manually. See [`AGENTS.md`](./AGENTS.md#optional-packs--reference-modules-not-wired-dependencies) for the wiring steps.

```text
kmp/auth/
  src/commonMain/kotlin/auth/
    data/
    domain/
    di/
    ui/

kmp/room_data/
  src/commonMain/kotlin/tasks/
    data/
    domain/
    di/

kmp/ui_theme/
  src/commonMain/kotlin/ui/theme/
```

## Architecture

This base includes:
- Gradle / settings / version-catalog plumbing
- platform entry points for Android / iOS / JVM
- a small root shell
- feature-first shared/component structure
- DI baseline
- theme token system
- optional pack modules under `kmp/` with usable source-set roots

Not included by default in the live scaffold:
- networking / HTTP client
- production repository implementations
- auth provider integrations
- server module
- web target
- large product/demo flows

## Conventions

- Prefer feature-first paths over metadata-heavy nesting.
- Avoid folders like `ui/screens/*`, `presentation/*`, or `navigation/tabs/*` unless they add real value.
- Keep shared UI tokenized; avoid hardcoded spacing, radii, and colors.
- Keep the base app small; push reusable extensions into packs.
- Keep pack internals flat inside each pack's `src/...` roots so the pack directory itself remains the canonical IDE module.

## Working with an AI agent?

Send [`AGENTS.md`](./AGENTS.md) to your agent instead of this file. It contains the repo-specific structure, conventions, and update rules.

## Consumed by the scaffolding skill

This repo is a canonical source for [`scaffold-factory`](https://github.com/mahdirzv/scaffold-factory). When an agent runs `scaffold.py create kmp MyApp`, it shallow-clones a pinned tag of this repo, copies it into the destination, then applies the find/replace rules declared in [`.scaffold.json`](./.scaffold.json) to rename the package namespace and project identifiers.

Packs selection is subtractive — the scaffold starts from the full repo and deletes pack directories the user did not request, stripping the matching `include(...)` line from `settings.gradle.kts`. See `.scaffold.json` for the pack map.

To consume manually without the skill: clone, then run find/replace yourself using the pairs in `.scaffold.json`.

