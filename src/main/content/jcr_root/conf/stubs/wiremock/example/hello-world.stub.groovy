stubs.server.with {
    stubFor(get("/example/hello")
            .willReturn(ok("Hello! I am example stub.")))
}
