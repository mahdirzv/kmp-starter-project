# Lessons Learned

## Decompose Navigation

- **ChildPages for tabs, ChildStack for push/pop.** Using a single `ChildStack` with `bringToFront` for tab switching is wrong — it destroys/recreates tabs on every switch. `ChildPages` keeps both tabs alive.
- **Use `pushNew` not `push`.** `push` requires `@DelicateDecomposeApi`. `pushNew` is the stable API.
- **Use `lifecycle.doOnDestroy {}` from Essenty** instead of `lifecycle.subscribe(onDestroy = ...)`.

## Compose Multiplatform

- **Material Icons artifact doesn't exist as a standalone library.** Use the Gradle plugin accessor `compose.materialIconsExtended` which provides `androidx.compose.material.icons.Icons.Default.*`.
- **Icons are `Icons.Default.*` not `Icons.Filled.*`** in Compose Multiplatform context.

## KMP Platform Engines

- Android → OkHttp, iOS → Darwin, JVM/Server → CIO. Each needs its own source set with an `actual` implementation of `PlatformEngine`.

## iOS Interop

- `No such module 'Shared'` LSP errors in Swift files are expected without an Xcode build. The KMP framework must be compiled first. These are not real errors.
- SKIE handles flow/sealed class bridging but the iOS side still needs `ObservableValue` helpers for Decompose `Value<T>` → SwiftUI `@Published`.

## OpenCode / AGENTS.md

- **AGENTS.md should be committed to git** — it's project-level context shared with your team and AI agents.
- **Hierarchical AGENTS.md** via `/init-deep` — root for project-wide context, subdirectories for module-scoped conventions. Children should never repeat parent content.
- **Keep under 500 lines** — every line competes with actual code in the agent's context window.
- **Structure: Stack → Structure → WHERE TO LOOK → Conventions → Boundaries → Build commands.** This is the pattern that works across OpenCode, Codex, Cursor, Copilot.
- **Score directories before creating subdirectory AGENTS.md** — not every dir needs one. Only create for dirs with >8 score (file count, complexity, distinct domain).
- OpenCode reads `AGENTS.md` at project root + walks subdirectories. Falls back to `CLAUDE.md` if no `AGENTS.md` exists.
