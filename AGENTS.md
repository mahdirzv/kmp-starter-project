# KMP Starter Project

**Generated:** 2026-04-03 | **Commit:** abcd759 | **Branch:** main

Kotlin Multiplatform starter template targeting Android, iOS, Desktop (JVM), Web, and Server.

## Stack

- **Kotlin** 2.2.10, **Compose Multiplatform** 1.10.3
- **Ktor** 3.3.3 — HTTP client (OkHttp/Darwin/CIO engines) + server (Netty)
- **Koin** 4.1.0 — Dependency injection
- **Decompose** 3.5.0 + **Essenty** 2.5.0 — Lifecycle-aware navigation
- **SKIE** 0.10.6 — iOS Swift interop for flows/sealed classes
- **kotlinx-serialization** 1.8.0 — JSON serialization
- **Material 3** 1.10.0-alpha05 — Compose theming
- AGP 9.1.0, Android compileSdk 36, minSdk 24

## Project Structure

```
shared/                     # KMP shared module (business logic, navigation, DI)
  src/commonMain/kotlin/de/sh3n/kmp_starter_project/
    auth/                   # AuthConfig sealed interface (Disabled/Anonymous/Bearer)
    data/remote/            # HttpClientFactory, ApiService (Ktor client)
    data/repository/        # Repository implementations
    domain/repository/      # Repository interfaces
    di/                     # Koin modules (networkModule, repositoryModule, viewModelModule)
    navigation/             # Decompose root navigation (RootComponent, ChildPages)
    navigation/tabs/        # Per-tab components (HomeTab, SettingsTab)
    presentation/home/      # HomeComponent + DefaultHomeComponent
    presentation/detail/    # DetailComponent + DefaultDetailComponent
    presentation/settings/  # SettingsComponent + DefaultSettingsComponent
    util/                   # Shared utilities
    theme/                  # (reserved for shared theme tokens if needed)

composeApp/                 # Compose Multiplatform UI module
  src/commonMain/kotlin/de/sh3n/kmp_starter_project/
    App.kt                  # App entry point (Koin init, theme)
    ui/RootContent.kt       # ChildPages composable + NavigationBar
    ui/HomeTabContent.kt    # Home tab inner ChildStack rendering
    ui/screens/home/        # HomeScreen composable
    ui/screens/detail/      # DetailScreen composable
    ui/screens/settings/    # SettingsScreen composable
    ui/theme/               # Color.kt, Type.kt, Theme.kt, Tokens.kt (spacing/sizing/radius)
  src/androidMain/          # MainActivity, StarterApp (Application class)
  src/jvmMain/              # Desktop main.kt

iosApp/                     # Native iOS app (SwiftUI)
  iosApp/iOSApp.swift       # App entry point
  iosApp/ContentView.swift  # Native TabView synced to Decompose ChildPages
  iosApp/DecomposeHelpers.swift  # ObservableValue + FlowObserver bridging

server/                     # Ktor server module (Netty)

gradle/libs.versions.toml   # All dependency versions (single source of truth)
```

## WHERE TO LOOK

| Task | Location | Notes |
|------|----------|-------|
| Add new screen | `shared/.../presentation/{name}/` + `composeApp/.../ui/screens/{name}/` | Interface+impl in shared, composable in composeApp |
| Add new tab | `shared/.../navigation/tabs/` + update `RootComponent` | Add to `Tab` sealed class + `ChildPages` config |
| Add API endpoint | `shared/.../data/remote/ApiService.kt` | Uses Ktor HttpClient from HttpClientFactory |
| Add repository | `shared/.../domain/repository/` (interface) + `shared/.../data/repository/` (impl) | Wire in `shared/.../di/Modules.kt` |
| Change theme | `composeApp/.../ui/theme/` | Color.kt, Type.kt, Tokens.kt, Theme.kt |
| Platform-specific code | `shared/src/{platform}Main/` | Use `expect`/`actual` in commonMain |
| iOS native UI | `iosApp/iosApp/ContentView.swift` | SwiftUI synced to Decompose ChildPages |
| Android entry | `composeApp/src/androidMain/.../MainActivity.kt` | StarterApp for Application class |
| Desktop entry | `composeApp/src/jvmMain/.../main.kt` | Koin + DefaultComponentContext |
| DI wiring | `shared/.../di/Modules.kt` | networkModule, repositoryModule, viewModelModule |

## Initialization Flow

```
Android:  StarterApp.onCreate → startKoin(sharedModules) → MainActivity → DefaultRootComponent → App → RootContent → ChildPages
Desktop:  main() → startKoin(sharedModules) → DefaultRootComponent → Window { App } → RootContent → ChildPages
iOS:      iOSApp.init → initKoin() → KoinHelper.createRootComponent → DefaultRootComponent → TabView synced to ChildPages
```

## Architecture & Conventions

### Clean Architecture Layers
- **domain/** — Interfaces only (repository contracts). No implementation details.
- **data/** — Implementations (Ktor HTTP client, repository impls). Depends on domain.
- **presentation/** — Components with lifecycle-scoped coroutines. Expose `StateFlow<UiState>`.
- **navigation/** — Decompose components. Root owns `ChildPages`, tabs own `ChildStack`.
- **di/** — Koin modules wire everything. Platform-specific modules in `iosMain`/`androidMain`.

### Navigation Pattern (Decompose)
- Root uses `ChildPages` for tabs (Home + Settings) — tabs stay alive on switch.
- Each tab owns its own `ChildStack` for push/pop navigation within that tab.
- Use `pushNew` (not `push`) to avoid `@DelicateDecomposeApi`.
- iOS renders native `TabView` synced via `Binding` to Decompose pages index.
- Android/Desktop render `NavigationBar` from Compose Material 3.

### Naming Conventions
- Component interfaces: `HomeComponent`, `SettingsComponent` (in their own files)
- Component implementations: `DefaultHomeComponent`, `DefaultSettingsComponent`
- UI state: `HomeUiState`, `SettingsUiState` (sealed interface inside component file)
- Screens: `HomeScreen.kt`, `DetailScreen.kt` (composable functions, not classes)
- Package: `de.sh3n.kmp_starter_project`

### Theming
- All values tokenized — no hardcoded colors, spacing, or sizing in screens.
- Use `AppTokens.Spacing.*`, `AppTokens.Sizing.*`, `AppTokens.Radius.*` from `Tokens.kt`.
- Colors in `Color.kt`, typography in `Type.kt`, composed in `Theme.kt`.

### Auth
- `AuthConfig` is a sealed interface: `Disabled`, `Anonymous`, `Bearer(token)`.
- Configured via Koin. Ktor auth plugin installs only when config requires it.

### Dependency Injection
- All wiring through Koin modules in `shared/di/Modules.kt`.
- iOS has `KoinIOS.kt` with `initKoinIOS()` for Swift-side init.
- Desktop/JVM calls `startKoin` in `main.kt`.
- Android calls `startKoin` in `StarterApp` Application class.

## Build & Verify

```shell
# Shared module (all targets)
./gradlew :shared:build

# Android APK
./gradlew :composeApp:assembleDebug

# Desktop run
./gradlew :composeApp:run

# Server run
./gradlew :server:run

# iOS — open iosApp/ in Xcode and build, or use Xcode CLI

# Web (JS)
./gradlew :shared:jsBrowserDevelopmentLibraryDistribution
npm install && npm run start
```

After any code change, verify at minimum:
1. `./gradlew :shared:build` — shared module compiles for all targets
2. `./gradlew :composeApp:build` — compose app compiles for JVM + Android
3. Check `lsp_diagnostics` on changed files — no new errors

## Boundaries

- Never hardcode colors, spacing, or sizing — use theme tokens.
- Never suppress type errors (`as Any`, `@Suppress("UNCHECKED_CAST")`).
- Never modify `gradle/libs.versions.toml` versions without checking KMP compatibility.
- Don't add platform-specific code to `commonMain` — use `expect`/`actual` or platform source sets.
- iOS `No such module 'Shared'` LSP errors are expected without Xcode build — not real errors.
- Repository interfaces live in `domain/`, implementations in `data/` — never invert this.

## Known Gaps

- Web (React) target is scaffolded but not wired to the new navigation
- No tests written yet
- Settings screen is placeholder — no actual settings content
- SKIE flow bridging for iOS not fully exercised yet
