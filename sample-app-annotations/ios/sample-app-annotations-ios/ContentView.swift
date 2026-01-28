//
//  ContentView.swift
//  sample-app-annotations-ios
//
//  Created by Sergio Belda Galbis on 24/12/24.
//

import SwiftUI
import sample_app

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all) // Compose has own keyboard handler
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.mainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
