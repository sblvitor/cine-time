# CineTime

<p align="center">
Um aplicativo Android Nativo de filmes e séries, desenvolvido em Kotlin, apoiado principalmente sobre a API do <a href="https://www.themoviedb.org/documentation/api">TheMovieDB (TMDB)</a>
e sobre o <a href="https://firebase.google.com/?hl=pt-br">Firebase</a>. Ele se baseia em Clean Architecture + MVVM, e busca utilizar as stacks do Android mais recentes, juntamente com as 
melhores práticas de desenvolvimento.
</p>

> Confira algumas das telas do aplicativo!

<p align="center">
<img src="art/pop_movies.jpeg" width="19%"/> <img src="art/top_rated_series.jpeg" width="19%"/> <img src="art/trending.jpeg" width="19%"/> <img src="art/my_lists.jpeg" width="19%"/> <img src="art/serie_details.jpeg" width="19%"/>
</p>

## Features
* 100% Kotlin
* MVVM + Clean Architecture
* Kotlin Coroutines + Flows
* Dependency Injection
* HTTP Client
* Firebase
* Componentes da Arquitetura Android
  * Navigation Component, etc
* Single Activity Pattern
* Material Design 3
* Lottie Animation

## Tech Stacks
As tech stacks utilizadas neste projeto são:
* [Retrofit](http://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/) - Http Clients usados para realizar operações sobre a API (request, cache, log, etc).
* [Firebase](https://firebase.google.com/?hl=pt-br) - Plataforma de desenvolvimento de aplicativos. Serve de auxílio no desenvolvimento do app.
  * [Autenticação](https://firebase.google.com/docs/auth?hl=pt-br) - Serviço de autenticação por Email/Senha (possível expansão).
  * [Cloud Firestore](https://firebase.google.com/docs/firestore?hl=pt-br) - Banco de dados na nuvem NoSQL flexível e escalonável.
  * [Storage](https://firebase.google.com/docs/storage?hl=pt-br) - Armazenamento de conteúdo gerado pelos usuários, como fotos ou videos (somente foto de perfil por enquanto).
* [Koin](https://insert-koin.io/) - Framework de injeção de dependência (em Kotlin puro).
* [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=pt-br) - Carrega e exibe páginas de conteúdo da rede. Dados são solicitados automaticamente à medida que o usuário rola para o final dos dados carregados.
* [Flow](https://developer.android.com/kotlin/flow?hl=pt-br) - Um stream de dados que pode ser computado de forma assíncrona.
  * [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow?hl=pt-br) - Fluxo observável com estado que emite atualizações de estado novas e atuais para os coletores.
* [Navigation Component](https://developer.android.com/guide/navigation?hl=pt-br) - Componente que auxilia a implementar as interações que permitem aos usuários navegar, entrar e sair de diferentes partes do conteúdo.
  * [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data?hl=pt-br#Safe-args) - Plug-in do Gradle que oferece type-safety ao navegar e passar dados entre destinos.
  * [Animações](https://developer.android.com/guide/navigation/navigation-animate-transitions?hl=pt-br) - Animações de transição entre destinos.
* [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=pt-br) - Substituto do 'SharedPreferences', é uma solução de armazenamento de dados (chave-valor). Usa corrotinas e fluxo do Kotlin para armazenar de modo assíncrono.
* [Glide](https://bumptech.github.io/glide/) - Biblioteca de carregamento de imagens, rápida e eficiente.
* [LottieAnimation](https://lottiefiles.com/) - Animações leves e escaláveis.
* [Splash Screen API](https://developer.android.com/develop/ui/views/launch/splash-screen) - Splash Screen mostrando ícone do app.
* [Material Design 3](https://m3.material.io/) - Sistema de design open source da Google. Foram utilizados os componentes, seguindo seus guidelines.

> Obs: Minímo SDK level 26

## Download
Vá até [Releases](https://github.com/sblvitor/cine-time/releases) para fazer o download da última versão do app.