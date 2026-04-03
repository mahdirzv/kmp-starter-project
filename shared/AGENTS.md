# shared/ — KMP Shared Module

Business logic, navigation, networking, DI. All platform-agnostic code lives here.

## Structure

```
src/commonMain/kotlin/de/sh3n/kmp_starter_project/
├── auth/             # AuthConfig sealed interface (Disabled/Anonymous/Bearer)
├── data/
│   ├── model/        # Data transfer objects
│   ├── remote/       # HttpClientFactory, ApiService, PlatformEngine (expect)
│   └── repository/   # Repository implementations
├── domain/
│   ├── model/        # Domain models
│   └── repository/   # Repository interfaces (contracts only)
├── di/               # Koin modules: networkModule, repositoryModule, viewModelModule
├── navigation/       # RootComponent (ChildPages), DefaultRootComponent
│   └── tabs/         # HomeTabComponent, SettingsTabComponent (+ Default impls)
├── presentation/
│   ├── home/         # HomeComponent + DefaultHomeComponent (coroutine scope)
│   ├── detail/       # DetailComponent + DefaultDetailComponent
│   └── settings/     # SettingsComponent + DefaultSettingsComponent
└── util/             # Shared utilities
```

## Platform Source Sets

| Source Set | Contents |
|------------|----------|
| `androidMain` | `Platform.android.kt`, `PlatformEngine.android.kt` (OkHttp) |
| `iosMain` | `Platform.ios.kt`, `PlatformEngine.ios.kt` (Darwin), `KoinIOS.kt` |
| `jvmMain` | `Platform.jvm.kt`, `PlatformEngine.jvm.kt` (CIO) |

## Conventions (This Module)

- **Expect/actual** for platform code: declare in `commonMain`, implement in `{platform}Main`.
- Components follow interface + `Default` impl pattern: `HomeComponent` → `DefaultHomeComponent`.
- UI state is a sealed interface inside the component file: `HomeComponent.HomeUiState`.
- Presentation components use `coroutineScope(lifecycle)` for lifecycle-scoped work.
- Navigation: root owns `ChildPages` (tabs), each tab owns `ChildStack` (push/pop).
- Use `pushNew` not `push` to avoid `@DelicateDecomposeApi`.
- Repositories: interface in `domain/repository/`, impl in `data/repository/`.
- All DI wiring in `di/Modules.kt` — never scatter Koin bindings.

## Anti-Patterns

- Don't put platform-specific code in `commonMain` — use expect/actual.
- Don't create components without a corresponding interface.
- Don't expose mutable state from components — only `StateFlow<UiState>`.
- Don't import from `composeApp` — dependency flows shared → composeApp, never reverse.
