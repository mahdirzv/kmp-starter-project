# KMP Base Scaffold

**Updated:** 2026-04-16

Minimal Kotlin Multiplatform scaffold targeting Android, iOS, and Desktop (JVM).

## Stack

- **Kotlin** 2.2.10, **Compose Multiplatform** 1.10.3
- **Koin** 4.1.0 — dependency injection baseline
- **Decompose** 3.5.0 + **Essenty** 2.5.0 — lifecycle-aware navigation
- **SKIE** 0.10.6 — iOS Swift interop for flows/sealed classes
- **Material 3** 1.10.0-alpha05 — Compose theming
- AGP 9.1.0, Android compileSdk 36, minSdk 24

## Project Structure

```text
shared/                     # Shared app logic, feature components, root wiring, DI
composeApp/                 # Compose Multiplatform UI module
  src/commonMain/...        # App + feature UIs + shared theme tokens
  src/androidMain/...       # Android entry points
  src/jvmMain/...           # Desktop entry point
iosApp/                     # Native iOS app (SwiftUI entry point)
kmp/                        # Optional KMP pack modules with real source-set roots
gradle/libs.versions.toml   # Dependency versions (single source of truth)
```

## Live App Layout

```text
composeApp/src/commonMain/kotlin/com/example/kmp_starter_project/
  App.kt
  home/ui/HomeScreen.kt
  settings/ui/SettingsScreen.kt
  root/ui/RootContent.kt
  ui/theme/

shared/src/commonMain/kotlin/com/example/kmp_starter_project/
  home/HomeComponent.kt
  home/DefaultHomeComponent.kt
  settings/SettingsComponent.kt
  settings/DefaultSettingsComponent.kt
  root/RootComponent.kt
  root/DefaultRootComponent.kt
  di/Modules.kt
```

## Optional Pack Modules

These are real Gradle/KMP pack modules, not loose reference folders or nested submodule trees.
Included module ids:
- `:kmp:auth`
- `:kmp:room_data`
- `:kmp:ui_theme`

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

## Scope

### Keep in base
- Gradle / settings / version-catalog plumbing
- platform entry points for Android / iOS / JVM
- small root shell + DI baseline
- feature-first structure for app and shared code
- theme token system
- expect/actual scaffolding when needed
- optional pack modules under `kmp/` with real source-set roots

### Not included by default
- networking / HTTP client
- production repository implementations
- auth provider integrations
- server module
- web target
- large product/demo flows

## Where to Look

| Task | Location | Notes |
|------|----------|-------|
| Add a screen | `composeApp/src/commonMain/.../{feature}/ui/` | Prefer `home/ui`, `settings/ui`, etc. |
| Add shared feature logic | `shared/src/commonMain/.../{feature}/` | Keep component + implementation together unless a deeper split adds value |
| Change root wiring | `shared/.../root/` + `composeApp/.../root/ui/` | Root owns navigation shell only |
| Change theme | `composeApp/.../ui/theme/` | Colors, typography, tokens, theme |
| Update reusable pack modules | `kmp/` | Keep each pack as a single imported Gradle module with flat source-set roots under that pack |
| Platform-specific code | `shared/src/{platform}Main/` | Use `expect`/`actual` |
| DI wiring | `shared/.../di/Modules.kt` | Keep wiring minimal |

## Initialization Flow

```text
Android:  StarterApp.onCreate → startKoin → MainActivity → RootComponent → App
Desktop:  main() → startKoin → RootComponent → Window { App }
iOS:      iOSApp → initKoin → RootComponent → SwiftUI bridge
```

## Architecture & Conventions

- **commonMain** stays platform-neutral.
- Prefer feature-first paths over metadata-heavy nesting.
- Do not reintroduce folders like `ui/screens/*`, `presentation/*`, or `navigation/tabs/*` unless there is a strong reason.
- Keep shared UI tokenized; avoid hardcoded colors, spacing, or sizing.
- Keep the base app minimal; move reusable extensions into packs.
- Keep pack internals flat inside each pack's `src/...` roots, and keep the pack directory itself as the module root so the IDE imports it cleanly.
- Use `expect`/`actual` for platform-specific logic.
- Repository/network/auth/server concerns stay out of the live base unless intentionally added.

## Build & Verify

```shell
./gradlew --no-daemon build
```

Use the full root build as the default verification step after structural changes.

## Agent Rules

- Treat `README.md` as the user-facing overview and keep `AGENTS.md` as the implementation-oriented companion.
- If you change structure, update both files in the same commit.
- Prefer documenting actual live paths, not aspirational ones.
- Keep pack examples aligned with the real `kmp/` tree.
- Do not describe the scaffold using outdated nested paths after refactors.

## Optional packs — reference modules, not wired dependencies

`kmp/auth`, `kmp/room_data`, and `kmp/ui_theme` are compiled as separate Gradle modules and included in `settings.gradle.kts`. **They are not currently consumed by `composeApp` or `shared`.** `composeApp/build.gradle.kts` has only `implementation(projects.shared)` — no dependency on `:kmp:*`.

Treat them as reference implementations: skeletons and DI boilerplate you follow when you decide to wire a feature. Not ready-to-use libraries.

### Why not wired yet?

Each pack's `build.gradle.kts` declares only `kotlin { jvm() }`. `composeApp/commonMain` targets android + ios + jvm and cannot depend on a jvm-only module from its common source set. Full cross-target wiring is tracked as a scaffold-factory v0.5.0 item.

### How to wire `kmp:ui_theme` into `composeApp` (the simplest pack)

1. In `kmp/ui_theme/build.gradle.kts` add `androidTarget()` and the iOS targets to the `kotlin { ... }` block, mirroring `composeApp/build.gradle.kts`.
2. In `composeApp/build.gradle.kts` add `implementation(projects.kmp.uiTheme)` to `commonMain.dependencies` (Gradle type-safe project accessors are enabled in `settings.gradle.kts`).
3. In `composeApp/src/commonMain/kotlin/.../ui/theme/Theme.kt` delete the duplicate and import `ui.theme.AppTheme` from the pack instead.

### Same pattern for `:kmp:auth` / `:kmp:room_data`

1. Expand targets as above.
2. Add `implementation(projects.kmp.auth)` (or `.roomData`) to `composeApp`.
3. Register the pack's Koin module in `shared/src/commonMain/kotlin/.../di/Modules.kt`:
   ```kotlin
   import auth.di.authModule
   import tasks.di.dataModule

   val sharedModules = listOf(sharedModule, authModule, dataModule)
   ```
4. Consume the pack's API from `composeApp` or a feature component.

### Why ship them at all?

Saves the blank-page step when you add auth or storage later — the skeleton, DI boilerplate, and domain interfaces are already there. Copy into the right module or follow the wiring steps above.
