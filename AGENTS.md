# KMP Base Scaffold

**Generated:** 2026-04-03 | **Updated:** 2026-04-15 | **Branch:** trim/kmp-base

Minimal Kotlin Multiplatform scaffold targeting Android, iOS, and Desktop (JVM).

## Stack

- **Kotlin** 2.2.10, **Compose Multiplatform** 1.10.3
- **Koin** 4.1.0 — dependency injection baseline
- **Decompose** 3.5.0 + **Essenty** 2.5.0 — lifecycle-aware navigation
- **SKIE** 0.10.6 — iOS Swift interop for flows/sealed classes
- **Material 3** 1.10.0-alpha05 — Compose theming
- AGP 9.1.0, Android compileSdk 36, minSdk 24

## Project Structure

```
shared/                     # KMP shared module (navigation, DI, smoke-test logic)
composeApp/                 # Compose Multiplatform UI module
  src/commonMain/...        # shared UI + theme tokens
  src/androidMain/...       # Android entry points
  src/jvmMain/...           # Desktop entry point
iosApp/                     # Native iOS app (SwiftUI entry point)
gradle/libs.versions.toml   # Dependency versions (single source of truth)
```

## Scope

### Keep in base
- Gradle / settings / version-catalog plumbing
- shared source-set structure
- platform entry points for Android / iOS / JVM
- app shell + navigation shell
- DI baseline
- theme token system
- one minimal smoke-test screen
- expect/actual scaffolding when needed

### Not included by default
- networking / HTTP client
- repository layer
- auth provider implementations
- server module
- web target
- product/demo feature flows

## Where to Look

| Task | Location | Notes |
|------|----------|-------|
| Add a screen | `composeApp/src/commonMain/.../ui/screens/{name}/` | Keep it small and tokenized |
| Add a tab | `shared/.../navigation/tabs/` + root component | Only if the base really needs it |
| Change theme | `composeApp/.../ui/theme/` | Colors, typography, tokens, theme |
| Platform-specific code | `shared/src/{platform}Main/` | Use `expect`/`actual` |
| DI wiring | `shared/.../di/Modules.kt` | Keep the base wiring minimal |

## Initialization Flow

```
Android:  StarterApp.onCreate → startKoin → MainActivity → RootComponent → App
Desktop:  main() → startKoin → RootComponent → Window { App }
iOS:      iOSApp → initKoin → RootComponent → SwiftUI bridge
```

## Architecture & Conventions

- **commonMain** stays platform-neutral.
- Keep shared UI tokenized; avoid hardcoded colors, spacing, or sizing.
- Keep navigation and DI baseline only; move opinionated features to packs later.
- Prefer deleting unused modules and wiring rather than leaving dead code.
- Use `expect`/`actual` for platform-specific logic.
- Repository/network/auth/server concerns are out of scope for the base.

## Build & Verify

```shell
./gradlew :shared:build
./gradlew :composeApp:build
```

If a change breaks either target, fix the wiring cleanly before moving on.

## Known Gaps

- No feature packs yet
- Smoke test is intentionally minimal
- Tests are still sparse
- iOS build is validated indirectly through shared/common compilation and platform entry structure
