package com.nataliya.moviedb.state


interface State {
    class Loading : State
    class Error : State
    class Loaded : State
}

