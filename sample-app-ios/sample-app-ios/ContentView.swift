//
//  ContentView.swift
//  sample-app-ios
//
//  Created by Sergio Belda Galbis on 23/12/24.
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
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
