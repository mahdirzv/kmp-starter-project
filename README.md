# KMP Base Scaffold

A minimal Kotlin Multiplatform starter for Android, iOS, and Desktop (JVM). Keep the base small, tokenized, and easy to extend with optional packs.

---

## Quick start

```bash
./gradlew :shared:build
./gradlew :composeApp:build
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

Open the `iosApp` directory in Xcode and run it there.

## Commands

| Command | Description |
|---|---|
| `./gradlew :shared:build` | Compile the shared module |
| `./gradlew :composeApp:build` | Build the Compose Multiplatform app |
| `./gradlew :composeApp:assembleDebug` | Build the Android debug APK |
| `./gradlew :composeApp:run` | Run the Desktop app |

## Project structure

```
shared/                     # KMP shared module (navigation, DI, smoke-test logic)
composeApp/                 # Compose Multiplatform UI module
  src/commonMain/...        # shared UI + theme tokens
  src/androidMain/...       # Android entry points
  src/jvmMain/...           # Desktop entry point
iosApp/                     # Native iOS app (SwiftUI entry point)
gradle/libs.versions.toml   # Dependency versions (single source of truth)
```

## Architecture

This base includes:
- Gradle / settings / version-catalog plumbing
- shared source-set structure
- platform entry points for Android / iOS / JVM
- app shell + navigation shell
- DI baseline
- theme token system
- one minimal smoke-test screen

Not included by default:
- networking / HTTP client
- repository layer
- auth provider implementations
- server module
- product/demo feature flows

## Working with an AI agent?

Send [`AGENTS.md`](./AGENTS.md) to your agent instead of this file. It contains the project-specific context, conventions, and extension notes.
