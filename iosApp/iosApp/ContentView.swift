import SwiftUI
import Shared

struct RootView: View {
    let root: RootComponent

    @ObservedObject
    private var pagesObserver: ObservableValue<ChildPages<AnyObject, RootComponentTab>>

    init(root: RootComponent) {
        self.root = root
        self.pagesObserver = ObservableValue(root.pages)
    }

    var body: some View {
        let pages = pagesObserver.value
        let selectedIndex = Int(pages.selectedIndex)

        TabView(selection: Binding(
            get: { selectedIndex },
            set: { index in root.selectTab(index: Int32(index)) }
        )) {
            homeTabView(pages: pages)
                .tabItem {
                    Label("Home", systemImage: "house.fill")
                }
                .tag(0)

            settingsTabView(pages: pages)
                .tabItem {
                    Label("Settings", systemImage: "gearshape.fill")
                }
                .tag(1)
        }
    }

    @ViewBuilder
    private func homeTabView(pages: ChildPages<AnyObject, RootComponentTab>) -> some View {
        if let homeTab = pages.items.first?.instance as? RootComponentTab.Home {
            HomeTabView(component: homeTab.component)
        } else {
            Color.clear
        }
    }

    @ViewBuilder
    private func settingsTabView(pages: ChildPages<AnyObject, RootComponentTab>) -> some View {
        if pages.items.count > 1, let settingsTab = pages.items[1].instance as? RootComponentTab.Settings {
            SettingsView(component: settingsTab.component.component)
        } else {
            Color.clear
        }
    }
}

struct HomeTabView: View {
    let component: HomeTabComponent

    @ObservedObject
    private var stackObserver: ObservableValue<ChildStack<AnyObject, HomeTabComponentChild>>

    init(component: HomeTabComponent) {
        self.component = component
        self.stackObserver = ObservableValue(component.stack)
    }

    var body: some View {
        let child = stackObserver.value.active.instance

        switch child {
        case let homeChild as HomeTabComponentChild.Home:
            HomeView(component: homeChild.component)
        case let detailChild as HomeTabComponentChild.Detail:
            DetailView(component: detailChild.component)
        default:
            EmptyView()
        }
    }
}

struct HomeView: View {
    let component: HomeComponent

    @ObservedObject
    private var stateObserver: FlowObserver<HomeUiState>

    init(component: HomeComponent) {
        self.component = component
        self.stateObserver = FlowObserver(flow: component.uiState, initial: HomeUiState(message: "", platform: "", isLoading: false, error: nil))
    }

    var body: some View {
        let state = stateObserver.value

        VStack(spacing: 16) {
            if state.isLoading {
                ProgressView()
                    .progressViewStyle(.circular)
            } else if let error = state.error {
                Text(error)
                    .foregroundColor(.red)
                    .font(.body)
                Button("Retry") {
                    component.onLoadGreeting()
                }
            } else if !state.message.isEmpty {
                Text(state.message)
                    .font(.title)
                Text(state.platform)
                    .font(.body)
                    .foregroundColor(.secondary)

                Button("View Detail") {
                    component.onNavigateToDetail(itemId: "sample-item")
                }
                .padding(.top, 16)
            } else {
                Button("Load Greeting") {
                    component.onLoadGreeting()
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding()
    }
}

struct DetailView: View {
    let component: DetailComponent

    var body: some View {
        VStack(spacing: 16) {
            Text("Detail Screen")
                .font(.title)
            Text("Item: \(component.itemId)")
                .font(.body)
                .foregroundColor(.secondary)
            Button("Go Back") {
                component.onNavigateBack()
            }
            .padding(.top, 16)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding()
    }
}

struct SettingsView: View {
    let component: SettingsComponent

    var body: some View {
        VStack(spacing: 16) {
            Text("Settings")
                .font(.title)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .padding()
    }
}
