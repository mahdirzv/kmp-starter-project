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

These are real Gradle/KMP pack modules so Android Studio treats them as Kotlin/Compose source, not loose reference files.

```text
kmp/auth/
  shared/src/commonMain/kotlin/auth/
    data/
    domain/
    di/
  composeApp/src/commonMain/kotlin/auth/ui/

kmp/room_data/
  shared/src/commonMain/kotlin/tasks/
    data/
    domain/
    di/

kmp/ui_theme/
  composeApp/src/commonMain/kotlin/ui/theme/
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
- Keep pack internals flat inside the source root, but do not remove the KMP module/source-set scaffolding those packs need to stay usable.

## Working with an AI agent?

Send [`AGENTS.md`](./AGENTS.md) to your agent instead of this file. It contains the repo-specific structure, conventions, and update rules.
